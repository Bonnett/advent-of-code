package uk.co.pete_b.advent.aoc2018;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day19 {
    private static final Pattern INPUT_PATTERN = Pattern.compile("^([a-z]+) ([0-9]+) ([0-9]+) ([0-9]+)$");

    public static int getRegisterZero(final List<String> input, final int registerZeroStart) {
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


        final Computer computer = new Computer(startIp, registerZeroStart, false);

        while (registerZeroStart != 1 || computer.getInstructionPointer() != 1) {
            if (computer.getInstructionPointer() >= operations.size()) {
                break;
            }

            computer.execute(operations.get(computer.getInstructionPointer()));
        }

        int sumOfDivisors = 0;
        final int[] registers = computer.getRegisters();
        for (int i = 1; i <= registers[2]; i++) {
            if (registers[2] % i == 0) {
                sumOfDivisors += i;
            }
        }

        return (registerZeroStart == 0) ? registers[0] : sumOfDivisors;
    }
}
