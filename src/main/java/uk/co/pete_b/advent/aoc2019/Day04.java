package uk.co.pete_b.advent.aoc2019;

import java.util.HashMap;
import java.util.Map;

public class Day04 {
    public static boolean isNumberValid(final int number) {
        boolean notGreater = true;
        boolean hasPair = false;
        final char[] numberStr = String.valueOf(number).toCharArray();
        for (int i = 0; i < numberStr.length - 1; i++) {
            if (numberStr[i] > numberStr[i + 1]) {
                notGreater = false;
                break;
            } else if (!hasPair && numberStr[i] == numberStr[i + 1]) {
                hasPair = true;
            }
        }

        return notGreater && hasPair;
    }

    public static boolean isNumberValidWithAdditionalCheck(final int number) {
        if (isNumberValid(number)) {
            final char[] numberStr = String.valueOf(number).toCharArray();
            final Map<Character, Integer> count = new HashMap<>();
            for (final char c : numberStr) {
                count.compute(c, (character, integer) -> {
                    if (integer == null) {
                        integer = 0;
                    }
                    integer++;
                    return integer;
                });
            }

            return count.containsValue(2);
        }

        return false;
    }
}

