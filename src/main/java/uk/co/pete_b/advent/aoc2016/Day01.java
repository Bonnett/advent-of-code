package uk.co.pete_b.advent.aoc2016;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashSet;
import java.util.Set;

public class Day01 {

    private static final Logger LOGGER = LoggerFactory.getLogger(Day01.class);

    private int bearing;
    private int xCoord;
    private int yCoord;
    private Set<Coords> stepTracing = new LinkedHashSet<>();
    private Coords firstDuplicate;

    public Day01() {
        this.bearing = 0;
        this.xCoord = 0;
        this.yCoord = 0;
    }

    public int[] calculateRoute(String route) {
        String[] steps = route.split(",");

        stepTracing.add(new Coords(xCoord, yCoord));
        boolean foundDuplicate = false;

        for (String step : steps) {
            String turnDirection = step.trim().substring(0, 1);
            int distance = Integer.valueOf(step.trim().substring(1));
            if (turnDirection.equals("R")) {
                bearing += 90;
            } else if (turnDirection.equals("L")) {
                bearing -= 90;
            }

            if (bearing < 0) {
                bearing += 360;
            } else if (bearing >= 360) {
                bearing -= 360;
            }

            if (bearing == 0) {
                int end = (yCoord + distance);
                for (yCoord++; yCoord < end; yCoord++) {
                    Coords coords = new Coords(xCoord, yCoord);
                    if (!foundDuplicate && stepTracing.contains(coords)) {
                        firstDuplicate = coords;
                        foundDuplicate = true;
                    }
                    stepTracing.add(coords);
                }
            } else if (bearing == 90) {
                int end = (xCoord + distance);
                for (xCoord++; xCoord < end; xCoord++) {
                    Coords coords = new Coords(xCoord, yCoord);
                    if (!foundDuplicate && stepTracing.contains(coords)) {
                        firstDuplicate = coords;
                        foundDuplicate = true;
                    }
                    stepTracing.add(coords);
                }
            } else if (bearing == 180) {
                int end = (yCoord - distance);
                for (yCoord--; end < yCoord; yCoord--) {
                    Coords coords = new Coords(xCoord, yCoord);
                    if (!foundDuplicate && stepTracing.contains(coords)) {
                        firstDuplicate = coords;
                        foundDuplicate = true;
                    }

                    stepTracing.add(coords);
                }
            } else if (bearing == 270) {
                int end = (xCoord - distance);
                for (xCoord--; end < xCoord; xCoord--) {
                    Coords coords = new Coords(xCoord, yCoord);
                    if (!foundDuplicate && stepTracing.contains(coords)) {
                        firstDuplicate = coords;
                        foundDuplicate = true;
                    }

                    stepTracing.add(coords);
                }
            }
        }

        LOGGER.info("Finished at " + xCoord + ", " + yCoord);
        return new int[]{xCoord, yCoord};
    }

    public int[] firstDuplicate() {
        return new int[]{firstDuplicate.getX(), firstDuplicate.getY()};
    }

    static class Coords {
        private final int x;
        private final int y;

        public Coords(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        @Override
        public int hashCode() {
            return HashCodeBuilder.reflectionHashCode(this, false);
        }

        @Override
        public boolean equals(Object otherCoords) {
            return EqualsBuilder.reflectionEquals(this, otherCoords, false);
        }

        @Override
        public String toString() {
            return "{" + x + "," + y + "}";
        }
    }
}
