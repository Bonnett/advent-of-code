package uk.co.pete_b.advent.aoc2018;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import uk.co.pete_b.advent.utils.Coordinate;

import java.util.*;

public class Day22 {
    public static Answers getRiskLevel(final int caveDepth, final Coordinate targetCoordinates) {
        final CaveSystem caveSystem = new CaveSystem(caveDepth, targetCoordinates);
        final int riskLevel = caveSystem.getRiskLevel();
        final int timeToTarget = caveSystem.travelToTarget();

        return new Answers(riskLevel, timeToTarget);
    }

    private static class CaveSystem {
        private static final int ADDITIONAL_CAVE = 300;

        private final CaveRegion[][] caveSystem;

        private final Coordinate startCoordinates = new Coordinate(0, 0);
        private final Coordinate targetCoordinates;

        public CaveSystem(final int caveDepth, final Coordinate targetCoordinates) {
            this.targetCoordinates = targetCoordinates;
            this.caveSystem = new CaveRegion[targetCoordinates.getY() + ADDITIONAL_CAVE][targetCoordinates.getX() + ADDITIONAL_CAVE];

            this.caveSystem[0][0] = new CaveRegion(caveDepth, startCoordinates, 0);

            this.caveSystem[targetCoordinates.getY()][targetCoordinates.getX()] = new CaveRegion(caveDepth, targetCoordinates, 0);

            for (int x = 1; x < this.caveSystem[0].length; x++) {
                this.caveSystem[0][x] = new CaveRegion(caveDepth, new Coordinate(x, 0), x * 16807);
            }

            for (int y = 1; y < this.caveSystem.length; y++) {
                this.caveSystem[y][0] = new CaveRegion(caveDepth, new Coordinate(0, y), y * 48271);
            }

            for (int y = 1; y < this.caveSystem.length; y++) {
                for (int x = 1; x < this.caveSystem[y].length; x++) {
                    final Coordinate coordinates = new Coordinate(x, y);
                    if (!coordinates.equals(targetCoordinates)) {
                        this.caveSystem[y][x] = new CaveRegion(caveDepth, coordinates, this.caveSystem[y - 1][x].erosionIndex * this.caveSystem[y][x - 1].erosionIndex);
                    }
                }
            }
        }

        public int getRiskLevel() {
            int riskLevel = 0;
            for (int y = 0; y <= targetCoordinates.getY(); y++) {
                for (int x = 0; x <= targetCoordinates.getX(); x++) {
                    riskLevel += caveSystem[y][x].regionType.riskLevel;
                }
            }

            return riskLevel;
        }

        public int travelToTarget() {
            final CaveRoute route = new CaveRoute(this.caveSystem[0][0], CaveTool.TORCH, 0);
            final CaveRoute target = new CaveRoute(this.caveSystem[this.targetCoordinates.getY()][this.targetCoordinates.getX()], CaveTool.TORCH, 0);

            final PriorityQueue<CaveRoute> queue = new PriorityQueue<>(Comparator.comparing(r -> r.minutesToReach));
            queue.add(route);
            final Map<CaveRoute, Integer> minutes = new HashMap<>();

            while (!queue.isEmpty()) {
                final CaveRoute current = queue.poll();
                if (minutes.containsKey(current) && minutes.get(current) <= current.minutesToReach) {
                    continue;
                }

                minutes.put(current, current.minutesToReach);
                if (current.equals(target)) {
                    return current.minutesToReach;
                }

                for (final CaveTool tool : current.caveRegion.regionType.toolList) {
                    if (tool != current.tool) {
                        queue.add(new CaveRoute(current.caveRegion, tool, current.minutesToReach + 7));
                    }
                }

                final List<Coordinate> adjacents = getAdjacentSquares(current.caveRegion);

                for (final Coordinate adjacent : adjacents) {
                    if (adjacent.equals(this.startCoordinates) || (adjacent.getY() < 0 || adjacent.getX() < 0 || adjacent.getY() == this.caveSystem.length || adjacent.getX() == this.caveSystem[adjacent.getY()].length)) {
                        continue;
                    }

                    final CaveRegion caveRegion = this.caveSystem[adjacent.getY()][adjacent.getX()];
                    if (caveRegion.regionType.allowedTools.contains(current.tool)) {
                        queue.add(new CaveRoute(caveRegion, current.tool, current.minutesToReach + 1));
                    }
                }
            }

            return -1;
        }

