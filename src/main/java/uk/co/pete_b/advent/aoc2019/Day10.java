package uk.co.pete_b.advent.aoc2019;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import uk.co.pete_b.advent.utils.Coordinate;

import java.util.*;

public class Day10 {
    public static Answer calculateBestPositionTotal(final String input) {
        final String[] parts = input.trim().split("\r?\n");
        final int height = parts.length;
        final int width = parts[0].length();

        final Set<Coordinate> asteroids = new HashSet<>();

        for (int i = 0; i < height; i++) {
            final String row = parts[i];
            for (int j = 0; j < width; j++) {
                if (row.charAt(j) == '#') {
                    asteroids.add(new Coordinate(j, i));
                }
            }
        }

        Coordinate tempBest = null;
        int highest = -1;

        for (final Coordinate station : asteroids) {
            final Set<Double> visibleAngles = new HashSet<>();
            for (final Coordinate asteroid : asteroids) {
                if (station.equals(asteroid)) {
                    continue;
                }
                visibleAngles.add(calculateAngle(station, asteroid));
            }

            if (visibleAngles.size() > highest) {
                highest = visibleAngles.size();
                tempBest = station;
            }
        }

        int twoHundredthSum = -1;

        if (highest > 200) {
            final Coordinate bestSpot = tempBest;
            final Map<Double, List<Coordinate>> asteroidMap = new TreeMap<>();

            for (final Coordinate asteroid : asteroids) {
                if (bestSpot.equals(asteroid)) {
                    continue;
                }

                asteroidMap.compute(calculateAngle(bestSpot, asteroid), (angle, coordinates) ->
                {
                    if (coordinates == null) {
                        coordinates = new ArrayList<>();
                    }

                    coordinates.add(asteroid);

                    return coordinates;
                });
            }

            // Sort each list by distance from bestSpot
            for (final Map.Entry<Double, List<Coordinate>> entry : asteroidMap.entrySet()) {
                entry.getValue().sort(Comparator.comparingDouble(o -> calculateDistance(bestSpot, o)));
            }

            int destroyedTotal = 0;
            Coordinate destroyed = null;
            while (destroyedTotal < 200) {
                for (final Double angle : asteroidMap.keySet()) {
                    if (asteroidMap.get(angle).isEmpty()) {
                        continue;
                    }

                    destroyed = asteroidMap.get(angle).remove(0);
                    destroyedTotal++;
                    if (destroyedTotal == 200) {
                        break;
                    }
                }
            }

            twoHundredthSum = destroyed.getX() * 100 + destroyed.getY();
        }

        return new Answer(highest, twoHundredthSum);
    }

    private static double calculateDistance(final Coordinate start, final Coordinate point) {
        final int x = point.getX() - start.getX();
        final int y = point.getY() - start.getY();

        return Math.sqrt(x * x + y * y);
    }

    private static double calculateAngle(final Coordinate start, final Coordinate point) {
        final double angle = Math.toDegrees(Math.atan2(point.getY() - start.getY(), point.getX() - start.getX()) + (Math.PI / 2));
        return (angle < 0) ? angle + 360 : angle;
    }

    public static class Answer {
        private final int highestVisible;
        private final int twoHundredthDestroyedSum;

        public Answer(final int highestVisible, final int twoHundredthDestroyedSum) {
            this.highestVisible = highestVisible;
            this.twoHundredthDestroyedSum = twoHundredthDestroyedSum;
        }

        @Override
        public int hashCode() {
            return HashCodeBuilder.reflectionHashCode(this, false);
        }

        @Override
        public boolean equals(final Object otherAnswer) {
            boolean isEqual = false;
            if (otherAnswer instanceof Answer) {
                isEqual = ((Answer) otherAnswer).highestVisible == this.highestVisible && ((Answer) otherAnswer).twoHundredthDestroyedSum == this.twoHundredthDestroyedSum;
            }

            return isEqual;
        }

        @Override
        public String toString() {
            return String.format("Most Visible: %d, 200th Destroyed Sum: %d", highestVisible, twoHundredthDestroyedSum);
        }
    }
}
