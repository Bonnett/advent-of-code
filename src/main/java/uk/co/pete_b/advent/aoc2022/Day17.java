package uk.co.pete_b.advent.aoc2022;

import org.apache.commons.lang3.ArrayUtils;
import uk.co.pete_b.advent.utils.Coordinate;

import java.util.*;
import java.util.stream.Collectors;

public class Day17 {

    public static final int ROCK_CACHE_HEIGHT = 35;

    public static int calculateTowerHeight(final String windDirections) {
        final Deque<Character> wind = new ArrayDeque<>(Arrays.asList(ArrayUtils.toObject(windDirections.trim().toCharArray())));

        final Deque<Rock> fallingRocks = createRocks();

        final Set<Coordinate> stoppedRocks = new HashSet<>();

        // Just add the base of the cave
        for (int i = 0; i < 7; i++) {
            stoppedRocks.add(new Coordinate(i, -1));
        }

        int maxRockHeight = 0;
        for (int i = 0; i < 2022; i++) {
            final Rock nextRockTemplate = fallingRocks.removeFirst();
            fallingRocks.addLast(nextRockTemplate);

            final Rock rock = new Rock(nextRockTemplate, 2, 3 + maxRockHeight);

            while (true) {
                final char windDirection = wind.removeFirst();
                wind.addLast(windDirection);
                if (windDirection == '>') {
                    if (rock.rightMost < 6) {
                        final Set<Coordinate> newPos = rock.getRightShift();
                        if (newPos.stream().noneMatch(stoppedRocks::contains)) {
                            rock.shiftRight();
                        }
                    }
                } else if (windDirection == '<') {
                    if (rock.leftMost > 0) {
                        final Set<Coordinate> newPos = rock.getLeftShift();
                        if (newPos.stream().noneMatch(stoppedRocks::contains)) {
                            rock.shiftLeft();
                        }
                    }
                }

                final Set<Coordinate> newPos = rock.getDownShift();
                if (newPos.stream().noneMatch(stoppedRocks::contains)) {
                    rock.shiftDown();
                } else {
                    stoppedRocks.addAll(rock.squares);
                    maxRockHeight = stoppedRocks.stream().mapToInt(Coordinate::getY).max().orElseThrow() + 1;
                    break;
                }
            }
        }

        return maxRockHeight;
    }

    public static long calculateTowerHeightViaCycles(final String windDirections, final long totalRocks) {
        final Deque<Character> wind = new ArrayDeque<>(Arrays.asList(ArrayUtils.toObject(windDirections.trim().toCharArray())));

        final Deque<Rock> fallingRocks = createRocks();

        final Set<Coordinate> stoppedRocks = new HashSet<>();

        // Just add the base of the cave
        for (int i = 0; i < 7; i++) {
            stoppedRocks.add(new Coordinate(i, -1));
        }

        final Map<String, CaveState> caveStates = new HashMap<>();

        boolean cycleFound = false;
        long heightOffset = 0;

        int maxRockHeight = 0;
        for (long i = 0; i < totalRocks; i++) {
            final Rock nextRockTemplate = fallingRocks.removeFirst();
            fallingRocks.addLast(nextRockTemplate);

            final Rock rock = new Rock(nextRockTemplate, 2, 3 + maxRockHeight);

            while (true) {
                final char windDirection = wind.removeFirst();
                wind.addLast(windDirection);
                if (windDirection == '>') {
                    if (rock.rightMost < 6) {
                        final Set<Coordinate> newPos = rock.getRightShift();
                        if (newPos.stream().noneMatch(stoppedRocks::contains)) {
                            rock.shiftRight();
                        }
                    }
                } else if (windDirection == '<') {
                    if (rock.leftMost > 0) {
                        final Set<Coordinate> newPos = rock.getLeftShift();
                        if (newPos.stream().noneMatch(stoppedRocks::contains)) {
                            rock.shiftLeft();
                        }
                    }
                }

                final Set<Coordinate> newPos = rock.getDownShift();
                if (newPos.stream().noneMatch(stoppedRocks::contains)) {
                    rock.shiftDown();
                } else {
                    stoppedRocks.addAll(rock.squares);
                    maxRockHeight = stoppedRocks.stream().mapToInt(Coordinate::getY).max().orElseThrow() + 1;

                    if (maxRockHeight > ROCK_CACHE_HEIGHT) {
                        final String rockCacheKey = getTopRocks(stoppedRocks, maxRockHeight);

                        if (!cycleFound && caveStates.containsKey(rockCacheKey)) {
                            final CaveState caveState = caveStates.get(rockCacheKey);
                            final long cycleLength = (i - caveState.rockCount());
                            final int cycleHeightChange = maxRockHeight - caveState.maximumRockHeight();

                            final long numCycles = (totalRocks - i) / cycleLength;

                            heightOffset = cycleHeightChange * numCycles;

                            i += numCycles * cycleLength;

                            cycleFound = true;
                        } else {
                            caveStates.put(rockCacheKey, new CaveState(i, maxRockHeight));
                        }
                    }

                    break;
                }
            }
        }

        return maxRockHeight + heightOffset;
    }

