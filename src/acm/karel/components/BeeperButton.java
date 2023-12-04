package acm.karel.components;

import acm.karel.map.KarelMapPainter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class BeeperButton extends JToggleButton {

    int amount;

    public BeeperButton(int amount) {
        super("" + amount);
        this.amount = amount;
        Dimension dimension = new Dimension(50, 50);
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

        KarelMapPainter.drawBeeper(graphics2D, getWidth() / 2, getHeight() / 2, getHeight() - 5, amount, isSelected());
    }
}
