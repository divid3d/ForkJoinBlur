import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;


public class ImageViewPanel extends JPanel {

    private BufferedImage image;

    ImageViewPanel(BufferedImage image) {
        super();
        this.image = image;
        Dimension dimension = new Dimension(image.getWidth(), image.getHeight());
        setPreferredSize(dimension);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(image, 0, 0, this);
    }
}

