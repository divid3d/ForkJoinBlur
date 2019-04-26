import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

class Utils {

    enum ImageExtensions {
        jpeg,jpg,gif,tiff,tif,png
    }

    static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(i + 1).toLowerCase();
        }
        return ext;
    }

    private static Dimension getScaledDimension(Dimension imgSize, Dimension boundary) {

        int original_width = imgSize.width;
        int original_height = imgSize.height;
        int bound_width = boundary.width;
        int bound_height = boundary.height;
        int new_width = original_width;
        int new_height = original_height;

        if (original_width > bound_width) {
            new_width = bound_width;
            new_height = (new_width * original_height) / original_width;
        }

        if (new_height > bound_height) {
            new_height = bound_height;
            new_width = (new_height * original_width) / original_height;
        }

        return new Dimension(new_width, new_height);
    }

    static BufferedImage resizeImage(BufferedImage img, Dimension boundary) {

        Dimension scaledDimension = getScaledDimension(new Dimension(img.getWidth(), img.getHeight()),boundary);

        Image tmp = img.getScaledInstance((int)scaledDimension.getWidth(), (int)scaledDimension.getHeight(), Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage((int)scaledDimension.getWidth(), (int)scaledDimension.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }
}