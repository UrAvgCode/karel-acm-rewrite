package acm.program;

import acm.program.components.ConsolePane;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public abstract class ConsoleProgram extends Program {

    private final ConsolePane console;
    private final JScrollPane scroll;

    protected ConsoleProgram() {
        super();
        setPreferredSize(null);
        console = new ConsolePane();
        scroll = new JScrollPane(console);
        scroll.setBorder(new EmptyBorder(10, 10, 10, 10));
        scroll.setBackground(Color.WHITE);

        redirectOut();
        add(scroll);
        pack();
    }

    private void redirectOut() {
        OutputStream out = new OutputStream() {
            @Override
            public void write(int b) {
            }

            @Override
            public void write(byte[] b, int off, int len) {
                console.print(new String(b, off, len));
            }
        };
        PrintStream printStream = new PrintStream(out, true, StandardCharsets.UTF_8);
        System.setOut(printStream);
    }

    public final void setFont(String fontName) {
        setFont(Font.decode(fontName));
    }

    public final void setFont(Font font) {
        super.setFont(font);
        if (console != null) {
            console.setFont(font);
        }
    }

    @Override
    public void setSize(int width, int height) {
        console.setDimension(width, height);
        pack();
    }

    public final String readLine() {
        return console.readLine("");
    }

    public final String readLine(String prompt) {
        return console.readLine(prompt);
    }

    public final int readInt() {
        return readInt("");
    }

    public final int readInt(String prompt) {
        return console.readInt(prompt);
    }

    public final int readInt(String prompt, int min, int max) {
        int input = readInt(prompt);
        if (input < min || input > max) {
            console.printError("Value is outside the range [" + min + ", " + max + "]\n");
            input = readInt(prompt, min, max);
        }
        return input;
    }

    public final double readDouble() {
        return readDouble("");
    }

    public final double readDouble(String prompt) {
        return console.readDouble(prompt);
    }

    public final double readDouble(String prompt, double min, double max) {
        double input = readDouble(prompt);
        if (input < min || input > max) {
            console.printError("Value is outside the range [" + min + ", " + max + "]\n");
            input = readDouble(prompt, min, max);
        }
        return input;
    }

    public final boolean readBoolean() {
        return readBoolean("");
    }

    public final boolean readBoolean(String prompt) {
        return readBoolean(prompt, "true", "false");
    }

    public final boolean readBoolean(String prompt, String trueString, String falseString) {
        return console.readBoolean(prompt, trueString, falseString);
    }
}