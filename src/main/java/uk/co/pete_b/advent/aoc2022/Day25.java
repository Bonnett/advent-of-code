package uk.co.pete_b.advent.aoc2022;

import java.util.List;

public class Day25 {

    public static String getSNAFUNumber(final List<String> input) {
        return convertToSNAFU(input.stream().mapToLong(Day25::convertToDecimal).sum());
    }

    private static String convertToSNAFU(long decimal) {
        final StringBuilder sb = new StringBuilder();

        long mod = decimal % 5;
        long div = decimal / 5;
        while (true) {
            if (mod == 1) {
                sb.append("1");
            } else if (mod == 2) {
                sb.append("2");
            } else if (mod == 0) {
                sb.append("0");
            } else if (mod == 3) {
                sb.append("=");
                div += 1;
            } else if (mod == 4) {
                sb.append("-");
                div += 1;
            }

            if (div == 0) {
                break;
            }

            mod = div % 5;
            div = div / 5;
        }

        return sb.reverse().toString();
    }

    private static long convertToDecimal(final String snafu) {
        long total = 0;
        for (int i = 0; i < snafu.length(); i++) {
            final long power = (long) Math.pow(5, i);
            char value = snafu.charAt(snafu.length() - (i + 1));
            switch (value) {
                case '2' -> total += power * 2;
                case '1' -> total += power;
                case '0' -> total += 0;
                case '-' -> total += power * -1;
                case '=' -> total += power * -2;
            }
        }

        return total;
    }
}
