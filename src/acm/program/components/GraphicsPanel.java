package acm.program.components;

import acm.graphics.GObject;
import acm.graphics.interfaces.GContainer;

import javax.swing.*;
import java.awt.*;
import java.util.ListIterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class GraphicsPanel extends JPanel implements Runnable, GContainer {
    private final Dimension dimension;
    private final CopyOnWriteArrayList<GObject> objects;

    public GraphicsPanel() {
        super(true);

        dimension = new Dimension(900, 600);
        setPreferredSize(dimension);

        objects = new CopyOnWriteArrayList<>();

        new Thread(this).start();
    }

    @Override
    @SuppressWarnings("InfiniteLoopStatement")
    public void run() {
        int fps = 60;
        int frameTime = 1000 / fps;
        long lastTime = System.currentTimeMillis();

        while (true) {
            long time = System.currentTimeMillis();
            long deltaTime = time - lastTime;

            if (deltaTime >= frameTime) {
                lastTime = time;
                repaint();
            }
        }
    }

    public void setSize(int width, int height) {
        dimension.setSize(width, height);
    }

    @Override
    public void add(GObject object) {
        if (!objects.contains(object)) objects.add(object);
    }

    @Override
    public void add(GObject object, double x, double y) {
        object.setLocation(x, y);
        add(object);
    }

    @Override
    public void remove(GObject object) {
        objects.remove(object);
    }

    @Override
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
        for (ListIterator<GObject> iterator = objects.listIterator(objects.size()); iterator.hasPrevious(); ) {
            final GObject object = iterator.previous();
            if (object.contains(x, y))
                return object;
        }
        return null;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (System.getProperty("os.name").equals("Linux"))
            Toolkit.getDefaultToolkit().sync();

        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (GObject object : objects) {
            if (object.isVisible())
                object.paint(graphics2D);
        }

    }
}
