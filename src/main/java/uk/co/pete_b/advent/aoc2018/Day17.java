package uk.co.pete_b.advent.aoc2018;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import uk.co.pete_b.advent.utils.Coordinate;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("WeakerAccess")
public class Day17 {

    public static Answer calculateWaterCoverage(final List<String> input) {
        final Ground ground = new Ground(input);
        final long totalWater = ground.startSpring();
        final long stagnantWater = ground.stopSpring();
        return new Answer(totalWater, stagnantWater);
    }

    private static class Ground {

        private enum GroundType {
            CLAY("#"), SAND("."), SPRING("+"), WATER("w");

            private final String icon;

            GroundType(final String icon) {
                this.icon = icon;
            }

            @Override
            public String toString() {
                return this.icon;
            }
        }

        private static final Pattern VERTICAL_PATTERN = Pattern.compile("^x=([0-9]+), y=([0-9]+)\\.\\.([0-9]+)$");
        private static final Pattern HORIZONTAL_PATTERN = Pattern.compile("^y=([0-9]+), x=([0-9]+)\\.\\.([0-9]+)$");
        private static final Coordinate WATER_SOURCE = new Coordinate(500, 0);

        final Map<Coordinate, GroundType> groundMap = new HashMap<>(Collections.singletonMap(WATER_SOURCE, GroundType.SPRING));

        private final GroundType[][] groundLayout;

        private final int smallestX;
        private final int largestX;

        private final int smallestY;
        private final int largestY;

        public Ground(final List<String> input) {
            int xMin = 500;
            int xMax = -1;

            int yMin = Integer.MAX_VALUE;
            int yMax = -1;
            for (final String line : input) {
                Matcher m = VERTICAL_PATTERN.matcher(line);
                if (m.matches()) {
                    int x = Integer.parseInt(m.group(1));
                    int startY = Integer.parseInt(m.group(2));
                    int endY = Integer.parseInt(m.group(3));

                    xMin = Math.min(xMin, x);
                    xMax = Math.max(xMax, x);
                    yMin = Math.min(yMin, startY);
                    yMax = Math.max(yMax, endY);

                    for (int y = startY; y <= endY; y++) {
                        groundMap.put(new Coordinate(x, y), GroundType.CLAY);
                    }
                } else {
                    m = HORIZONTAL_PATTERN.matcher(line);
                    if (m.matches()) {

                        int y = Integer.parseInt(m.group(1));
                        int startX = Integer.parseInt(m.group(2));
                        int endX = Integer.parseInt(m.group(3));

                        xMin = Math.min(xMin, startX);
                        xMax = Math.max(xMax, endX);

                        yMin = Math.min(yMin, y);
                        yMax = Math.max(yMax, y);

                        for (int x = startX; x <= endX; x++) {
                            groundMap.put(new Coordinate(x, y), GroundType.CLAY);
                        }
                    }
                }
            }

            this.smallestX = xMin;
            this.largestX = xMax;

            this.smallestY = yMin;
            this.largestY = yMax;
            this.groundLayout = new GroundType[this.largestY + 2][this.largestX + 3];

            for (int y = 0; y <= this.largestY + 1; y++) {
                for (int x = this.smallestX - 2; x <= this.largestX + 1; x++) {
                    final Coordinate coords = new Coordinate(x, y);
                    this.groundLayout[y][x] = this.groundMap.compute(coords, (coordinate, groundType) -> {
                        if (groundType == null) {
                            groundType = GroundType.SAND;
                        }
                        return groundType;
                    });
                }
            }
        }

        public long startSpring() {
            this.pourDownwards(WATER_SOURCE);
            return this.groundMap.entrySet().stream().filter(e -> e.getKey().getY() >= this.smallestY && e.getKey().getY() <= this.largestY && e.getValue() == GroundType.WATER).count();
        }

        public long stopSpring() {
            this.stopDownwards(WATER_SOURCE);
            return this.groundMap.entrySet().stream().filter(e -> e.getKey().getY() >= this.smallestY && e.getKey().getY() <= this.largestY && e.getValue() == GroundType.WATER).count();
        }

        private void stopDownwards(final Coordinate startPoint) {
            Coordinate ground = startPoint;

            while (!isEnclosedByClay(ground)) {
                if (this.groundMap.get(ground) == GroundType.SAND) {
                    return;
                }

                this.setGroundType(ground, GroundType.SAND);
                ground = ground.down();
                if (ground.getY() > this.largestY) {
                    return;
                }
            }

            this.stopSideways(ground.up());
        }

        private void stopSideways(final Coordinate startPoint) {
            this.stopLeft(startPoint.left());
            this.stopRight(startPoint.right());
        }

        private void stopLeft(final Coordinate startPoint) {
            Coordinate ground = startPoint;
            while (this.groundMap.get(ground) == GroundType.WATER) {
                this.setGroundType(ground, GroundType.SAND);
                ground = ground.left();
            }

            if (this.groundMap.get(ground) != GroundType.CLAY && this.groundMap.get(ground.right()) == GroundType.SAND) {
                stopDownwards(ground.downRight());
            }
        }

