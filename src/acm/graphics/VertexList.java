package acm.graphics;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;

public class VertexList {

    private final ArrayList<Point2D.Double> vertices;
    private final Polygon polygon;
    private final double[] cache = {0, 0, 0, 0, 0};

    public VertexList() {
        vertices = new ArrayList<>();
        polygon = new Polygon();
    }

    public synchronized void add(Point2D.Double[] vertices) {
        this.vertices.addAll(Arrays.asList(vertices));
    }

    public synchronized void addVertex(double x, double y) {
        vertices.add(new Point2D.Double(x, y));
    }

    public synchronized void addEdge(double dx, double dy) {
        if (vertices.isEmpty()) {
            addVertex(dx, dy);
        } else {
            Point2D.Double lastPoint = vertices.get(vertices.size() - 1);
            addVertex(lastPoint.x + dx, lastPoint.y + dy);
        }
    }

    public synchronized void addPolarEdge(double radius, double angle) {
        double radiansAngle = Math.toRadians(angle);
        addEdge(radius * Math.cos(radiansAngle), radius * Math.sin(radiansAngle));
    }

    public synchronized void addArc(double arcWidth, double arcHeight, double start, double sweep) {
        double cx = 0;
        double cy = 0;
        if (!vertices.isEmpty()) {
            Point2D.Double lastPoint = vertices.get(vertices.size() - 1);
            cx = lastPoint.x;
            cy = lastPoint.y;
        }

        double aspectRatio = arcHeight / arcWidth;
        double radiusX = arcWidth / 2.0;
        double radiusY = arcHeight / 2.0;
        double x0 = cx - radiusX * Math.cos(Math.toRadians(start));
        double y0 = cy + radiusY * Math.sin(Math.toRadians(start));

        if (sweep > 359.99) sweep = 360;
        if (sweep < -359.99) sweep = -360;

        double dt = Math.atan2(1, Math.max(arcWidth, arcHeight));
        int nSteps = (int) (Math.toRadians(Math.abs(sweep)) / dt);
        dt = Math.toRadians(sweep) / nSteps;
        double theta = Math.toRadians(start);

        for (int i = 0; i < nSteps; i++) {
            theta += dt;
            double px = x0 + radiusX * Math.cos(theta);
            double py = y0 - radiusX * Math.sin(theta) * aspectRatio;
            addVertex(px, py);
        }
    }

    public synchronized void clear() {
        vertices.clear();
    }

    public Rectangle getBounds(double x, double y, double xScale, double yScale, double angle) {
        return createPolygon(x, y, xScale, yScale, angle).getBounds();
    }

    public boolean contains(double x, double y, double xScale, double yScale, double angle, double px, double py) {
        return createPolygon(x, y, xScale, yScale, angle).contains(px, py);
    }

    public synchronized Polygon createPolygon(double x, double y, double xScale, double yScale, double angle) {
        if (cache[0] == x && cache[1] == y && cache[2] == xScale && cache[3] == yScale && cache[4] == angle) {
            return polygon;
        } else {
            cache[0] = x;
            cache[1] = y;
            cache[2] = xScale;
            cache[3] = yScale;
            cache[4] = angle;
        }

        double radiansAngle = Math.toRadians(angle);
        double sin = Math.sin(radiansAngle);
        double cos = Math.cos(radiansAngle);
        polygon.reset();

        for (Point2D.Double vertex : vertices) {
            double xPos1 = vertex.x * xScale;
            double yPos1 = vertex.y * yScale;
            double xPos2 = xPos1 * cos - yPos1 * sin;
            double yPos2 = xPos1 * sin + yPos1 * cos;
            polygon.addPoint((int) (x + xPos2), (int) (y + yPos2));
        }

        return polygon;
    }
}
