package uk.co.pete_b.advent.aoc2023;

import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class Day13 {
    public static int sumOfReflections(final List<String> allPatterns) {
        int rowSum = 0;
        int colSum = 0;

        final List<List<char[]>> patterns = getPatterns(allPatterns);

        for (List<char[]> pattern : patterns) {
            int horizontalRow = getHorizontalReflectionRow(pattern, -1);

            if (horizontalRow != -1) {
                rowSum += horizontalRow;
            } else {
                colSum += getHorizontalReflectionRow(rotateClockwise(pattern), -1);
            }
        }

        return (100 * rowSum) + colSum;
    }

    public static int sumOfReflectionsWithSmudge(final List<String> allPatterns) {
        int rowSum = 0;
        int colSum = 0;

        final List<List<char[]>> patterns = getPatterns(allPatterns);

        final List<Pair<List<char[]>, List<char[]>>> patternPairs = patterns.stream().map(x -> Pair.of(x, rotateClockwise(x))).toList();

        for (Pair<List<char[]>, List<char[]>> pair : patternPairs) {
            final int leftCount = getRowCount(pair.getLeft());
            final int rightCount = getRowCount(pair.getRight());
            if (leftCount != -1) {
                rowSum += leftCount;
            } else if (rightCount != -1) {
                colSum += rightCount;
            }
        }

        return (100 * rowSum) + colSum;
    }

    private static int getRowCount(final List<char[]> normal) {
        final int currentRowValue = getHorizontalReflectionRow(normal, -1);
        for (int y = 0; y < normal.size(); y++) {
            for (int x = 0; x < normal.get(y).length; x++) {
                final List<char[]> newPattern = normal.stream().map(arr -> Arrays.copyOf(arr, arr.length)).toList();
                char c = newPattern.get(y)[x];
                newPattern.get(y)[x] = c == '.' ? '#' : '.';
                int rowCount = getHorizontalReflectionRow(newPattern, currentRowValue);

                if (rowCount != -1 && rowCount != currentRowValue) {
                    return rowCount;
                }
            }
        }

        return -1;
    }

    private static List<List<char[]>> getPatterns(List<String> allPatterns) {
        final List<List<char[]>> patterns = new ArrayList<>();
        final List<char[]> currentPattern = new ArrayList<>();
        for (String line : allPatterns) {
            if (line.isEmpty()) {
                if (!currentPattern.isEmpty()) {
                    patterns.add(new ArrayList<>(currentPattern));
                    currentPattern.clear();
                }
                continue;
            }

            currentPattern.add(line.toCharArray());
        }

        patterns.add(currentPattern);

        return patterns;
    }

    private static List<char[]> rotateClockwise(final List<char[]> pattern) {
        final int currentLength = pattern.size();
        final int currentWidth = pattern.get(0).length;
        final List<StringBuilder> rotatedBuilders = IntStream.range(0, currentWidth).mapToObj(x -> new StringBuilder()).toList();
        for (int y = 0; y < currentWidth; y++) {
            for (int x = 0; x < currentLength; x++) {
                rotatedBuilders.get(y).insert(0, pattern.get(x)[y]);
            }
        }

        return rotatedBuilders.stream().map(StringBuilder::toString).map(String::toCharArray).toList();
    }

    private static int getHorizontalReflectionRow(final List<char[]> pattern, final int ignoredValue) {
        final List<char[]> checkedPattern = new ArrayList<>();
        checkedPattern.add(pattern.get(0));
        int horizontalRow = -1;
        for (int i = 1; i < pattern.size(); i++) {
            final String previousLine = new String(pattern.get(i - 1));
            final String currentLine = new String(pattern.get(i));
            if (previousLine.equals(currentLine) && checkListsMirror(checkedPattern, pattern.subList(i, pattern.size())) && i != ignoredValue) {
                horizontalRow = i;
                break;
            } else {
                checkedPattern.add(pattern.get(i));
            }
        }
        return horizontalRow;
    }

    private static boolean checkListsMirror(final List<char[]> leftSide, final List<char[]> rightSide) {
        boolean match = true;
        final int iterations = Math.min(leftSide.size(), rightSide.size());
        for (int i = 0; i < iterations && match; i++) {
            match = new String(leftSide.get(leftSide.size() - (i + 1))).equals(new String(rightSide.get(i)));
        }

        return match;
    }
}
