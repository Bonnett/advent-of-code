package uk.co.pete_b.advent.aoc2017;

public class Day01 {
    public static int calculateCaptureNextValue(final String input) {
        int total = 0;

        for (int i = 0; i < input.length(); i++) {
            final int nextIndex = (i == input.length() - 1) ? 0 : i + 1;
            if (input.charAt(i) == input.charAt(nextIndex)) {
                total += Integer.parseInt(String.valueOf(input.charAt(i)));
            }
        }

        return total;
    }

    public static int calculateCaptureHalfway(final String input) {
        int total = 0;

        int halfWay = input.length() / 2;

        for (int i = 0; i < input.length(); i++) {
            final int nextIndex = (i + halfWay) % input.length();
            if (input.charAt(i) == input.charAt(nextIndex)) {
                total += Integer.parseInt(String.valueOf(input.charAt(i)));
            }
        }

        return total;
    }
}
