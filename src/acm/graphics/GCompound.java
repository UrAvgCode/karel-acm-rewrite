package acm.graphics;

import acm.graphics.interfaces.GContainer;

import java.awt.*;
import java.util.ArrayList;

public abstract class GCompound extends GObject implements GContainer {
    private final ArrayList<GObject> objects = new ArrayList<>();

    public GCompound() {
        super();
    }

    @Override
    public void paint(Graphics2D g) {
        if (visible) {
            g = (Graphics2D) g.create();
            g.translate(x, y);
            for (GObject object : objects) {
                object.paint(g);
            }
        }
    }

    public void add(GObject object) {
        objects.add(object);
    }

    public void add(GObject object, double x, double y) {
        object.setLocation(x, y);
        objects.add(object);
    }

    public void remove(GObject object) {
        objects.remove(object);
    }

    public void removeAll() {
        objects.clear();
    }

    @Override
    public int getElementCount() {
        return objects.size();
    }

    @Override
    public GObject getElement(int i) {
        return objects.get(i);
    }

    @Override
    public GObject getElementAt(double x, double y) {
        for (GObject object : objects) {
            if (object.contains(x - this.x, y - this.y)) {
                return object;
            }
        }
        return null;
    }

    @Override
    public boolean contains(double x, double y) {
        for (GObject object : objects) {
            if (object.contains(x - this.x, y - this.y)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Rectangle getBounds() {
        double boundsX = 0;
        double boundsY = 0;
        double boundsX2 = 0;
        double boundsY2 = 0;

        for (GObject object : objects) {
            Rectangle objectBounds = object.getBounds();
            if (objectBounds.getX() < boundsX) {
                boundsX = objectBounds.getX();
            }
            if (objectBounds.getY() < boundsY) {
                boundsY = objectBounds.getY();
            }
            if (objectBounds.getX() + objectBounds.getWidth() > boundsX2) {
                boundsX2 = objectBounds.getX() + objectBounds.getWidth();
            }
            if (objectBounds.getY() + objectBounds.getHeight() > boundsY2) {
                boundsY2 = objectBounds.getY() + objectBounds.getHeight();
            }
        }

        double boundsWidth = boundsX2 - boundsX;
        double boundsHeight = boundsY2 - boundsY;
        boundsX += x;
        boundsY += y;

        return new Rectangle((int) boundsX, (int) boundsY, (int) boundsWidth, (int) boundsHeight);
    }

    public double getHeight() {
        return getBounds().getHeight();
    }

    public double getWidth() {
        return getBounds().getWidth();
    }
}
