package uk.co.pete_b.advent.aoc2020;

import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiFunction;

public class Day18 {

    private static final char SPACE = ' ';
    private static final char OPEN_PARENTHESIS = '(';
    private static final char CLOSE_PARENTHESIS = ')';
    private static final char MULTIPLY = '*';
    private static final char ADD = '+';

    private enum Operation {ADD, MULTIPLY}

    public static long calculateSums(final String input, boolean samePrecedence) {
        final String[] sums = input.split("\n");
        long total = 0;

        final BiFunction<AtomicLong, String, Integer> sumFunction = (samePrecedence) ? Day18::samePrecedenceSum : Day18::differentPrecedenceSum;

        for (String sum : sums) {
            final AtomicLong sumTotal = new AtomicLong(0);
            sumFunction.apply(sumTotal, sum);
            total += sumTotal.get();
        }

        return total;
    }

    private static int samePrecedenceSum(final AtomicLong total, final String sum) {
        Operation currentOperation = Operation.ADD;

        for (int i = 0; i < sum.length(); i++) {
            final char currentChar = sum.charAt(i);
            if (currentChar != SPACE) {
                if (currentChar == OPEN_PARENTHESIS) {
                    final AtomicLong bracketVal = new AtomicLong();
                    i += samePrecedenceSum(bracketVal, sum.substring(i + 1));
                    if (currentOperation == Operation.ADD) {
                        total.addAndGet(bracketVal.get());
                    } else {
                        total.set(total.get() * bracketVal.get());
                    }
                } else if (currentChar == CLOSE_PARENTHESIS) {
                    return i + 1;
                } else if (currentChar == MULTIPLY) {
                    currentOperation = Operation.MULTIPLY;
                } else if (currentChar == ADD) {
                    currentOperation = Operation.ADD;
                } else {
                    final StringBuilder nextNumber = new StringBuilder();
                    nextNumber.append(currentChar);
                    for (int j = i + 1; j < sum.length(); j++) {
                        final char nextChar = sum.charAt(j);
                        if (nextChar != OPEN_PARENTHESIS && nextChar != CLOSE_PARENTHESIS && nextChar != SPACE && nextChar != MULTIPLY && nextChar != ADD) {
                            nextNumber.append(nextChar);
                        } else {
                            break;
                        }
                    }
                    i += nextNumber.length() - 1;
                    final int number = Integer.parseInt(nextNumber.toString());
                    if (currentOperation == Operation.ADD) {
                        total.addAndGet(number);
                    } else {
                        total.set(total.get() * number);
                    }
                }
            }
        }

        return sum.length();
    }

    private static int differentPrecedenceSum(final AtomicLong total, final String sum) {

        for (int i = 0; i < sum.length(); i++) {
            final char currentChar = sum.charAt(i);
            if (currentChar != SPACE) {
                if (currentChar == OPEN_PARENTHESIS) {
                    final AtomicLong bracketVal = new AtomicLong();
                    i += differentPrecedenceSum(bracketVal, sum.substring(i + 1));
                    total.addAndGet(bracketVal.get());
                } else if (currentChar == CLOSE_PARENTHESIS) {
                    return i + 1;
                } else if (currentChar == MULTIPLY) {
                    final AtomicLong bracketVal = new AtomicLong();
                    i += differentPrecedenceSum(bracketVal, sum.substring(i + 1));
                    total.set(total.get() * bracketVal.get());
                    // We treat multiply as a virtual parenthesis so return here
                    return i + 1;
                } else if (currentChar != ADD) {
                    final StringBuilder nextNumber = new StringBuilder();
                    nextNumber.append(currentChar);
                    for (int j = i + 1; j < sum.length(); j++) {
                        final char nextChar = sum.charAt(j);
                        if (nextChar != OPEN_PARENTHESIS && nextChar != CLOSE_PARENTHESIS && nextChar != SPACE && nextChar != MULTIPLY && nextChar != ADD) {
                            nextNumber.append(nextChar);
                        } else {
                            break;
                        }
                    }
                    i += nextNumber.length() - 1;

                    final int number = Integer.parseInt(nextNumber.toString());
                    total.addAndGet(number);
                }
            }
        }

        return sum.length();
    }
}
