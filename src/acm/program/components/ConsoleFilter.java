package acm.program.components;

import javax.swing.text.*;
import java.awt.*;

public class ConsoleFilter extends DocumentFilter {
    private static final Color TYPE_COLOR = Color.BLUE;

    public enum Filter {
        STRING, INT, DOUBLE, PRINT
    }

    public Filter filter = Filter.PRINT;
    public int editOffset = 0;

    @Override
    public void replace(FilterBypass fb, int offs, int length, String str, AttributeSet a) throws BadLocationException {
        if (offs >= editOffset) {
            String inputLine = fb.getDocument().getText(editOffset, fb.getDocument().getLength() - editOffset);
            inputLine = new StringBuilder(inputLine).insert(offs - editOffset, str).toString();

            if (canBeTyped(inputLine) && !str.equals("\n")) {
                StyleContext sc = StyleContext.getDefaultStyleContext();
                AttributeSet attributeSet = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, TYPE_COLOR);

                super.replace(fb, offs, length, str, attributeSet);
            }
        }
    }

    private boolean canBeTyped(String str) {
        return switch (filter) {
            case STRING -> true;
            case INT -> str.matches("[+-]?[0-9]{0,10}");
            case DOUBLE -> str.matches("[+-]?[0-9]*[.]?[0-9]*");
            case PRINT -> false;
        };
    }

    @Override
    public void remove(FilterBypass fb, int offs, int length) throws BadLocationException {
        if (offs >= editOffset) super.remove(fb, offs, length);
    }

    @Override
    public void insertString(FilterBypass fb, int offs, String str, AttributeSet a) throws BadLocationException {
        if (offs >= editOffset) super.insertString(fb, offs, str, a);
    }

}
