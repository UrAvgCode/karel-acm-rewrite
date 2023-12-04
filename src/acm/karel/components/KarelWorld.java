package acm.karel.components;

import acm.karel.Direction;
import acm.karel.Karel;
import acm.karel.map.KarelCell;
import acm.karel.map.KarelMapData;
import acm.karel.map.KarelMapLoader;
import acm.karel.map.KarelMapPainter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.util.ArrayList;

public class KarelWorld extends JPanel implements MouseListener, MouseMotionListener {

    private enum EditOption {
        KAREL, COLOR, ADD_WALLS, REMOVE_WALLS, ADD_BEEPERS, REMOVE_BEEPERS
    }

    private final KarelMapLoader mapLoader;
    public boolean editMode = false;
    private EditOption editOption = EditOption.KAREL;
    private Color editColor = Color.WHITE;
    private final ArrayList<KarelCell> editedCells = new ArrayList<>();
    public int beepersEditAmount = 1;
    private KarelMapData map;
    private int pauseLength = 256;

    private int xOffset = 0;
    private int yOffset = 0;
    private int cellSize = 0;

    public KarelWorld(File defaultMap) {
        super();
        setBackground(Color.LIGHT_GRAY);

        mapLoader = new KarelMapLoader();
        map = mapLoader.loadWorld(defaultMap);

        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public void loadMap(File file) {
        map = mapLoader.loadWorld(file);
        repaint();
    }

    public void resetMap() {
        map = mapLoader.loadWorld();
        repaint();
    }

    public void safeWorld(String fileName) {
        //mapLoader.safeWorld(fileName, map);
        repaint();
    }

    public void setPauseLength(int pauseLength) {
        this.pauseLength = pauseLength;
    }

    public void pause() {
        try {
            Thread.sleep(pauseLength);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void move() {
        if (frontIsClear()) {
            map.karelX += map.karelDirection.getDeltaX();
            map.karelY += map.karelDirection.getDeltaY();
            repaint();
            pause();
        } else {
            throw new RuntimeException("Karel crashed into wall");
        }
    }

    public void turnLeft() {
        map.karelDirection = map.karelDirection.getLeft();
        repaint();
        pause();
    }

    public void turnRight() {
        map.karelDirection = map.karelDirection.getRight();
        repaint();
        pause();
    }

    public void turnAround() {
        map.karelDirection = map.karelDirection.getOpposite();
        repaint();
        pause();
    }

    public void putBeeper() {
        if (map.beepersInBag > 0) {
            map.getKarelCell().addBeeper();
            if (map.beepersInBag != Karel.INFINITE) map.beepersInBag--;
            repaint();
            pause();
        } else {
            throw new RuntimeException("No beepers in bag");
        }
    }

    public void pickBeeper() {
        if (map.getKarelCell().hasBeepers()) {
            map.getKarelCell().removeBeeper();
            if (map.beepersInBag != Karel.INFINITE) map.beepersInBag++;
            repaint();
            pause();
        } else {
            throw new RuntimeException("No beeper to pick up");
        }
    }

    public boolean beepersInBag() {
        return map.beepersInBag > 0;
    }

    public boolean directionIsClear(Direction direction) {
        return switch (direction) {
            case NORTH -> map.karelY < map.height - 1 && !map.getCell(map.karelX, map.karelY + 1).hasWallSouth();
            case EAST -> map.karelX < map.width - 1 && !map.getCell(map.karelX + 1, map.karelY).hasWallWest();
            case SOUTH -> map.karelY > 0 && !map.getKarelCell().hasWallSouth();
            case WEST -> map.karelX > 0 && !map.getKarelCell().hasWallWest();
        };
    }

    public boolean frontIsClear() {
        return directionIsClear(map.karelDirection);
    }

    public boolean leftIsClear() {
        return directionIsClear(map.karelDirection.getLeft());
    }

    public boolean rightIsClear() {
        return directionIsClear(map.karelDirection.getRight());
    }

    public boolean beepersPresent() {
        return map.getKarelCell().hasBeepers();
    }

    public boolean facingDirection(Direction direction) {
        return map.karelDirection == direction;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (System.getProperty("os.name").equals("Linux"))
            Toolkit.getDefaultToolkit().sync();

        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int minOffset = 25;

        int worldWidth = getWidth() - 2 * minOffset;
        int worldHeight = getHeight() - 2 * minOffset;
        if (worldWidth < 0 || worldHeight < 0) return;

        if (worldWidth / map.width < worldHeight / map.height) {
            worldHeight = worldWidth * map.height / map.width;
        } else {
            worldWidth = worldHeight * map.width / map.height;
        }

        xOffset = (getWidth() - worldWidth) / 2;
        yOffset = (getHeight() - worldHeight) / 2;

        cellSize = worldWidth / map.width;

        KarelMapPainter.drawMap(graphics2D, xOffset, yOffset, cellSize, map);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (!editMode) return;
        editedCells.clear();

        int mouseX = e.getX();
        int mouseY = e.getY();

        if (!isPointInMap(mouseX, mouseY)) return;

        if (isKarelCell(mouseX, mouseY)) {
            editOption = EditOption.KAREL;
            return;
        }

        if (editOption == EditOption.COLOR) {
            KarelCell cell = getClickCell(mouseX, mouseY);
            if (cell != null && !editedCells.contains(cell)) {
                cell.color = editColor;
                repaint();
                editedCells.add(cell);
            }
            return;
        }

        KarelCell wall = getClickWallWest(mouseX, mouseY);
        if (wall != null) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                wall.addWallWest();
                editOption = EditOption.ADD_WALLS;
            } else if (e.getButton() == MouseEvent.BUTTON3) {
                wall.removeWallWest();
                editOption = EditOption.REMOVE_WALLS;
            }
            repaint();
            return;
        }

        KarelCell wallSouth = getClickWallSouth(mouseX, mouseY);
        if (wallSouth != null) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                wallSouth.addWallSouth();
                editOption = EditOption.ADD_WALLS;
            } else if (e.getButton() == MouseEvent.BUTTON3) {
                wallSouth.removeWallSouth();
                editOption = EditOption.REMOVE_WALLS;
            }
            repaint();
            return;
        }

