package acm.karel.map;

import acm.karel.Direction;

import java.awt.*;
import java.awt.geom.Path2D;

public class KarelMapIcon {
    private static final Color KAREL_COLOR = new Color(230, 230, 230);

    private static final int[] X_POINTS_FOOT = new int[]{-25, -15, -15, -20, -20, -25};
    private static final int[] Y_POINTS_FOOT = new int[]{3, 3, 8, 8, 16, 16};

    private static final int[] X_POINTS_FOOT2 = new int[]{4, 9, 9, 17, 17, 4};
    private static final int[] Y_POINTS_FOOT2 = new int[]{22, 22, 27, 27, 32, 32};

    private static final int[] X_POINTS_BODY = new int[]{-15, 15, 25, 25, -8, -15};
    private static final int[] Y_POINTS_BODY = new int[]{-32, -32, -22, 22, 22, 15};

    private static final int[] X_POINTS_WINDOW = new int[]{-6, 14, 14, -6};
    private static final int[] Y_POINTS_WINDOW = new int[]{-24, -24, 3, 3};

    private static final int[] X_POINTS_LINE = new int[]{4, 14};
    private static final int[] Y_POINTS_LINE = new int[]{12, 12};


    public static void draw(Graphics2D g, int x, int y, int size, Direction direction) {
        float scale = size / 64f;

        Polygon body = createPolygon(X_POINTS_BODY, Y_POINTS_BODY, x, y, scale, direction);
        Polygon window = createPolygon(X_POINTS_WINDOW, Y_POINTS_WINDOW, x, y, scale, direction);
        Polygon line = createPolygon(X_POINTS_LINE, Y_POINTS_LINE, x, y, scale, direction);
        Polygon foot = createPolygon(X_POINTS_FOOT, Y_POINTS_FOOT, x, y, scale, direction);
        Polygon foot2 = createPolygon(X_POINTS_FOOT2, Y_POINTS_FOOT2, x, y, scale, direction);

        final Path2D path = new Path2D.Double(Path2D.WIND_EVEN_ODD);
        path.append(body, false);
        path.append(window, false);

        g.setColor(KAREL_COLOR);
        g.fill(path);

        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(1));
        g.fillPolygon(foot);
        g.fillPolygon(foot2);
        g.draw(path);
        g.drawPolygon(line);
    }

    private static Polygon createPolygon(int[] xPoints, int[] yPoints, int x, int y, float scale, Direction direction) {
        int sin = direction.getSin();
        int cos = direction.getCos();

        Polygon polygon = new Polygon();
        for (int i = 0; i < xPoints.length; i++) {
            float xPos1 = xPoints[i] * scale;
            float yPos1 = yPoints[i] * scale;
            float xPos2 = xPos1 * cos - yPos1 * sin;
            float yPos2 = xPos1 * sin + yPos1 * cos;
            polygon.addPoint((int) (x + xPos2), (int) (y + yPos2));
        }

        return polygon;
    }
}
