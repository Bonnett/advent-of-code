package uk.co.pete_b.advent.aoc2020;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day02 {

    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^([0-9]+)-([0-9]+) ([a-z]): (.*)$");

    public static long countValidPasswordsRule1(final List<String> passwords) {
        return passwords.stream().filter(Day02::isPasswordValidRule1).count();
    }

    public static long countValidPasswordsRule2(final List<String> passwords) {
        return passwords.stream().filter(Day02::isPasswordValidRule2).count();
    }

    private static boolean isPasswordValidRule1(final String password) {
        final Matcher matcher = PASSWORD_PATTERN.matcher(password);
        if (matcher.find()) {
            final int minOccurs = Integer.parseInt(matcher.group(1));
            final int maxOccurs = Integer.parseInt(matcher.group(2));
            final char targetChar = matcher.group(3).charAt(0);
            final String pass = matcher.group(4);
            int totalMatched = 0;
            for (int i = 0; i < pass.length(); i++) {
                if (pass.charAt(i) == targetChar) {
                    totalMatched++;
                }
            }

            return minOccurs <= totalMatched && totalMatched <= maxOccurs;
        }

        return false;
    }

    private static boolean isPasswordValidRule2(final String password) {
        final Matcher matcher = PASSWORD_PATTERN.matcher(password);
        if (matcher.find()) {
            final int positionOne = Integer.parseInt(matcher.group(1)) - 1;
            final int positionTwo = Integer.parseInt(matcher.group(2)) - 1;
            final char targetChar = matcher.group(3).charAt(0);
            final String pass = matcher.group(4);

            return pass.charAt(positionOne) == targetChar ^ pass.charAt(positionTwo) == targetChar;
        }

        return false;
    }
}
