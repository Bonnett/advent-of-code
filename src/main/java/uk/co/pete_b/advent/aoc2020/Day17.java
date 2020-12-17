package uk.co.pete_b.advent.aoc2020;

import uk.co.pete_b.advent.utils.Coordinate3D;
import uk.co.pete_b.advent.utils.Coordinate4D;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day17 {

    private static final Character ACTIVE = '#';
    private static final Character INACTIVE = '.';

    public static long countActiveCubesInThreeDimensions(final String inputMap) {
        final Map<Coordinate3D, Character> pocketDimension = new HashMap<>();
        final String[] rows = inputMap.split("\n");
        for (int y = 0; y < rows.length; y++) {
            char[] row = rows[y].toCharArray();
            for (int x = 0; x < row.length; x++) {
                pocketDimension.put(new Coordinate3D(x, y, 0), row[x]);
            }
        }

        int startZ = 0;
        int endZ = 1;
        int startY = 0;
        int endY = rows.length;
        int startX = 0;
        int endX = rows[0].length();

        final Map<Coordinate3D, Character> updatedDimensions = new HashMap<>();
        int numberOfCycles = 6;
        for (int cycleNumber = 1; cycleNumber <= numberOfCycles; cycleNumber++) {
            startX--;
            startY--;
            startZ--;
            endX++;
            endY++;
            for (int z = startZ; z < endZ; z++) {
                for (int y = startY; y < endY; y++) {
                    for (int x = startX; x < endX; x++) {
                        final Coordinate3D point = new Coordinate3D(x, y, z);
                        final Character state = pocketDimension.getOrDefault(point, INACTIVE);
                        final List<Coordinate3D> neighbours = getNeighbours(x, y, z);

                        int activeCount = 0;
                        for (Coordinate3D neighbour : neighbours) {
                            if (pocketDimension.getOrDefault(neighbour, INACTIVE) == ACTIVE) {
                                activeCount += (z == 0 && neighbour.getZ() != 0) ? 2 : 1;

                                if (activeCount > 3) {
                                    break;
                                }
                            }
                        }

                        Character newState = INACTIVE;
                        if (state == ACTIVE && (activeCount == 2 || activeCount == 3)) {
                            newState = ACTIVE;
                        } else if (state == INACTIVE && activeCount == 3) {
                            newState = ACTIVE;
                        }
                        updatedDimensions.put(point, newState);
                    }
                }
            }

            pocketDimension.clear();
            pocketDimension.putAll(updatedDimensions);
        }

        int activeCount = 0;
        for (Map.Entry<Coordinate3D, Character> entry : pocketDimension.entrySet()) {
            if (entry.getValue() == ACTIVE) {
                activeCount += (entry.getKey().getZ() == 0) ? 1 : 2;
            }
        }

        return activeCount;
    }

    public static int countActiveCubesInFourDimensions(final String inputMap) {
        final Map<Coordinate4D, Character> pocketDimension = new HashMap<>();
        final String[] rows = inputMap.split("\n");
        for (int y = 0; y < rows.length; y++) {
            char[] row = rows[y].toCharArray();
            for (int x = 0; x < row.length; x++) {
                pocketDimension.put(new Coordinate4D(x, y, 0, 0), row[x]);
            }
        }

        int startZ = 0;
        int endZ = 1;
        int startY = 0;
        int endY = rows.length;
        int startX = 0;
        int endX = rows[0].length();
        int startT = 0;
        int endT = 1;

        final Map<Coordinate4D, Character> updatedDimensions = new HashMap<>();
        int numberOfCycles = 6;
        for (int cycleNumber = 1; cycleNumber <= numberOfCycles; cycleNumber++) {
            startX--;
            startY--;
            startZ--;
            startT--;
            endX++;
            endY++;
            endZ++;

            for (int z = startZ; z < endZ; z++) {
                for (int y = startY; y < endY; y++) {
                    for (int x = startX; x < endX; x++) {
                        for (int t = startT; t < endT; t++) {
                            final Coordinate4D point = new Coordinate4D(x, y, z, t);
                            final Character state = pocketDimension.getOrDefault(point, INACTIVE);
                            final List<Coordinate4D> neighbours = getNeighbours(x, y, z, t);

                            int activeCount = 0;
                            for (Coordinate4D neighbour : neighbours) {
                                if (pocketDimension.getOrDefault(neighbour, INACTIVE) == ACTIVE) {
                                    activeCount += (t == 0 && neighbour.getT() != 0) ? 2 : 1;

                                    if (activeCount > 3) {
                                        break;
                                    }
                                }
                            }

                            Character newState = INACTIVE;
                            if (state == ACTIVE && (activeCount == 2 || activeCount == 3)) {
                                newState = ACTIVE;
                            } else if (state == INACTIVE && activeCount == 3) {
                                newState = ACTIVE;
                            }
                            updatedDimensions.put(point, newState);
                        }
                    }
                }
            }

            pocketDimension.clear();
            pocketDimension.putAll(updatedDimensions);
        }

        int activeCount = 0;
        for (Map.Entry<Coordinate4D, Character> entry : pocketDimension.entrySet()) {
            if (entry.getValue() == ACTIVE) {
                activeCount += (entry.getKey().getT() == 0) ? 1 : 2;
            }
        }

        return activeCount;
    }

    private static List<Coordinate3D> getNeighbours(final int xCoord, final int yCoord, final int zCoord) {
        final List<Coordinate3D> neighbours = new ArrayList<>();
        for (int z = zCoord - 1; z <= zCoord + 1; z++) {
            for (int y = yCoord - 1; y <= yCoord + 1; y++) {
                for (int x = xCoord - 1; x <= xCoord + 1; x++) {
                    if (x == xCoord && y == yCoord && z == zCoord) {
                        continue;
                    }
                    neighbours.add(new Coordinate3D(x, y, z));
                }
            }
        }
        
        return neighbours;
    }

    private static List<Coordinate4D> getNeighbours(final int xCoord, final int yCoord, final int zCoord, final int wCoord) {
        final List<Coordinate4D> neighbours = new ArrayList<>();
        for (int z = zCoord - 1; z <= zCoord + 1; z++) {
            for (int y = yCoord - 1; y <= yCoord + 1; y++) {
                for (int x = xCoord - 1; x <= xCoord + 1; x++) {
                    for (int w = wCoord - 1; w <= wCoord + 1; w++) {

                        if (x == xCoord && y == yCoord && z == zCoord && w == wCoord) {
                            continue;
                        }
                        neighbours.add(new Coordinate4D(x, y, z, w));
                    }
                }
            }
        }

        return neighbours;
    }
}
