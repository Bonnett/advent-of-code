package uk.co.pete_b.advent.aoc2023;

import uk.co.pete_b.advent.utils.Coordinate;

import java.util.*;

public class Day21 {
    public static int getReachablePlotCount(final List<String> gardenLayout, final int steps) {
        final Garden garden = createGarden(gardenLayout);

        return getReachableSizes(garden, steps).get(steps - 1);
    }

    private static List<Integer> getReachableSizes(final Garden garden, final int steps) {
        final List<Integer> sizes = new ArrayList<>();
        final Set<Coordinate> pointsReached = new HashSet<>(Collections.singleton(garden.start));

        for (int i = 0; i < steps; i++) {
            final Set<Coordinate> newPoints = new HashSet<>();
            final Iterator<Coordinate> iterator = pointsReached.iterator();
            while (iterator.hasNext()) {
                final Coordinate point = iterator.next();
                getAdjacentSpaces(point).stream().filter(coordinate ->
                {
                    int newX;
                    int newY;
                    if (coordinate.getX() > 0) {
                        newX = coordinate.getX() % garden.width;
                    } else {
                        newX = garden.width - ((coordinate.getX() * -1) % garden.width);
                    }

                    if (coordinate.getY() > 0) {
                        newY = coordinate.getY() % garden.height;
                    } else {
                        newY = garden.height - ((coordinate.getY() * -1) % garden.height);
                    }

                    return !garden.rocks.contains(new Coordinate(newX, newY));
                }).forEach(newPoints::add);
                iterator.remove();
            }

            pointsReached.addAll(newPoints);
            sizes.add(pointsReached.size());
        }

        return sizes;
    }

    public static long calculateViaMaths(final List<String> gardenLayout, final long target) {
        final Garden garden = createGarden(gardenLayout);

        final List<Integer> sizes = getReachableSizes(garden, 65 + 131 * 2);

        // These allow us to create a polynomial - numbers courtesy of reddit
        final int a0 = sizes.get(65 - 1);
        final int a1 = sizes.get(65 + 131 - 1);
        final int a2 = sizes.get(65 + 131 * 2 - 1);

        return solvePolynomial(target, garden.height, a0, a1, a2);
    }

    private static long solvePolynomial(final long target, final int height, final int a0, final int a1, final int a2) {
        final long n = target / height;

        final long b1 = a1 - a0;
        final long b2 = a2 - a1;

        return a0 + b1 * n + (n * (n - 1) / 2) * (b2 - b1);
    }


    private static Garden createGarden(final List<String> gardenLayout) {
        final int height = gardenLayout.size();
        final int width = gardenLayout.get(0).length();
        final Set<Coordinate> rocks = new HashSet<>();
        Coordinate start = null;
        for (int y = 0; y < gardenLayout.size(); y++) {
            final String gardenLine = gardenLayout.get(y);
            for (int x = 0; x < gardenLine.length(); x++) {
                final char c = gardenLine.charAt(x);
                if (c == 'S') {
                    start = new Coordinate(x, y);
                } else if (c == '#') {
                    rocks.add(new Coordinate(x, y));
                }
            }
        }

        if (start == null) {
            throw new IllegalStateException();
        }

        return new Garden(rocks, start, height, width);
    }

    private static List<Coordinate> getAdjacentSpaces(final Coordinate position) {
        return Arrays.asList(position.up(), position.left(), position.down(), position.right());
    }

    private record Garden(Set<Coordinate> rocks, Coordinate start, int height, int width) {
    }
}
