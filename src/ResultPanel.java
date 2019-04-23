import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ResultPanel extends JPanel {

    public ResultPanel(BufferedImage source, BufferedImage processed, int height, int width, int threshold, int arraySize, long processingTime) {

        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(new ImageViewPanel(source), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        this.add(new ImageViewPanel(processed), gbc);

        gbc.insets = new Insets(2, 10, 2, 2);
        gbc.gridx = 0;
        gbc.gridy = 1;
        this.add(new JLabel("Width: " + height + " px"), gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        this.add(new JLabel("Height: " + width + " px"), gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        this.add(new JLabel("Array size: " + arraySize), gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        this.add(new JLabel("Threshold: " + threshold), gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        this.add(new JLabel("Processing time: " + processingTime + " ms"), gbc);
        
    }
}
