package uk.co.pete_b.advent.aoc2019;

import java.text.DecimalFormat;

public class Day05 {
    private static final DecimalFormat FORMAT = new DecimalFormat("0000");

    public static int getDiagnosticCode(final int input, final int[] operations) {
        int returnValue = -1;
        final int[] state = operations.clone();
        int currentPos = 0;
        boolean shouldQuit = false;
        while (!shouldQuit && currentPos < state.length - 1) {
            final String instruction = FORMAT.format(state[currentPos]);
            final String opCode = instruction.substring(2);
            switch (opCode) {
                case "01":
                case "02": {
                    final int valueA = instruction.charAt(1) == '0' ? state[state[currentPos + 1]] : state[currentPos + 1];
                    final int valueB = instruction.charAt(0) == '0' ? state[state[currentPos + 2]] : state[currentPos + 2];
                    final int target = state[currentPos + 3];
                    if (opCode.equalsIgnoreCase("01")) {
                        state[target] = valueA + valueB;
                    } else {
                        state[target] = valueA * valueB;
                    }
                    currentPos += 4;
                    break;
                }
                case "03": {
                    final int valueA = state[currentPos + 1];
                    state[valueA] = input;
                    currentPos += 2;
                    break;
                }
                case "04": {
                    returnValue = instruction.charAt(1) == '0' ? state[state[currentPos + 1]] : state[currentPos + 1];
                    currentPos += 2;
                    break;
                }
                case "05":
                case "06": {
                    final int valueA = instruction.charAt(1) == '0' ? state[state[currentPos + 1]] : state[currentPos + 1];
                    final int valueB = instruction.charAt(0) == '0' ? state[state[currentPos + 2]] : state[currentPos + 2];
                    if ((opCode.equalsIgnoreCase("05")) == (valueA != 0)) {
                        currentPos = valueB;
                    } else {
                        currentPos += 3;
                    }
                    break;
                }
                case "07":
                case "08": {
                    final int valueA = instruction.charAt(1) == '0' ? state[state[currentPos + 1]] : state[currentPos + 1];
                    final int valueB = instruction.charAt(0) == '0' ? state[state[currentPos + 2]] : state[currentPos + 2];
                    final int target = state[currentPos + 3];
                    final boolean shouldOperate = (opCode.equalsIgnoreCase("07")) ? valueA < valueB : valueA == valueB;
                    if (shouldOperate) {
                        state[target] = 1;
                    } else {
                        state[target] = 0;
                    }
                    currentPos += 4;
                    break;
                }
                case "99":
                    shouldQuit = true;
                    break;
                default:
                    throw new IllegalStateException("Something's wrong");
            }
        }

        return returnValue;
    }
}
