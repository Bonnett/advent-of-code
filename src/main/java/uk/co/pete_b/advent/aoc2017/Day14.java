package uk.co.pete_b.advent.aoc2017;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class Day14 {

    private static final int GRID_SIZE = 128;

    public static class Answers {
        private final int numberOfSquaresUsed;
        private final int numberOfGroups;

        Answers(int score, int nonCancelled) {
            this.numberOfSquaresUsed = score;
            this.numberOfGroups = nonCancelled;
        }

        public int getNumberOfSquaresUsed() {
            return numberOfSquaresUsed;
        }

        public int getNumberOfGroups() {
            return numberOfGroups;
        }
    }

    public static class GridSquare {
        private char value;

        GridSquare(char value) {
            this.value = value;
        }
    }

    public static Answers calculateSquaresUsed(final String input) {
        int used = 0;

        final List<List<GridSquare>> grid = new ArrayList<>();

        for (int i = 0; i < GRID_SIZE; i++) {
            final String hash = Day10.calculateKnotHash(input + "-" + i);
            final StringBuilder rowBuilder = new StringBuilder();

            for (int j = 0; j < hash.length(); j++) {
                String rowToAppend = StringUtils.leftPad(Integer.toBinaryString(Integer.parseInt(hash.substring(j, j + 1), 16)), 4, "0");
                rowBuilder.append(rowToAppend.replaceAll("0", " ").replaceAll("1", "#"));
            }

            final List<GridSquare> rowList = new ArrayList<>();
            final String row = rowBuilder.toString();
            for (int j = 0; j < row.length(); j++) {
                rowList.add(new GridSquare(row.charAt(j)));
            }
            used += row.replaceAll(" ", "").length();
            grid.add(rowList);
        }

        int groupCount = 0;
        int numberInGroups = 0;
        int currX = 0;
        int currY = 0;

        while (numberInGroups != used) {
            GridSquare square = grid.get(currY).get(currX);
            if (square.value == '#') {
                groupCount++;
                square.value = 'X';
                numberInGroups++;
                numberInGroups += checkAdjacent(grid, currX, currY, groupCount);
            }

            if (currX < GRID_SIZE - 1) {
                currX++;
            } else if (currY < GRID_SIZE - 1) {
                currY++;
                currX = 0;
            }
        }

        return new Answers(used, groupCount);
    }

    private static int checkAdjacent(final List<List<GridSquare>> grid, final int currX, final int currY, final int groupCount) {
        int numberInGroups = 0;
        if (currX + 1 < GRID_SIZE) {
            final GridSquare square = grid.get(currY).get(currX + 1);

            if (square.value == '#') {
                square.value = 'X';
                numberInGroups++;
                numberInGroups += checkAdjacent(grid, currX + 1, currY, groupCount);
            }
        }
        if (currX - 1 >= 0) {
            final GridSquare square = grid.get(currY).get(currX - 1);

            if (square.value == '#') {
                square.value = 'X';
                numberInGroups++;
                numberInGroups += checkAdjacent(grid, currX - 1, currY, groupCount);
            }
        }
        if (currY + 1 < GRID_SIZE) {
            final GridSquare square = grid.get(currY + 1).get(currX);

            if (square.value == '#') {
                square.value = 'X';
                numberInGroups++;
                numberInGroups += checkAdjacent(grid, currX, currY + 1, groupCount);
            }
        }
        if (currY - 1 >= 0) {
            final GridSquare square = grid.get(currY - 1).get(currX);

            if (square.value == '#') {
                square.value = 'X';
                numberInGroups++;
                numberInGroups += checkAdjacent(grid, currX, currY - 1, groupCount);
            }
        }

        return numberInGroups;
    }
}
