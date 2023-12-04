package acm.util;

import java.awt.*;
import java.util.Random;

public class RandomGenerator extends Random {

    private static RandomGenerator instance;

    public RandomGenerator() {
        super();
    }

    public int nextInt(int min, int max) {
        return min + nextInt(max - min + 1);
    }

    public double nextDouble(double min, double max) {
        return min + nextDouble(max - min);
    }

    public boolean nextBoolean(double probability) {
        return nextDouble() < probability;
    }

    public Color nextColor() {
        int r = this.nextInt(256);
        int g = this.nextInt(256);
        int b = this.nextInt(256);
        return new Color(r, g, b);
    }

    public static RandomGenerator getInstance() {
        if (instance == null)
            instance = new RandomGenerator();
        return instance;
    }
}
