package uk.co.pete_b.advent.aoc2017;

import uk.co.pete_b.advent.utils.Direction;

public class Day19 {

    public static class Answers {
        private final String pathNavigated;
        private final int numberOfSteps;

        Answers(final String pathNavigated, final int numberOfSteps) {
            this.pathNavigated = pathNavigated;
            this.numberOfSteps = numberOfSteps;
        }

        public String getPathNavigated() {
            return pathNavigated;
        }

        public int getNumberOfSteps() {
            return numberOfSteps;
        }
    }

    public static Answers navigatePath(final String input) {
        final StringBuilder path = new StringBuilder();
        int numSteps = 0;

        final String[] lines = input.split("\r?\n");
        final char[][] grid = new char[lines.length][];
        for (int i = 0; i < lines.length; i++) {
            grid[i] = lines[i].toCharArray();
        }

        // find start point
        int x = 0;
        int y = 0;
        Direction heading = Direction.DOWN;
        for (int i = 0; i < grid[y].length; i++) {
            if (grid[y][i] == '|') {
                x = i;
                break;
            }
        }

        boolean foundEnd = false;
        while (!foundEnd) {
            numSteps++;
            final char currSquare = grid[y][x];
            switch (currSquare) {
                case '|':
                case '-': {
                    if (heading == Direction.DOWN) {
                        y++;
                    } else if (heading == Direction.UP) {
                        y--;
                    } else if (heading == Direction.LEFT) {
                        x--;
                    } else if (heading == Direction.RIGHT) {
                        x++;
                    }
                    break;
                }
                case '+': {
                    if (heading == Direction.DOWN || heading == Direction.UP) {
                        if (x == 0) {
                            heading = Direction.RIGHT;
                            x++;
                        } else if (x == grid[y].length - 1) {
                            heading = Direction.LEFT;
                            x--;
                        } else if (grid[y][x + 1] == ' ') {
                            heading = Direction.LEFT;
                            x--;
                        } else {
                            heading = Direction.RIGHT;
                            x++;
                        }
                    } else {
                        if (y == 0) {
                            heading = Direction.DOWN;
                            y++;
                        } else if (y == grid.length - 1) {
                            heading = Direction.UP;
                            y--;
                        } else if (grid[y + 1][x] == ' ') {
                            heading = Direction.UP;
                            y--;
                        } else {
                            heading = Direction.DOWN;
                            y++;
                        }
                    }
                    break;
                }
                case ' ': {
                    // Deduct the last step & exit
                    foundEnd = true;
                    numSteps--;
                    break;
                }
                default: {
                    path.append(currSquare);
                    if (heading == Direction.DOWN) {
                        y++;
                    } else if (heading == Direction.UP) {
                        y--;
                    } else if (heading == Direction.LEFT) {
                        x--;
                    } else if (heading == Direction.RIGHT) {
                        x++;
                    }
                    break;
                }
            }
        }

        return new Answers(path.toString(), numSteps);
    }
}
