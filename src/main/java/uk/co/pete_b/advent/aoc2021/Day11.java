package uk.co.pete_b.advent.aoc2021;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import uk.co.pete_b.advent.utils.Coordinate;

import java.util.*;

public class Day11 {
    public static Answer flashOctopuses(final List<String> grid) {
        final Map<Coordinate, Octopus> octopusMap = new HashMap<>();
        final List<Octopus> octopuses = new ArrayList<>();
        final int height = grid.size();
        final int width = grid.get(0).length();

        for (int y = 0; y < height; y++) {
            final String row = grid.get(y);
            for (int x = 0; x < width; x++) {
                final Coordinate point = new Coordinate(x, y);
                final int value = ((int) row.charAt(x)) - 48;
                final Octopus octopus = new Octopus(point, value);
                octopusMap.put(point, octopus);
                octopuses.add(octopus);
            }
        }

        int totalFlashes = 0;
        int totalFlashesAt100 = 0;
        int iteration = 0;
        while (true) {
            iteration++;

            final Set<Octopus> flashedOctopuses = new HashSet<>();
            final Set<Octopus> newFlashes = new HashSet<>();
            for (Octopus octopus : octopuses) {
                if (octopus.increment() > 9) {
                    flashedOctopuses.add(octopus);
                    newFlashes.add(octopus);
                }
            }
            totalFlashes += flashedOctopuses.size();

            while (!newFlashes.isEmpty()) {
                final List<Octopus> additionalFlashes = new ArrayList<>();
                for (Octopus octopus : newFlashes) {
                    checkForFlash(flashedOctopuses, additionalFlashes, octopusMap.get(octopus.position.up()));
                    checkForFlash(flashedOctopuses, additionalFlashes, octopusMap.get(octopus.position.upLeft()));
                    checkForFlash(flashedOctopuses, additionalFlashes, octopusMap.get(octopus.position.left()));
                    checkForFlash(flashedOctopuses, additionalFlashes, octopusMap.get(octopus.position.downLeft()));
                    checkForFlash(flashedOctopuses, additionalFlashes, octopusMap.get(octopus.position.down()));
                    checkForFlash(flashedOctopuses, additionalFlashes, octopusMap.get(octopus.position.downRight()));
                    checkForFlash(flashedOctopuses, additionalFlashes, octopusMap.get(octopus.position.right()));
                    checkForFlash(flashedOctopuses, additionalFlashes, octopusMap.get(octopus.position.upRight()));
                }
                totalFlashes += additionalFlashes.size();
                newFlashes.clear();
                newFlashes.addAll(additionalFlashes);

            }

            int resetCount = 0;
            for (Octopus octopus : octopuses) {
                if (octopus.resetEnergy()) {
                    resetCount++;
                }
            }

            if (resetCount == 100) {
                break;
            }

            if (iteration == 100) {
                totalFlashesAt100 = totalFlashes;
            }
        }

        return new Answer(totalFlashesAt100, iteration);
    }

    private static void checkForFlash(Set<Octopus> octopuses, List<Octopus> flashedOctopuses, Octopus octopus) {
        if (octopus != null && octopus.increment() > 9 && !octopuses.contains(octopus)) {
            flashedOctopuses.add(octopus);
            octopuses.add(octopus);
        }
    }

    private static final class Octopus {
        private final Coordinate position;
        private int value;

        private Octopus(final Coordinate position, final int value) {
            this.position = position;
            this.value = value;
        }

        private int increment() {
            this.value++;

            return this.value;
        }

        private boolean resetEnergy() {
            if (value > 9) {
                value = 0;
                return true;
            }
            return false;
        }
    }

    public static class Answer {
        private final int totalFlashesAfter100Iterations;
        private final int stepWhereAllOctopusFlash;

        Answer(int totalFlashesAfter100Iterations, int middleCorrectionScore) {
            this.totalFlashesAfter100Iterations = totalFlashesAfter100Iterations;
            this.stepWhereAllOctopusFlash = middleCorrectionScore;
        }

        @Override
        public int hashCode() {
            return HashCodeBuilder.reflectionHashCode(this, false);
        }

        @Override
        public boolean equals(final Object otherAnswer) {
            boolean isEqual = false;
            if (otherAnswer instanceof Answer) {
                isEqual = ((Answer) otherAnswer).totalFlashesAfter100Iterations == this.totalFlashesAfter100Iterations && ((Answer) otherAnswer).stepWhereAllOctopusFlash == this.stepWhereAllOctopusFlash;
            }

            return isEqual;
        }

        @Override
        public String toString() {
            return String.format("Total Flashes after 100 Iterations: %d, Iteration where all flash: %d", totalFlashesAfter100Iterations, stepWhereAllOctopusFlash);
        }
    }
}
