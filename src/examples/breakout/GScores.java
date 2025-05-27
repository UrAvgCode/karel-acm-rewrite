package examples.breakout;

import acm.graphics.GCompound;
import acm.graphics.GLabel;

import java.awt.*;

public class GScores extends GCompound {
    private final int MAX_LIVES = 3;

    private int score = 0;
    private int lives = MAX_LIVES;
    final private GLabel gui;
    final private GHeart[] hearts = new GHeart[MAX_LIVES];

    public GScores() {
        gui = new GLabel("Score: " + score);
        gui.setFont("Arial-bold-24");
        gui.setColor(Color.WHITE);
        add(gui);

        gui.setLabel("Score: " + score);

        final int heartOffset = 320;
        for (int i = 0; i < MAX_LIVES; i++) {
            hearts[i] = new GHeart(1.4);
            add(hearts[i], gui.getWidth() + heartOffset + 1.5 * hearts[i].getWidth() * i, -gui.getHeight() / 1.5);
        }
    }

    public void addScore() {
        score++;
        gui.setLabel("Score: " + score);
    }

    public void looseLive() {
        lives -= 1;
        for (int i = 0; i < MAX_LIVES; i++) {
            if (i >= lives) {
                hearts[i].setVisible(false);
            }
        }
    }

    public void reset() {
        score = 0;
        lives = MAX_LIVES;

        gui.setLabel("Score: " + score);
        for (int i = 0; i < MAX_LIVES; i++) {
            hearts[i].setVisible(true);
        }
    }

    public int getScore() {
        return score;
    }

    public int getLives() {
        return lives;
    }
}