package acm.program;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Constructor;

public abstract class Program extends JFrame implements Runnable, MouseListener, MouseMotionListener, KeyListener, ActionListener {
    public static final String NORTH = "North";
    public static final String SOUTH = "South";
    public static final String EAST = "East";
    public static final String WEST = "West";
    public static final String CENTER = "Center";

    public static void main(String[] args) {
        String className = System.getProperty("sun.java.command");
        if (className == null) {
            try {
                throw new ClassNotFoundException("Cannot determine main class.");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            Class<?> clazz = Class.forName(className);
            Constructor<?> ctor = clazz.getConstructor();
            Object object = ctor.newInstance();
            Program program = (Program) object;
            program.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Program() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle(getClass().getSimpleName());
        setResizable(true);

        setPreferredSize(new Dimension(900, 600));
        pack();

        init();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void init() {
    }

    @Override
    public void run() {

    }

    public void start() {
        new Thread(this).start();
    }

    public void exit() {
        System.exit(0);
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public void pause(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void print(String s) {
        System.out.print(s);
    }

    public void print(int i) {
        System.out.print(i);
    }

    public void print(float f) {
        System.out.print(f);
    }

    public void print(double d) {
        System.out.print(d);
    }

    public void print(char c) {
        System.out.print(c);
    }

    public void print(boolean b) {
        System.out.print(b);
    }

    public void print(long l) {
        System.out.print(l);
    }

    public void print(Object o) {
        System.out.print(o);
    }

    public void println() {
        System.out.println();
    }

    public void println(String s) {
        System.out.println(s);
    }

    public void println(int i) {
        System.out.println(i);
    }

    public void println(float f) {
        System.out.println(f);
    }

    public void println(double d) {
        System.out.println(d);
    }

    public void println(char c) {
        System.out.println(c);
    }

    public void println(boolean b) {
        System.out.println(b);
    }

    public void println(long l) {
        System.out.println(l);
    }

    public void println(Object o) {
        System.out.println(o);
    }

    public void addMouseListeners() {
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public void addActionListeners() {
        addActionListeners(this);
    }

    public void addActionListeners(Container container) {
        for (Component component : container.getComponents()) {
            if (component instanceof JButton button) {
                button.addActionListener(this);
            } else if (component instanceof Container subContainer) {
                addActionListeners(subContainer);
            }
        }
    }

    public void addKeyListeners() {
        addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
