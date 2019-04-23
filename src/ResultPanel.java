import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ResultPanel extends JPanel {

    public ResultPanel(BufferedImage source, BufferedImage processed){

        this.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        GridLayout gridLayout = new GridLayout(0,2);
        gridLayout.setVgap(10);
        gridLayout.setHgap(10);

        this.setLayout(gridLayout);
        this.add(new ImageViewPanel(source));
        this.add(new ImageViewPanel(processed));
    }
}
