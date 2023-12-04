package acm.karel.map;

import acm.karel.Karel;

import java.awt.*;

public class KarelCell {
    private int beepers;
    private boolean wallSouth;
    private boolean wallWest;

    public Color color;

    public KarelCell() {
        this(0, false, false, null);
    }

    public KarelCell(int beepers, boolean wallSouth, boolean wallWest, Color color) {
        this.beepers = beepers;
        this.wallSouth = wallSouth;
        this.wallWest = wallWest;
    }

    public boolean hasBeepers() {
        return beepers > 0;
    }

    public int getBeepers() {
        return beepers;
    }

    public void addBeeper() {
        addBeepers(1);
    }

    public void addBeepers(int beepers) {
        if (beepers == Karel.INFINITE) {
            this.beepers = Karel.INFINITE;
        } else if (this.beepers != Karel.INFINITE) {
            this.beepers += beepers;
        }
    }

    public void removeBeeper() {
        removeBeepers(1);
    }

    public void removeBeepers(int beepers) {
        if (beepers == Karel.INFINITE) {
            this.beepers = 0;
        } else if (this.beepers != Karel.INFINITE) {
            this.beepers = Math.max(0, this.beepers - beepers);
        }
    }

    public boolean hasWallSouth() {
        return wallSouth;
    }

    public boolean hasWallWest() {
        return wallWest;
    }

    public void addWallSouth() {
        wallSouth = true;
    }

    public void addWallWest() {
        wallWest = true;
    }

    public void removeWallSouth() {
        wallSouth = false;
    }

    public void removeWallWest() {
        wallWest = false;
    }

    public void toggleWallSouth() {
        wallSouth = !wallSouth;
    }

    public void toggleWallWest() {
        wallWest = !wallWest;
    }
}
