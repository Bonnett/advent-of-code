package uk.co.pete_b.advent.utils;

import org.apache.commons.lang3.builder.HashCodeBuilder;

public class LongCoordinate3D {
    protected long x;
    protected long y;
    protected long z;

    public LongCoordinate3D(final long x, final long y, final long z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public LongCoordinate3D(final LongCoordinate3D coordinate) {
        this.x = coordinate.x;
        this.y = coordinate.y;
        this.z = coordinate.z;
    }

    public static LongCoordinate3D vector(final LongCoordinate3D start, final LongCoordinate3D end) {
        return new LongCoordinate3D(end.x - start.x, end.y - start.y, end.z - start.z);
    }

    public long getX() {
        return this.x;
    }

    public long getY() {
        return this.y;
    }

    public long getZ() {
        return this.z;
    }

    public void move(final long x, final long y, final long z) {
        this.x += x;
        this.y += y;
        this.z += z;
    }

    public void move(final LongCoordinate3D movement) {
        this.x += movement.x;
        this.y += movement.y;
        this.z += movement.z;
    }

    public LongCoordinate3D getNextPosition(final LongCoordinate3D movement) {
        return new LongCoordinate3D(this.x + movement.x, this.y + movement.y, this.z + movement.z);
    }

    public long getManhattanDistance() {
        return Math.abs(this.x) + Math.abs(this.y) + Math.abs(this.z);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this, false);
    }

    @Override
    public boolean equals(final Object otherCoords) {
        boolean isEqual = false;
        if (otherCoords instanceof LongCoordinate3D) {
            isEqual = ((LongCoordinate3D) otherCoords).x == this.x && ((LongCoordinate3D) otherCoords).y == this.y && ((LongCoordinate3D) otherCoords).z == this.z;
        }

        return isEqual;
    }

    @Override
    public String toString() {
        return String.format("[x=%3d, y=%3d, z=%3d]", x, y, z);
    }
}
