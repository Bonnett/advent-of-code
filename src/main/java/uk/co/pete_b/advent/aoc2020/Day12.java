package uk.co.pete_b.advent.aoc2020;

import uk.co.pete_b.advent.utils.CompassBearing;
import uk.co.pete_b.advent.utils.Coordinate;

public class Day12 {
    public static int calculateManhattanDistance(final String instructions) {
        final String[] steps = instructions.split("\n");
        CompassBearing currentHeading = CompassBearing.EAST;
        Coordinate currentPosition = new Coordinate(0, 0);

        for (String instruction : steps) {
            final String operation = instruction.substring(0, 1);
            final int value = Integer.parseInt(instruction.substring(1));

            switch (operation) {
                case "L":
                    if (value == 90) {
                        currentHeading = currentHeading.getLeft();
                    } else if (value == 180) {
                        currentHeading = currentHeading.getLeft().getLeft();
                    } else if (value == 270) {
                        currentHeading = currentHeading.getRight();
                    }
                    break;
                case "R":
                    if (value == 90) {
                        currentHeading = currentHeading.getRight();
                    } else if (value == 180) {
                        currentHeading = currentHeading.getRight().getRight();
                    } else if (value == 270) {
                        currentHeading = currentHeading.getLeft();
                    }
                    break;
                case "F":
                    if (currentHeading == CompassBearing.NORTH) {
                        currentPosition = new Coordinate(currentPosition.getX(), currentPosition.getY() - value);
                    } else if (currentHeading == CompassBearing.EAST) {
                        currentPosition = new Coordinate(currentPosition.getX() + value, currentPosition.getY());
                    } else if (currentHeading == CompassBearing.SOUTH) {
                        currentPosition = new Coordinate(currentPosition.getX(), currentPosition.getY() + value);
                    } else if (currentHeading == CompassBearing.WEST) {
                        currentPosition = new Coordinate(currentPosition.getX() - value, currentPosition.getY());
                    }
                    break;
                case "N":
                    currentPosition = new Coordinate(currentPosition.getX(), currentPosition.getY() - value);
                    break;
                case "E":
                    currentPosition = new Coordinate(currentPosition.getX() + value, currentPosition.getY());
                    break;
                case "S":
                    currentPosition = new Coordinate(currentPosition.getX(), currentPosition.getY() + value);
                    break;
                case "W":
                    currentPosition = new Coordinate(currentPosition.getX() - value, currentPosition.getY());
                    break;
            }

        }

        return Math.abs(currentPosition.getX()) + Math.abs(currentPosition.getY());
    }

    public static int calculateManhattanDistanceWithWaypoint(final String instructions) {
        final String[] steps = instructions.split("\n");
        Coordinate currentPosition = new Coordinate(0, 0);
        Coordinate waypoint = new Coordinate(10, -1);

        for (String instruction : steps) {
            final String operation = instruction.substring(0, 1);
            final int value = Integer.parseInt(instruction.substring(1));

            switch (operation) {
                case "L" -> {
                    for (int i = 0; i < value / 90; i++) {
                        waypoint = new Coordinate(waypoint.getY(), -waypoint.getX());
                    }
                }
                case "R" -> {
                    for (int i = 0; i < value / 90; i++) {
                        waypoint = new Coordinate(-waypoint.getY(), waypoint.getX());
                    }
                }
                case "F" -> currentPosition = new Coordinate((waypoint.getX() * value) + currentPosition.getX(), (waypoint.getY() * value) + currentPosition.getY());
                case "N" -> waypoint = new Coordinate(waypoint.getX(), waypoint.getY() - value);
                case "E" -> waypoint = new Coordinate(waypoint.getX() + value, waypoint.getY());
                case "S" -> waypoint = new Coordinate(waypoint.getX(), waypoint.getY() + value);
                case "W" -> waypoint = new Coordinate(waypoint.getX() - value, waypoint.getY());
            }
        }

        return Math.abs(currentPosition.getX()) + Math.abs(currentPosition.getY());
    }
}
