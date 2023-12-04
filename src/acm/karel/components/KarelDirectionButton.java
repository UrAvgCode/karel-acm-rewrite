package acm.karel.components;

import acm.karel.Direction;
import acm.karel.map.KarelMapIcon;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class KarelDirectionButton extends JButton {

    private final Direction direction;

    public KarelDirectionButton(Direction direction) {
        super(direction.toString());
        this.direction = direction;

        Dimension dimension = new Dimension(40, 40);
        setPreferredSize(dimension);
        setMaximumSize(dimension);
        setMinimumSize(dimension);

        setBackground(new Color(240, 240, 240));
        setBorder(new EmptyBorder(0, 0, 0, 0));
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        graphics2D.setColor(getBackground());
        graphics2D.fillRect(0, 0, getWidth(), getHeight());

        KarelMapIcon.draw(graphics2D, getWidth() / 2, getHeight() / 2, getHeight() - 5, direction);
    }
}
