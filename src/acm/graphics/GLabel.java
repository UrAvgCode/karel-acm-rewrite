package acm.graphics;

import java.awt.*;
import java.awt.font.FontRenderContext;

public class GLabel extends GObject {
    public static final Font DEFAULT_FONT = new Font("Default", Font.PLAIN, 12);

    private String text;
    private Font font;

    public GLabel(String text) {
        this(text, 0, 0);
    }

    public GLabel(String text, double x, double y) {
        super();
        this.text = text;
        this.font = DEFAULT_FONT;

        this.x = x;
        this.y = y;
    }

    @Override
    public void paint(Graphics2D g) {
        if (visible) {
            g.setColor(color);
            g.setFont(font);
            g.drawString(text, (int) x, (int) y);
        }
    }

    public void setLabel(String text) {
        this.text = text;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public void setFont(String fontName) {
        font = Font.decode(fontName);
    }

    @Override
    public boolean contains(double x, double y) {
        return getBounds().contains(x, y);
    }

    @Override
    public Rectangle getBounds() {
        FontRenderContext frc = new FontRenderContext(font.getTransform(), true, true);
        Rectangle bounds = font.getStringBounds(text, frc).getBounds();
        bounds.x += (int) x;
        bounds.y += (int) y;
        return bounds;
    }

    public double getAscent() {
        return y - getBounds().getY();
    }

    public double getWidth() {
        return getBounds().getWidth();
    }

    public double getHeight() {
        return getBounds().getHeight();
    }

}
