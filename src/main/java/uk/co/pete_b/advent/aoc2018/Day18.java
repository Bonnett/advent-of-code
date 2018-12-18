package uk.co.pete_b.advent.aoc2018;

import uk.co.pete_b.advent.utils.Coordinate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class Day18 {
    public static long getWoodedResourceValue(final List<String> input, final int minutes) {
        final Wood wood = new Wood(input);
        int timeElapsed = 0;
        long resourceValue = -1;
        final Map<String, Integer> previousValues = new HashMap<>();
        final Map<Integer, Long> scores = new HashMap<>();
        boolean foundCycle = false;
        int indexOfCycleEnd = -1;
        int indexOfCycleStart = -1;
        while (timeElapsed < minutes) {
            timeElapsed++;
            wood.advanceMinute();

            long woodedAcres = wood.woodMap.values().stream().filter(x -> x.currentType == Wood.AcreType.TREE).count();
            long lumberYards = wood.woodMap.values().stream().filter(x -> x.currentType == Wood.AcreType.LUMBER).count();
            resourceValue = woodedAcres * lumberYards;

            final String map = wood.plot();
            if (foundCycle) {
                indexOfCycleStart = previousValues.get(map) - 1;
                break;
            }
            if (previousValues.containsKey(map)) {
                indexOfCycleEnd = timeElapsed;
                foundCycle = true;
            }

            if (!previousValues.containsKey(map)) {
                previousValues.put(map, timeElapsed);
            }
            scores.put(timeElapsed, resourceValue);
        }

        if (indexOfCycleStart != -1) {
            resourceValue = scores.get((minutes - indexOfCycleStart) % (indexOfCycleEnd - indexOfCycleStart) + indexOfCycleStart);
        }

        return resourceValue;
    }

    private static class Wood {

        private enum AcreType {
            OPEN("."), TREE("|"), LUMBER("#");

            private final String icon;

            AcreType(final String icon) {
                this.icon = icon;
            }

            @Override
            public String toString() {
                return this.icon;
            }

            static AcreType fromValue(final String icon) {
                AcreType type = null;
                for (AcreType value : AcreType.values()) {
                    if (value.icon.equals(icon)) {
                        type = value;
                        break;
                    }
                }

                return type;
            }
        }

        private final WoodSquare[][] wood;
        private final Map<Coordinate, WoodSquare> woodMap = new HashMap<>();

        public Wood(final List<String> input) {
            wood = new WoodSquare[input.size()][];
            for (int y = 0; y < input.size(); y++) {
                final String line = input.get(y);
                wood[y] = new WoodSquare[line.length()];
                for (int x = 0; x < line.length(); x++) {
                    final Coordinate coords = new Coordinate(x, y);
                    final WoodSquare square = new WoodSquare(coords, AcreType.fromValue(line.substring(x, x + 1)));
                    wood[y][x] = square;
                    woodMap.put(coords, square);
                }
            }
        }

        public void advanceMinute() {
            for (final WoodSquare[] woodSquares : wood) {
                for (WoodSquare woodSquare : woodSquares) {
                    final List<Coordinate> adjacentSquares = getAdjacentSquares(woodSquare.coordinate);
                    switch (woodSquare.currentType) {

                        case OPEN: {
                            int numberOfTrees = 0;
                            for (final Coordinate adjacent : adjacentSquares) {
                                WoodSquare square = woodMap.get(adjacent);
                                if (square != null && square.currentType == AcreType.TREE) {
                                    numberOfTrees++;
                                }
                            }

                            if (numberOfTrees >= 3) {
                                woodSquare.newType = AcreType.TREE;
                            } else {
                                woodSquare.newType = AcreType.OPEN;
                            }
                        }
                        break;
                        case TREE: {
                            int numberOfLumberYards = 0;
                            for (final Coordinate adjacent : adjacentSquares) {
                                WoodSquare square = woodMap.get(adjacent);
                                if (square != null && square.currentType == AcreType.LUMBER) {
                                    numberOfLumberYards++;
                                }
                            }

                            if (numberOfLumberYards >= 3) {
                                woodSquare.newType = AcreType.LUMBER;
                            } else {
                                woodSquare.newType = AcreType.TREE;
                            }
                        }
                        break;
                        case LUMBER: {
                            boolean hasLumber = false;
                            boolean hasTrees = false;
                            for (final Coordinate adjacent : adjacentSquares) {
                                WoodSquare square = woodMap.get(adjacent);
                                if (square != null) {
                                    if (square.currentType == AcreType.LUMBER) {
                                        hasLumber = true;
                                    } else if (square.currentType == AcreType.TREE) {
                                        hasTrees = true;
                                    }
                                }
                            }

                            if (!hasLumber || !hasTrees) {
                                woodSquare.newType = AcreType.OPEN;
                            } else {
                                woodSquare.newType = AcreType.LUMBER;
                            }
                        }
                        break;
                    }
                }
            }

            Arrays.stream(wood).forEach(woodSquares -> IntStream.range(0, woodSquares.length).forEach(x -> woodSquares[x].finishMinute()));
        }

        private List<Coordinate> getAdjacentSquares(final Coordinate coordinate) {
            return Arrays.asList(coordinate.up(), coordinate.upLeft(), coordinate.left(), coordinate.downLeft(), coordinate.down(), coordinate.downRight(), coordinate.right(), coordinate.upRight());
        }

        public String plot() {
            final StringBuilder sb = new StringBuilder();
            for (WoodSquare[] woodSquares : this.wood) {
                for (WoodSquare woodSquare : woodSquares) {
                    sb.append(woodSquare.currentType);
                }
            }

            return sb.toString();
        }

        private class WoodSquare {
            private AcreType currentType;
            private AcreType newType;
            private final Coordinate coordinate;

            private WoodSquare(final Coordinate coordinate, final AcreType startType) {
                this.coordinate = coordinate;
                this.currentType = startType;
            }

            void finishMinute() {
                this.currentType = newType;
            }
        }
    }
}
