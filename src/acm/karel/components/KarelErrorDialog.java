package acm.karel.components;

import acm.program.components.ConsolePane;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class KarelErrorDialog extends JDialog {

    public KarelErrorDialog(JFrame parent, String message) {
        super(parent, "KarelErrorDialog", true);
        setSize(580, 250);
        setLocationRelativeTo(parent);

        JTextArea textArea = new JTextArea(message);
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));
        textArea.setEditable(false);

        Color backgroundColor = new Color(255, 96, 96);
        getContentPane().setBackground(backgroundColor);
        textArea.setBackground(backgroundColor);

        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);
        setVisible(true);
    }

}
