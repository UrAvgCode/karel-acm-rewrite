package acm.karel.map;

import acm.karel.Karel;

import java.awt.*;

public class KarelMapPainter {
    public static void drawMap(Graphics2D graphics2D, int x, int y, int cellSize, KarelMapData map) {
        int width = cellSize * map.width;
        int height = cellSize * map.height;

        drawBackground(graphics2D, x, y, width, height);
        drawCrosses(graphics2D, x, y, cellSize, map);
        drawColors(graphics2D, x, y, cellSize, map);
        drawGrid(graphics2D, x, y, cellSize, map);
        drawNumbers(graphics2D, x, y, cellSize, map);
        drawBorder(graphics2D, x, y, width, height);

        drawAllBeepers(graphics2D, x, y, cellSize, map);
        drawWalls(graphics2D, x, y, cellSize, map);
        drawKarel(graphics2D, x, y, cellSize, map);
    }

    private static void drawBackground(Graphics2D graphics2D, int x, int y, int width, int height) {
        graphics2D.setColor(Color.WHITE);
        graphics2D.fillRect(x, y, width, height);
    }

    private static void drawBorder(Graphics2D graphics2D, int x, int y, int width, int height) {
        graphics2D.setColor(Color.BLACK);
        graphics2D.setStroke(new BasicStroke(2));
        graphics2D.drawRect(x, y, width, height);
    }

    private static void drawGrid(Graphics2D graphics2D, int mapX, int mapY, int cellSize, KarelMapData map) {
        graphics2D.setColor(new Color(0, 0, 0, 50));
        BasicStroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{3}, 0);
        graphics2D.setStroke(dashed);

        for (int cellX = 0; cellX < map.width; cellX++)
            graphics2D.drawLine(mapX + cellX * cellSize, mapY, mapX + cellX * cellSize, mapY + cellSize * map.height);
        for (int cellY = 0; cellY < map.height; cellY++)
            graphics2D.drawLine(mapX, mapY + cellY * cellSize, mapX + cellSize * map.width, mapY + cellY * cellSize);
    }

    private static void drawCrosses(Graphics2D graphics2D, int mapX, int mapY, int cellSize, KarelMapData map) {
        graphics2D.setColor(Color.DARK_GRAY);
        graphics2D.setStroke(new BasicStroke(1));

        int crossSize = 2;
        for (int cellX = 0; cellX < map.width; cellX++) {
            for (int cellY = 0; cellY < map.height; cellY++) {
                int x = mapX + cellX * cellSize + cellSize / 2;
                int y = mapY + cellY * cellSize + cellSize / 2;

                graphics2D.drawLine(x, y + crossSize, x, y - crossSize);
                graphics2D.drawLine(x + crossSize, y, x - crossSize, y);
            }
        }
    }

    private static void drawColors(Graphics2D graphics2D, int mapX, int mapY, int cellSize, KarelMapData map) {
        for (int cellX = 0; cellX < map.width; cellX++) {
            for (int cellY = 0; cellY < map.height; cellY++) {
                if (map.getCell(cellX, cellY).color != null) {
                    int x = mapX + cellX * cellSize;
                    int y = mapY + (map.height - cellY - 1) * cellSize;

                    graphics2D.setColor(map.getCell(cellX, cellY).color);
                    graphics2D.fillRect(x, y, cellSize, cellSize);
                }
            }
        }
    }

    private static void drawNumbers(Graphics2D graphics2D, int mapX, int mapY, int cellSize, KarelMapData map) {
        graphics2D.setColor(Color.BLACK);
        graphics2D.setFont(new Font("Arial", Font.PLAIN, 14));

        for (int cellX = 0; cellX < map.width; cellX++)
            graphics2D.drawString(Integer.toString(cellX + 1), mapX + cellX * cellSize + cellSize / 2, mapY + cellSize * map.height + 20);
        for (int cellY = 0; cellY < map.height; cellY++)
            graphics2D.drawString(Integer.toString(map.height - cellY), mapX - 20, mapY + cellY * cellSize + cellSize / 2);
    }

    private static void drawKarel(Graphics2D graphics2D, int mapX, int mapY, int cellSize, KarelMapData map) {
        int karelSize = (int) (cellSize * 0.9);
        int x = mapX + map.karelX * cellSize + cellSize / 2;
        int y = mapY + (map.height - map.karelY - 1) * cellSize + cellSize / 2;
        KarelMapIcon.draw(graphics2D, x, y, karelSize, map.karelDirection);
    }

    private static void drawAllBeepers(Graphics2D graphics2D, int mapX, int mapY, int cellSize, KarelMapData map) {
        for (int cellX = 0; cellX < map.width; cellX++) {
            for (int cellY = 0; cellY < map.height; cellY++) {
                if (map.getCell(cellX, cellY).hasBeepers()) {
                    int x = mapX + cellX * cellSize + cellSize / 2;
                    int y = mapY + (map.height - cellY - 1) * cellSize + cellSize / 2;
                    drawBeeper(graphics2D, x, y, (cellSize * 2) / 3, map.getCell(cellX, cellY).getBeepers());
                }
            }
        }
    }

    public static void drawBeeper(Graphics2D graphics2D, int x, int y, int size, int count) {
        drawBeeper(graphics2D, x, y, size, count, true);
    }

    public static void drawBeeper(Graphics2D graphics2D, int x, int y, int size, int count, boolean border) {
        int[] xPoints = new int[]{x, x + size / 2, x, x - size / 2};
        int[] yPoints = new int[]{y - size / 2, y, y + size / 2, y};

        graphics2D.setColor(Color.GRAY);
        graphics2D.fillPolygon(xPoints, yPoints, 4);

        graphics2D.setColor(Color.BLACK);
        if (border) {
            graphics2D.setStroke(new BasicStroke(2));
            graphics2D.drawPolygon(xPoints, yPoints, 4);
        }

        String text;
        if (count <= 1) text = "";
        else if (count == Karel.INFINITE) text = " ∞";
        else if (count > 999) text = "999+";
        else text = Integer.toString(count);

        int fontSize = 13;
        Font font = new Font("Arial", Font.BOLD, fontSize);
        FontMetrics metrics = graphics2D.getFontMetrics(font);
        int textWidth = metrics.stringWidth(text);
        int textAscent = metrics.getAscent();

        graphics2D.setFont(font);
        graphics2D.drawString(text, x - textWidth / 2, y + textAscent / 2 - 1);
    }

    private static void drawWalls(Graphics2D graphics2D, int mapX, int mapY, int cellSize, KarelMapData map) {
        graphics2D.setColor(Color.BLACK);
        graphics2D.setStroke(new BasicStroke(2));

        for (int cellX = 0; cellX < map.width; cellX++) {
            for (int cellY = 0; cellY < map.height; cellY++) {
                if (map.getCell(cellX, cellY).hasWallSouth()) {
                    int x = mapX + cellX * cellSize;
                    int y = mapY + (map.height - cellY) * cellSize;
                    graphics2D.drawLine(x, y, x + cellSize, y);
                }
                if (map.getCell(cellX, cellY).hasWallWest()) {
                    int x = mapX + cellX * cellSize;
                    int y = mapY + (map.height - cellY - 1) * cellSize;
                    graphics2D.drawLine(x, y, x, y + cellSize);
                }
            }
        }
    }
}
