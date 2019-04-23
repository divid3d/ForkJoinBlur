import javax.swing.*;
import java.awt.event.KeyEvent;

public class JNumberTextField extends JTextField {
    private static final long serialVersionUID = 1L;

    @Override
    public void processKeyEvent(KeyEvent ev) {
        if (Character.isDigit(ev.getKeyChar()) || ev.getKeyChar() == '\b') {
            super.processKeyEvent(ev);
        }
        ev.consume();
        return;
    }


    public Long getNumber() {
        Long result = null;
        String text = getText();
        if (text != null && !"".equals(text)) {
            result = Long.valueOf(text);
        }
        return result;
    }
}