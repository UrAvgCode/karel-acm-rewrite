package examples.breakout;

import acm.graphics.GOval;

import java.awt.*;

public class GBall extends GOval {
	private double vx = 2;
	private double vy = 3;

	public GBall(int size) {
		super(size, size);
		setColor(Color.WHITE);
		setFilled(true);
		setFillColor(Color.WHITE);
	}

	public void move() {
		this.move(vx, vy);
	}

	public void deflectX() {
		vx = -vx;
	}

	public void deflectY() {
		vy = -vy;
	}

	public void deflectPaddle() {
		vy = -Math.abs(vy);
	}
}
