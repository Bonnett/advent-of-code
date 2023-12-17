package uk.co.pete_b.advent.aoc2023;

import uk.co.pete_b.advent.utils.Coordinate;
import uk.co.pete_b.advent.utils.Direction;

import java.util.*;

public class Day17 {
    public static int findLowestHeatLoss(final List<String> lavaMap) {
        final Map<Coordinate, Integer> lava = new HashMap<>();
        int height = lavaMap.size();
        int width = lavaMap.get(0).length();
        for (int y = 0; y < height; y++) {
            final String line = lavaMap.get(y);
            for (int x = 0; x < width; x++) {
                lava.put(new Coordinate(x, y), line.charAt(x) - 48);
            }
        }

        final Coordinate startingCoordinate = new Coordinate(0, 0);
        final Coordinate targetCoordinate = new Coordinate(width - 1, height - 1);
        final Map<RouteState, Integer> heatMap = new HashMap<>();
        final LavaRoute routeRight = new LavaRoute(new Bearing(startingCoordinate, Direction.RIGHT), 0, 0);
        final LavaRoute routeDown = new LavaRoute(new Bearing(startingCoordinate, Direction.DOWN), 0, 0);
        final PriorityQueue<LavaRoute> queue = new PriorityQueue<>(Comparator.comparing(r -> r.totalHeat));
        queue.add(routeRight);
        queue.add(routeDown);

        while (!queue.isEmpty()) {
            final LavaRoute route = queue.poll();
            final RouteState routeState = new RouteState(route.bearing, route.consecutiveDistance);
            if (heatMap.containsKey(routeState) && heatMap.get(routeState) <= route.totalHeat) {
                continue;
            }

            heatMap.put(routeState, route.totalHeat);

            for (final Bearing bearing : getAdjacentSquares(route)) {
                if (!lava.containsKey(bearing.position)) {
                    continue;
                }

                int consecutiveDistance = 0;
                if (bearing.direction == route.bearing.direction) {
                    consecutiveDistance = route.consecutiveDistance + 1;
                }

                if (consecutiveDistance == 3) {
                    continue;
                }

                queue.add(new LavaRoute(bearing, consecutiveDistance, route.totalHeat + lava.get(bearing.position)));
            }
        }

        return heatMap.entrySet().stream()
                .filter(x -> x.getKey().bearing.position.equals(targetCoordinate))
                .map(Map.Entry::getValue)
                .mapToInt(Integer::intValue)
                .min()
                .orElseThrow();
    }

    public static int findLowestHeatLossWithUltraCrucible(final List<String> lavaMap) {
        final Map<Coordinate, Integer> lava = new HashMap<>();
        int height = lavaMap.size();
        int width = lavaMap.get(0).length();
        for (int y = 0; y < height; y++) {
            final String line = lavaMap.get(y);
            for (int x = 0; x < width; x++) {
                lava.put(new Coordinate(x, y), line.charAt(x) - 48);
            }
        }

        final Coordinate startingCoordinate = new Coordinate(0, 0);
        final Coordinate targetCoordinate = new Coordinate(width - 1, height - 1);
        final Map<RouteState, Integer> heatMap = new HashMap<>();
        final LavaRoute routeRight = new LavaRoute(new Bearing(startingCoordinate, Direction.RIGHT), 0, 0);
        final LavaRoute routeDown = new LavaRoute(new Bearing(startingCoordinate, Direction.DOWN), 0, 0);
        final PriorityQueue<LavaRoute> queue = new PriorityQueue<>(Comparator.comparing(r -> r.totalHeat));
        queue.add(routeRight);
        queue.add(routeDown);

        while (!queue.isEmpty()) {
            final LavaRoute route = queue.poll();
            final RouteState routeState = new RouteState(route.bearing, route.consecutiveDistance);
            if (heatMap.containsKey(routeState) && heatMap.get(routeState) <= route.totalHeat) {
                continue;
            }

            heatMap.put(routeState, route.totalHeat);

            for (final Bearing bearing : getAdjacentSquares(route)) {
                if (!lava.containsKey(bearing.position)) {
                    continue;
                }

                if (route.consecutiveDistance < 3 && bearing.direction != route.bearing.direction) {
                    continue;
                }

                int consecutiveDistance = 0;
                if (bearing.direction == route.bearing.direction) {
                    consecutiveDistance = route.consecutiveDistance + 1;
                }

                if (consecutiveDistance == 10) {
                    continue;
                }

                queue.add(new LavaRoute(bearing, consecutiveDistance, route.totalHeat + lava.get(bearing.position)));
            }
        }

        return heatMap.entrySet().stream()
                .filter(x -> x.getKey().bearing.position.equals(targetCoordinate))
                .filter(x -> x.getKey().consecutiveDistance >= 3)
                .map(Map.Entry::getValue)
                .mapToInt(Integer::intValue)
                .min()
                .orElseThrow();
    }

    private record RouteState(Bearing bearing, int consecutiveDistance) {
    }

    private record LavaRoute(Bearing bearing, int consecutiveDistance, int totalHeat) {
    }

    private static List<Bearing> getAdjacentSquares(final LavaRoute route) {
        final List<Bearing> nextMoves = new ArrayList<>();

        switch (route.bearing.direction) {
            case LEFT -> {
                nextMoves.add(new Bearing(route.bearing.position.down(), Direction.DOWN));
                nextMoves.add(new Bearing(route.bearing.position.left(), Direction.LEFT));
                nextMoves.add(new Bearing(route.bearing.position.up(), Direction.UP));
            }
            case UP -> {
                nextMoves.add(new Bearing(route.bearing.position.left(), Direction.LEFT));
                nextMoves.add(new Bearing(route.bearing.position.up(), Direction.UP));
                nextMoves.add(new Bearing(route.bearing.position.right(), Direction.RIGHT));
            }
            case RIGHT -> {
                nextMoves.add(new Bearing(route.bearing.position.up(), Direction.UP));
                nextMoves.add(new Bearing(route.bearing.position.right(), Direction.RIGHT));
                nextMoves.add(new Bearing(route.bearing.position.down(), Direction.DOWN));
            }
            case DOWN -> {
                nextMoves.add(new Bearing(route.bearing.position.right(), Direction.RIGHT));
                nextMoves.add(new Bearing(route.bearing.position.down(), Direction.DOWN));
                nextMoves.add(new Bearing(route.bearing.position.left(), Direction.LEFT));
            }
        }

        return nextMoves;
    }

    private record Bearing(Coordinate position, Direction direction) {

    }
}
