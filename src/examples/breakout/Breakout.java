package examples.breakout;

import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Breakout extends GraphicsProgram {
    private final RandomGenerator random = new RandomGenerator();

    private final int WIDTH = 600;
    private final int HEIGHT = 800;

    private final int BALL_SIZE = 35;
    private final int PADDLE_WIDTH = 140;
    private final int PADDLE_HEIGHT = 30;
    private final int PADDLE_SEPARATION = 40;

    private final int BRICK_ROWS = 5;
    private final int BRICK_COLUMNS = 8;
    private final int BRICK_OFFSET = 32;

    private final Color BACKGROUND_COLOR = Color.BLACK;
    private final Color PADDLE_COLOR = Color.LIGHT_GRAY;
    private final Color FONT_COLOR = Color.WHITE;

    private GBall ball;
    private GRect paddle;
    private final GRect[] bricks = new GRect[BRICK_ROWS * BRICK_COLUMNS];

    private boolean game_paused;
    private GLabel press_to_start;

    private GScores gui;

    public void run() {
        setup();

        //noinspection InfiniteLoopStatement
        while (true) {
            if (!game_paused) {
                ball.move();
                checkForCollision();
            }
            pause(4);
        }
    }

    private void setup() {
        setSize(WIDTH, HEIGHT);
        setBackground(BACKGROUND_COLOR);
        addMouseListeners();

        createPaddle();
        createBall();
        ball.setVisible(false);
        createBricks();

        createGUI();
        createPauseScreen();
    }

    private void resetGame() {
        for (int i = 0; i < BRICK_ROWS * BRICK_COLUMNS; i++) {
            remove(bricks[i]);
        }
        createBricks();

        ball.setLocation((WIDTH / 2.0) - (BALL_SIZE / 2.0), HEIGHT / 3.0);
        ball.setVisible(false);

        createPauseScreen();
    }

    private void createPauseScreen() {
        game_paused = true;

        if (gui.getScore() < BRICK_ROWS * BRICK_COLUMNS) {
            press_to_start = new GLabel("Press To Start");
        } else {
            press_to_start = new GLabel("YOU WON !!!");
        }

        press_to_start.setFont("Arial-bold-50");
        press_to_start.setColor(FONT_COLOR);
        add(press_to_start, (WIDTH / 2.0) - (press_to_start.getWidth() / 2), HEIGHT / 2.0);
    }

    public void mousePressed(MouseEvent e) {
        if (game_paused) {
            gui.reset();
            ball.setVisible(true);

            game_paused = false;
            remove(press_to_start);
        }
    }

    private void createGUI() {
        gui = new GScores();
        add(gui, BRICK_OFFSET, gui.getHeight());
    }

    private void createBall() {
        ball = new GBall(BALL_SIZE);
        add(ball, (WIDTH / 2.0) - (BALL_SIZE / 2.0), HEIGHT / 3.0);
    }

    private void createPaddle() {
        paddle = new GRect(PADDLE_WIDTH, PADDLE_HEIGHT);
        paddle.setColor(PADDLE_COLOR);
        paddle.setFilled(true);
        paddle.setFillColor(PADDLE_COLOR);
        add(paddle, (WIDTH / 2.0) - (PADDLE_WIDTH / 2.0), HEIGHT - PADDLE_SEPARATION - PADDLE_HEIGHT);
    }

    private void createBricks() {
        Color brick_color = new Color(random.nextInt(0, 150), random.nextInt(0, 150), random.nextInt(0, 150));

        final double totalWidth = WIDTH - 2 * BRICK_OFFSET;
        final double totalHeight = HEIGHT * 0.2f;
        final double brickWidth = totalWidth / BRICK_COLUMNS;
        final double brickHeight = totalHeight / BRICK_ROWS;

        int n = 0;
        for (int y = 0; y < BRICK_ROWS; y++) {
            brick_color = nextGradientColor(brick_color);
            for (int x = 0; x < BRICK_COLUMNS; x++) {
                bricks[n] = new GRect(brickWidth, brickHeight);
                bricks[n].setColor(BACKGROUND_COLOR);
                bricks[n].setFilled(true);
                bricks[n].setFillColor(brick_color);
                add(bricks[n], BRICK_OFFSET + (x * brickWidth), 1.5 * BRICK_OFFSET + (y * brickHeight));
                n++;
            }
        }
    }

    private Color nextGradientColor(Color original) {
        int red = original.getRed();
        int green = original.getGreen();
        int blue = original.getBlue();

        red = Math.min(255, red + 30);
        green = Math.min(255, green + 30);
        blue = Math.min(255, blue + 30);

        return new Color(red, green, blue);
    }

    public void mouseMoved(MouseEvent e) {
        movePaddle(e);
    }

    public void mouseDragged(MouseEvent e) {
        movePaddle(e);
    }

    private void movePaddle(MouseEvent e) {
        int x = e.getX();

        if (x < PADDLE_WIDTH / 2) {
            paddle.setLocation(0, HEIGHT - PADDLE_SEPARATION - PADDLE_HEIGHT);
        } else if (x > WIDTH - PADDLE_WIDTH / 2) {
            paddle.setLocation(WIDTH - PADDLE_WIDTH, HEIGHT - PADDLE_SEPARATION - PADDLE_HEIGHT);
        } else {
            paddle.setLocation(x - (PADDLE_WIDTH / 2.0), HEIGHT - PADDLE_SEPARATION - PADDLE_HEIGHT);
        }
    }

    private void checkForCollision() {
        checkForCollisionWithWall();
        checkForCollisionWithObjects();
    }

    private void checkForCollisionWithWall() {
        double x = ball.getX();
        double y = ball.getY();

        if ((x <= 0) || (x >= (WIDTH - BALL_SIZE))) {
            ball.deflectX();
        }

        if (y <= 0) {
            ball.deflectY();
        }

        if (y >= HEIGHT + 200) {
            gui.looseLive();
            if (gui.getLives() <= 0) {
                resetGame();
            } else {
                ball.setLocation((WIDTH / 2.0) - (BALL_SIZE / 2.0), HEIGHT / 3.0);
            }
        }
    }

    private void checkForCollisionWithObjects() {
        double x = ball.getX() + BALL_SIZE / 2.0;
        double y = ball.getY() + BALL_SIZE / 2.0;

        for (int degrees = 0; degrees <= 360; degrees += 10) {
            double check_x = x + Math.cos(Math.toRadians(degrees)) * (BALL_SIZE / 2.0 + 1);
            double check_y = y + Math.sin(Math.toRadians(degrees)) * (BALL_SIZE / 2.0 + 1);

            GObject collision_obj = getElementAt(check_x, check_y);

            if (collision_obj == paddle) {
                ball.deflectPaddle();
                break;

            } else if (collision_obj != null && collision_obj != ball && collision_obj != gui) {
                if ((degrees >= 30 && degrees <= 150) || (degrees >= 210 && degrees <= 330)) {
                    ball.deflectY();
                }
                if ((degrees <= 60 || degrees >= 300) || (degrees >= 120 && degrees <= 240)) {
                    ball.deflectX();
                }

                gui.addScore();
                if (gui.getScore() >= BRICK_COLUMNS * BRICK_ROWS) {
                    resetGame();
                }

                remove(collision_obj);
                break;
            }
        }
    }
}
