package uk.co.pete_b.advent.aoc2023;

import java.util.*;
import java.util.stream.IntStream;

public class Day15 {
    public static int sumHASHAlgorithm(final String inputStrings) {
        final String[] parts = inputStrings.split(",");

        return Arrays.stream(parts).mapToInt(Day15::runHASHAlgorithm).sum();
    }

    private static int runHASHAlgorithm(final String part) {
        int partSum = 0;
        for (int i = 0; i < part.length(); i++) {
            partSum += part.charAt(i);
            partSum *= 17;
            partSum %= 256;
        }

        return partSum;
    }

    public static int runHASHMAPAlgorithm(final String inputOperations) {
        int sum = 0;

        final String[] operations = inputOperations.trim().split(",");
        final List<LinkedHashMap<String, Integer>> boxes = new ArrayList<>();

        IntStream.range(0, 256).forEach(x -> boxes.add(new LinkedHashMap<>()));

        for (String operation : operations) {
            if (operation.contains("=")) {
                final String[] opDetails = operation.split("=");
                final int boxNumber = runHASHAlgorithm(opDetails[0]);
                final int focalLength = Integer.parseInt(opDetails[1]);
                boxes.get(boxNumber).put(opDetails[0], focalLength);
            } else if (operation.endsWith("-")) {
                final String label = operation.substring(0, operation.length() - 1);
                final int boxNumber = runHASHAlgorithm(label);
                boxes.get(boxNumber).remove(label);
            }
        }

        for (int boxNumber = 0; boxNumber < boxes.size(); boxNumber++) {
            final LinkedHashMap<String, Integer> box = boxes.get(boxNumber);
            final List<Integer> values = new ArrayList<>(box.values());
            for (int slotNumber = 0; slotNumber < values.size(); slotNumber++) {
                sum += (boxNumber + 1) * (slotNumber + 1) * values.get(slotNumber);
            }
        }

        return sum;
    }
}
