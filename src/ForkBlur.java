import java.awt.image.BufferedImage;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ForkBlur extends RecursiveAction {

    private int[] mSource;
    private int mStart;
    private int mLength;
    private int mThreshold;
    private int[] mDestination;
    private int mBlurWidth;

    private ForkBlur(int[] src, int start, int length, int[] dst, int threshold, int blurWidth) {
        mSource = src;
        mStart = start;
        mLength = length;
        mDestination = dst;
        mThreshold = threshold;
        mBlurWidth = blurWidth;
    }

    protected  void computeDirectly() {
        int sidePixels = (mBlurWidth - 1) / 2;
        for (int index = mStart; index < mStart + mLength; index++) {
            // Calculate average.
            float rt = 0, gt = 0, bt = 0;
            for (int mi = -sidePixels; mi <= sidePixels; mi++) {
                int mindex = Math.min(Math.max(mi + index, 0), mSource.length - 1);
                int pixel = mSource[mindex];
                rt += (float) ((pixel & 0x00ff0000) >> 16) / mBlurWidth;
                gt += (float) ((pixel & 0x0000ff00) >> 8) / mBlurWidth;
                bt += (float) ((pixel & 0x000000ff)) / mBlurWidth;
            }

            int dpixel = (0xff000000)
                    | (((int) rt) << 16)
                    | (((int) gt) << 8)
                    | (((int) bt));
            mDestination[index] = dpixel;
        }
    }
    //protected static int sThreshold = 100000;

    @Override
    protected void compute() {
        if (mLength < mThreshold) {
            computeDirectly();
            return;
        }

        int split = mLength / 2;

        invokeAll(new ForkBlur(mSource, mStart, split, mDestination, mThreshold,mBlurWidth),
                new ForkBlur(mSource, mStart + split, mLength - split,
                        mDestination, mThreshold,mBlurWidth));
    }


    public static ProcessedImage blurParallel(BufferedImage srcImage, int sThreshold, int sBlurWidth) {
        int w = srcImage.getWidth();
        int h = srcImage.getHeight();

        int[] src = srcImage.getRGB(0, 0, w, h, null, 0, w);
        int[] dst = new int[src.length];

        ForkBlur fb = new ForkBlur(src, 0, src.length, dst, sThreshold,sBlurWidth);

        ForkJoinPool pool = new ForkJoinPool();


        long startTime = System.currentTimeMillis();
        pool.invoke(fb);
        long endTime = System.currentTimeMillis();


        BufferedImage dstImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        dstImage.setRGB(0, 0, w, h, dst, 0, w);

        return new ProcessedImage(dstImage, w, h, src.length, sThreshold,sBlurWidth, endTime - startTime);
    }

    public static void blurSingle(BufferedImage srcImage,int blurWidth) {
        blurParallel(srcImage, srcImage.getWidth() * srcImage.getHeight(),blurWidth);
    }
}
