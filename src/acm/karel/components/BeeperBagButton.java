package acm.karel.components;

import acm.karel.map.KarelBagIcon;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class BeeperBagButton extends JButton implements MouseListener {
    private int amount;

    public BeeperBagButton() {
        super();
        setActionCommand("beeperBagButton");
        addMouseListener(this);
        amount = 0;

        Dimension dimension = new Dimension(80, 80);
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

        int margin = 5;

        KarelBagIcon.draw(graphics2D, margin, margin, getHeight() - 2 * margin, amount);
    }

    public void setAmount(int amount) {
        this.amount = amount;
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            fireActionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "beeperBagButtonRightClick"));
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
