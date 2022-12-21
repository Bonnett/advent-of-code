package uk.co.pete_b.advent.aoc2022;

import java.util.*;
import java.util.function.BiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day21 {

    public static final Pattern BASE_SUM_PATTERN = Pattern.compile("\\((-?[0-9]+) (.) (-?[0-9]+)\\)");
    public static final Pattern LEFT_SIDE_IS_VALUE_SUM = Pattern.compile("^\\((-?[0-9]+) (.) (.+)\\)$");
    public static final Pattern RIGHT_SIDE_IS_VALUE_SUM = Pattern.compile("^\\((.+) (.) (-?[0-9]+)\\)$");
    private static final BiFunction<Long, Long, Long> ADD_FN = Long::sum;
    private static final BiFunction<Long, Long, Long> MINUS_FN = (x, y) -> x - y;
    private static final BiFunction<Long, Long, Long> MULTIPLICATION_FN = (x, y) -> x * y;
    private static final BiFunction<Long, Long, Long> DIVISION_FN = (x, y) -> x / y;
    private static final BiFunction<Long, Long, Boolean> EQUALITY_FN = Objects::equals;
    public static final String ROOT_MONKEY = "root";
    public static final String HUMAN_PLAYER = "humn";

    public static long calculateRootMonkeysSum(final List<String> monkeySetup) {
        final Map<String, Monkey> monkeys = getMonkeys(monkeySetup);

        return monkeys.get(ROOT_MONKEY).getValue(monkeys);
    }

    public static long findNumberToYell(final List<String> monkeySetup) {
        final Map<String, Monkey> monkeys = getMonkeys(monkeySetup);

        final StringBuilder leftSum = new StringBuilder();
        final StringBuilder rightSum = new StringBuilder();
        monkeys.get(ROOT_MONKEY).getSumAsStrings(leftSum, rightSum, monkeys);

        // Left side of the sum will contain an X, right side will have simplified to a value
        final String simplifiedSum = simplifySum(leftSum);
        final long target = Long.parseLong(simplifySum(rightSum));

        final long answer = finishSum(simplifiedSum, target);
        monkeys.get(HUMAN_PLAYER).sum = answer;

        // Verify that we've got the right number
        if (monkeys.get(ROOT_MONKEY).checkEquality(monkeys)) {
            return answer;
        }

        return -1;
    }

    private static Map<String, Monkey> getMonkeys(List<String> monkeySetup) {
        final Map<String, Monkey> monkeys = new HashMap<>();
        for (String monkeyLine : monkeySetup) {
            final String[] parts = monkeyLine.split(": ");
            final String monkeyName = parts[0];
            final Monkey monkey;
            if (parts[1].contains(" ")) {
                monkey = new Monkey(monkeyName, parts[1]);
            } else {
                monkey = new Monkey(monkeyName, Long.parseLong(parts[1]));
            }
            monkeys.put(monkeyName, monkey);
        }
        return monkeys;
    }

    private static String simplifySum(StringBuilder leftSum) {
        String calculation = leftSum.toString();
        while (true) {
            final Matcher m = BASE_SUM_PATTERN.matcher(calculation);
            if (m.find()) {
                long val1 = Long.parseLong(m.group(1));
                long val2 = Long.parseLong(m.group(3));

                long newVal = 0;
                switch (m.group(2)) {
                    case "+" -> newVal = ADD_FN.apply(val1, val2);
                    case "-" -> newVal = MINUS_FN.apply(val1, val2);
                    case "*" -> newVal = MULTIPLICATION_FN.apply(val1, val2);
                    case "/" -> newVal = DIVISION_FN.apply(val1, val2);
                }

                calculation = calculation.replaceAll(Pattern.quote(m.group(0)), String.valueOf(newVal));
            } else {
                break;
            }
        }

        return calculation;
    }

    private static long finishSum(String simplifiedSum, long target) {
        while (!simplifiedSum.equals("X")) {
            final Matcher startingMatcher = LEFT_SIDE_IS_VALUE_SUM.matcher(simplifiedSum);
            if (startingMatcher.find()) {
                simplifiedSum = startingMatcher.group(3);
                final long value = Long.parseLong(startingMatcher.group(1));
                switch (startingMatcher.group(2)) {
                    case "+" -> target -= value;
                    case "-" -> {
                        // If taking away then we'll end up with -(answer) by adding the value to both sides, so times everything by -1 to correct
                        target *= -1;
                        target += value;
                    }
                    case "*" -> target /= value;
                    case "/" -> target *= value;
                }
            }
            else {
                final Matcher endingMatcher = RIGHT_SIDE_IS_VALUE_SUM.matcher(simplifiedSum);
                if (endingMatcher.find()) {
                    simplifiedSum = endingMatcher.group(1);
                    final long value = Long.parseLong(endingMatcher.group(3));
                    switch (endingMatcher.group(2)) {
                        case "+" -> target -= value;
                        case "-" -> target += value;
                        case "*" -> target /= value;
                        case "/" -> target *= value;
                    }
                }
            }
        }

        return target;
    }

    private static class Monkey {

        private final String monkeyName;
        private boolean hasValue = false;

        private long sum;

        private final List<String> monkeysNeeded = new ArrayList<>();

        private BiFunction<Long, Long, Long> calculation = null;

        private String fn = null;

        public Monkey(String monkeyName, final long sum) {
            this.monkeyName = monkeyName;
            this.sum = sum;
            this.hasValue = true;
        }

        public Monkey(String monkeyName, final String function) {
            this.monkeyName = monkeyName;
            this.fn = function.substring(5, 6);
            switch (function.substring(5, 6)) {
                case "+" -> this.calculation = ADD_FN;
                case "-" -> this.calculation = MINUS_FN;
                case "*" -> this.calculation = MULTIPLICATION_FN;
                case "/" -> this.calculation = DIVISION_FN;
            }
            this.monkeysNeeded.add(function.substring(0, 4));
            this.monkeysNeeded.add(function.substring(7));
        }

        public long getValue(final Map<String, Monkey> monkeys) {
            if (!this.hasValue) {
                final long valueOne = monkeys.get(monkeysNeeded.get(0)).getValue(monkeys);
                final long valueTwo = monkeys.get(monkeysNeeded.get(1)).getValue(monkeys);
                return calculation.apply(valueOne, valueTwo);
            }

            return this.sum;
        }

        public void getSumAsString(final StringBuilder sb, final Map<String, Monkey> monkeys) {
            if (!this.hasValue) {
                sb.append("(");
                monkeys.get(monkeysNeeded.get(0)).getSumAsString(sb, monkeys);
                sb.append(" ").append(fn).append(" ");
                monkeys.get(monkeysNeeded.get(1)).getSumAsString(sb, monkeys);
                sb.append(")");
            } else {
                if (this.monkeyName.equals(HUMAN_PLAYER)) {
                    sb.append("X");
                } else {
                    sb.append(this.sum);
                }
            }
        }

        public void getSumAsStrings(StringBuilder leftSum, StringBuilder rightSum, Map<String, Monkey> monkeys) {
            monkeys.get(monkeysNeeded.get(0)).getSumAsString(leftSum, monkeys);
            monkeys.get(monkeysNeeded.get(1)).getSumAsString(rightSum, monkeys);
        }

        public boolean checkEquality(Map<String, Monkey> monkeys) {
            final long valueOne = monkeys.get(monkeysNeeded.get(0)).getValue(monkeys);
            final long valueTwo = monkeys.get(monkeysNeeded.get(1)).getValue(monkeys);

            return EQUALITY_FN.apply(valueOne, valueTwo);
        }
    }
}
