package acm.karel.controlls;

import javax.swing.*;
import java.awt.*;
import java.util.Hashtable;

public class KarelStartControls extends JPanel {

    private final JButton startButton = new JButton("Start");
    private final JButton resetButton = new JButton("Reset");
    private final JButton loadButton = new JButton("Load World");
    private final JButton newButton = new JButton("New World");
    private final JButton editButton = new JButton("Edit World");
    private final JSlider speedSlider = new JSlider(0, 32, 16);

    public KarelStartControls(KarelControls listener) {
        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(layout);

        startButton.addActionListener(listener);
        resetButton.addActionListener(listener);
        loadButton.addActionListener(listener);
        newButton.addActionListener(listener);
        editButton.addActionListener(listener);
        speedSlider.addChangeListener(listener);

        speedSlider.setMajorTickSpacing(16);
        speedSlider.setMinorTickSpacing(2);
        speedSlider.setPaintTicks(true);
        speedSlider.setSnapToTicks(true);

        speedSlider.setPaintLabels(true);
        speedSlider.setLabelTable(new Hashtable<>() {
            {
                put(speedSlider.getMinimum(), new JLabel("Slow"));
                put(speedSlider.getMaximum(), new JLabel("Fast"));
            }
        });

        JComponent[] components = {startButton, resetButton, loadButton, newButton, editButton, speedSlider};
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

    public int getPauseLength() {
        int sliderValue = speedSlider.getMaximum() - speedSlider.getValue();
        return sliderValue * sliderValue;
    }

    public void setEnabled(boolean enabled) {
        startButton.setEnabled(enabled);
        resetButton.setEnabled(enabled);
        loadButton.setEnabled(enabled);
        newButton.setEnabled(enabled);
        editButton.setEnabled(enabled);
    }

}
