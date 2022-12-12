package uk.co.pete_b.advent.aoc2022;

import uk.co.pete_b.advent.utils.Coordinate;

import java.util.*;

public class Day12 {
    public static int findFewestSteps(final List<String> inputMap) {
        final HeightMap map = parseHeightMap(inputMap);
        return findShortestRoute(map.heightMap, map.startingPosition, map.endPosition);
    }

    public static int findFewestStepsWithAllStartingPositions(final List<String> inputMap) {
        final HeightMap map = parseHeightMap(inputMap);

        return map.startingPoints.parallelStream()
                .map(startingPoint -> findShortestRoute(map.heightMap, startingPoint, map.endPosition))
                .min(Comparator.comparing(Integer::valueOf)).orElseThrow();
    }

    private static int findShortestRoute(final Map<Coordinate, Integer> heightMap, final Coordinate startingPosition, final Coordinate endPosition) {
        final TreeRoute route = new TreeRoute(startingPosition);
        final Map<Coordinate, Integer> shortestRoute = new HashMap<>();

        final PriorityQueue<TreeRoute> queue = new PriorityQueue<>((o1, o2) -> {
            int o1Height = heightMap.get(o1.currentPoint);
            int o2Height = heightMap.get(o2.currentPoint);
            if (o2Height > o1Height) {
                return 1;
            } else if (o2Height < o1Height) {
                return -1;
            } else {
                return Comparator.comparing(TreeRoute::getSteps).compare(o1, o2);
            }
        });

        queue.add(route);

        while (!queue.isEmpty()) {
            final TreeRoute current = queue.poll();

            if (shortestRoute.containsKey(current.currentPoint) && shortestRoute.get(current.currentPoint) <= current.getSteps()) {
                continue;
            }

            shortestRoute.put(current.currentPoint, current.getSteps());

            if (current.currentPoint.equals(endPosition)) {
                continue;
            }

            for (final Coordinate adjacent : getAdjacentSquares(current.currentPoint)) {
                if (heightMap.containsKey(adjacent) && !current.getCurrentRoute().contains(adjacent) &&
                        (heightMap.get(adjacent) - heightMap.get(current.getCurrentPoint()) <= 1)) {
                    queue.add(new TreeRoute(current, adjacent));
                }
            }
        }

        return shortestRoute.getOrDefault(endPosition, Integer.MAX_VALUE) - 1;
    }

    private static List<Coordinate> getAdjacentSquares(final Coordinate cave) {
        return Arrays.asList(cave.right(), cave.down(), cave.left(), cave.up());
    }

    private static HeightMap parseHeightMap(final List<String> inputMap) {
        final Map<Coordinate, Integer> heightMap = new HashMap<>();
        final List<Coordinate> startingPoints = new ArrayList<>();
        Coordinate start = null;
        Coordinate end = null;

        for (int y = 0; y < inputMap.size(); y++) {
            final String row = inputMap.get(y);
            for (int x = 0; x < row.length(); x++) {
                final char cellHeight = row.charAt(x);
                final Coordinate coordinate = new Coordinate(x, y);
                if (cellHeight == 'S') {
                    start = coordinate;
                    startingPoints.add(coordinate);
                    heightMap.put(coordinate, 1);
                } else if (cellHeight == 'E') {
                    end = coordinate;
                    heightMap.put(coordinate, 26);
                } else {
                    if (cellHeight == 'a') {
                        startingPoints.add(coordinate);
                    }
                    heightMap.put(coordinate, (((int) cellHeight) - 96));
                }
            }
        }

        return new HeightMap(heightMap, start, startingPoints, end);
    }

    private record HeightMap(Map<Coordinate, Integer> heightMap, Coordinate startingPosition, List<Coordinate> startingPoints,
                             Coordinate endPosition) {
    }

    private static class TreeRoute {
        private final Set<Coordinate> currentRoute;
        private final Coordinate currentPoint;

        public TreeRoute(final Coordinate startPoint) {
            this.currentRoute = new LinkedHashSet<>();
            this.currentRoute.add(startPoint);
            this.currentPoint = startPoint;
        }

        public TreeRoute(final TreeRoute currentRoute, final Coordinate nextPoint) {
            this.currentRoute = new LinkedHashSet<>(currentRoute.currentRoute);
            this.currentRoute.add(nextPoint);
            this.currentPoint = nextPoint;
        }

        public Set<Coordinate> getCurrentRoute() {
            return currentRoute;
        }

        public int getSteps() {
            return this.currentRoute.size();
        }

        public Coordinate getCurrentPoint() {
            return currentPoint;
        }
    }
}
