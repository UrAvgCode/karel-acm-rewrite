package acm.graphics;

import acm.graphics.interfaces.GScalable;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class GLine extends GObject implements GScalable {
    public static final BasicStroke DEFAULT_STROKE = new BasicStroke();

    private double dx;
    private double dy;
    private BasicStroke stroke;

    public GLine(double x, double y, double x2, double y2) {
        super();
        this.x = x;
        this.y = y;
        dx = x2 - x;
        dy = y2 - y;
        stroke = DEFAULT_STROKE;
    }

    @Override
    public void paint(Graphics2D g) {
        if (visible) {
            g.setColor(color);
            g.setStroke(stroke);
            g.drawLine((int) x, (int) y, (int) (x + dx), (int) (y + dy));
            g.setStroke(DEFAULT_STROKE);
        }
    }

    public void setStartPoint(double x, double y) {
        dx += this.x - x;
        dy += this.y - y;
        this.x = x;
        this.y = y;
    }

    public void setEndPoint(double x, double y) {
        this.dx = x - this.x;
        this.dy = y - this.y;
    }

    public void setStrokeWidth(int width) {
        stroke = new BasicStroke(width);
    }

    @Override
    public boolean contains(double x, double y) {
        return getLine().contains(x, y);
    }

    @Override
    public void scale(double scaleX, double scaleY) {
        dx *= scaleX;
        dy *= scaleY;
    }

    @Override
    public void scale(double scale) {
        scale(scale, scale);
    }

    private Line2D.Double getLine() {
        return new Line2D.Double(x, y, dx, dy);
    }

    @Override
    public Rectangle getBounds() {
        double x = Math.min(this.x, this.x + dx);
        double y = Math.min(this.y, this.y + dy);
        return new Rectangle((int) x, (int) y, (int) Math.abs(dx), (int) Math.abs(dy));
    }

    public Point2D.Double getStartPoint() {
        return new Point2D.Double(x, y);
    }

    public Point2D.Double getEndPoint() {
        return new Point2D.Double(x + dx, y + dy);
    }

    public double getLength() {
        return Math.sqrt(dx * dx + dy * dy);
    }

    public double getStrokeWidth() {
        return stroke.getLineWidth();
    }
}
