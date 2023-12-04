package acm.karel;

public enum Direction {
    NORTH, EAST, SOUTH, WEST;

    public Direction getLeft() {
        return switch (this) {
            case NORTH -> WEST;
            case EAST -> NORTH;
            case SOUTH -> EAST;
            case WEST -> SOUTH;
        };
    }

    public Direction getRight() {
        return switch (this) {
            case NORTH -> EAST;
            case EAST -> SOUTH;
            case SOUTH -> WEST;
            case WEST -> NORTH;
        };
    }

    public Direction getOpposite() {
        return switch (this) {
            case NORTH -> SOUTH;
            case EAST -> WEST;
            case SOUTH -> NORTH;
            case WEST -> EAST;
        };
    }

    public int getDeltaX() {
        return switch (this) {
            case NORTH, SOUTH -> 0;
            case EAST -> 1;
            case WEST -> -1;
        };
    }

    public int getDeltaY() {
        return switch (this) {
            case NORTH -> 1;
            case EAST, WEST -> 0;
            case SOUTH -> -1;
        };
    }

    public int getSin() {
        return switch (this) {
            case NORTH -> -1;
            case EAST, WEST -> 0;
            case SOUTH -> 1;
        };
    }

    public int getCos() {
        return switch (this) {
            case NORTH, SOUTH -> 0;
            case EAST -> 1;
            case WEST -> -1;
        };
    }
}
