package uk.co.pete_b.advent.aoc2020;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Day08 {
    public static int findAccumulatorValue(final String instructions) {
        final String[] operations = instructions.split("\n");

        return getAccumulatorValue(operations, true);
    }

    public static int fixAndFindAccumulatorValue(final String instructions) {
        final String[] operations = instructions.split("\n");

        String oldOperation;
        for (int opToChange = 0; opToChange < operations.length; opToChange++) {
            oldOperation = operations[opToChange];
            if (oldOperation.startsWith("acc")) {
                continue;
            } else {
                operations[opToChange] = oldOperation.replaceAll("nop", "xxx").replaceAll("jmp", "nop").replaceAll("xxx", "jmp");
            }

            final int accumulator = getAccumulatorValue(operations, false);
            if (accumulator == -1) {
                operations[opToChange] = oldOperation;
            } else {
                return accumulator;
            }
        }

        return -1;
    }

    private static int getAccumulatorValue(final String[] operations, boolean returnAccumulatorOnLoop) {
        int accumulator = 0;
        int index = 0;

        final Set<Integer> instructionsUsed = new HashSet<>(Collections.singletonList(index));

        while (true) {
            final String[] operation = operations[index].split(" ");
            switch (operation[0]) {
                case "nop" -> index++;
                case "acc" -> {
                    index++;
                    accumulator += Integer.parseInt(operation[1]);
                }
                case "jmp" -> index += Integer.parseInt(operation[1]);
            }

            if (instructionsUsed.contains(index)) {
                if (!returnAccumulatorOnLoop) {
                    return -1;
                }
                break;
            } else if (index == operations.length) {
                break;
            }

            instructionsUsed.add(index);
        }

        return accumulator;
    }
}
