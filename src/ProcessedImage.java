import java.awt.image.BufferedImage;

public class ProcessedImage {

    private BufferedImage image;
    private int width;
    private int height;
    private int arraySize;
    private long processingTime;
    private int threshold;
    private int blurWidth;

    public ProcessedImage(BufferedImage image, int width, int height, int arraySize, int threshold, int blurWidth, long processingTime) {
        this.image = image;
        this.width = width;
        this.height = height;
        this.arraySize = arraySize;
        this.threshold = threshold;
        this.blurWidth = blurWidth;
        this.processingTime = processingTime;
    }

    public BufferedImage getImage() {
        return image;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getArraySize() {
        return arraySize;
    }

    public long getProcessingTime() {
        return processingTime;
    }

    public int getThreshold() {
        return threshold;
    }

    public int getBlurWidth() {
        return blurWidth;
    }

    public String getInfo() {
        return "Width:\t" + this.width + " px" + "\n" + "Height:\t" + this.height + " px" + "\n" + "Array size:\t" + this.arraySize + "\n" + "Threshold:\t" + this.threshold + "\n" + "Processing time:\t" + this.processingTime + " ms";
    }
}
