package uk.co.pete_b.advent.utils;

import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Coordinate3D {
    protected final int x;
    protected final int y;
    protected final int z;

    public Coordinate3D(final int x, final int y, final int z) {
        this.x = x;
        this.y = y;
        this.z = z;
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

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this, false);
    }

    @Override
    public boolean equals(final Object otherCoords) {
        boolean isEqual = false;
        if (otherCoords instanceof Coordinate3D) {
            isEqual = ((Coordinate3D) otherCoords).x == this.x && ((Coordinate3D) otherCoords).y == this.y && ((Coordinate3D) otherCoords).z == this.z;
        }

        return isEqual;
    }

    @Override
    public String toString() {
        return String.format("[%d, %d, %d]", x, y, z);
    }
}
