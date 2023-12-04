package acm.program;

import acm.graphics.GObject;
import acm.program.components.GraphicsPanel;

import java.awt.*;

public abstract class GraphicsProgram extends Program {

    private final GraphicsPanel panel;

    protected GraphicsProgram() {
        super();
        setPreferredSize(null);

        panel = new GraphicsPanel();
        add(panel, CENTER);
        pack();

        panel.setBackground(Color.WHITE);
    }

    @Override
    public final void setSize(int width, int height) {
        panel.setSize(width, height);
        pack();
        setLocationRelativeTo(null);
    }

    @Override
    public final void setBackground(Color color) {
        super.setBackground(color);
        if (this.panel != null) panel.setBackground(color);
    }

    public final void add(GObject object) {
        panel.add(object);
    }

    public final void add(GObject object, double x, double y) {
        panel.add(object, x, y);
    }

    public final void remove(GObject object) {
        panel.remove(object);
    }

    public final void removeAll() {
        panel.removeAll();
    }

    public final int getElementCount() {
        return panel.getElementCount();
    }

    public final GObject getElement(int i) {
        return panel.getElement(i);
    }

    public final GObject getElementAt(double x, double y) {
        return panel.getElementAt(x, y);
    }
}