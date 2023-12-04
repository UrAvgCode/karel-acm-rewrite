package acm.karel;

public abstract class SuperKarel extends Karel {

    public final void turnRight() {
        getWorld().turnRight();
    }

    public final void turnAround() {
        getWorld().turnAround();
    }

    public final boolean random() {
        return random(0.5);
    }

    public final boolean random(double probability) {
        return Math.random() < probability;
    }

}
