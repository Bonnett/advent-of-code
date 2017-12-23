package uk.co.pete_b.advent.aoc2017;

import java.util.HashMap;
import java.util.Map;

public class Day23 {

    private static final String NUMBER_REGEX = "-?[0-9]+";

    public static long getAnswer(final String input) {
        final Map<String, Long> registers = new HashMap<>();
        final String[] instructions = input.split("\r?\n");

        int operationToExecute = 0;
        int timesMulCalled = 0;

        while (true) {
            final String[] instruction = instructions[operationToExecute].split(" ");
            switch (instruction[0]) {
                case "set": {
                    final String action = instruction[2];
                    if (action.matches(NUMBER_REGEX)) {
                        registers.put(instruction[1], Long.valueOf(action));
                    } else {
                        registers.put(instruction[1], registers.getOrDefault(action, 0L));
                    }
                    break;
                }
                case "sub": {
                    final String register = instruction[1];
                    final long startVal = registers.getOrDefault(register, 0L);
                    final String action = instruction[2];
                    if (action.matches(NUMBER_REGEX)) {
                        registers.put(instruction[1], startVal - Long.valueOf(instruction[2]));
                    } else {
                        registers.put(instruction[1], startVal - registers.getOrDefault(action, 0L));
                    }
                    break;
                }
                case "mul": {
                    final String register = instruction[1];
                    final long startVal = registers.getOrDefault(register, 0L);
                    final String action = instruction[2];
                    if (action.matches(NUMBER_REGEX)) {
                        registers.put(instruction[1], startVal * Long.valueOf(instruction[2]));
                    } else {
                        registers.put(instruction[1], startVal * registers.getOrDefault(action, 0L));
                    }
                    timesMulCalled++;
                    break;
                }
                case "jnz": {
                    final String register = instruction[1];
                    if (register.matches(NUMBER_REGEX)) {
                        final long startVal = Long.valueOf(register);
                        if (startVal != 0L) {
                            final String action = instruction[2];
                            if (action.matches(NUMBER_REGEX)) {
                                operationToExecute += Long.valueOf(instruction[2]) - 1;
                            } else {
                                operationToExecute += registers.getOrDefault(action, 0L) - 1;
                            }
                        }
                    } else {
                        final long startVal = registers.getOrDefault(register, 0L);
                        if (startVal != 0L) {
                            final String action = instruction[2];
                            if (action.matches(NUMBER_REGEX)) {
                                operationToExecute += Long.valueOf(instruction[2]) - 1;
                            } else {
                                operationToExecute += registers.getOrDefault(action, 0L) - 1;
                            }
                        }
                    }
                    break;
                }
            }

            operationToExecute++;

            if (operationToExecute >= instructions.length) {
                break;
            }
        }

        return timesMulCalled;
    }

    public static long optimisedAlgorithm() {
        long b, c, d, e, f;
        long g = 0;
        long h = 0;

        b = 84;
        c = b;

        b *= 100;
        b -= -100000;
        c = b;
        c -= -17000;

        while (true) {
            f = 1;
            d = 2;
            do {
                // The inner loop sets f = 0 if b is a multiple of d
                if (b % d == 0) {
                    f = 0;
                    break;
                }

                /*
                e = 2;
                do {
                    g = d;
                    g *= e;
                    g -= b;
                    if (g == 0) {
                        f = 0;
                    }

                    e -= -1;
                    g = e;
                    g -= b;
                } while (g != 0);*/

                d -= -1;
                g = d;
                g -= b;
            } while (g != 0);

            if (f == 0) {
                h -= -1;
            }

            g = b;
            g -= c;

            if (g == 0) {
                break;
            }

            b -= -17;
        }

        return h;
    }
}
