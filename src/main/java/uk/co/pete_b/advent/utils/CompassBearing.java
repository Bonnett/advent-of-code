package uk.co.pete_b.advent.utils;

public enum CompassBearing {
    WEST, NORTH, EAST, SOUTH;

    CompassBearing left;
    CompassBearing right;

    private void setLeft(CompassBearing left) {
        this.left = left;
        left.right = this;
    }

    public CompassBearing getLeft() {
        if (left == null) {
            init();
        }

        return left;
    }

    public CompassBearing getRight() {
        if (right == null) {
            init();
        }

        return right;
    }

    private void init() {
        CompassBearing.NORTH.setLeft(CompassBearing.WEST);
        CompassBearing.WEST.setLeft(CompassBearing.SOUTH);
        CompassBearing.SOUTH.setLeft(CompassBearing.EAST);
        CompassBearing.EAST.setLeft(CompassBearing.NORTH);
    }
}
