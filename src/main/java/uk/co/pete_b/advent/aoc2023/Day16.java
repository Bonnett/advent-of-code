package uk.co.pete_b.advent.aoc2023;

import uk.co.pete_b.advent.utils.Coordinate;
import uk.co.pete_b.advent.utils.Direction;

import java.util.*;
import java.util.stream.IntStream;

public class Day16 {
    public static long calculateTotalEnergized(final List<String> mirrorMap) {
        final Map<Coordinate, Character> mirrors = new HashMap<>();
        int height = mirrorMap.size();
        int width = mirrorMap.get(0).length();
        for (int y = 0; y < height; y++) {
            final String line = mirrorMap.get(y);
            for (int x = 0; x < width; x++) {
                if (line.charAt(x) == '.') {
                    continue;
                }

                mirrors.put(new Coordinate(x, y), line.charAt(x));
            }
        }

        final Deque<Beam> beamHeads = new ArrayDeque<>();
        beamHeads.add(new Beam(new Coordinate(0, 0), Direction.RIGHT));
        return traverseMirrors(beamHeads, mirrors, height, width);
    }
    public static long calculateMaximumEnergized(final List<String> mirrorMap) {
        final Map<Coordinate, Character> mirrors = new HashMap<>();
        int height = mirrorMap.size();
        int width = mirrorMap.get(0).length();
        for (int y = 0; y < height; y++) {
            final String line = mirrorMap.get(y);
            for (int x = 0; x < width; x++) {
                if (line.charAt(x) == '.') {
                    continue;
                }

                mirrors.put(new Coordinate(x, y), line.charAt(x));
            }
        }

        final long maxDownwards = IntStream.range(0, width).mapToLong(x -> {
            final Deque<Beam> beamHeads = new ArrayDeque<>();
            beamHeads.add(new Beam(new Coordinate(x, 0), Direction.DOWN));
            return traverseMirrors(beamHeads, mirrors, height, width);
        }).max().orElseThrow();

        final long maxUpwards = IntStream.range(0, width).mapToLong(x -> {
            final Deque<Beam> beamHeads = new ArrayDeque<>();
            beamHeads.add(new Beam(new Coordinate(x, (height - 1)), Direction.UP));
            return traverseMirrors(beamHeads, mirrors, height, width);
        }).max().orElseThrow();

        final long maxRightwards = IntStream.range(0, height).mapToLong(y -> {
            final Deque<Beam> beamHeads = new ArrayDeque<>();
            beamHeads.add(new Beam(new Coordinate(0, y), Direction.RIGHT));
            return traverseMirrors(beamHeads, mirrors, height, width);
        }).max().orElseThrow();

        final long maxLeftwards = IntStream.range(0, height).mapToLong(y -> {
            final Deque<Beam> beamHeads = new ArrayDeque<>();
            beamHeads.add(new Beam(new Coordinate((width - 1), y), Direction.LEFT));
            return traverseMirrors(beamHeads, mirrors, height, width);
        }).max().orElseThrow();

        return Math.max(Math.max(Math.max(maxDownwards, maxUpwards), maxLeftwards), maxRightwards);
    }

