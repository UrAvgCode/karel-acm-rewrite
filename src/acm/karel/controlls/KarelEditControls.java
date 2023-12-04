package acm.karel.controlls;

import acm.karel.Direction;
import acm.karel.Karel;
import acm.karel.components.BeeperBagButton;
import acm.karel.components.BeeperButton;
import acm.karel.components.ColorButton;
import acm.karel.components.KarelDirectionButton;

import javax.swing.*;
import java.awt.*;

public class KarelEditControls extends JPanel {

    private final JButton cancelButton = new JButton("Cancel");
    private final JButton saveButton = new JButton("Save");
    private final JColorChooser colorChooser = new JColorChooser();
    private final BeeperBagButton beeperBagButton;

    public KarelEditControls(KarelControls listener) {
        setBackground(new Color(240, 240, 240));

        cancelButton.addActionListener(listener);
        saveButton.addActionListener(listener);

        colorChooser.setPreviewPanel(new JPanel());

        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(layout);

        JComponent[] components = {saveButton, cancelButton};
        add(Box.createVerticalGlue());
        for (JComponent component : components) {
            component.setMaximumSize(new Dimension(150, 50));
            component.setAlignmentX(Component.CENTER_ALIGNMENT);
            component.setFont(new Font("Arial", Font.PLAIN, 14));
        }

        beeperBagButton = new BeeperBagButton();
        beeperBagButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        beeperBagButton.addActionListener(listener);

        add(beeperBagButton);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(createBeeperButtonPanel(listener));
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(createKarelButtonPanel(listener));
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(createColorButtonPanel(listener));
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(saveButton);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(cancelButton);
        add(Box.createRigidArea(new Dimension(0, 20)));

        add(Box.createVerticalGlue());
    }

    public void setBeeperBagAmount(int amount) {
        beeperBagButton.setAmount(amount);
    }

    private JPanel createBeeperButtonPanel(KarelControls listener) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        ButtonGroup buttonGroup = new ButtonGroup();
        BeeperButton[] buttons = {new BeeperButton(1), new BeeperButton(10), new BeeperButton(Karel.INFINITE)};
        buttons[0].setSelected(true);
        for (BeeperButton button : buttons) {
            button.addActionListener(listener);
            buttonGroup.add(button);
            panel.add(button);
        }
        return panel;
    }

    private JPanel createKarelButtonPanel(KarelControls listener) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        KarelDirectionButton[] buttons = {
                new KarelDirectionButton(Direction.NORTH),
                new KarelDirectionButton(Direction.EAST),
                new KarelDirectionButton(Direction.SOUTH),
                new KarelDirectionButton(Direction.WEST)
        };
        for (KarelDirectionButton button : buttons) {
            button.addActionListener(listener);
            panel.add(button);
        }
        return panel;
    }

    private JPanel createColorButtonPanel(KarelControls listener) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 5));
        Dimension dimension = new Dimension(150, 90);
        panel.setMaximumSize(dimension);
        panel.setPreferredSize(dimension);
        ButtonGroup buttonGroup = new ButtonGroup();
        ColorButton[] buttons = {
                new ColorButton(Color.BLACK),
                new ColorButton(Color.DARK_GRAY),
                new ColorButton(Color.GRAY),
                new ColorButton(Color.LIGHT_GRAY),
                new ColorButton(Color.WHITE),
                new ColorButton(Color.RED),
                new ColorButton(Color.PINK),
                new ColorButton(Color.ORANGE),
                new ColorButton(Color.YELLOW),
                new ColorButton(Color.GREEN),
                new ColorButton(Color.CYAN),
                new ColorButton(Color.BLUE),
                new ColorButton(Color.MAGENTA),
                new ColorButton(new Color(0, 0, 0, 0)),
                new ColorButton(null)
        };
        for (ColorButton button : buttons) {
            button.addActionListener(listener);
            buttonGroup.add(button);
            panel.add(button);
        }
        return panel;
    }
}