        private List<Coordinate> getAdjacentSquares(final CaveRegion caveRegion) {
            return Arrays.asList(caveRegion.coordinate.right(), caveRegion.coordinate.down(), caveRegion.coordinate.left(), caveRegion.coordinate.up());
        }

        public enum RegionType {
            ROCKY(".", 0, new HashSet<>(Arrays.asList(CaveTool.CLIMBING_GEAR, CaveTool.TORCH))),
            WET("=", 1, new HashSet<>(Arrays.asList(CaveTool.CLIMBING_GEAR, CaveTool.NEITHER))),
            NARROW("|", 2, new HashSet<>(Arrays.asList(CaveTool.TORCH, CaveTool.NEITHER)));

            private final String icon;
            private final int riskLevel;
            private final Set<CaveTool> allowedTools;
            private final List<CaveTool> toolList;

            RegionType(final String icon, final int riskLevel, final Set<CaveTool> allowedTools) {
                this.icon = icon;
                this.riskLevel = riskLevel;
                this.allowedTools = allowedTools;
                this.toolList = new ArrayList<>(allowedTools);
            }

            @Override
            public String toString() {
                return this.icon;
            }

            public static RegionType fromRiskLevel(final int riskLevel) {
                RegionType region = null;
                for (RegionType regionType : values()) {
                    if (regionType.riskLevel == riskLevel) {
                        region = regionType;
                        break;
                    }
                }

                return region;
            }
        }

        public enum CaveTool {
            CLIMBING_GEAR, TORCH, NEITHER
        }

        public static class CaveRegion {
            private final Coordinate coordinate;
            private RegionType regionType;
            private final int erosionIndex;

            public CaveRegion(final int caveDepth, final Coordinate coordinate, final int geologicIndex) {
                this.coordinate = coordinate;
                this.erosionIndex = ((geologicIndex + caveDepth) % 20183);
                this.regionType = RegionType.fromRiskLevel(erosionIndex % 3);
            }

            @Override
            public int hashCode() {
                return HashCodeBuilder.reflectionHashCode(this, false);
            }

            @Override
            public boolean equals(final Object otherRegion) {
                boolean isEqual = false;
                if (otherRegion instanceof CaveRegion) {
                    isEqual = ((CaveRegion) otherRegion).coordinate == this.coordinate && ((CaveRegion) otherRegion).regionType == this.regionType && ((CaveRegion) otherRegion).erosionIndex == this.erosionIndex;
                }

                return isEqual;
            }
        }

        public static class CaveRoute {
            private transient int minutesToReach;
            private CaveTool tool;
            private CaveRegion caveRegion;

            public CaveRoute(CaveRegion caveRegion, CaveTool tool, int minutesToReach) {
                this.caveRegion = caveRegion;
                this.tool = tool;
                this.minutesToReach = minutesToReach;
            }

            @Override
            public int hashCode() {
                return HashCodeBuilder.reflectionHashCode(this, false);
            }

            @Override
            public boolean equals(final Object otherRoute) {
                boolean isEqual = false;
                if (otherRoute instanceof CaveRoute) {
                    isEqual = ((CaveRoute) otherRoute).caveRegion == this.caveRegion && ((CaveRoute) otherRoute).tool == this.tool;
                }

                return isEqual;
            }
        }
    }

    public static class Answers {
        private final int riskLevel;
        private final int timeToReachTarget;

        Answers(final int riskLevel, final int timeToReachTarget) {
            this.riskLevel = riskLevel;
            this.timeToReachTarget = timeToReachTarget;
        }

        int getRiskLevel() {
            return this.riskLevel;
        }

        int getTimeToReachTarget() {
            return this.timeToReachTarget;
        }
    }
}
