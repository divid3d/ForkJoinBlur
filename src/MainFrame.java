import javax.swing.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        super("Image blur");
        add(new ImageInputPanel());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);

    }
}
