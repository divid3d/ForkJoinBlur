import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

class ImagesFrame extends JFrame {


    ImagesFrame(URL url, int threshold, int blurWidth) {
        super("Result");

        BufferedImage source = getImageFromUrl(url);

        if (source != null) {
            Dimension resizedDimension = Utils.getScaledDimension(new Dimension(source.getWidth(), source.getHeight()), new Dimension(900, 700));
            ProcessedImage processedForkJoin = ForkBlur.blur(source, threshold, blurWidth);
            ProcessedImage procesedDirectly = ForkBlur.blurDirectly(source, blurWidth);
            BufferedImage resizedSource = resize(source, (int) resizedDimension.getHeight(), (int) resizedDimension.getWidth());
            BufferedImage resizedProcesed = resize(processedForkJoin.getImage(), (int) resizedDimension.getHeight(), (int) resizedDimension.getWidth());
            System.out.println(processedForkJoin.getInfo());
            add(new ResultPanel(resizedSource, resizedProcesed, processedForkJoin.getHeight(), processedForkJoin.getWidth(), processedForkJoin.getArraySize(), processedForkJoin.getThreshold(), processedForkJoin.getBlurWidth(), processedForkJoin.getProcessingTime(), procesedDirectly.getProcessingTime()));
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
            Dimension resizedDimension = Utils.getScaledDimension(new Dimension(source.getWidth(), source.getHeight()), new Dimension(900, 700));
            ProcessedImage processedForkJoin = ForkBlur.blur(source, threshold, blurWidth);
            ProcessedImage procesedDirectly = ForkBlur.blurDirectly(source, blurWidth);
            BufferedImage resizedSource = resize(source, (int) resizedDimension.getHeight(), (int) resizedDimension.getWidth());
            BufferedImage resizedProcessed = resize(processedForkJoin.getImage(), (int) resizedDimension.getHeight(), (int) resizedDimension.getWidth());
            System.out.println(processedForkJoin.getInfo());
            add(new ResultPanel(resizedSource, resizedProcessed, processedForkJoin.getHeight(), processedForkJoin.getWidth(), processedForkJoin.getArraySize(), processedForkJoin.getThreshold(), processedForkJoin.getBlurWidth(), processedForkJoin.getProcessingTime(), procesedDirectly.getProcessingTime()));
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

    private BufferedImage resize(BufferedImage img, int height, int width) {
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }

}
