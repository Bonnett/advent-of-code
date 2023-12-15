package uk.co.pete_b.advent.aoc2023;

import uk.co.pete_b.advent.utils.Coordinate;

import java.util.*;
import java.util.function.Function;

public class Day14 {

    public static final Comparator<Coordinate> NORTHWARDS_COMPARISON = (rockOne, rockTwo) -> Integer.compare(rockOne.getY(), rockTwo.getY());
    public static final Comparator<Coordinate> SOUTHWARDS_COMPARISON = (rockOne, rockTwo) -> Integer.compare(rockTwo.getY(), rockOne.getY());
    public static final Comparator<Coordinate> WESTWARDS_COMPARISON = (rockOne, rockTwo) -> Integer.compare(rockOne.getX(), rockTwo.getX());
    public static final Comparator<Coordinate> EASTWARDS_COMPARISON = (rockOne, rockTwo) -> Integer.compare(rockTwo.getX(), rockOne.getX());

    public static int calculateTotalLoad(final List<String> rockLayout) {
        final Set<Coordinate> cubeRocks = new HashSet<>();
        final Set<Coordinate> roundedRocks = new HashSet<>();

        final int height = rockLayout.size();
        final int width = rockLayout.get(0).length();

        parseRockLayout(rockLayout, height, width, roundedRocks, cubeRocks);

        final Set<Coordinate> newRoundedRocks = new HashSet<>();
        roundedRocks.stream().sorted(NORTHWARDS_COMPARISON).forEach(rock -> newRoundedRocks.add(tilt(cubeRocks, newRoundedRocks, rock, (x) -> (x.getY() == 0), Coordinate::up)));

        return newRoundedRocks.stream().mapToInt(x -> height - x.getY()).sum();
    }

    private static void parseRockLayout(List<String> rockLayout, int height, int width, Set<Coordinate> roundedRocks, Set<Coordinate> cubeRocks) {
        for (int y = 0; y < height; y++) {
            final String line = rockLayout.get(y);
            for (int x = 0; x < width; x++) {
                if (line.charAt(x) == 'O') {
                    roundedRocks.add(new Coordinate(x, y));
                } else if (line.charAt(x) == '#') {
                    cubeRocks.add(new Coordinate(x, y));
                }
            }
        }
    }

    public static int calculateLoadInCycles(final List<String> rockLayout) {
        final int cycles = 1000000000;
        final Set<Coordinate> cubeRocks = new HashSet<>();
        final Set<Coordinate> roundedRocks = new HashSet<>();

        final int height = rockLayout.size();
        final int width = rockLayout.get(0).length();

        parseRockLayout(rockLayout, height, width, roundedRocks, cubeRocks);

        final Set<Coordinate> newRoundedRocks = new HashSet<>();

        final Map<Set<Coordinate>, Integer> previousIterations = new HashMap<>();

        int cycleSize = -1;

        for (int i = 0; i < cycles; i++) {
            roundedRocks.stream().sorted(NORTHWARDS_COMPARISON).forEach(rock -> newRoundedRocks.add(tilt(cubeRocks, newRoundedRocks, rock, (x) -> (x.getY() == 0), Coordinate::up)));
            resetRockCollections(roundedRocks, newRoundedRocks);

            roundedRocks.stream().sorted(WESTWARDS_COMPARISON).forEach(rock -> newRoundedRocks.add(tilt(cubeRocks, newRoundedRocks, rock, (x) -> (x.getX() == 0), Coordinate::left)));
            resetRockCollections(roundedRocks, newRoundedRocks);

            roundedRocks.stream().sorted(SOUTHWARDS_COMPARISON).forEach(rock -> newRoundedRocks.add(tilt(cubeRocks, newRoundedRocks, rock, (x) -> (x.getY() == (height - 1)), Coordinate::down)));
            resetRockCollections(roundedRocks, newRoundedRocks);

            roundedRocks.stream().sorted(EASTWARDS_COMPARISON).forEach(rock -> newRoundedRocks.add(tilt(cubeRocks, newRoundedRocks, rock, (x) -> (x.getX() == (width - 1)), Coordinate::right)));
            resetRockCollections(roundedRocks, newRoundedRocks);

            if (previousIterations.containsKey(roundedRocks)) {
                cycleSize = i - previousIterations.get(roundedRocks);
                break;
            }

            previousIterations.put(new HashSet<>(roundedRocks), i);
        }

        final int target = previousIterations.get(roundedRocks) - 1 + (cycles - previousIterations.get(roundedRocks)) % cycleSize;

        return previousIterations.entrySet().stream().filter(entry -> entry.getValue().equals(target)).findFirst().map(Map.Entry::getKey).orElseThrow().stream().mapToInt(x -> height - x.getY()).sum();
    }

    private static void resetRockCollections(Set<Coordinate> roundedRocks, Set<Coordinate> newRoundedRocks) {
        roundedRocks.clear();
        roundedRocks.addAll(newRoundedRocks);
        newRoundedRocks.clear();
    }

    private static Coordinate tilt(final Set<Coordinate> cubeRocks, final Set<Coordinate> roundedRocks, final Coordinate currentRock,
                                   final Function<Coordinate, Boolean> limitFn, final Function<Coordinate, Coordinate> tiltFn) {
        if (limitFn.apply(currentRock)) {
            return currentRock;
        }

        Coordinate newRockPosition = currentRock;
        while (!cubeRocks.contains(tiltFn.apply(newRockPosition)) && !roundedRocks.contains(tiltFn.apply(newRockPosition))) {
            newRockPosition = tiltFn.apply(newRockPosition);
            if (limitFn.apply(newRockPosition)) {
                break;
            }
        }

        return newRockPosition;
    }
}
