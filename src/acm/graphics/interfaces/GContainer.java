package acm.graphics.interfaces;

import acm.graphics.GObject;

public interface GContainer {

    void add(GObject object);

    void add(GObject object, double x, double y);

    void remove(GObject object);

    void removeAll();

    int getElementCount();

    GObject getElement(int i);

    GObject getElementAt(double x, double y);
}