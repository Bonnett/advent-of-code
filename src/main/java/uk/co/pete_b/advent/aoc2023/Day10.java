package uk.co.pete_b.advent.aoc2023;

import uk.co.pete_b.advent.utils.Coordinate;

import java.util.*;

import static uk.co.pete_b.advent.utils.Maths.shoelaceArea;

public class Day10 {
    public static Answer findFarthestPipe(final List<String> pipeLayout) {
        Coordinate startingPoint = null;
        final Map<Coordinate, Set<Coordinate>> pipeMap = new HashMap<>();

        for (int y = 0; y < pipeLayout.size(); y++) {
            final char[] line = pipeLayout.get(y).toCharArray();
            for (int x = 0; x < line.length; x++) {
                final char pipeType = line[x];
                if (pipeType == '.') {
                    continue;
                }

                final Coordinate pipe = new Coordinate(x, y);
                final Set<Coordinate> connectedPipes = new HashSet<>();
                switch (pipeType) {
                    case 'S' -> {
                        startingPoint = pipe;
                        continue;
                    }
                    case '|' -> {
                        connectedPipes.add(pipe.up());
                        connectedPipes.add(pipe.down());
                    }
                    case '-' -> {
                        connectedPipes.add(pipe.left());
                        connectedPipes.add(pipe.right());
                    }
                    case 'L' -> {
                        connectedPipes.add(pipe.up());
                        connectedPipes.add(pipe.right());
                    }
                    case 'J' -> {
                        connectedPipes.add(pipe.up());
                        connectedPipes.add(pipe.left());
                    }
                    case '7' -> {
                        connectedPipes.add(pipe.down());
                        connectedPipes.add(pipe.left());
                    }
                    case 'F' -> {
                        connectedPipes.add(pipe.down());
                        connectedPipes.add(pipe.right());
                    }
                }

                pipeMap.put(pipe, connectedPipes);
            }
        }

        if (startingPoint == null) {
            throw new IllegalStateException("Failed to find start point");
        }

        final List<Coordinate> startingPipeConnections = new ArrayList<>();
        if (pipeMap.getOrDefault(startingPoint.left(), Collections.emptySet()).contains(startingPoint)) {
            startingPipeConnections.add(startingPoint.left());
        }
        if (pipeMap.getOrDefault(startingPoint.right(), Collections.emptySet()).contains(startingPoint)) {
            startingPipeConnections.add(startingPoint.right());
        }
        if (pipeMap.getOrDefault(startingPoint.up(), Collections.emptySet()).contains(startingPoint)) {
            startingPipeConnections.add(startingPoint.up());
        }
        if (pipeMap.getOrDefault(startingPoint.down(), Collections.emptySet()).contains(startingPoint)) {
            startingPipeConnections.add(startingPoint.down());
        }

        Coordinate previousRouteOne = startingPoint;
        Coordinate previousRouteTwo = startingPoint;
        Coordinate routeOne = startingPipeConnections.get(0);
        Coordinate routeTwo = startingPipeConnections.get(1);
        final List<Coordinate> routeOnePipes = new ArrayList<>();
        final List<Coordinate> routeTwoPipes = new ArrayList<>();

        int steps = 1;
        while (!routeOne.equals(routeTwo)) {
            routeOnePipes.add(routeOne);
            routeTwoPipes.add(routeTwo);

            final Coordinate newRouteOne = getNextPipe(pipeMap, routeOne, previousRouteOne);
            final Coordinate newRouteTwo = getNextPipe(pipeMap, routeTwo, previousRouteTwo);
            previousRouteOne = routeOne;
            routeOne = newRouteOne;
            previousRouteTwo = routeTwo;
            routeTwo = newRouteTwo;

            steps++;
        }

        // Build a polygon of our route
        final List<Coordinate> completeRoute = new ArrayList<>();
        completeRoute.add(startingPoint);
        completeRoute.addAll(routeOnePipes);
        completeRoute.add(routeOne);
        Collections.reverse(routeTwoPipes);
        completeRoute.addAll(routeTwoPipes);

        final int enclosedPoints = shoelaceArea(completeRoute) - steps + 1;

        return new Answer(steps, enclosedPoints);
    }

    private static Coordinate getNextPipe(final Map<Coordinate, Set<Coordinate>> pipeMap, final Coordinate currentPipe, final Coordinate lastPipe) {
        final Set<Coordinate> connections = pipeMap.get(currentPipe);
        return connections.stream().filter(pipe -> !pipe.equals(lastPipe)).findFirst().orElseThrow();
    }

    public record Answer(int steps, int enclosedPoints) {
    }
}
