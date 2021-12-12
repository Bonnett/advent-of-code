package uk.co.pete_b.advent.aoc2021;

import java.util.*;

public class Day12 {
    public static int countUniqueRoutes(final List<String> routeList, boolean allowSmallCaveTwice) {
        final Map<String, Set<String>> caveMap = new HashMap<>();
        for (String route : routeList) {
            final String[] parts = route.split("-");
            caveMap.compute(parts[0], (destination, destinations) -> {
                if (destinations == null) {
                    destinations = new HashSet<>();
                }
                destinations.add(parts[1]);

                return destinations;
            });

            caveMap.compute(parts[1], (destination, destinations) -> {
                if (destinations == null) {
                    destinations = new HashSet<>();
                }
                destinations.add(parts[0]);

                return destinations;
            });
        }

        final List<String> smallCaves = new ArrayList<>();
        for (String cave : caveMap.keySet()) {
            if (cave.equals("start") || cave.equals("end") || cave.equals(cave.toUpperCase())) {
                continue;
            }
            smallCaves.add(cave);
        }

        final Set<List<String>> finishedRoutes = new HashSet<>();

        if (allowSmallCaveTwice) {
            for (String smallCave : smallCaves) {
                finishedRoutes.addAll(traverseCave(caveMap, smallCave));
            }
        }
        else
        {
            finishedRoutes.addAll(traverseCave(caveMap, null));
        }

        return finishedRoutes.size();
    }

    private static Set<List<String>> traverseCave(final Map<String, Set<String>> caveMap, final String smallCaveAllowedTwice) {
        final Set<List<String>> finishedRoutes = new HashSet<>();
        final List<Route> routes = new ArrayList<>();
        final Set<String> startCaveOutputs = caveMap.get("start");
        for (String cave : startCaveOutputs) {
            final Route route = new Route();
            route.visitCave(cave, smallCaveAllowedTwice);
            routes.add(route);
        }

        while (true) {
            final List<Route> newRoutes = new ArrayList<>();
            for (Route route : routes) {
                final String currentCave = route.getCurrentCave();
                if (currentCave == null) {
                    throw new IllegalStateException("Current Cave cannot be null");
                }
                final Set<String> caves = caveMap.getOrDefault(currentCave, Collections.emptySet());
                for (String cave : caves) {
                    final Route newRoute = new Route(route);
                    if (newRoute.visitCave(cave, smallCaveAllowedTwice)) {
                        if (cave.equals("end")) {
                            finishedRoutes.add(newRoute.routeTaken);
                        } else {
                            newRoutes.add(newRoute);
                        }
                    }
                }
            }

            routes.clear();
            routes.addAll(newRoutes);

            if (newRoutes.isEmpty()) {
                break;
            }
        }

        return finishedRoutes;
    }

    private static class Route {
        private final Set<String> smallCavesVisited;
        private final List<String> routeTaken;
        private boolean visitedSmallCaveTwice;

        public Route() {
            this.smallCavesVisited = new HashSet<>();
            this.routeTaken = new ArrayList<>();

            this.smallCavesVisited.add("start");
            this.routeTaken.add("start");
            this.visitedSmallCaveTwice = false;
        }

        public Route(final Route originalRoute) {
            this.smallCavesVisited = new HashSet<>(originalRoute.smallCavesVisited);
            this.routeTaken = new ArrayList<>(originalRoute.routeTaken);
            this.visitedSmallCaveTwice = originalRoute.visitedSmallCaveTwice;
        }

        public String getCurrentCave() {
            return this.routeTaken.get(this.routeTaken.size() - 1);
        }

        public boolean visitCave(final String cave, final String smallCaveAllowedTwice) {
            if (cave.equals(smallCaveAllowedTwice) && this.smallCavesVisited.contains(cave) && !this.visitedSmallCaveTwice) {
                this.smallCavesVisited.remove(cave);
                this.visitedSmallCaveTwice = true;
            }

            if (cave.equals(cave.toLowerCase())) {
                if (this.smallCavesVisited.contains(cave)) {
                    return false;
                }
                this.smallCavesVisited.add(cave);
            }

            this.routeTaken.add(cave);

            return true;
        }

        public String toString() {
            return this.routeTaken.toString();
        }
    }
}
