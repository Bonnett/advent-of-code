package uk.co.pete_b.advent.aoc2023;

import uk.co.pete_b.advent.utils.Coordinate;
import uk.co.pete_b.advent.utils.LongCoordinate;

import java.util.ArrayList;
import java.util.List;

import static uk.co.pete_b.advent.utils.Maths.longShoelaceArea;
import static uk.co.pete_b.advent.utils.Maths.shoelaceArea;

public class Day18 {
    public static int calculateLavaCapacity(final List<String> diggingInstructions) {
        Coordinate currentLocation = new Coordinate(0, 0);

        final List<Coordinate> trenchRoute = new ArrayList<>();
        trenchRoute.add(currentLocation);

        int routeLength = 0;

        for (String instruction : diggingInstructions) {
            final String[] parts = instruction.split(" ");
            final String direction = parts[0];
            final int length = Integer.parseInt(parts[1]);
            routeLength += length;

            switch (direction) {
                case "R" -> currentLocation = new Coordinate(currentLocation.getX() + length, currentLocation.getY());
                case "U" -> currentLocation = new Coordinate(currentLocation.getX(), currentLocation.getY() - length);
                case "L" -> currentLocation = new Coordinate(currentLocation.getX() - length, currentLocation.getY());
                case "D" -> currentLocation = new Coordinate(currentLocation.getX(), currentLocation.getY() + length);
            }
            trenchRoute.add(currentLocation);
        }

        return shoelaceArea(trenchRoute) + routeLength / 2 + 1;
    }
    public static long calculateLavaCapacityViaHex(final List<String> diggingInstructions) {
        LongCoordinate currentLocation = new LongCoordinate(0, 0);

        final List<LongCoordinate> trenchRoute = new ArrayList<>();
        trenchRoute.add(currentLocation);

        long routeLength = 0;

        for (String instruction : diggingInstructions) {
            final String[] parts = instruction.split(" ");
            final String direction = parts[2].substring(7, 8);
            final long length = Long.parseLong(parts[2].substring(2, 7), 16);
            routeLength += length;

            switch (direction) {
                case "0" -> currentLocation = new LongCoordinate(currentLocation.getX() + length, currentLocation.getY());
                case "1" -> currentLocation = new LongCoordinate(currentLocation.getX(), currentLocation.getY() + length);
                case "2" -> currentLocation = new LongCoordinate(currentLocation.getX() - length, currentLocation.getY());
                case "3" -> currentLocation = new LongCoordinate(currentLocation.getX(), currentLocation.getY() - length);
            }

            trenchRoute.add(currentLocation);
        }

        return longShoelaceArea(trenchRoute) + (routeLength / 2L) + 1L;
    }
}
