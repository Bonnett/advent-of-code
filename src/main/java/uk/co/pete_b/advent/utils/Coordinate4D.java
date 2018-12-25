package uk.co.pete_b.advent.utils;

import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Coordinate4D {
    protected final int x;
    protected final int y;
    protected final int z;
    protected final int t;

    public Coordinate4D(final int x, final int y, final int z, final int t) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.t = t;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getZ() {
        return this.z;
    }

    public int getT() {
        return this.t;
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this, false);
    }

    @Override
    public boolean equals(final Object otherCoords) {
        boolean isEqual = false;
        if (otherCoords instanceof Coordinate4D) {
            isEqual = ((Coordinate4D) otherCoords).x == this.x && ((Coordinate4D) otherCoords).y == this.y && ((Coordinate4D) otherCoords).z == this.z && ((Coordinate4D) otherCoords).t == this.t;
        }

        return isEqual;
    }

    @Override
    public String toString() {
        return String.format("[%d, %d, %d, %d]", x, y, z, t);
    }
}
