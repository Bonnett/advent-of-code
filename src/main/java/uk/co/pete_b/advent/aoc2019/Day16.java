package uk.co.pete_b.advent.aoc2019;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class Day16 {
    private static final int ITERATIONS = 100;
    private static final int[] BASE_PATTERN = new int[]{0, 1, 0, -1};

    public static String getFirstEightDigits(final String inputString) {
        final int inputLength = inputString.length();
        List<Integer> inputSignal = new ArrayList<>();
        for (int i = 0; i < inputLength; i++) {
            inputSignal.add(Character.getNumericValue(inputString.charAt(i)));
        }

        final int[][] multipliers = new int[inputLength][inputLength];
        for (int i = 0; i < inputLength; i++) {
            int written = -1;
            for (int j = 0; j < inputLength + 1 && written <= inputLength; j++) {
                for (int k = 0; k < i + 1 && written < inputLength; k++) {
                    written++;
                    if (written == 0) {
                        continue;
                    }
                    multipliers[i][written - 1] = BASE_PATTERN[(j % BASE_PATTERN.length)];
                }
            }
        }

        for (int i = 0; i < ITERATIONS; i++) {
            List<Integer> outputSignal = new ArrayList<>();
            for (int outputIndex = 0; outputIndex < inputLength; outputIndex++) {
                int newValue = 0;
                for (int value = 0; value < inputLength; value++) {
                    newValue += inputSignal.get(value) * multipliers[outputIndex][value];
                }
                outputSignal.add(Math.abs(newValue % 10));
            }
            inputSignal = outputSignal;
        }

        return StringUtils.join(inputSignal, "").substring(0, 8);
    }

    public static String getDigitsWithTenThousandRepeat(final String input) {
        final int offset = Integer.parseInt(input.substring(0, 7));
        final String repeated = input.repeat(10000);
        final int inputLength = repeated.length();

        final List<Integer> inputSignal = new ArrayList<>();
        for (int i = 0; i < inputLength; i++) {
            inputSignal.add(Character.getNumericValue(repeated.charAt(i)));
        }

        for (int i = 0; i < ITERATIONS; i++) {
            for (int j = inputLength - 1; j > offset - 1; j--) {
                inputSignal.set(j - 1, (inputSignal.get(j - 1) + inputSignal.get(j)) % 10);
            }
        }

        return StringUtils.join(inputSignal.subList(offset, offset + 8), "");
    }
}
