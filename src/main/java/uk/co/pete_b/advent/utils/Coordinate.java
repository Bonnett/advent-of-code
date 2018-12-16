package uk.co.pete_b.advent.utils;

import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Coordinate implements Comparable<Coordinate> {
    protected int x;
    protected int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Coordinate left() {
        return new Coordinate(x - 1, y);
    }

    public Coordinate right() {
        return new Coordinate(x + 1, y);
    }

    public Coordinate up() {
        return new Coordinate(x, y - 1);
    }

    public Coordinate down() {
        return new Coordinate(x, y + 1);
    }

    public Coordinate upLeft() {
        return up().left();
    }

    public Coordinate upRight() {
        return up().right();
    }

    public Coordinate downLeft() {
        return down().left();
    }

    public Coordinate downRight() {
        return down().right();
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this, false);
    }

    @Override
    public boolean equals(final Object otherCoords) {
        boolean isEqual = false;
        if (otherCoords instanceof Coordinate) {
            isEqual = ((Coordinate) otherCoords).x == this.x && ((Coordinate) otherCoords).y == this.y;
        }

        return isEqual;
    }

    @Override
    public String toString() {
        return String.format("[%d, %d]", x, y);
    }

    @Override
    public int compareTo(final Coordinate otherCoords) {
        if (this.y > otherCoords.y || (this.y == otherCoords.y && this.x > otherCoords.x)) {
            return 1;
        } else if (this.equals(otherCoords)) {
            return 0;
        } else {
            return -1;
        }
    }
}
