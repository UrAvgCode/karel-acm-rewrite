package acm.graphics;

import java.awt.*;

public abstract class GObject {

    protected double x;
    protected double y;
    protected boolean visible;
    protected Color color;

    protected GObject() {
        this.x = 0;
        this.y = 0;
        this.visible = true;
        this.color = Color.BLACK;
    }

    public abstract void paint(Graphics2D g);

    @SuppressWarnings("CallToPrintStackTrace")
    public void pause(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setLocation(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void move(double dx, double dy) {
        this.x += dx;
        this.y += dy;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isVisible() {
        return this.visible;
    }

    public abstract boolean contains(double x, double y);

    public abstract Rectangle getBounds();

    public double getWidth() {
        return getBounds().getWidth();
    }

    public double getHeight() {
        return getBounds().getHeight();
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }
}
