package acm.karel.components;

import javax.swing.*;
import java.awt.*;

public class KarelErrorDialog extends JDialog {

    public KarelErrorDialog(JFrame parent, String message) {
        super(parent, "KarelErrorDialog", true);
        setSize(400, 200);
        setLocationRelativeTo(parent);

        JTextArea textArea = new JTextArea(message);
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        Color backgroundColor = new Color(255, 96, 96);
        getContentPane().setBackground(backgroundColor);
        textArea.setBackground(backgroundColor);

        add(textArea, BorderLayout.CENTER);
        setVisible(true);
    }

}
