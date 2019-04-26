import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

class ResultPanel extends JPanel {

    ResultPanel(BufferedImage source, ProcessedImage forkJoinProcessed, ProcessedImage directlyProcessed) {

        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(new ImageViewPanel(Utils.resizeImage(source, new Dimension(900, 700))), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        this.add(new ImageViewPanel(Utils.resizeImage(forkJoinProcessed.getImage(), new Dimension(900, 700))), gbc);

        gbc.insets = new Insets(2, 10, 2, 2);
        gbc.gridx = 0;
        gbc.gridy = 1;
        this.add(new JLabel("Width: " + forkJoinProcessed.getWidth() + " px"), gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        this.add(new JLabel("Height: " + forkJoinProcessed.getHeight() + " px"), gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        this.add(new JLabel("Array size: " + forkJoinProcessed.getArraySize()), gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        this.add(new JLabel("Threshold: " + forkJoinProcessed.getThreshold()), gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        this.add(new JLabel("Blur width: " + forkJoinProcessed.getBlurWidth() + " px"), gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        this.add(new JLabel("ForkJoin processing time: " + forkJoinProcessed.getProcessingTime() + " ms"), gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        this.add(new JLabel("Directly processing time: " + directlyProcessed.getProcessingTime() + " ms"), gbc);
    }
}
