package uk.co.pete_b.advent.aoc2018;

import uk.co.pete_b.advent.utils.Coordinate;

public class Day11 {
    private static final int GRID_SIZE = 300;

    public static Answer findLargestCell(final int serialNumber, final int subGridSize) {
        return findLargestCell(generateGrid(serialNumber), subGridSize);
    }

    public static Answer findLargestTotalPower(final int serialNumber) {
        final int[][] grid = generateGrid(serialNumber);
        int largestPower = 0;
        int gridSizeForLargestPower = 0;
        Coordinate maxCoordinate = null;

        // After running up to GRID_SIZE I found the answer to be < 20 for all examples
        for (int i = 1; i <= 20; i++) {
            final Answer answer = findLargestCell(grid, i);
            if (answer.getSubGridPower() > largestPower) {
                largestPower = answer.getSubGridPower();
                gridSizeForLargestPower = i;
                maxCoordinate = answer.getCoordinate();
            }
        }

        return new Answer(maxCoordinate, gridSizeForLargestPower, largestPower);
    }

    private static Answer findLargestCell(final int[][] grid, final int subGridSize) {
        final int[][] subGrid = generateSubGrid(grid, subGridSize);

        int maxSize = 0;
        Coordinate maxCoordinate = null;
        for (int y = 0; y < subGrid.length; y++) {
            for (int x = 0; x < subGrid[y].length; x++) {
                if (subGrid[y][x] > maxSize) {
                    maxCoordinate = new Coordinate(x + 1, y + 1);
                    maxSize = subGrid[y][x];
                }
            }
        }

        return new Answer(maxCoordinate, subGridSize, maxSize);
    }

    private static int[][] generateSubGrid(final int[][] grid, final int subGridSize) {
        final int[][] subGrid = new int[grid.length - subGridSize][grid.length - subGridSize];
        for (int y = 0; y < grid.length; y++) {
            if (subGrid.length < grid[y].length - subGridSize) {
                subGrid[y] = new int[grid[y].length - subGridSize];
            }
            for (int x = 0; x < grid[y].length; x++) {
                populateSubGrid(subGrid, subGridSize, x, y, grid[y][x]);
            }
        }

        return subGrid;
    }

    private static int[][] generateGrid(int serialNumber) {
        final int[][] grid = new int[GRID_SIZE][GRID_SIZE];
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[y].length; x++) {
                grid[y][x] = calculatePower(serialNumber, x + 1, y + 1);
            }
        }

        return grid;
    }

    private static void populateSubGrid(final int[][] subGrid, final int subGridSize, final int x, final int y, final int cellPower) {
        for (int i = y; i > y - subGridSize && i >= 0; i--) {
            for (int j = x; j > x - subGridSize && j >= 0; j--) {
                if (j < subGrid.length && i < subGrid.length) {
                    subGrid[i][j] += cellPower;
                }
            }
        }
    }

    static int calculatePower(final int serialNumber, final int x, final int y) {
        final int rackId = x + 10;
        int power = rackId * ((rackId * y) + serialNumber) / 100;
        power -= 10 * (power / 10) + 5;

        return power;
    }

    public static class Answer {
        private final Coordinate coordinate;
        private final int subGridSize;
        private final int subGridPower;

        public Answer(final Coordinate coordinate, final int subGridSize, final int subGridPower) {
            this.coordinate = coordinate;
            this.subGridSize = subGridSize;
            this.subGridPower = subGridPower;
        }

        public Coordinate getCoordinate() {
            return coordinate;
        }

        public int getSubGridSize() {
            return subGridSize;
        }

        public int getSubGridPower() {
            return subGridPower;
        }

        @Override
        public String toString() {
            return String.format("Coordinate: %s, Grid Size: %d, Power: %d", coordinate, subGridSize, subGridPower);
        }
    }
}
