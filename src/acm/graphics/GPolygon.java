package acm.graphics;

import acm.graphics.interfaces.GFillable;
import acm.graphics.interfaces.GScalable;

import java.awt.*;
import java.awt.geom.Point2D;

public class GPolygon extends GObject implements GFillable, GScalable {

    private final VertexList vertices = new VertexList();

    private double rotation = 0;
    private double xScale = 1;
    private double yScale = 1;

    private boolean filled = false;
    private Color fillColor = color;

    public GPolygon() {
        super();
    }

    public GPolygon(Point2D.Double[] vertices) {
        super();
        this.vertices.add(vertices);
    }

    @Override
    public void paint(Graphics2D g) {
        if (visible) {
            Polygon polygon = vertices.createPolygon(x, y, xScale, yScale, rotation);

            if (filled) {
                g.setColor(fillColor);
                g.fillPolygon(polygon);
            }
            g.setColor(color);
            g.drawPolygon(polygon);
        }
    }

    public void addVertex(double x, double y) {
        vertices.addVertex(x, y);
    }

    public void addEdge(double dx, double dy) {
        vertices.addEdge(dx, dy);
    }

    public void addPolarEdge(double radius, double angle) {
        vertices.addPolarEdge(radius, angle);
    }

    public void addArc(double arcWidth, double arcHeight, double start, double sweep) {
        vertices.addArc(arcWidth, arcHeight, start, sweep);
    }

    public void clear() {
        vertices.clear();
    }

    @Override
    public void scale(double scaleX, double scaleY) {
        xScale *= scaleX;
        yScale *= scaleY;
    }

    @Override
    public void scale(double scale) {
        scale(scale, scale);
    }

    public void rotate(double angle) {
        rotation += angle;
    }

    @Override
    public void setFilled(boolean filled) {
        this.filled = filled;
    }

    @Override
    public boolean isFilled() {
        return false;
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
        return vertices.contains(this.x, this.y, xScale, yScale, rotation, x, y);
    }

    @Override
    public Rectangle getBounds() {
        return vertices.getBounds(x, y, xScale, yScale, rotation);
    }
}
