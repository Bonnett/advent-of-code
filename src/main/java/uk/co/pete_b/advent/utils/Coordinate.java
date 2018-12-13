package uk.co.pete_b.advent.utils;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Coordinate {
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
    public boolean equals(Object otherCoords) {
        return EqualsBuilder.reflectionEquals(this, otherCoords, false);
    }

    @Override
    public String toString() {
        return String.format("[%d, %d]", x, y);
    }
}
