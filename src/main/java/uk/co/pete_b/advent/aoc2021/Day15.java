package uk.co.pete_b.advent.aoc2021;

import uk.co.pete_b.advent.utils.Coordinate;

import java.util.*;

public class Day15 {

    private static final int CAVE_EXPANSION_FACTOR = 5;

    public static int calculateLowestTotalRisk(final List<String> grid, boolean expandGrid) {
        final Map<Coordinate, Integer> caveMap = new HashMap<>();
        final int[][] startingRisks = new int[grid.size()][grid.size()];
        // it's a square
        final int caveSize = grid.size() * ((expandGrid) ? CAVE_EXPANSION_FACTOR : 1);
        for (int y = 0; y < grid.size(); y++) {
            for (int x = 0; x < grid.get(y).length(); x++) {
                final int risk = Integer.parseInt(grid.get(y).substring(x, x + 1));
                caveMap.put(new Coordinate(x, y), risk);
                startingRisks[y][x] = risk;
            }
        }

        if (expandGrid) {
            for (int y = 0; y < caveSize; y++) {
                for (int x = 0; x < caveSize; x++) {
                    if (x < grid.size() && y < grid.size()) {
                        continue;
                    }

                    int offset = x / grid.size() + y / grid.size();
                    int newVal = startingRisks[y % grid.size()][x % grid.size()] + offset;
                    if (newVal > 9) {
                        newVal -= 9;
                    }

                    caveMap.put(new Coordinate(x, y), newVal);
                }
            }
        }

        final CaveRoute route = new CaveRoute(new Coordinate(0, 0));

        final PriorityQueue<CaveRoute> queue = new PriorityQueue<>(Comparator.comparing(r -> r.totalRisk));
        queue.add(route);
        final Map<Coordinate, Integer> riskMap = new HashMap<>();

        while (!queue.isEmpty()) {
            final CaveRoute current = queue.poll();
            if (riskMap.containsKey(current.currentPoint) && riskMap.get(current.currentPoint) <= current.totalRisk) {
                continue;
            }

            riskMap.put(current.currentPoint, current.totalRisk);

            for (final Coordinate adjacent : getAdjacentSquares(current.currentPoint)) {
                if (!caveMap.containsKey(adjacent)) {
                    continue;
                }

                queue.add(new CaveRoute(current, adjacent, caveMap.get(adjacent)));
            }
        }

        return riskMap.get(new Coordinate(caveSize - 1, caveSize - 1));
    }

    private static List<Coordinate> getAdjacentSquares(final Coordinate cave) {
        return Arrays.asList(cave.right(), cave.down(), cave.left(), cave.up());
    }

    private static class CaveRoute {
        private final Coordinate currentPoint;
        private int totalRisk = 0;

        public CaveRoute(final Coordinate startPoint) {
            this.currentPoint = startPoint;
        }

        public CaveRoute(final CaveRoute currentRoute, final Coordinate nextPoint, final int addedRisk) {
            this.totalRisk = currentRoute.totalRisk + addedRisk;
            this.currentPoint = nextPoint;
        }
    }
}
