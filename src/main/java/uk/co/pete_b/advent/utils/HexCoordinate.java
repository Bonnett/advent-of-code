package uk.co.pete_b.advent.utils;

public class HexCoordinate extends Coordinate3D {
    public HexCoordinate(int x, int y, int z) {
        super(x, y, z);
    }

    public HexCoordinate northEast() {
        return new HexCoordinate(x + 1, y, z - 1);
    }

    public HexCoordinate east() {
        return new HexCoordinate(x + 1, y - 1, z);
    }

    public HexCoordinate southEast() {
        return new HexCoordinate(x, y - 1, z + 1);
    }

    public HexCoordinate southWest() {
        return new HexCoordinate(x - 1, y, z + 1);
    }

    public HexCoordinate west() {
        return new HexCoordinate(x - 1, y + 1, z);
    }

    public HexCoordinate northWest() {
        return new HexCoordinate(x , y + 1, z - 1);
    }
}
