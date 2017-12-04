package uk.co.pete_b.advent.aoc2017;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.HashMap;
import java.util.Map;

public class Day03 {

    private enum Direction {
        LEFT, UP, RIGHT, DOWN
    }

    private static class CoOrd {
        private final int x;
        private final int y;

        CoOrd(final int x, final int y) {
            this.x = x;
            this.y = y;
        }

        CoOrd left() {
            return new CoOrd(x + 1, y);
        }

        CoOrd right() {
            return new CoOrd(x - 1, y);
        }

        CoOrd up() {
            return new CoOrd(x, y + 1);
        }

        CoOrd down() {
            return new CoOrd(x, y - 1);
        }

        CoOrd upLeft() {
            return up().left();
        }

        CoOrd upRight() {
            return up().right();
        }

        CoOrd downLeft() {
            return down().left();
        }

        CoOrd downRight() {
            return down().right();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;

            if (o == null || getClass() != o.getClass()) return false;

            CoOrd coOrd = (CoOrd) o;

            return new EqualsBuilder()
                    .append(x, coOrd.x)
                    .append(y, coOrd.y)
                    .isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder(17, 37)
                    .append(y)
                    .toHashCode();
        }

        @Override
        public String toString() {
            return String.format("[%d, %d]", x, y);
        }
    }

    private long answerPart2 = -1;
    private final Map<Integer, CoOrd> gridReverse = new HashMap<>();
    private final Map<Integer, Long> totals = new HashMap<>();

    public Day03(final int maxLength) {
        boolean solvedPart2 = false;
        final Map<CoOrd, Integer> grid = new HashMap<>();

        CoOrd target = new CoOrd(0, 0);
        Direction heading = Direction.LEFT;

        for (int i = 0; i < maxLength + 1; i++) {
            while (grid.containsKey(target)) {
                if (heading == Direction.LEFT) {
                    if ((target.x == 0 && target.y == 0) || grid.containsKey(target.up())) {
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
        final CoOrd position = gridReverse.get(value);
        return Math.abs(position.x) + Math.abs(position.y);
    }

    public long getTotal(final int value) {
        return totals.get(value);
    }

    public long getAnswerPart2() {
        return answerPart2;
    }
}
