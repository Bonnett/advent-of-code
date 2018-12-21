package uk.co.pete_b.advent.aoc2018;

import uk.co.pete_b.advent.utils.Coordinate;

import java.util.*;

public class Day20 {
    public static Answer findAnswers(final String input) {
        final String tidiedInput = input.substring(1, input.length() - 1);
        Coordinate currentLocation = new Coordinate(0, 0);
        final Map<Coordinate, Integer> roomsMap = new HashMap<>(Collections.singletonMap(currentLocation, 0));
        final LinkedList<Coordinate> queue = new LinkedList<>();

        for (int i = 0; i < tidiedInput.length(); i++) {
            final char currentChar = tidiedInput.charAt(i);
            switch (currentChar) {
                case '(':
                    queue.add(currentLocation);
                    break;
                case ')':
                    currentLocation = queue.pollLast();
                    break;
                case '|':
                    currentLocation = queue.peekLast();
                    break;
                case 'N':
                    currentLocation = getNewLocation(currentLocation, roomsMap, currentLocation.up());
                    break;
                case 'E':
                    currentLocation = getNewLocation(currentLocation, roomsMap, currentLocation.left());
                    break;
                case 'W':
                    currentLocation = getNewLocation(currentLocation, roomsMap, currentLocation.right());
                    break;
                case 'S':
                    currentLocation = getNewLocation(currentLocation, roomsMap, currentLocation.down());
                    break;
            }
        }

        final int shortestFullPath = roomsMap.values().stream().max(Integer::compareTo).orElse(-1);
        final long greaterThatOneThousand = roomsMap.values().stream().filter(x -> x >= 1000).count();

        return new Answer(shortestFullPath, greaterThatOneThousand);
    }

    private static Coordinate getNewLocation(final Coordinate currentLocation, final Map<Coordinate, Integer> roomsMap, final Coordinate newCoordinate) {
        int distance = roomsMap.get(currentLocation) + 1;
        if (roomsMap.containsKey(newCoordinate)) {
            distance = Math.min(roomsMap.get(newCoordinate), roomsMap.get(currentLocation) + 1);
        }

        roomsMap.put(newCoordinate, distance);
        return newCoordinate;
    }

    public static class Answer {
        private final int shortestFullPath;
        private final long pathsLargerThanOneThousand;

        public Answer(final int shortestFullPath, final long pathsLargerThanOneThousand) {
            this.shortestFullPath = shortestFullPath;
            this.pathsLargerThanOneThousand = pathsLargerThanOneThousand;
        }

        public int getShortestFullPath() {
            return shortestFullPath;
        }

        public long getPathsLargerThanOneThousand() {
            return pathsLargerThanOneThousand;
        }
    }

}