    private static long traverseMirrors(Deque<Beam> beamHeads, Map<Coordinate, Character> mirrors, int height, int width) {
        final Set<Beam> visitedSpots = new HashSet<>();
        while (!beamHeads.isEmpty()) {
            final Beam beam = beamHeads.pop();
            if (!visitedSpots.add(beam)) {
                continue;
            }

            Coordinate currentLocation = beam.head;

            switch (beam.currentHeading) {
                case LEFT -> {
                    while (mirrors.getOrDefault(currentLocation, '-').equals('-') && currentLocation.getX() > 0) {
                        currentLocation = currentLocation.left();
                        visitedSpots.add(new Beam(currentLocation, beam.currentHeading));
                    }

                    if (mirrors.containsKey(currentLocation)) {
                        switch (mirrors.get(currentLocation)) {
                            case '|' -> {
                                final Coordinate forkOne = currentLocation.up();
                                if (forkOne.getY() >= 0) {
                                    beamHeads.add(new Beam(forkOne, Direction.UP));
                                }
                                final Coordinate forkTwo = currentLocation.down();
                                if (forkTwo.getY() < height) {
                                    beamHeads.add(new Beam(forkTwo, Direction.DOWN));
                                }
                            }
                            case '/' -> {
                                final Coordinate newLocation = currentLocation.down();
                                if (newLocation.getY() < height) {
                                    beamHeads.add(new Beam(newLocation, Direction.DOWN));
                                }
                            }
                            case '\\' -> {
                                final Coordinate newLocation = currentLocation.up();
                                if (newLocation.getY() >= 0) {
                                    beamHeads.add(new Beam(newLocation, Direction.UP));
                                }
                            }
                        }
                    }
                }
                case UP -> {
                    while (mirrors.getOrDefault(currentLocation, '|').equals('|') && currentLocation.getY() > 0) {
                        currentLocation = currentLocation.up();
                        visitedSpots.add(new Beam(currentLocation, beam.currentHeading));
                    }

                    if (mirrors.containsKey(currentLocation)) {
                        switch (mirrors.get(currentLocation)) {
                            case '-' -> {
                                final Coordinate forkOne = currentLocation.left();
                                if (forkOne.getX() >= 0) {
                                    beamHeads.add(new Beam(forkOne, Direction.LEFT));
                                }
                                final Coordinate forkTwo = currentLocation.right();
                                if (forkTwo.getX() < width) {
                                    beamHeads.add(new Beam(forkTwo, Direction.RIGHT));
                                }
                            }
                            case '\\' -> {
                                final Coordinate newLocation = currentLocation.left();
                                if (newLocation.getX() >= 0) {
                                    beamHeads.add(new Beam(newLocation, Direction.LEFT));
                                }
                            }
                            case '/' -> {
                                final Coordinate newLocation = currentLocation.right();
                                if (newLocation.getX() < width) {
                                    beamHeads.add(new Beam(newLocation, Direction.RIGHT));
                                }
                            }
                        }
                    }
                }
                case RIGHT -> {
                    while (mirrors.getOrDefault(currentLocation, '-').equals('-') && currentLocation.getX() < width - 1) {
                        currentLocation = currentLocation.right();
                        visitedSpots.add(new Beam(currentLocation, beam.currentHeading));
                    }

                    if (mirrors.containsKey(currentLocation)) {
                        switch (mirrors.get(currentLocation)) {
                            case '|' -> {
                                final Coordinate forkOne = currentLocation.up();
                                if (forkOne.getY() >= 0) {
                                    beamHeads.add(new Beam(forkOne, Direction.UP));
                                }
                                final Coordinate forkTwo = currentLocation.down();
                                if (forkTwo.getY() < height) {
                                    beamHeads.add(new Beam(forkTwo, Direction.DOWN));
                                }
                            }
                            case '/' -> {
                                final Coordinate newLocation = currentLocation.up();
                                if (newLocation.getY() >= 0) {
                                    beamHeads.add(new Beam(newLocation, Direction.UP));
                                }
                            }
                            case '\\' -> {
                                final Coordinate newLocation = currentLocation.down();
                                if (newLocation.getY() < height) {
                                    beamHeads.add(new Beam(newLocation, Direction.DOWN));
                                }
                            }
                        }
                    }
                }
                case DOWN -> {
                    while (mirrors.getOrDefault(currentLocation, '|').equals('|') && currentLocation.getY() < height - 1) {
                        currentLocation = currentLocation.down();
                        visitedSpots.add(new Beam(currentLocation, beam.currentHeading));
                    }

                    if (mirrors.containsKey(currentLocation)) {
                        switch (mirrors.get(currentLocation)) {
                            case '-' -> {
                                final Coordinate forkOne = currentLocation.left();
                                if (forkOne.getX() >= 0) {
                                    beamHeads.add(new Beam(forkOne, Direction.LEFT));
                                }
                                final Coordinate forkTwo = currentLocation.right();
                                if (forkTwo.getX() < width) {
                                    beamHeads.add(new Beam(forkTwo, Direction.RIGHT));
                                }
                            }
                            case '\\' -> {
                                final Coordinate newLocation = currentLocation.right();
                                if (newLocation.getX() < width) {
                                    beamHeads.add(new Beam(newLocation, Direction.RIGHT));
                                }
                            }
                            case '/' -> {
                                final Coordinate newLocation = currentLocation.left();
                                if (newLocation.getX() >= 0) {
                                    beamHeads.add(new Beam(newLocation, Direction.LEFT));
                                }
                            }
                        }
                    }
                }
            }
        }

        return visitedSpots.stream().map(Beam::head).distinct().count();
    }

    private record Beam(Coordinate head, Direction currentHeading) {
    }
}
