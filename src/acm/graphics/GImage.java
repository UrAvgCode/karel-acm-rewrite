package acm.graphics;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;

public class GImage extends GObject {

    private static final MediaTracker tracker = new MediaTracker(new Component() {
    });

    private double width;
    private double height;
    private Image image;

    public GImage(String filename) {
        this(filename, 0, 0);
    }

    public GImage(String filename, double x, double y) {
        this(loadImage(filename), x, y);
    }

    public GImage(Image image) {
        this(image, 0, 0);
    }

    public GImage(Image image, double x, double y) {
        super();
        color = null;

        this.x = x;
        this.y = y;

        this.image = image;

        width = image.getWidth(null);
        height = image.getHeight(null);
    }

    public GImage(int[][] pixels) {
        this(pixels, 0, 0);
    }

    public GImage(int[][] pixels, double x, double y) {
        super();
        color = null;

        this.x = x;
        this.y = y;

        BufferedImage image = new BufferedImage(pixels.length, pixels[0].length, BufferedImage.TYPE_INT_ARGB);

        for (int i = 0; i < pixels.length; i++) {
            for (int j = 0; j < pixels[0].length; j++) {
                image.setRGB(i, j, pixels[i][j]);
            }
        }

        this.image = image;

        width = image.getWidth(null);
        height = image.getHeight(null);
    }

    @Override
    public void paint(Graphics2D g) {
        if (visible) {
            g.setColor(color);
            g.drawImage(image, (int) x, (int) y, (int) width, (int) height, color, null);
        }
    }

    private static Image loadImage(String filename) {
        URL url = ClassLoader.getSystemResource(filename);
        Image result;
        if (url == null) {
            result = Toolkit.getDefaultToolkit().getImage(filename);
        } else {
            result = Toolkit.getDefaultToolkit().getImage(url);
        }

        tracker.addImage(result, 0);
        try {
            tracker.waitForID(0);
        } catch (InterruptedException e) {
            System.err.println("Failed to load image " + filename);
            e.printStackTrace();
        }
        tracker.removeImage(result, 0);

        return result;
    }

    public void setSize(double width, double height) {
        this.width = width;
        this.height = height;
    }

    public void setImage(String filename) {
        image = loadImage(filename);
        setSize(image.getWidth(null), image.getHeight(null));
    }

    public void scale(double scale) {
        width *= scale;
        height *= scale;
    }

    @Override
    public boolean contains(double x, double y) {
        return getBounds().contains(x, y);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, (int) width, (int) height);
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
}
