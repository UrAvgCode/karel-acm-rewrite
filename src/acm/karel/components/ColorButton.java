package acm.karel.components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ColorButton extends JToggleButton {

    private final Color color;

    public ColorButton(Color color) {
        super("");
        if (color == null) {
            setActionCommand("noColor");
        } else {
            setActionCommand(Integer.toString(color.getRGB()));
        }

        this.color = color;
        Dimension dimension = new Dimension(25, 25);
        setPreferredSize(dimension);
        setMaximumSize(dimension);
        setMinimumSize(dimension);

        setBorder(new EmptyBorder(0, 0, 0, 0));
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;
        //graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        graphics2D.setColor(getBackground());
        graphics2D.fillRect(0, 0, getWidth(), getHeight());

        int margin = 5;
        int width = getWidth() - 2 * margin;
        int height = getHeight() - 2 * margin;

        if (color == null || color.getRGB() == 0) {
            graphics2D.setColor(Color.WHITE);
        } else {
            graphics2D.setColor(color);
        }

        graphics2D.fillRect(margin, margin, width, height);
        if (isSelected()) {
            graphics2D.setColor(Color.BLACK);
            graphics2D.setStroke(new BasicStroke(2));
            graphics2D.drawRect(margin, margin, width, height);
        } else {
            graphics2D.setColor(Color.BLACK);
            graphics2D.setStroke(new BasicStroke(1));
            graphics2D.drawRect(margin, margin, width, height);
        }

        if (color == null) {
            graphics2D.setColor(Color.BLACK);
            graphics2D.drawLine(margin, margin, margin + width, margin + height);
            graphics2D.drawLine(margin + width, margin, margin, margin + height);
        } else if (color.getRGB() == 0) {
            graphics2D.setColor(Color.BLACK);

            int x = getWidth() / 2;
            int y = getWidth() / 2;
            int crossSize = 3;

            graphics2D.drawLine(x, y + crossSize, x, y - crossSize);
            graphics2D.drawLine(x + crossSize, y, x - crossSize, y);
        }

    }
}
