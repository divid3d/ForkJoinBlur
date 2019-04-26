import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

class ImagesFrame extends JFrame {


    ImagesFrame(URL url, int threshold, int blurWidth) {
        super("Result");

        BufferedImage source = getImageFromUrl(url);

        if (source != null) {
            ProcessedImage processedForkJoin = ForkBlur.blur(source, threshold, blurWidth);
            ProcessedImage procesedDirectly = ForkBlur.blurDirectly(source, blurWidth);
            add(new ResultPanel(source, processedForkJoin, procesedDirectly));
        } else {
            JOptionPane.showMessageDialog(this, "Nie można pobrać obrazu", "Błąd", JOptionPane.ERROR_MESSAGE);
            this.dispose();
        }


        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);
    }

    ImagesFrame(String path, int threshold, int blurWidth) {
        super("Result");

        BufferedImage source = getImageFromUri(path);

        if (source != null) {
            ProcessedImage processedForkJoin = ForkBlur.blur(source, threshold, blurWidth);
            ProcessedImage procesedDirectly = ForkBlur.blurDirectly(source, blurWidth);
            add(new ResultPanel(source, processedForkJoin, procesedDirectly));
        } else {
            JOptionPane.showMessageDialog(this, "Nie można wczytać obrazu", "Błąd", JOptionPane.ERROR_MESSAGE);
            this.dispose();
        }

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);

    }

    private BufferedImage getImageFromUrl(URL url) {
        try {
            return ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private BufferedImage getImageFromUri(String path) {
        try {
            return ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


}
