package acm.karel.controlls;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class KarelNewWorldControls extends JPanel {

    private final JButton backButton = new JButton("Cancel");

    public KarelNewWorldControls(ActionListener listener) {
        backButton.addActionListener(listener);

        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(layout);

        JComponent[] components = {backButton};
        add(Box.createVerticalGlue());
        for (JComponent component : components) {
            component.setMaximumSize(new Dimension(150, 50));
            component.setAlignmentX(Component.CENTER_ALIGNMENT);
            component.setFont(new Font("Arial", Font.PLAIN, 14));
            component.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            add(component);
            add(Box.createRigidArea(new Dimension(0, 20)));
        }
        add(Box.createVerticalGlue());
    }

}
