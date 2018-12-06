package uk.co.pete_b.advent.utils;

public enum Direction {
    LEFT("left"), UP("up"), RIGHT("right"), DOWN("down");
    private final String direction;

    Direction(final String direction) {
        this.direction = direction;
    }

    Direction left;
    Direction right;

    private void setLeft(Direction left) {
        this.left = left;
        left.right = this;
    }

    public Direction getLeft() {
        if (left == null) {
            init();
        }
        return left;
    }

    public Direction getRight() {
        if (right == null) {
            init();
        }
        return right;
    }

    private void init() {
        Direction.UP.setLeft(Direction.LEFT);
        Direction.LEFT.setLeft(Direction.DOWN);
        Direction.DOWN.setLeft(Direction.RIGHT);
        Direction.RIGHT.setLeft(Direction.UP);
    }

    public static Direction fromString(final String text) {
        for (Direction dir : Direction.values()) {
            if (dir.direction.equalsIgnoreCase(text)) {
                return dir;
            }
        }

        return null;
    }
}
