package uk.co.pete_b.advent;

public final class Utils {
    private Utils() {

    }

    public enum Direction {
        LEFT, UP, RIGHT, DOWN;

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
    }
}
