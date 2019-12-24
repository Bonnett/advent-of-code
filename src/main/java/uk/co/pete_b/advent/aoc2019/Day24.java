package uk.co.pete_b.advent.aoc2019;

import uk.co.pete_b.advent.utils.Coordinate;

import java.util.*;

public class Day24 {

    public static final char BUG = '#';
    public static final char SPACE = '.';

    public static long calculateBioDiversity(final List<String> layout) {
        Map<Coordinate, Character> grid = new HashMap<>();
        for (int y = 0; y < layout.size(); y++) {
            final char[] line = layout.get(y).toCharArray();
            for (int x = 0; x < line.length; x++) {
                grid.put(new Coordinate(x, y), line[x]);
            }
        }

        final Set<Map<Coordinate, Character>> history = new HashSet<>();
        while (!history.contains(grid)) {
            history.add(grid);
            grid = advanceOneMinute(grid);
        }

        return grid.entrySet().stream().filter(x -> x.getValue() == BUG).mapToLong(Day24::calculateScore).sum();
    }

    private static long calculateScore(Map.Entry<Coordinate, Character> square) {
        final Coordinate coordinate = square.getKey();
        return (long) Math.pow(2, coordinate.getY() * 5 + coordinate.getX());
    }

    private static Map<Coordinate, Character> advanceOneMinute(Map<Coordinate, Character> grid) {
        final Map<Coordinate, Character> nextStage = new HashMap<>();
        for (Map.Entry<Coordinate, Character> entry : grid.entrySet()) {
            int numberOfAdjacentBugs = 0;
            if (grid.getOrDefault(entry.getKey().up(), SPACE) == BUG) {
                numberOfAdjacentBugs++;
            }
            if (grid.getOrDefault(entry.getKey().left(), SPACE) == BUG) {
                numberOfAdjacentBugs++;
            }
            if (grid.getOrDefault(entry.getKey().down(), SPACE) == BUG) {
                numberOfAdjacentBugs++;
            }
            if (grid.getOrDefault(entry.getKey().right(), SPACE) == BUG) {
                numberOfAdjacentBugs++;
            }

            if (entry.getValue() == BUG) {
                nextStage.put(entry.getKey(), numberOfAdjacentBugs == 1 ? BUG : SPACE);
            } else {
                nextStage.put(entry.getKey(), numberOfAdjacentBugs == 1 || numberOfAdjacentBugs == 2 ? BUG : SPACE);
            }
        }

        return nextStage;
    }
}
