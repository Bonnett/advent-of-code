package uk.co.pete_b.advent.aoc2018;

import uk.co.pete_b.advent.utils.Coordinate3D;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day23 {

    public static int findStrongestNanobotRange(final List<String> input) {
        final List<Nanobot> nanobots = new ArrayList<>();
        for (final String line : input) {
            nanobots.add(new Nanobot(line));
        }

        Collections.sort(nanobots);
        final Nanobot strongest = nanobots.get(0);

        int nanobotsInRange = 0;
        for (final Nanobot bot : nanobots) {
            final int distance = calculateDistance(strongest.position, bot.position);

            if (distance <= strongest.signalStrength) {
                nanobotsInRange++;
            }
        }

        return nanobotsInRange;
    }

    public static int findDistanceToBestSpot(final List<String> input) {
        final List<Nanobot> nanobots = new ArrayList<>();
        for (final String line : input) {
            nanobots.add(new Nanobot(line));
        }

        int maxX = nanobots.stream().mapToInt(x -> x.position.getX()).max().orElse(Integer.MAX_VALUE);
        int minX = nanobots.stream().mapToInt(x -> x.position.getX()).min().orElse(Integer.MIN_VALUE);

        int maxY = nanobots.stream().mapToInt(x -> x.position.getY()).max().orElse(Integer.MAX_VALUE);
        int minY = nanobots.stream().mapToInt(x -> x.position.getY()).min().orElse(Integer.MIN_VALUE);

        int maxZ = nanobots.stream().mapToInt(x -> x.position.getZ()).max().orElse(Integer.MAX_VALUE);
        int minZ = nanobots.stream().mapToInt(x -> x.position.getZ()).min().orElse(Integer.MIN_VALUE);

        final Coordinate3D origin = new Coordinate3D(0, 0, 0);
        Coordinate3D bestPoint = null;

        int gridSize = Math.max(Math.max(maxZ - minZ, maxX - minX), maxY - minY);

        while (gridSize > 0) {

            int largestInGrid = 0;

            for (int z = minZ; z <= maxZ; z += gridSize) {
                for (int y = minY; y <= maxY; y += gridSize) {
                    for (int x = minX; x <= maxX; x += gridSize) {
                        final Coordinate3D point = new Coordinate3D(x, y, z);
                        int numberInGrid = 0;
                        for (final Nanobot bot : nanobots) {
                            final int distance = calculateDistance(point, bot.position);
                            if (distance - bot.signalStrength < gridSize) {
                                numberInGrid++;
                            }
                        }

                        if (numberInGrid > largestInGrid) {
                            largestInGrid = numberInGrid;
                            bestPoint = point;
                        } else if (numberInGrid == largestInGrid) {
                            if (bestPoint == null || calculateDistance(origin, point) < calculateDistance(origin, bestPoint)) {
                                bestPoint = point;
                            }
                        }
                    }
                }
            }

            minX = bestPoint.getX() - gridSize;
            maxX = bestPoint.getX() + gridSize;

            minY = bestPoint.getY() - gridSize;
            maxY = bestPoint.getY() + gridSize;

            minZ = bestPoint.getZ() - gridSize;
            maxZ = bestPoint.getZ() + gridSize;

            gridSize = gridSize / 2;
        }

        return calculateDistance(origin, bestPoint);
    }

    private static int calculateDistance(final Coordinate3D pointOne, final Coordinate3D pointTwo) {
        return Math.abs(pointOne.getX() - pointTwo.getX()) +
                Math.abs(pointOne.getY() - pointTwo.getY()) +
                Math.abs(pointOne.getZ() - pointTwo.getZ());
    }

    private static class Nanobot implements Comparable<Nanobot> {
        private static final Pattern LINE_PATTERN = Pattern.compile("pos=<([^,]+),([^,]+),([^,]+)>, r=([^,]+)");

        private final Coordinate3D position;
        private final int signalStrength;

        public Nanobot(final String input) {
            final Matcher matcher = LINE_PATTERN.matcher(input);

            if (matcher.matches()) {
                this.position = new Coordinate3D(Integer.valueOf(matcher.group(1)), Integer.valueOf(matcher.group(2)), Integer.valueOf(matcher.group(3)));
                this.signalStrength = Integer.valueOf(matcher.group(4));
            } else {
                throw new IllegalArgumentException("Invalid input");
            }
        }

        @Override
        public int compareTo(Nanobot other) {
            return Integer.compare(other.signalStrength, this.signalStrength);
        }

        @Override
        public String toString() {
            return position.toString() + " signalStrength: " + signalStrength;
        }
    }
}
