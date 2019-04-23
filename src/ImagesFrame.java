import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ImagesFrame extends JFrame {


    public ImagesFrame(URL url, int threshold) {
        super("Result");

        BufferedImage source = getImageFromUrl(url);

        if (source != null) {
            ProcessedImage processed = ForkBlur.blur(source, threshold);
            System.out.println(processed.getInfo());
            add(new ResultPanel(source, processed.getImage()));
        } else {
            JOptionPane.showMessageDialog(this, "Nie można pobrać obrazu", "Błąd", JOptionPane.ERROR_MESSAGE);
            this.dispose();
        }


        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);
    }

    public ImagesFrame(String path, int threshold) {
        super("Result");

        BufferedImage source = getImageFromUri(path);

        if (source != null) {
            ProcessedImage processed = ForkBlur.blur(source, threshold);
            System.out.println(processed.getInfo());
            add(new ResultPanel(source, processed.getImage()));
        } else {
            JOptionPane.showMessageDialog(this, "Nie można wczytać obrazu", "Błąd", JOptionPane.ERROR_MESSAGE);
        }

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);

    }

    public BufferedImage getImageFromUrl(URL url) {
        try {
            return ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public BufferedImage getImageFromUri(String path) {
        try {
            return ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
