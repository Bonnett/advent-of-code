package uk.co.pete_b.advent.aoc2022;

import uk.co.pete_b.advent.utils.Coordinate;
import uk.co.pete_b.advent.utils.Direction;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day22 {

    public static final Pattern MOVE_PATTERN = Pattern.compile("([^LR]+|L|R)");

    private enum Tile {
        OPEN, WALL
    }

    public static int getFinalPassword(final List<String> puzzleNotes) {
        final Map<Coordinate, Tile> world = new HashMap<>();
        String moves = null;
        Coordinate currentPosition = null;
        int rows = 0;
        int cols = puzzleNotes.get(0).length();
        for (String line : puzzleNotes) {
            if (line.startsWith(" ") || line.startsWith(".") || line.startsWith("#")) {
                for (int x = 0; x < line.length(); x++) {
                    final char c = line.charAt(x);
                    if (c == ' ') {
                        continue;
                    }

                    final Coordinate coord = new Coordinate(x, rows);
                    final Tile tile = c == '.' ? Tile.OPEN : Tile.WALL;
                    world.put(coord, tile);
                    if (currentPosition == null && rows == 0 && tile == Tile.OPEN) {
                        currentPosition = coord;
                    }
                }
                rows++;
            } else {
                moves = line;
            }
        }

        if (moves == null) {
            throw new IllegalStateException("Failed to find map moves");
        }

        if (currentPosition == null) {
            throw new IllegalStateException("Failed to find starting position");
        }

        Direction currentOrientation = Direction.RIGHT;
        final Matcher m = MOVE_PATTERN.matcher(moves);
        while (m.find()) {
            final String move = m.group(1);
            if (move.equals("L")) {
                currentOrientation = currentOrientation.getLeft();
            } else if (move.equals("R")) {
                currentOrientation = currentOrientation.getRight();
            } else {
                final int distance = Integer.parseInt(move);
                for (int i = 0; i < distance; i++) {
                    Coordinate newPosition;
                    switch (currentOrientation) {
                        case LEFT -> newPosition = currentPosition.left();
                        case RIGHT -> newPosition = currentPosition.right();
                        case UP -> newPosition = currentPosition.up();
                        case DOWN -> newPosition = currentPosition.down();
                        default -> throw new IllegalStateException("Failed to determine orientation");
                    }

                    if (!world.containsKey(newPosition)) {
                        switch (currentOrientation) {
                            case LEFT -> {
                                newPosition = new Coordinate(cols, newPosition.getY());
                                while (!world.containsKey(newPosition)) {
                                    newPosition = newPosition.left();
                                }
                            }
                            case RIGHT -> {
                                newPosition = new Coordinate(0, newPosition.getY());
                                while (!world.containsKey(newPosition)) {
                                    newPosition = newPosition.right();
                                }
                            }
                            case DOWN -> {
                                newPosition = new Coordinate(newPosition.getX(), 0);
                                while (!world.containsKey(newPosition)) {
                                    newPosition = newPosition.down();
                                }
                            }
                            case UP -> {
                                newPosition = new Coordinate(newPosition.getX(), rows);
                                while (!world.containsKey(newPosition)) {
                                    newPosition = newPosition.up();
                                }
                            }
                            default -> throw new IllegalStateException("Failed to determine orientation");
                        }
                    }

                    if (world.get(newPosition) == Tile.OPEN) {
                        currentPosition = newPosition;
                    } else {
                        break;
                    }
                }
            }
        }

        final int facing = switch (currentOrientation) {
            case RIGHT -> 0;
            case DOWN -> 1;
            case LEFT -> 2;
            case UP -> 3;
        };

        return ((currentPosition.getY() + 1) * 1000) + ((currentPosition.getX() + 1) * 4) + facing;
    }

    public static int getFinalPasswordAsCube(final List<String> puzzleNotes, final int cubeSize) {
        final Map<Coordinate, Tile> world = new HashMap<>();
        String moves = null;
        Coordinate currentPosition = null;
        Direction currentOrientation = Direction.RIGHT;
        int rows = 0;
        for (String line : puzzleNotes) {
            if (line.startsWith(" ") || line.startsWith(".") || line.startsWith("#")) {
                for (int x = 0; x < line.length(); x++) {
                    final char c = line.charAt(x);
                    if (c == ' ') {
                        continue;
                    }

                    final Coordinate coord = new Coordinate(x, rows);
                    final Tile tile = c == '.' ? Tile.OPEN : Tile.WALL;
                    world.put(coord, tile);
                    if (currentPosition == null && rows == 0 && tile == Tile.OPEN) {
                        currentPosition = coord;
                    }
                }
                rows++;
            } else {
                moves = line;
            }
        }

        if (moves == null) {
            throw new IllegalStateException("Failed to find map moves");
        }

        if (currentPosition == null) {
            throw new IllegalStateException("Failed to find starting position");
        }

        // My data and the example data aren't arranged the same >:|
        final Map<Warp, Warp> warps;
        if (cubeSize == 4) {
            warps = calculateWarpsExample();
        } else {
            warps = calculateWarpsMyData();
        }

        final Matcher m = MOVE_PATTERN.matcher(moves);
        while (m.find()) {
            final String move = m.group(1);
            if (move.equals("L")) {
                currentOrientation = currentOrientation.getLeft();
            } else if (move.equals("R")) {
                currentOrientation = currentOrientation.getRight();
            } else {
                final int distance = Integer.parseInt(move);
                for (int i = 0; i < distance; i++) {
                    Coordinate newPosition;
                    switch (currentOrientation) {
                        case LEFT -> newPosition = currentPosition.left();
                        case RIGHT -> newPosition = currentPosition.right();
                        case UP -> newPosition = currentPosition.up();
                        case DOWN -> newPosition = currentPosition.down();
                        default -> throw new IllegalStateException("Failed to determine orientation");
                    }

                    if (!world.containsKey(newPosition)) {
                        final Warp newDetails = warps.get(new Warp(currentPosition, currentOrientation));
                        if (world.get(newDetails.newPosition()) == Tile.OPEN) {
                            currentPosition = newDetails.newPosition();
                            currentOrientation = newDetails.newDirection();
                        }
                    } else {
                        if (world.get(newPosition) == Tile.OPEN) {
                            currentPosition = newPosition;
                        } else {
                            break;
                        }
                    }
                }
            }
        }

        final int facing = switch (currentOrientation) {
            case RIGHT -> 0;
            case DOWN -> 1;
            case LEFT -> 2;
            case UP -> 3;
        };

        return ((currentPosition.getY() + 1) * 1000) + ((currentPosition.getX() + 1) * 4) + facing;
    }


    /*

    My cube is laid out as such:
          2211
          2211
          33
          33
        5544
        5544
        66
        66
     */
    private static Map<Warp, Warp> calculateWarpsMyData() {
        final Map<Warp, Warp> warps = new LinkedHashMap<>();

        // Bottom side 1 -> Right side 3
        for (int x = 100; x < 150; x++) {
            final Coordinate source = new Coordinate(x, 49);
            final Coordinate target = new Coordinate(99, x - 50);
            warps.put(new Warp(source, Direction.DOWN), new Warp(target, Direction.LEFT));
            warps.put(new Warp(target, Direction.RIGHT), new Warp(source, Direction.UP));
        }

        // Right side 1 -> Right side 4 (upside down)
        for (int y = 0; y < 50; y++) {
            final Coordinate source = new Coordinate(149, y);
            final Coordinate target = new Coordinate(99, 149 - y);
            warps.put(new Warp(source, Direction.RIGHT), new Warp(target, Direction.LEFT));
            warps.put(new Warp(target, Direction.RIGHT), new Warp(source, Direction.LEFT));
        }

        // Top side 1 -> Bottom side 6
        for (int x = 100; x < 150; x++) {
            final Coordinate source = new Coordinate(x, 0);
            final Coordinate target = new Coordinate(x - 100, 199);
            warps.put(new Warp(source, Direction.UP), new Warp(target, Direction.UP));
            warps.put(new Warp(target, Direction.DOWN), new Warp(source, Direction.DOWN));

        }

        // Left side 2 -> left side 5 (upside down)
        for (int y = 0; y < 50; y++) {
            final Coordinate source = new Coordinate(50, y);
            final Coordinate target = new Coordinate(0, 149 - y);
            warps.put(new Warp(source, Direction.LEFT), new Warp(target, Direction.RIGHT));
            warps.put(new Warp(target, Direction.LEFT), new Warp(source, Direction.RIGHT));
        }

        // Top side 2 -> left side 6
        for (int x = 50; x < 100; x++) {
            final Coordinate source = new Coordinate(x, 0);
            final Coordinate target = new Coordinate(0, x + 100);
            warps.put(new Warp(source, Direction.UP), new Warp(target, Direction.RIGHT));
            warps.put(new Warp(target, Direction.LEFT), new Warp(source, Direction.DOWN));
        }

        // Left side 3 -> Top side 5
        for (int y = 50; y < 100; y++) {
            final Coordinate source = new Coordinate(50, y);
            final Coordinate target = new Coordinate(y - 50, 100);
            warps.put(new Warp(source, Direction.LEFT), new Warp(target, Direction.DOWN));
            warps.put(new Warp(target, Direction.UP), new Warp(source, Direction.RIGHT));
        }

        // Bottom side 4 -> Right side 6
        for (int x = 50; x < 100; x++) {
            final Coordinate source = new Coordinate(x, 149);
            final Coordinate target = new Coordinate(49, x + 100);
            warps.put(new Warp(source, Direction.DOWN), new Warp(target, Direction.LEFT));
            warps.put(new Warp(target, Direction.RIGHT), new Warp(source, Direction.UP));
        }

        return warps;
    }

    private static Map<Warp, Warp> calculateWarpsExample() {
        final int cubeSize = 4;
        final Map<Warp, Warp> warps = new LinkedHashMap<>();

        // Square 1 top row <-> 2 top side
        for (int x = cubeSize * 2; x < cubeSize * 3; x++) {
            final Coordinate source = new Coordinate(x, 0);
            final Coordinate target = new Coordinate((cubeSize * 3) - (source.getX() + 1), cubeSize);
            warps.put(new Warp(source, Direction.UP), new Warp(target, Direction.DOWN));
            warps.put(new Warp(target, Direction.UP), new Warp(source, Direction.DOWN));
        }

        // Square 1 left side <-> 3 top side
        for (int y = 0; y < cubeSize; y++) {
            final Coordinate source = new Coordinate(cubeSize * 2, y);
            final Coordinate target = new Coordinate(source.getY() + cubeSize, cubeSize);
            warps.put(new Warp(source, Direction.LEFT), new Warp(target, Direction.DOWN));
            warps.put(new Warp(target, Direction.UP), new Warp(source, Direction.RIGHT));
        }

        // Square 1 right side <-> 6 right side
        for (int y = 0; y < cubeSize; y++) {
            final Coordinate source = new Coordinate((cubeSize * 3) - 1, y);
            final Coordinate target = new Coordinate((cubeSize * 4) - 1, (cubeSize * 3) - (source.getY() + 1));
            warps.put(new Warp(source, Direction.RIGHT), new Warp(target, Direction.LEFT));
            warps.put(new Warp(target, Direction.RIGHT), new Warp(source, Direction.LEFT));
        }

        // Square 2 left side <-> 6 bottom side
        for (int y = 0; y < cubeSize; y++) {
            final Coordinate source = new Coordinate(0, cubeSize + y);
            final Coordinate target = new Coordinate((cubeSize * 4) - (y + 1), (cubeSize * 3) - 1);
            warps.put(new Warp(source, Direction.LEFT), new Warp(target, Direction.UP));
            warps.put(new Warp(target, Direction.DOWN), new Warp(source, Direction.RIGHT));
        }

        // Square 2 bottom side <-> 5 bottom side
        for (int x = 0; x < cubeSize; x++) {
            final Coordinate source = new Coordinate(x, (cubeSize * 2) - 1);
            final Coordinate target = new Coordinate((cubeSize * 3) - (x + 1), (cubeSize * 3) - 1);
            warps.put(new Warp(source, Direction.DOWN), new Warp(target, Direction.UP));
            warps.put(new Warp(target, Direction.DOWN), new Warp(source, Direction.UP));
        }

        // Square 3 bottom side <-> 5 left side
        for (int x = 0; x < cubeSize; x++) {
            final Coordinate source = new Coordinate(x + cubeSize, (cubeSize * 2) - 1);
            final Coordinate target = new Coordinate((cubeSize * 2), (cubeSize * 3) - (x + 1));
            warps.put(new Warp(source, Direction.DOWN), new Warp(target, Direction.RIGHT));
            warps.put(new Warp(target, Direction.LEFT), new Warp(source, Direction.UP));
        }

        // Square 4 right side <-> 6 top side
        for (int y = 0; y < cubeSize; y++) {
            final Coordinate source = new Coordinate((cubeSize * 3) - 1, y + cubeSize);
            final Coordinate target = new Coordinate((cubeSize * 4) - (y + 1), (cubeSize * 2));
            warps.put(new Warp(source, Direction.RIGHT), new Warp(target, Direction.DOWN));
            warps.put(new Warp(target, Direction.UP), new Warp(source, Direction.LEFT));
        }

        return warps;
    }

    private record Warp(Coordinate newPosition, Direction newDirection) {
    }
}
