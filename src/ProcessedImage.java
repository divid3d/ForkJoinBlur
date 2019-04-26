import java.awt.image.BufferedImage;

class ProcessedImage {

    private BufferedImage image;
    private int width;
    private int height;
    private int arraySize;
    private long processingTime;
    private int threshold;
    private int blurWidth;

    ProcessedImage(BufferedImage image, int width, int height, int arraySize, int threshold, int blurWidth, long processingTime) {
        this.image = image;
        this.width = width;
        this.height = height;
        this.arraySize = arraySize;
        this.threshold = threshold;
        this.blurWidth = blurWidth;
        this.processingTime = processingTime;
    }

    BufferedImage getImage() {
        return image;
    }

    int getWidth() {
        return width;
    }

    int getHeight() {
        return height;
    }

    int getArraySize() {
        return arraySize;
    }

    long getProcessingTime() {
        return processingTime;
    }

    int getThreshold() {
        return threshold;
    }

    int getBlurWidth() {
        return blurWidth;
    }

    String getInfo() {
        return "Width:\t" + this.width + " px" + "\n" + "Height:\t" + this.height + " px" + "\n" + "Array size:\t" + this.arraySize + "\n" + "Threshold:\t" + this.threshold + "\n" + "Processing time:\t" + this.processingTime + " ms";
    }

}
