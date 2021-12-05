package uk.co.pete_b.advent.aoc2021;

import uk.co.pete_b.advent.utils.Coordinate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day05 {
    public static long calculatePointsWithOverlaps(final List<String> lineDefinitions, final boolean includeDiagonals) {
        final Map<Coordinate, Integer> linePositions = new HashMap<>();

        for (String line : lineDefinitions) {
            final String[] coordinates = line.split(" -> ");
            final String[] startCoordinate = coordinates[0].split(",");
            final String[] endCoordinate = coordinates[1].split(",");
            final Coordinate start = new Coordinate(Integer.parseInt(startCoordinate[0]), Integer.parseInt(startCoordinate[1]));
            final Coordinate end = new Coordinate(Integer.parseInt(endCoordinate[0]), Integer.parseInt(endCoordinate[1]));
            if (start.getX() == end.getX()) {
                final int startY = Math.min(start.getY(), end.getY());
                final int endY = Math.max(start.getY(), end.getY());

                for (int i = startY; i <= endY; i++) {
                    markLinePosition(linePositions, new Coordinate(start.getX(), i));
                }

            } else if (start.getY() == end.getY()) {
                final int startX = Math.min(start.getX(), end.getX());
                final int endX = Math.max(start.getX(), end.getX());

                for (int i = startX; i <= endX; i++) {
                    markLinePosition(linePositions, new Coordinate(i, start.getY()));
                }
            } else if (includeDiagonals) {
                final int startX = Math.min(start.getX(), end.getX());
                final int startY = Math.min(start.getY(), end.getY());
                final int offset = Math.abs(end.getX() - start.getX());

                if (start.equals(new Coordinate(startX, startY)) || end.equals(new Coordinate(startX, startY))) {
                    for (int i = 0; i <= offset; i++) {
                        markLinePosition(linePositions, new Coordinate(i + startX, i + startY));
                    }
                } else if (start.getX() == startX) {
                    for (int i = 0; i <= offset; i++) {
                        markLinePosition(linePositions, new Coordinate(i + start.getX(), start.getY() - i));
                    }
                } else {
                    for (int i = 0; i <= offset; i++) {
                        markLinePosition(linePositions, new Coordinate(i + end.getX(), end.getY() - i));
                    }
                }
            }
        }

        return linePositions.values().stream().filter(x -> x > 1).count();
    }

    private static void markLinePosition(final Map<Coordinate, Integer> hits, final Coordinate coordinate) {
        hits.compute(coordinate, (c, hitCount) -> {
            if (hitCount == null) {
                hitCount = 0;
            }
            hitCount++;

            return hitCount;
        });
    }
}
