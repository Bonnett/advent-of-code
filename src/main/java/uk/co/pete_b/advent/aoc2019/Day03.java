package uk.co.pete_b.advent.aoc2019;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import uk.co.pete_b.advent.utils.Coordinate;

import java.util.*;

public class Day03 {
    public static Answer calculateClosestCrossingPoint(final String lineOne, final String lineTwo) {
        final List<Coordinate> routeOne = getRouteCoordinates(lineOne);
        final List<Coordinate> routeTwo = getRouteCoordinates(lineTwo);

        final Set<Coordinate> crossovers = new HashSet<>();

        findCrossovers(routeOne, routeTwo, crossovers);
        findCrossovers(routeTwo, routeOne, crossovers);

        int smallestManhattanDistance = Integer.MAX_VALUE;
        int smallestLength = Integer.MAX_VALUE;

        for (final Coordinate coordinate : crossovers) {
            final int manhattanDist = Math.abs(coordinate.getX()) + Math.abs(coordinate.getY());
            if (smallestManhattanDistance > manhattanDist) {
                smallestManhattanDistance = manhattanDist;
            }

            final int length = routeOne.indexOf(coordinate) + routeTwo.indexOf(coordinate) + 2;
            if (smallestLength > length) {
                smallestLength = length;
            }
        }

        return new Answer(smallestManhattanDistance, smallestLength);
    }

    private static void findCrossovers(final List<Coordinate> routeOne, final List<Coordinate> routeTwo, final Set<Coordinate> crossovers) {
        final Set<Coordinate> setRouteTwo = new LinkedHashSet<>(routeTwo);
        for (final Coordinate pointOnRoute : routeOne) {
            if (setRouteTwo.contains(pointOnRoute)) {
                crossovers.add(pointOnRoute);
            }
        }
    }

    private static List<Coordinate> getRouteCoordinates(final String route) {
        final String[] directions = route.trim().split(",");
        int curX = 0;
        int curY = 0;

        final List<Coordinate> points = new ArrayList<>();
        for (final String direction : directions) {
            final int distance = Integer.parseInt(direction.substring(1));
            switch (direction.charAt(0)) {
                case 'R':
                    for (int i = 0; i < distance; i++) {
                        points.add(new Coordinate(++curX, curY));
                    }
                    break;
                case 'L':
                    for (int i = 0; i < distance; i++) {
                        points.add(new Coordinate(--curX, curY));
                    }
                    break;
                case 'U':
                    for (int i = 0; i < distance; i++) {
                        points.add(new Coordinate(curX, ++curY));
                    }
                    break;
                case 'D':
                    for (int i = 0; i < distance; i++) {
                        points.add(new Coordinate(curX, --curY));
                    }
                    break;
            }
        }

        return points;
    }

    public static class Answer {
        private final int smallestManhattanDistance;
        private final int smallestLength;

        public Answer(final int smallestManhattanDistance, final int stagnantWater) {
            this.smallestManhattanDistance = smallestManhattanDistance;
            this.smallestLength = stagnantWater;
        }

        @Override
        public int hashCode() {
            return HashCodeBuilder.reflectionHashCode(this, false);
        }

        @Override
        public boolean equals(final Object otherAnswer) {
            boolean isEqual = false;
            if (otherAnswer instanceof Answer) {
                isEqual = ((Answer) otherAnswer).smallestManhattanDistance == this.smallestManhattanDistance && ((Answer) otherAnswer).smallestLength == this.smallestLength;
            }

            return isEqual;
        }

        @Override
        public String toString() {
            return String.format("Shortest Manhattan Distance: %d, Shortest Distance: %d", smallestManhattanDistance, smallestLength);
        }
    }
}
