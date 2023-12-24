package uk.co.pete_b.advent.aoc2023;

import org.apache.commons.lang3.tuple.Pair;
import uk.co.pete_b.advent.utils.Coordinate;

import java.util.*;
import java.util.function.Function;

public class Day23 {
    public static int findLongestPath(final List<String> forestLayout, final boolean applySlopes) {
        final ForestMap forestMap = getForestMap(forestLayout);

        return getLongestRoute(forestMap, applySlopes) - 1;
    }

    private static ForestMap getForestMap(final List<String> forestLayout) {
        final Map<Coordinate, PathType> forestMap = new HashMap<>();
        final Map<Coordinate, Function<Coordinate, Coordinate>> slopeMap = new HashMap<>();
        final Coordinate startCoordinate = new Coordinate(1, 0);
        final Coordinate endCoordinate = new Coordinate(forestLayout.get(0).length() - 2, forestLayout.size() - 1);
        for (int y = 0; y < forestLayout.size(); y++) {
            final String line = forestLayout.get(y);
            for (int x = 0; x < line.length(); x++) {
                final char c = line.charAt(x);
                if (c == '.') {
                    forestMap.put(new Coordinate(x, y), PathType.PATH);
                } else if (c != '#') {
                    final Coordinate slope = new Coordinate(x, y);
                    forestMap.put(slope, PathType.SLOPE);
                    switch (c) {
                        case '^' -> slopeMap.put(slope, Coordinate::up);
                        case '>' -> slopeMap.put(slope, Coordinate::right);
                        case 'v' -> slopeMap.put(slope, Coordinate::down);
                        case '<' -> slopeMap.put(slope, Coordinate::left);
                    }
                }
            }
        }

        return new ForestMap(forestMap, slopeMap, startCoordinate, endCoordinate);
    }

    public static int findLongestPathIgnoringSlopes(final List<String> forestLayout) {
        return findLongestPathIgnoringSlopes(getForestMap(forestLayout));
    }

    private static int findLongestPathIgnoringSlopes(final ForestMap forest) {
        final Coordinate start = forest.startCoordinate;
        final Coordinate end = forest.endCoordinate;
        final Map<Pair<Coordinate, Coordinate>, Integer> connectedNodeMap = new HashMap<>();
        final Map<Coordinate, Set<Coordinate>> nodes = new HashMap<>();

        final Deque<PathRoute> routeQueue = new ArrayDeque<>();
        routeQueue.add(new PathRoute(start, start, new LinkedHashSet<>(Collections.singletonList(start))));

        while (!routeQueue.isEmpty()) {
            final PathRoute path = routeQueue.pop();
            if (connectedNodeMap.containsKey(Pair.of(path.startCoordinate, path.currentPoint))) {
                continue;
            }

            final List<Coordinate> options = getAdjacentSpaces(path.currentPoint)
                    .stream()
                    .filter(option -> !path.route.contains(option))
                    .filter(forest.forest::containsKey)
                    .toList();

            if (path.currentPoint.equals(end) || options.size() > 1) {
                connectedNodeMap.put(Pair.of(path.startCoordinate, path.currentPoint), path.route.size());
                connectedNodeMap.put(Pair.of(path.currentPoint, path.startCoordinate), path.route.size());

                nodes.compute(path.currentPoint, (coordinate, coordinates) -> {
                    if (coordinates == null) {
                        coordinates = new LinkedHashSet<>();
                    }
                    coordinates.add(path.startCoordinate);

                    return coordinates;
                });

                nodes.compute(path.startCoordinate, (coordinate, coordinates) -> {
                    if (coordinates == null) {
                        coordinates = new LinkedHashSet<>();
                    }
                    coordinates.add(path.currentPoint);

                    return coordinates;
                });

                for (Coordinate option : options) {
                    final Set<Coordinate> newRoute = new LinkedHashSet<>(Arrays.asList(path.currentPoint, option));
                    routeQueue.add(new PathRoute(option, path.currentPoint, newRoute));
                }
            } else {
                final Coordinate newPosition = options.get(0);
                final Set<Coordinate> newRoute = new LinkedHashSet<>(path.route);
                newRoute.add(newPosition);

                routeQueue.add(new PathRoute(newPosition, path.startCoordinate, newRoute));
            }
        }

        final Deque<Path> routeSize = new ArrayDeque<>();
        routeSize.push(new Path(start, 0));
        final Set<Coordinate> visited = new HashSet<>();
        int best = 0;
        while (!routeSize.isEmpty()) {
            final Path path = routeSize.pop();
            final Coordinate currentPoint = path.currentPoint;
            final Integer distance = path.length;

            if (distance == -1) {
                visited.remove(currentPoint);
                continue;
            }

            if (currentPoint.equals(end)) {
                if (best < distance) {
                    best = distance;
                }
                continue;
            }

            if (visited.contains(currentPoint)) {
                continue;
            }

            visited.add(currentPoint);
            routeSize.push(new Path(currentPoint, -1));
            for (Coordinate connectedNode : nodes.get(currentPoint)) {
                routeSize.push(new Path(connectedNode, distance + connectedNodeMap.get(Pair.of(connectedNode, currentPoint)) - 1));
            }
        }

        return best;
    }

    private static int getLongestRoute(final ForestMap forest, final boolean applySlopes) {
        final Map<Coordinate, Function<Coordinate, Coordinate>> slopeMap = (applySlopes) ? forest.slopes : Collections.emptyMap();

        final PriorityQueue<PathRoute> paths = new PriorityQueue<>((o1, o2) -> Integer.compare(o2.route.size(), o1.route.size()));
        final Set<Coordinate> startRoute = new LinkedHashSet<>();
        startRoute.add(forest.startCoordinate);
        paths.add(new PathRoute(forest.startCoordinate, forest.startCoordinate, startRoute));

        int longestRoute = 0;
        while (!paths.isEmpty()) {
            final PathRoute route = paths.poll();
            if (route.currentPoint.equals(forest.endCoordinate)) {
                if (longestRoute < route.route.size()) {
                    longestRoute = route.route.size();
                }
                continue;
            }

            final List<Coordinate> options = getAdjacentSpaces(route.currentPoint);
            for (Coordinate option : options) {
                if (route.route.contains(option) || !forest.forest.containsKey(option)) {
                    continue;
                }

                final Set<Coordinate> newRoute = new LinkedHashSet<>(route.route);
                newRoute.add(option);
                if (slopeMap.containsKey(option)) {
                    final Coordinate newPosition = slopeMap.get(option).apply(option);
                    if (route.route.contains(newPosition)) {
                        continue;
                    }
                    newRoute.add(newPosition);
                    paths.add(new PathRoute(newPosition, route.startCoordinate, newRoute));
                } else {
                    paths.add(new PathRoute(option, route.startCoordinate, newRoute));
                }
            }
        }

        return longestRoute;
    }

    private static List<Coordinate> getAdjacentSpaces(final Coordinate position) {
        return Arrays.asList(position.up(), position.left(), position.down(), position.right());
    }

    private record ForestMap(Map<Coordinate, PathType> forest, Map<Coordinate, Function<Coordinate, Coordinate>> slopes, Coordinate startCoordinate,
                             Coordinate endCoordinate) {
    }

    private record PathRoute(Coordinate currentPoint, Coordinate startCoordinate, Set<Coordinate> route) {
    }

    private record Path(Coordinate currentPoint, int length) {
    }

    private enum PathType {
        PATH, SLOPE
    }
}