    public static String getTopRocks(Set<Coordinate> rocks, int maxHeight) {
        final StringBuilder sb = new StringBuilder();
        final int minHeight = maxHeight - ROCK_CACHE_HEIGHT;
        for (int y = minHeight; y < maxHeight; y++) {
            for (int x = 0; x < 7; x++) {
                sb.append(rocks.contains(new Coordinate(x, y)) ? "#" : ".");
            }
        }

        return sb.toString();
    }

    private static Deque<Rock> createRocks() {
        final Rock hLine = new Rock(new HashSet<>(Arrays.asList(new Coordinate(0, 0), new Coordinate(1, 0), new Coordinate(2, 0), new Coordinate(3, 0))));
        final Rock plus = new Rock(new HashSet<>(Arrays.asList(new Coordinate(1, 0), new Coordinate(0, 1), new Coordinate(1, 1), new Coordinate(2, 1), new Coordinate(1, 2))));
        final Rock lShape = new Rock(new HashSet<>(Arrays.asList(new Coordinate(0, 0), new Coordinate(1, 0), new Coordinate(2, 0), new Coordinate(2, 1), new Coordinate(2, 2))));
        final Rock vLine = new Rock(new HashSet<>(Arrays.asList(new Coordinate(0, 0), new Coordinate(0, 1), new Coordinate(0, 2), new Coordinate(0, 3))));
        final Rock square = new Rock(new HashSet<>(Arrays.asList(new Coordinate(0, 0), new Coordinate(0, 1), new Coordinate(1, 0), new Coordinate(1, 1))));

        return new ArrayDeque<>(Arrays.asList(hLine, plus, lShape, vLine, square));
    }

    private static class Rock {
        private int leftMost;
        private int rightMost;

        private final Set<Coordinate> squares;

        public Rock(final Set<Coordinate> squares) {
            this.squares = squares;
            this.leftMost = squares.stream().mapToInt(Coordinate::getX).min().orElseThrow();
            this.rightMost = squares.stream().mapToInt(Coordinate::getX).max().orElseThrow();
        }

        public Rock(final Rock baseRock, int xOffset, int yOffset) {
            this.squares = baseRock.squares.stream().map(square -> new Coordinate(square.getX() + xOffset, square.getY() + yOffset)).collect(Collectors.toSet());
            this.leftMost = baseRock.leftMost + xOffset;
            this.rightMost = baseRock.rightMost + xOffset;
        }

        public Set<Coordinate> getLeftShift() {
            return this.squares.stream().map(Coordinate::left).collect(Collectors.toSet());
        }

        public Set<Coordinate> getRightShift() {
            return this.squares.stream().map(Coordinate::right).collect(Collectors.toSet());
        }

        public Set<Coordinate> getDownShift() {
            // Up = Down here
            return this.squares.stream().map(Coordinate::up).collect(Collectors.toSet());
        }

        public void shiftLeft() {
            this.leftMost--;
            this.rightMost--;
            this.squares.forEach(Coordinate::shiftLeft);
        }

        public void shiftRight() {
            this.leftMost++;
            this.rightMost++;
            this.squares.forEach(Coordinate::shiftRight);
        }

        public void shiftDown() {
            this.squares.forEach(Coordinate::shiftUp);
        }
    }

    private record CaveState(long rockCount, int maximumRockHeight) {
    }
}
