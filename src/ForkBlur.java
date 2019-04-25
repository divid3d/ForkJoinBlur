import java.awt.image.BufferedImage;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ForkBlur extends RecursiveAction {

    private int[] source;
    private int start;
    private int length;
    private int threshold;
    private int[] destination;
    private int blurWidth;

    private ForkBlur(int[] src, int start, int length, int[] dst, int threshold, int blurWidth) {
        source = src;
        this.start = start;
        this.length = length;
        destination = dst;
        this.threshold = threshold;
        this.blurWidth = blurWidth;
    }

    private void computeDirectly() {
        int sidePixels = (blurWidth - 1) / 2;
        for (int index = start; index < start + length; index++) {
            // Calculate average.
            float rt = 0, gt = 0, bt = 0;
            for (int mi = -sidePixels; mi <= sidePixels; mi++) {
                int mindex = Math.min(Math.max(mi + index, 0), source.length - 1);
                int pixel = source[mindex];
                rt += (float) ((pixel & 0x00ff0000) >> 16) / blurWidth;
                gt += (float) ((pixel & 0x0000ff00) >> 8) / blurWidth;
                bt += (float) ((pixel & 0x000000ff)) / blurWidth;
            }

            int dpixel = (0xff000000)
                    | (((int) rt) << 16)
                    | (((int) gt) << 8)
                    | (((int) bt));
            destination[index] = dpixel;
        }
    }

    @Override
    protected void compute() {
        if (length < threshold) {
            computeDirectly();
            return;
        }

        int split = length / 2;

        invokeAll(new ForkBlur(source, start, split, destination, threshold, blurWidth),
                new ForkBlur(source, start + split, length - split,
                        destination, threshold, blurWidth));
    }


    static ProcessedImage blur(BufferedImage srcImage, int sThreshold, int sBlurWidth) {
        int w = srcImage.getWidth();
        int h = srcImage.getHeight();

        int[] src = srcImage.getRGB(0, 0, w, h, null, 0, w);
        int[] dst = new int[src.length];

        ForkBlur fb = new ForkBlur(src, 0, src.length, dst, sThreshold, sBlurWidth);

        ForkJoinPool pool = new ForkJoinPool();

        long startTime = System.currentTimeMillis();
        pool.invoke(fb);
        long endTime = System.currentTimeMillis();

        BufferedImage dstImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        dstImage.setRGB(0, 0, w, h, dst, 0, w);

        return new ProcessedImage(dstImage, w, h, src.length, sThreshold, sBlurWidth, endTime - startTime);
    }

    static ProcessedImage blurDirectly(BufferedImage srcImage, int blurWidth) {
        return blur(srcImage, srcImage.getWidth() * srcImage.getHeight() + 1, blurWidth);
    }
}