        private void stopRight(final Coordinate startPoint) {
            Coordinate ground = startPoint;
            while (this.groundMap.get(ground) == GroundType.WATER) {
                this.setGroundType(ground, GroundType.SAND);
                ground = ground.right();
            }

            if (this.groundMap.get(ground) != GroundType.CLAY && this.groundMap.get(ground.left()) == GroundType.SAND) {
                this.stopDownwards(ground.downLeft());
            }
        }

        private void pourDownwards(final Coordinate startPoint) {
            Coordinate ground = startPoint;
            Coordinate lastWatered = ground;
            while (this.groundMap.get(ground) != GroundType.CLAY) {
                this.setGroundType(ground, GroundType.WATER);
                lastWatered = ground;
                if (ground.getY() == this.largestY) {
                    return;
                }
                ground = ground.down();
            }

            this.pourSideways(lastWatered);

            while (isEnclosedByClay(lastWatered)) {
                lastWatered = lastWatered.up();
                if (this.groundMap.get(lastWatered) != GroundType.WATER) {
                    break;
                }
                this.pourSideways(lastWatered);
            }
        }

        private void pourSideways(final Coordinate lastWatered) {
            this.pourLeft(lastWatered.left());
            this.pourRight(lastWatered.right());
        }

        private void pourLeft(final Coordinate startPoint) {
            Coordinate ground = startPoint;
            while (ground.getX() >= this.smallestX - 1 && this.groundMap.get(ground) == GroundType.SAND) {
                this.setGroundType(ground, GroundType.WATER);
                if (this.groundMap.get(ground.down()) == GroundType.SAND) {
                    this.pourDownwards(ground.down());
                    if (this.groundMap.get(ground.downLeft()) == GroundType.SAND) {
                        break;
                    }
                }

                ground = ground.left();
            }
        }

        private void pourRight(Coordinate startPoint) {
            Coordinate ground = startPoint;
            while (ground.getX() < this.largestX + 1 && this.groundMap.get(ground) == GroundType.SAND) {

                this.setGroundType(ground, GroundType.WATER);
                if (this.groundMap.get(ground.down()) == GroundType.SAND) {
                    this.pourDownwards(ground.down());
                    if (this.groundMap.get(ground.downRight()) == GroundType.SAND) {
                        break;
                    }
                }
                ground = ground.right();
            }
        }

        private boolean isEnclosedByClay(final Coordinate coordinate) {
            boolean leftSideIsClay = (this.groundMap.get(coordinate) == GroundType.CLAY);
            Coordinate left = coordinate.left();
            while (!leftSideIsClay && left.getX() >= this.smallestX - 1) {
                if (this.groundMap.get(left) == GroundType.SAND) {
                    break;
                } else if (this.groundMap.get(left) == GroundType.CLAY) {
                    leftSideIsClay = true;
                }
                left = left.left();
            }

            boolean rightSideIsClay = (this.groundMap.get(coordinate) == GroundType.CLAY);
            Coordinate right = coordinate.right();
            while (!rightSideIsClay && right.getX() <= this.largestX + 1) {
                if (this.groundMap.get(right) == GroundType.SAND) {
                    break;
                } else if (this.groundMap.get(right) == GroundType.CLAY) {
                    rightSideIsClay = true;
                }
                right = right.right();
            }

            return leftSideIsClay && rightSideIsClay;
        }

        private void setGroundType(final Coordinate coordinates, final GroundType groundType) {
            this.groundMap.put(coordinates, groundType);
            this.groundLayout[coordinates.getY()][coordinates.getX()] = groundType;
        }

        public void plot() {
            for (int y = 0; y <= this.largestY + 1; y++) {
                for (int x = this.smallestX - 1; x <= this.largestX + 1; x++) {
                    System.out.print(this.groundLayout[y][x]);
                }
                System.out.println();
            }
            System.out.println();
        }
    }

    public static class Answer {
        private final long allWater;
        private final long stagnantWater;

        public Answer(final long allWater, final long stagnantWater) {
            this.allWater = allWater;
            this.stagnantWater = stagnantWater;
        }

        @Override
        public int hashCode() {
            return HashCodeBuilder.reflectionHashCode(this, false);
        }

        @Override
        public boolean equals(final Object otherAnswer) {
            boolean isEqual = false;
            if (otherAnswer instanceof Day17.Answer) {
                isEqual = ((Day17.Answer) otherAnswer).allWater == this.allWater && ((Day17.Answer) otherAnswer).stagnantWater == this.stagnantWater;
            }

            return isEqual;
        }

        @Override
        public String toString() {
            return String.format("Total Water: %d, Stagnant: %d", allWater, stagnantWater);
        }
    }
}

