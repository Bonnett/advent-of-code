package uk.co.pete_b.advent.aoc2017;

import org.apache.commons.lang3.StringUtils;
import uk.co.pete_b.advent.Utils.Direction;

public class Day22 {

    private static final int GRID_SIZE = 1001;

    public static int getVirusInfections(final String inputMap) {
        final char[][] grid = getInputMap(inputMap);

        int numberOfInfections = 0;

        int currX = (GRID_SIZE - 1) / 2;
        int currY = (GRID_SIZE - 1) / 2;
        Direction heading = Direction.UP;

        for (int i = 0; i < 10000; i++) {
            if (grid[currY][currX] == '.') {
                numberOfInfections++;
                grid[currY][currX] = '#';
                heading = heading.getLeft();
            } else {
                heading = heading.getRight();
                grid[currY][currX] = '.';
            }

            switch (heading) {
                case UP:
                    currY--;
                    break;
                case DOWN:
                    currY++;
                    break;
                case LEFT:
                    currX--;
                    break;
                case RIGHT:
                    currX++;
                    break;
            }
        }

        return numberOfInfections;
    }

    public static int getVirusInfectionsWithWeakening(final String inputMap) {

        final char[][] grid = getInputMap(inputMap);

        int numberOfInfections = 0;

        int currX = (GRID_SIZE - 1) / 2;
        int currY = (GRID_SIZE - 1) / 2;
        Direction heading = Direction.UP;

        for (int i = 0; i < 10000000; i++) {
            if (grid[currY][currX] == '.') {
                heading = heading.getLeft();
                grid[currY][currX] = 'W';
            } else if (grid[currY][currX] == 'W') {
                numberOfInfections++;
                grid[currY][currX] = '#';
            } else if (grid[currY][currX] == '#') {
                grid[currY][currX] = 'F';
                heading = heading.getRight();
            } else {
                // Reverse
                heading = heading.getRight().getRight();
                grid[currY][currX] = '.';
            }

            switch (heading) {
                case UP:
                    currY--;
                    break;
                case DOWN:
                    currY++;
                    break;
                case LEFT:
                    currX--;
                    break;
                case RIGHT:
                    currX++;
                    break;
            }
        }

        return numberOfInfections;
    }

    private static char[][] getInputMap(final String inputMap) {
        final String[] inputLines = inputMap.split("\r?\n");
        final char[][] grid = new char[GRID_SIZE][GRID_SIZE];
        final String filler = StringUtils.repeat('.', GRID_SIZE);
        final int startPoint = (GRID_SIZE - inputLines[0].length()) / 2;
        final int endPoint = startPoint + inputLines[0].length();
        for (int i = 0; i < GRID_SIZE; i++) {
            if (i < startPoint || i >= endPoint) {
                grid[i] = filler.toCharArray();
            } else {
                String subString = filler.substring(endPoint);
                grid[i] = (subString + inputLines[i - startPoint] + subString).toCharArray();
            }
        }

        return grid;
    }
}
