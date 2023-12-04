package acm.karel.map;

import acm.karel.Direction;
import acm.karel.Karel;

@SuppressWarnings("MismatchedReadAndWriteOfArray")
public class KarelMapData {
    private final KarelCell[][] map;
    public final int width;
    public final int height;
    public int karelX = 0;
    public int karelY = 0;
    public Direction karelDirection = Direction.EAST;
    public int beepersInBag = Karel.INFINITE;

    public KarelMapData() {
        this(10, 10);
    }

    public KarelMapData(int width, int height) {
        map = new KarelCell[width][height];
        this.width = width;
        this.height = height;

        for (KarelCell[] karelCells : map)
            for (int i = 0; i < karelCells.length; i++)
                karelCells[i] = new KarelCell();
    }

    public KarelCell getCell(int x, int y) {
        return map[x][y];
    }

    public KarelCell getKarelCell() {
        return map[karelX][karelY];
    }
}
