package uk.co.pete_b.advent.utils;

import org.apache.commons.lang3.builder.HashCodeBuilder;

public class LongCoordinate implements Comparable<LongCoordinate> {
    protected long x;
    protected long y;

    public LongCoordinate(long x, long y) {
        this.x = x;
        this.y = y;
    }

    public long getX() {
        return x;
    }

    public long getY() {
        return y;
    }

    public LongCoordinate left() {
        return new LongCoordinate(x - 1, y);
    }

    public LongCoordinate right() {
        return new LongCoordinate(x + 1, y);
    }

    public LongCoordinate up() {
        return new LongCoordinate(x, y - 1);
    }

    public LongCoordinate down() {
        return new LongCoordinate(x, y + 1);
    }

    public LongCoordinate upLeft() {
        return up().left();
    }

    public LongCoordinate upRight() {
        return up().right();
    }

    public LongCoordinate downLeft() {
        return down().left();
    }

    public LongCoordinate downRight() {
        return down().right();
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this, false);
    }

    @Override
    public boolean equals(final Object otherCoords) {
        boolean isEqual = false;
        if (otherCoords instanceof LongCoordinate) {
            isEqual = ((LongCoordinate) otherCoords).x == this.x && ((LongCoordinate) otherCoords).y == this.y;
        }

        return isEqual;
    }

    @Override
    public String toString() {
        return String.format("[%d, %d]", x, y);
    }

    @Override
    public int compareTo(final LongCoordinate otherCoords) {
        if (this.y > otherCoords.y || (this.y == otherCoords.y && this.x > otherCoords.x)) {
            return 1;
        } else if (this.equals(otherCoords)) {
            return 0;
        } else {
            return -1;
        }
    }
}
