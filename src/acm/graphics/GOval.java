package acm.graphics;

import acm.graphics.interfaces.GFillable;
import acm.graphics.interfaces.GResizable;
import acm.graphics.interfaces.GScalable;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class GOval extends GObject implements GFillable, GResizable, GScalable {
    private double width;
    private double height;
    private boolean filled = false;
    private Color fillColor = color;

    public GOval(double width, double height) {
        this(0, 0, width, height);
    }

    public GOval(double x, double y, double width, double height) {
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
                g.fillOval((int) x, (int) y, (int) width, (int) height);
            }
            g.setColor(color);
            g.drawOval((int) x, (int) y, (int) width, (int) height);
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

    private Ellipse2D.Double getEllipse() {
        return new Ellipse2D.Double(x, y, width, height);
    }

    @Override
    public boolean contains(double x, double y) {
        return getEllipse().contains(x, y);
    }

    public boolean intersects(GRect rect) {
        return getEllipse().intersects(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
    }

    public boolean intersects(GOval oval) {
        double xScale = height / width;

        double radiusX = (width / 2) * xScale;
        double radiusY = height / 2;
        double centerX = x * xScale + radiusX;
        double centerY = y + radiusY;

        double ovalRadiusX = (oval.getWidth() / 2) * xScale;
        double ovalRadiusY = oval.getHeight() / 2;
        double ovalCenterX = oval.getX() * xScale + ovalRadiusX;
        double ovalCenterY = oval.getY() + ovalRadiusY;

        double distanceX = Math.abs(centerX - ovalCenterX);
        double distanceY = Math.abs(centerY - ovalCenterY);

        double distance = Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));
        double sin = distanceY / distance;
        double cos = distanceX / distance;

        double ovalRadius = Math.sqrt(Math.pow(ovalRadiusY * sin, 2) + Math.pow(ovalRadiusX * cos, 2));

        return distance <= ovalRadius + radiusX;
    }
}
