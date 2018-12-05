package uk.co.pete_b.advent.aoc2018;

import java.util.Stack;

public class Day05 {
    private static final int UPPER_LOWER_DIFFERENCE = 'a' - 'A';

    public static int reduceStringLength(final String input) {
        final Stack<Character> characters = new Stack<>();

        for (int i = 0; i < input.length(); i++) {
            final char latestChar = input.charAt(i);
            if (characters.size() == 0) {
                characters.push(latestChar);
            } else if (Math.abs(characters.peek() - latestChar) == UPPER_LOWER_DIFFERENCE) {
                characters.pop();
            } else {
                characters.push(latestChar);
            }
        }

        return characters.size();
    }

    public static int removeAndReduce(final String input) {
        int minLength = Integer.MAX_VALUE;
        for (int i = 'A'; i <= 'Z'; i++) {
            final String newString = input.replaceAll(String.valueOf((char) i), "").replaceAll(String.valueOf((char) (i + UPPER_LOWER_DIFFERENCE)), "");
            final int newLength = reduceStringLength(newString);
            if (newLength < minLength) {
                minLength = newLength;
            }
        }

        return minLength;
    }
}
