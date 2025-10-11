package utils;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

// helper class để lắng nghe sự thay đổi của JTextField hoặc bất kỳ Document nào trong Swing.
public class SimpleDocumentListener implements DocumentListener {
    private final Runnable onChange;

    public SimpleDocumentListener(Runnable onChange) {
        this.onChange = onChange;
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        onChange.run();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        onChange.run();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        onChange.run();
    }
}
