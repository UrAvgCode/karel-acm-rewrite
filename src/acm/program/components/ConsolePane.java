package acm.program.components;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ConsolePane extends JTextPane implements KeyListener {
    private static final Color PRINT_COLOR = Color.BLACK;
    private static final Color ERROR_COLOR = Color.RED;

    private final Dimension dimension;
    private final StyledDocument document = getStyledDocument();
    private final ConsoleFilter filter = new ConsoleFilter();
    private boolean readingLine = false;

    public ConsolePane() {
        super();
        dimension = new Dimension(700, 800);
        setPreferredSize(dimension);
        setBackground(Color.WHITE);
        setFont(new Font("Monospaced", Font.PLAIN, 25));

        AbstractDocument doc = (AbstractDocument) getDocument();
        doc.setDocumentFilter(filter);

        addKeyListener(this);
    }

    public void setDimension(int width, int height) {
        dimension.setSize(width, height);
    }

    public void print(String s) {
        print(s, PRINT_COLOR);
    }

    public void printError(String s) {
        print(s, ERROR_COLOR);
    }

    public void print(String s, Color color) {
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet attributes = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, color);
        try {
            document.insertString(document.getLength(), s, attributes);
            filter.editOffset = document.getLength();
            setCaretPosition(document.getLength());
        } catch (BadLocationException e) {
            throw new RuntimeException(e);
        }
    }

    private String read(String prompt) {
        print(prompt);
        int editOffset = document.getLength();
        readingLine = true;
        synchronized (ConsolePane.class) {
            try {
                ConsolePane.class.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return getText().substring(editOffset, getText().length() - 1);
    }

    public String readLine(String prompt) {
        filter.filter = ConsoleFilter.Filter.STRING;
        return read(prompt);
    }

    public int readInt(String prompt) {
        filter.filter = ConsoleFilter.Filter.INT;
        try {
            return Integer.parseInt(read(prompt));
        } catch (NumberFormatException e) {
            printError("Integer not valid. Try again\n");
            return readInt(prompt);
        }
    }

    public double readDouble(String prompt) {
        filter.filter = ConsoleFilter.Filter.DOUBLE;
        try {
            return Double.parseDouble(read(prompt));
        } catch (NumberFormatException e) {
            printError("Double not valid. Try again\n");
            return readDouble(prompt);
        }
    }

    public boolean readBoolean(String prompt, String trueString, String falseString) {
        String input = readLine(prompt);
        if (input.equalsIgnoreCase(trueString)) {
            return true;
        } else if (input.equalsIgnoreCase(falseString)) {
            return false;
        } else {
            printError("Boolean not valid. Type " + trueString + " or " + falseString + "\n");
            return readBoolean(prompt, trueString, falseString);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (readingLine) {
            if (getCaretPosition() < filter.editOffset) {
                setCaretPosition(document.getLength());
            }

            if (e.getKeyChar() == '\n') {
                print("\n");
                filter.filter = ConsoleFilter.Filter.PRINT;
                readingLine = false;
                synchronized (ConsolePane.class) {
                    ConsolePane.class.notify();
                }
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}