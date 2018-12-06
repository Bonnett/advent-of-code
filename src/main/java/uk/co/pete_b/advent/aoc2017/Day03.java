package uk.co.pete_b.advent.aoc2017;

import uk.co.pete_b.advent.utils.Coordinate;
import uk.co.pete_b.advent.utils.Direction;

import java.util.HashMap;
import java.util.Map;

public class Day03 {

    private long answerPart2 = -1;
    private final Map<Integer, Coordinate> gridReverse = new HashMap<>();
    private final Map<Integer, Long> totals = new HashMap<>();

    public Day03(final int maxLength) {
        boolean solvedPart2 = false;
        final Map<Coordinate, Integer> grid = new HashMap<>();

        Coordinate target = new Coordinate(0, 0);
        Direction heading = Direction.LEFT;

        for (int i = 0; i < maxLength + 1; i++) {
            while (grid.containsKey(target)) {
                if (heading == Direction.LEFT) {
                    if ((target.getX() == 0 && target.getY() == 0) || grid.containsKey(target.up())) {
                        target = target.left();
                    } else {
                        target = target.up();
                        heading = Direction.UP;
                    }
                } else if (heading == Direction.UP) {
                    if (grid.containsKey(target.right())) {
                        target = target.up();
                    } else {
                        target = target.right();
                        heading = Direction.RIGHT;
                    }
                } else if (heading == Direction.RIGHT) {
                    if (grid.containsKey(target.down())) {
                        target = target.right();
                    } else {
                        target = target.down();
                        heading = Direction.DOWN;
                    }
                } else if (heading == Direction.DOWN) {
                    if (grid.containsKey(target.left())) {
                        target = target.down();
                    } else {
                        target = target.left();
                        heading = Direction.LEFT;
                    }
                }
            }

            int key = (i + 1);

            if (!solvedPart2) {
                long total;
                if (key == 1) {
                    total = 1;
                } else {
                    total = 0;
                    if (grid.containsKey(target.up())) {
                        total += totals.get(grid.get(target.up()));
                    }
                    if (grid.containsKey(target.upLeft())) {
                        total += totals.get(grid.get(target.upLeft()));
                    }
                    if (grid.containsKey(target.left())) {
                        total += totals.get(grid.get(target.left()));
                    }
                    if (grid.containsKey(target.downLeft())) {
                        total += totals.get(grid.get(target.downLeft()));
                    }
                    if (grid.containsKey(target.down())) {
                        total += totals.get(grid.get(target.down()));
                    }
                    if (grid.containsKey(target.downRight())) {
                        total += totals.get(grid.get(target.downRight()));
                    }
                    if (grid.containsKey(target.right())) {
                        total += totals.get(grid.get(target.right()));
                    }
                    if (grid.containsKey(target.upRight())) {
                        total += totals.get(grid.get(target.upRight()));
                    }
                }

                if (total > maxLength) {
                    solvedPart2 = true;
                    answerPart2 = total;
                }

                totals.put(key, total);
            }

            grid.put(target, key);
            gridReverse.put(key, target);
        }
    }

    public int calculateDistance(final int value) {
        final Coordinate position = gridReverse.get(value);
        return Math.abs(position.getX()) + Math.abs(position.getY());
    }

    public long getTotal(final int value) {
        return totals.get(value);
    }

    public long getAnswerPart2() {
        return answerPart2;
    }
}
