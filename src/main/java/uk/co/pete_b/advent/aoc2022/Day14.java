package uk.co.pete_b.advent.aoc2022;

import uk.co.pete_b.advent.utils.Coordinate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day14 {

    private static final Coordinate SAND_START = new Coordinate(500, 0);
    private static final Character ROCK = '#';
    private static final Character SAND = 'o';

    public static int countTotalSand(final List<String> rockPaths, boolean infinteAbyss) {
        final Map<Coordinate, Character> caveMap = new HashMap<>();
        int abyssStart = Integer.MIN_VALUE;
        for (String rockPath : rockPaths) {
            final List<Coordinate> nodes = Arrays.stream(rockPath.split(" -> "))
                    .map(x -> x.split(","))
                    .map(x -> new Coordinate(Integer.parseInt(x[0]), Integer.parseInt(x[1])))
                    .toList();

            for (int i = 0; i < nodes.size() - 1; i++) {
                final Coordinate start = nodes.get(i);
                final Coordinate end = nodes.get(i + 1);

                final int maxY = Math.max(start.getY(), end.getY());
                if (maxY > abyssStart) {
                    abyssStart = maxY;
                }

                if (start.getX() == end.getX()) {
                    int startCoord = Math.min(start.getY(), end.getY());
                    int endCoord = Math.max(start.getY(), end.getY());
                    for (int y = startCoord; y <= endCoord; y++) {
                        caveMap.put(new Coordinate(start.getX(), y), ROCK);
                    }
                } else {
                    int startCoord = Math.min(start.getX(), end.getX());
                    int endCoord = Math.max(start.getX(), end.getX());
                    for (int x = startCoord; x <= endCoord; x++) {
                        caveMap.put(new Coordinate(x, start.getY()), ROCK);
                    }
                }
            }
        }

        if (!infinteAbyss) {
            abyssStart += 2;
        }

        Coordinate sand = SAND_START;
        int sandAdded = 0;

        while ((infinteAbyss && (sand.getY() < abyssStart)) || (!infinteAbyss && !caveMap.containsKey(SAND_START))) {
            final Coordinate down = sand.down();
            final Coordinate downLeft = sand.downLeft();
            final Coordinate downRight = sand.downRight();
            if (caveMap.containsKey(down) && caveMap.containsKey(downLeft) && caveMap.containsKey(downRight)) {
                caveMap.put(sand, SAND);
                sandAdded++;
                sand = SAND_START;
            } else if (caveMap.containsKey(down) && caveMap.containsKey(downLeft)) {
                sand = downRight;
            } else if (caveMap.containsKey(down)) {
                sand = downLeft;
            } else {
                sand = down;
                if (!infinteAbyss && sand.getY() == abyssStart) {
                    caveMap.put(sand.up(), SAND);
                    sandAdded++;
                    sand = SAND_START;
                }
            }
        }

        return sandAdded;
    }
}
