package uk.co.pete_b.advent.aoc2017;

import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

public class Day21 {

    public static int getPixelsOn(final int iterations, final String rules) {
        final List<String> grid = new ArrayList<>(Arrays.asList(".#.", "..#", "###"));
        final Map<String, List<String>> ruleBook = new HashMap<>();

        final String[] ruleList = rules.split("\r?\n");
        for (String ruleEntry : ruleList) {
            String[] ruleParts = ruleEntry.split(" => ");
            final List<String> output = Arrays.stream(ruleParts[1].split("/")).collect(Collectors.toList());
            ruleParts = ruleParts[0].split("/");
            final StringBuilder normal = new StringBuilder();
            final StringBuilder reverse = new StringBuilder();
            for (String part : ruleParts) {
                normal.append(part);
                reverse.append(StringUtils.reverse(part));
            }

            ruleBook.put(normal.toString(), output);
            ruleBook.put(normal.reverse().toString(), output);
            ruleBook.put(reverse.toString(), output);
            ruleBook.put(reverse.reverse().toString(), output);

            final StringBuilder rotate = new StringBuilder();
            final StringBuilder rotateReverse = new StringBuilder();
            for (int i = 0; i < ruleParts.length; i++) {
                for (String rulePart : ruleParts) {
                    rotate.append(rulePart.charAt(i));
                    reverse.insert(0, rulePart.charAt(i));
                }
            }

            ruleBook.put(rotate.toString(), output);
            ruleBook.put(rotate.reverse().toString(), output);
            ruleBook.put(rotateReverse.toString(), output);
            ruleBook.put(rotateReverse.reverse().toString(), output);
        }

        for (int iter = 0; iter < iterations; iter++) {
            if (grid.size() % 2 == 0) {
                final int gridSize = 2;
                final List<StringBuilder> newGrid = new ArrayList<>();
                for (int i = 0; i < (grid.size() / gridSize) * 3; i++) {
                    newGrid.add(new StringBuilder());
                }

                for (int i = 0; i < (grid.size() / gridSize); i++) {
                    for (int j = 0; j < (grid.size() / gridSize); j++) {
                        final List<String> pattern = ruleBook.get(grid.get(i * gridSize).substring(j * gridSize, (j * gridSize) + gridSize) +
                                grid.get(i * gridSize + 1).substring(j * gridSize, (j * gridSize) + gridSize));

                        for (int k = 0; k < pattern.size(); k++) {
                            newGrid.get(i * 3 + k).append(pattern.get(k));
                        }
                    }
                }

                grid.clear();
                for (StringBuilder sb : newGrid) {
                    grid.add(sb.toString());
                }
            } else {
                final int gridSize = 3;
                final List<StringBuilder> newGrid = new ArrayList<>();
                for (int i = 0; i < (grid.size() / gridSize) * 4; i++) {
                    newGrid.add(new StringBuilder());
                }

                for (int i = 0; i < (grid.size() / gridSize); i++) {
                    for (int j = 0; j < (grid.size() / gridSize); j++) {
                        final List<String> pattern = ruleBook.get(grid.get(i * gridSize).substring(j * gridSize, (j * gridSize) + gridSize) +
                                grid.get(i * gridSize + 1).substring(j * gridSize, (j * gridSize) + gridSize) +
                                grid.get(i * gridSize + 2).substring(j * gridSize, (j * gridSize) + gridSize));

                        for (int k = 0; k < pattern.size(); k++) {
                            newGrid.get(i * 4 + k).append(pattern.get(k));
                        }
                    }
                }

                grid.clear();
                for (StringBuilder sb : newGrid) {
                    grid.add(sb.toString());
                }
            }
        }

        return StringUtils.join(grid, "").replaceAll("\\.", "").length();
    }
}