        KarelCell cell = getClickCell(mouseX, mouseY);
        if (cell != null && !editedCells.contains(cell)) {
            if (e.getButton() == MouseEvent.BUTTON3 && cell.hasBeepers()) {
                cell.removeBeepers(beepersEditAmount);
                editOption = EditOption.REMOVE_BEEPERS;
            } else if (e.getButton() == MouseEvent.BUTTON1) {
                cell.addBeepers(beepersEditAmount);
                editOption = EditOption.ADD_BEEPERS;
            }
            repaint();
            editedCells.add(cell);
        }
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
        if (!editMode) return;

        int mouseX = e.getX();
        int mouseY = e.getY();

        if (!isPointInMap(mouseX, mouseY)) return;

        if (editOption == EditOption.COLOR) {
            KarelCell cell = getClickCell(mouseX, mouseY);
            if (cell != null && !editedCells.contains(cell)) {
                cell.color = editColor;
                repaint();
                editedCells.add(cell);
            }
            return;
        }

        switch (editOption) {
            case KAREL -> {
                int cellX = (mouseX - xOffset) / (cellSize);
                int cellY = map.height - (mouseY - yOffset) / cellSize - 1;

                map.karelX = cellX;
                map.karelY = cellY;
                repaint();
            }
            case ADD_WALLS -> {
                KarelCell wall = getClickWallWest(mouseX, mouseY);
                if (wall != null) {
                    wall.addWallWest();
                    editedCells.add(wall);
                    repaint();
                    return;
                }

                KarelCell wallSouth = getClickWallSouth(mouseX, mouseY);
                if (wallSouth != null) {
                    wallSouth.addWallSouth();
                    repaint();
                    editedCells.add(wallSouth);
                }
            }
            case REMOVE_WALLS -> {
                KarelCell wall = getClickWallWest(mouseX, mouseY);
                if (wall != null) {
                    wall.removeWallWest();
                    repaint();
                    editedCells.add(wall);
                    return;
                }

                wall = getClickWallSouth(mouseX, mouseY);
                if (wall != null) {
                    wall.removeWallSouth();
                    repaint();
                    editedCells.add(wall);
                }
            }
            case ADD_BEEPERS -> {
                KarelCell cell = getClickCell(mouseX, mouseY);
                if (cell != null && !editedCells.contains(cell)) {
                    cell.addBeepers(beepersEditAmount);
                    repaint();
                    editedCells.add(cell);
                }
            }
            case REMOVE_BEEPERS -> {
                KarelCell cell = getClickCell(mouseX, mouseY);
                if (cell != null && !editedCells.contains(cell)) {
                    cell.removeBeepers(beepersEditAmount);
                    repaint();
                    editedCells.add(cell);
                }
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    public void setBeeperEditAmount(int amount) {
        beepersEditAmount = amount;
    }

    public int getBeepersInBag() {
        return map.beepersInBag;
    }

    public void addBeepersToBag(int amount) {
        if (amount == Karel.INFINITE) {
            map.beepersInBag = Karel.INFINITE;
        } else if (map.beepersInBag != Karel.INFINITE) {
            map.beepersInBag += amount;
        }
    }

    public void removeBeepersFromBag(int amount) {
        if (amount == Karel.INFINITE) {
            map.beepersInBag = 0;
        } else if (map.beepersInBag != Karel.INFINITE) {
            map.beepersInBag = Math.max(0, map.beepersInBag - amount);
        }
    }

    public int getBeeperEditAmount() {
        return beepersEditAmount;
    }

    public void setKarelDirection(Direction direction) {
        map.karelDirection = direction;
        repaint();
    }

    public void setEditColor(Color color) {
        if (color == null) {
            editOption = EditOption.KAREL;
        } else {
            editOption = EditOption.COLOR;
            editColor = color;
        }
    }

    private boolean isKarelCell(int x, int y) {
        int cellX = (x - xOffset) / (cellSize);
        int cellY = map.height - (y - yOffset) / cellSize - 1;

        return cellX == map.karelX && cellY == map.karelY;
    }

    private boolean isPointInMap(int x, int y) {
        return x > xOffset && x < xOffset + map.width * cellSize && y > yOffset && y < yOffset + map.height * cellSize;
    }

    private KarelCell getClickCell(int x, int y) {
        int cellX = (x - xOffset) / (cellSize);
        int cellY = map.height - (y - yOffset) / cellSize - 1;

        return map.getCell(cellX, cellY);
    }

    private KarelCell getClickWallWest(int x, int y) {
        int mouseX = x - xOffset;

        int cellX = (x - xOffset) / (cellSize);
        int cellY = map.height - (y - yOffset) / cellSize - 1;

        if (mouseX % cellSize < cellSize * 0.1 && cellX != 0) {
            return map.getCell(cellX, cellY);
        } else if (mouseX % cellSize > cellSize * 0.9 && cellX != map.width - 1) {
            return map.getCell(cellX + 1, cellY);
        } else {
            return null;
        }
    }

    private KarelCell getClickWallSouth(int x, int y) {
        int mouseY = y - yOffset;

        int cellX = (x - xOffset) / (cellSize);
        int cellY = map.height - (y - yOffset) / cellSize - 1;

        if (mouseY % cellSize < cellSize * 0.1 && cellY != map.height - 1) {
            return map.getCell(cellX, cellY + 1);
        } else if (mouseY % cellSize > cellSize * 0.9 && cellY != 0) {
            return map.getCell(cellX, cellY);
        } else {
            return null;
        }
    }
}
