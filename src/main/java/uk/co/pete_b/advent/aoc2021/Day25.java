package uk.co.pete_b.advent.aoc2021;

import uk.co.pete_b.advent.utils.Coordinate;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Day25 {
    public static int findStepWithNoMoves(final List<String> input) {
        final int width = input.get(0).length();
        final int height = input.size();
        final Set<Coordinate> eastMoving = new HashSet<>();
        final Set<Coordinate> southMoving = new HashSet<>();

        for (int y = 0; y < input.size(); y++) {
            final char[] line = input.get(y).toCharArray();
            for (int x = 0; x < line.length; x++) {
                if (line[x] == '>') {
                    eastMoving.add(new Coordinate(x, y));
                } else if (line[x] == 'v') {
                    southMoving.add(new Coordinate(x, y));
                }
            }
        }

        AtomicInteger numberOfMoves = new AtomicInteger();
        int steps = 0;
        do {
            steps++;
            numberOfMoves.set(0);
            final Set<Coordinate> newEastCucumbers = eastMoving.parallelStream().map(cucumber -> {
                Coordinate target = cucumber.right();
                if (target.getX() == width) {
                    target = new Coordinate(0, target.getY());
                }
                if (!eastMoving.contains(target) && !southMoving.contains(target)) {
                    numberOfMoves.getAndIncrement();
                    return target;
                } else {
                    return cucumber;
                }
            }).collect(Collectors.toSet());
            eastMoving.clear();
            eastMoving.addAll(newEastCucumbers);

            final Set<Coordinate> newSouthCucumbers = southMoving.parallelStream().map(cucumber -> {
                Coordinate target = cucumber.down();
                if (target.getY() == height) {
                    target = new Coordinate(target.getX(), 0);
                }
                if (!eastMoving.contains(target) && !southMoving.contains(target)) {
                    numberOfMoves.getAndIncrement();
                    return target;
                } else {
                    return cucumber;
                }
            }).collect(Collectors.toSet());
            southMoving.clear();
            southMoving.addAll(newSouthCucumbers);
        }
        while (numberOfMoves.get() != 0);

        return steps;
    }
}
