package uk.co.pete_b.advent.utils;

import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Coordinate3D {
    protected int x;
    protected int y;
    protected int z;

    public Coordinate3D(final int x, final int y, final int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Coordinate3D(final Coordinate3D coordinate) {
        this.x = coordinate.x;
        this.y = coordinate.y;
        this.z = coordinate.z;
    }

    public static Coordinate3D vector(final Coordinate3D start, final Coordinate3D end) {
        return new Coordinate3D(end.x - start.x, end.y - start.y, end.z - start.z);
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

    public void move(final int x, final int y, final int z) {
        this.x += x;
        this.y += y;
        this.z += z;
    }

    public void move(final Coordinate3D movement) {
        this.x += movement.x;
        this.y += movement.y;
        this.z += movement.z;
    }

    public int getManhattanDistance() {
        return Math.abs(this.x) + Math.abs(this.y) + Math.abs(this.z);
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
        return String.format("[x=%3d, y=%3d, z=%3d]", x, y, z);
    }
}
