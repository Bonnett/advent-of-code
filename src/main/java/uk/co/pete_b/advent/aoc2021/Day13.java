package uk.co.pete_b.advent.aoc2021;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.pete_b.advent.utils.Coordinate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day13 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day13.class);

    public static int calculateNumberOfDots(final List<String> rules, final boolean stopAtOne) {
        final Set<Coordinate> coordinates = new HashSet<>();
        final List<String> foldRules = new ArrayList<>();
        int width = 0;
        int height = 0;
        for (String rule : rules) {
            if (rule.isEmpty()) {
                continue;
            }

            if (rule.startsWith("fold")) {
                foldRules.add(rule);
            } else {
                final String[] coords = rule.split(",");
                final int x = Integer.parseInt(coords[0]);
                final int y = Integer.parseInt(coords[1]);
                width = Math.max(x, width);
                height = Math.max(y, height);
                coordinates.add(new Coordinate(x, y));
            }
        }
        // Factor in starting at 0
        width++;
        height++;

        int[][] grid = new int[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                grid[y][x] = coordinates.contains(new Coordinate(x, y)) ? 1 : 0;
            }
        }

        for (String foldRule : foldRules) {
            String axis = foldRule.substring(11, 12);
            int value = Integer.parseInt(foldRule.substring(13));

            if (axis.equals("y")) {
                int newHeight = height - value;
                if (height % 2 == 1) {
                    newHeight--;
                }
                final int[][] newGrid = new int[newHeight][width];
                System.arraycopy(grid, 0, newGrid, 0, newHeight);

                for (int y = height - 1; y > newHeight; y--) {
                    for (int x = 0; x < grid[y].length; x++) {
                        newGrid[(value - (y - value))][x] |= grid[y][x];
                    }
                }

                height = newHeight;
                grid = newGrid;
            } else {
                int newWidth = width - value;
                if (width % 2 == 1) {
                    newWidth--;
                }
                final int[][] newGrid = new int[height][newWidth];
                for (int y = 0; y < newGrid.length; y++) {
                    System.arraycopy(grid[y], 0, newGrid[y], 0, newWidth);
                }
                for (int y = 0; y < newGrid.length; y++) {
                    for (int x = value + 1; x < width; x++) {
                        newGrid[y][(value - (x - value))] |= grid[y][x];
                    }
                }

                width = newWidth;
                grid = newGrid;
            }

            if (stopAtOne) {
                break;
            }
        }

        return debugGrid(grid, !stopAtOne);
    }

    private static int debugGrid(int[][] grid, boolean printOutput) {
        int total = 0;
        final StringBuilder sb = new StringBuilder();
        for (int[] ints : grid) {
            for (int anInt : ints) {
                sb.append((anInt == 1) ? "#" : " ");
                total += anInt;
            }
            sb.append("\r\n");
        }
        sb.append("\r\n");
        if (printOutput) {

            LOGGER.info("\r\n{}", sb);
        }

        return total;
    }
}
