package uk.co.pete_b.advent.aoc2018;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day21 {
    private static final Pattern INPUT_PATTERN = Pattern.compile("^([a-z]+) ([0-9]+) ([0-9]+) ([0-9]+)$");

    public static int runProgram(final List<String> input, final boolean breakOnFirstCheck) {
        final int startIp = Integer.valueOf(input.get(0).substring(4));
        final List<String> instructions = new ArrayList<>(input.subList(1, input.size()));

        final List<Computer.Operation> operations = new ArrayList<>();
        for (final String instruction : instructions) {
            final Matcher matcher = INPUT_PATTERN.matcher(instruction);

            if (matcher.matches()) {
                final int[] inputs = new int[3];
                final String operation = matcher.group(1);
                for (int i = 1; i < matcher.groupCount(); i++) {
                    inputs[i - 1] = Integer.parseInt(matcher.group(i + 1));
                }

                operations.add(new Computer.Operation(operation, inputs));
            }
        }

        final Computer computer = new Computer(startIp, 0, false);

        final Set<Integer> possibleExitValues = new HashSet<>();
        int lastUniqueExitValue = 0;

        while (computer.getInstructionPointer() < operations.size()) {
            // Operation 28 is a check which can cause our program to exit - work out what value it wants
            if (computer.getInstructionPointer() == 28) {
                if (breakOnFirstCheck || !possibleExitValues.add(computer.getRegisters()[1])) {
                    break;
                } else {
                    lastUniqueExitValue = computer.getRegisters()[1];
                }
            }

            computer.execute(operations.get(computer.getInstructionPointer()));
        }

        return breakOnFirstCheck ? computer.getRegisters()[1] : lastUniqueExitValue;
    }
}
