package examples.breakout;

import acm.graphics.GCompound;
import acm.graphics.GOval;
import acm.graphics.GPolygon;

import static java.awt.Color.RED;

public class GHeart extends GCompound {

	public GHeart(double size) {
		drawCircle(0, 5 * size);
		drawCircle(10 * size, 5 * size);

		GPolygon triangle = new GPolygon();

		triangle.addVertex(0, 0);
		triangle.addVertex(8.5 * size, 3.7 * size);
		triangle.addVertex(0, 15 * size);
		triangle.addVertex(-8.5 * size, 3.7 * size);

		triangle.setColor(RED);
		triangle.setFilled(true);
		triangle.setFillColor(RED);

		add(triangle, 10 * size, 5 * size);
	}

	private void drawCircle(double x, double radius) {
		GOval circle = new GOval(radius * 2, radius * 2);
		circle.setColor(RED);
		circle.setFilled(true);
		circle.setFillColor(RED);
		add(circle, x, 0);
	}
}
