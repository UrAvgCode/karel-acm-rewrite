package acm.karel.map;

import java.awt.*;
import java.util.Arrays;

public class KarelBagIcon {
    private static final int[] X_POINTS = {48, 40, 47, 52, 55, 55, 54, 52, 48, 41, 23, 16, 12, 10, 9, 9, 12, 17, 24, 16};
    private static final int[] Y_POINTS = {0, 16, 22, 29, 37, 45, 52, 57, 61, 64, 64, 61, 57, 52, 45, 37, 29, 22, 16, 0};

    public static void draw(Graphics2D graphics2D, int x, int y, int size, int amount) {
        float scale = size / 64f;

        int[] xPoints = Arrays.stream(X_POINTS).asDoubleStream().mapToInt(value -> (int) (x + value * scale)).toArray();
        int[] yPoints = Arrays.stream(Y_POINTS).asDoubleStream().mapToInt(value -> (int) (y + value * scale)).toArray();

        graphics2D.setStroke(new BasicStroke(2));
        graphics2D.setColor(new Color(124, 95, 67));
        graphics2D.fillPolygon(xPoints, yPoints, xPoints.length);

        graphics2D.setColor(Color.BLACK);
        graphics2D.drawPolygon(xPoints, yPoints, xPoints.length);

        if (amount > 0) KarelMapPainter.drawBeeper(graphics2D, x + size / 2, y + (size * 2) / 3, size / 2, amount);
    }

}
