package acm.karel.components;

import javax.swing.*;

public class KarelErrorDialog extends JDialog {

    public KarelErrorDialog(JFrame parent) {
        super(parent, "KarelErrorDialog", true);
        setSize(400, 200);
        setLocationRelativeTo(parent);
        setVisible(true);
    }

}
