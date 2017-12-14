package uk.co.pete_b.advent.aoc2017;

import java.util.*;

public class Day13 {

    public static class Answers {
        private final int noDelaySeverity;
        private final int delayToGetThrough;

        Answers(int score, int nonCancelled) {
            this.noDelaySeverity = score;
            this.delayToGetThrough = nonCancelled;
        }

        public int getNoDelaySeverity() {
            return noDelaySeverity;
        }

        public int getDelayToGetThrough() {
            return delayToGetThrough;
        }
    }

    public static Answers getSeverity(final String input) {

        final Map<Integer, Integer> gridSizes = new HashMap<>();
        final Map<Integer, Integer> scannerPosition = new HashMap<>();

        for (String line : input.split("\r?\n")) {
            final String[] parts = line.split(": ");
            final int column = Integer.parseInt(parts[0]);
            // Length 4 = 0,1,2,3,2,1 -> 0,1,2,3,4,5
            gridSizes.put(column, 2 * Integer.parseInt(parts[1]) - 2);
            scannerPosition.put(column, 0);
        }

        final int max = gridSizes.keySet().stream().max(Integer::compareTo).get();

        final List<Integer> keys = new ArrayList<>(gridSizes.keySet());
        Collections.sort(keys);

        Answers answers;
        int iteration = 0;
        int collisionScore = 0;

        while (true) {
            int numCollisions = 0;

            setStartPositions(iteration, gridSizes, scannerPosition);

            for (int i = 0; i < max + 1; i++) {
                if (scannerPosition.get(i) != null && scannerPosition.get(i) == 0) {
                    if (iteration == 0) {
                        collisionScore += i * ((gridSizes.get(i) + 2) / 2);
                    }

                    numCollisions++;
                    if (iteration != 0) {
                        break;
                    }
                }

                advanceScanner(gridSizes, scannerPosition);
            }

            if (numCollisions == 0) {
                answers = new Answers(collisionScore, iteration);
                break;
            }

            iteration++;

            for (Integer column : gridSizes.keySet()) {
                scannerPosition.put(column, 0);
            }

        }


        return answers;
    }

    private static void advanceScanner(final Map<Integer, Integer> gridSizes, final Map<Integer, Integer> scannerPosition) {
        for (Map.Entry<Integer, Integer> positions : scannerPosition.entrySet()) {
            int nextValue = positions.getValue() + 1;
            if (nextValue == gridSizes.get(positions.getKey())) {
                nextValue = 0;
            }

            scannerPosition.put(positions.getKey(), nextValue);
        }
    }

    private static void setStartPositions(final int startPosition, final Map<Integer, Integer> gridSizes, final Map<Integer, Integer> scannerPosition) {
        for (Map.Entry<Integer, Integer> positions : scannerPosition.entrySet()) {
            scannerPosition.put(positions.getKey(), startPosition % gridSizes.get(positions.getKey()));
        }
    }
}
