package acm.graphics;

import acm.graphics.interfaces.GFillable;
import acm.graphics.interfaces.GResizable;
import acm.graphics.interfaces.GScalable;

import java.awt.*;

public class GRect extends GObject implements GFillable, GResizable, GScalable {
    private double width;
    private double height;
    private boolean filled = false;
    private Color fillColor = color;

    public GRect(double width, double height) {
        this(0, 0, width, height);
    }

    public GRect(double x, double y, double width, double height) {
        super();

        this.x = x;
        this.y = y;

        this.width = width;
        this.height = height;
    }

    @Override
    public void paint(Graphics2D g) {
        if (visible) {
            if (filled) {
                g.setColor(fillColor);
                g.fillRect((int) x, (int) y, (int) width, (int) height);
            }
            g.setColor(color);
            g.drawRect((int) x, (int) y, (int) width, (int) height);
        }
    }

    @Override
    public void setSize(double width, double height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void setBounds(double x1, double y1, double x2, double y2) {
        setLocation(x1, y1);
        setSize(x2 - x1, y2 - y1);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, (int) width, (int) height);
    }

    @Override
    public void scale(double scaleX, double scaleY) {
        this.width *= scaleX;
        this.height *= scaleY;
    }

    @Override
    public void scale(double scale) {
        scale(scale, scale);
    }

    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public double getHeight() {
        return height;
    }

    @Override
    public void setFilled(boolean filled) {
        this.filled = filled;
    }

    @Override
    public boolean isFilled() {
        return filled;
    }

    @Override
    public void setFillColor(Color color) {
        this.fillColor = color;
    }

    @Override
    public Color getFillColor() {
        return null;
    }

    @Override
    public boolean contains(double x, double y) {
        return getBounds().contains(x, y);
    }

    public boolean intersects(GRect rect) {
        double rx = rect.x;
        double ry = rect.y;

        double x2 = width + x;
        double y2 = height + y;
        double rx2 = rect.width + rx;
        double ry2 = rect.height + ry;

        return rx2 > x && ry2 > y && x2 > rx && y2 > ry;
    }

    public boolean intersects(GOval oval) {
        return oval.intersects(this);
    }

}
