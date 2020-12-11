package uk.co.pete_b.advent.aoc2020;

import uk.co.pete_b.advent.utils.Coordinate;

import java.nio.CharBuffer;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

public class Day11 {

    private static final char EMPTY_SEAT = 'L';
    private static final char OCCUPIED_SEAT = '#';
    private static final char FLOOR = '.';

    public static long calculateEmptySeats(final String exampleInput, boolean adjacent) {
        char[][] seatMap = Arrays.stream(exampleInput.split("\n")).map(String::toCharArray).toArray(char[][]::new);

        final int numberOfSeatsToMatch = (adjacent) ? 4 : 5;

        final SeatCountFunction<char[][], Integer, Integer, Long> seatCountFunction = (adjacent) ? Day11::countAdjacentOccupiedSeats : Day11::countVisibleOccupiedSeats;

        while (true) {
            int numberOfStateChanges = 0;

            char[][] newSeatMap = new char[seatMap.length][seatMap[0].length];

            for (int y = 0; y < seatMap.length; y++) {
                for (int x = 0; x < seatMap[y].length; x++) {
                    final char curSeat = seatMap[y][x];
                    char newSeat = FLOOR;

                    final long occupiedAdjacentSeats = seatCountFunction.apply(seatMap, x, y);
                    if (curSeat == EMPTY_SEAT && occupiedAdjacentSeats == 0) {
                        newSeat = OCCUPIED_SEAT;
                        numberOfStateChanges++;
                    } else if (curSeat == OCCUPIED_SEAT && occupiedAdjacentSeats >= numberOfSeatsToMatch) {
                        newSeat = EMPTY_SEAT;
                        numberOfStateChanges++;
                    } else if (curSeat != FLOOR) {
                        newSeat = curSeat;
                    }

                    newSeatMap[y][x] = newSeat;
                }
            }

            if (numberOfStateChanges == 0) {
                break;
            }

            seatMap = newSeatMap;
        }

        return Arrays.stream(seatMap)
                .map(CharBuffer::wrap)
                .flatMapToInt(CharBuffer::chars)
                .filter(i -> i == OCCUPIED_SEAT)
                .count();
    }

    private static long countVisibleOccupiedSeats(final char[][] seatMap, final int row, final int col) {
        int numVisible = 0;
        // Going up
        for (int y = col - 1; y >= 0; y--) {
            if (seatMap[y][row] == OCCUPIED_SEAT) {
                numVisible++;
                break;
            }

            if (seatMap[y][row] == EMPTY_SEAT) {
                break;
            }
        }

        // Going down
        for (int y = col + 1; y < seatMap.length; y++) {
            if (seatMap[y][row] == OCCUPIED_SEAT) {
                numVisible++;
                break;
            }

            if (seatMap[y][row] == EMPTY_SEAT) {
                break;
            }
        }

        // Going left
        for (int x = row - 1; x >= 0; x--) {
            if (seatMap[col][x] == OCCUPIED_SEAT) {
                numVisible++;
                break;
            }

            if (seatMap[col][x] == EMPTY_SEAT) {
                break;
            }
        }

        // Going right
        for (int x = row + 1; x < seatMap[col].length; x++) {
            if (seatMap[col][x] == OCCUPIED_SEAT) {
                numVisible++;
                break;
            }

            if (seatMap[col][x] == EMPTY_SEAT) {
                break;
            }
        }

        // Going up and left
        for (int y = col - 1; y >= 0; y--) {
            final int newRow = row - (col - y);
            if (newRow < 0 || seatMap[y][newRow] == EMPTY_SEAT) {
                break;
            }

            if (seatMap[y][newRow] == OCCUPIED_SEAT) {
                numVisible++;
                break;
            }
        }

        // Going up and right
        for (int y = col - 1; y >= 0; y--) {
            final int newRow = row + (col - y);
            if (newRow == seatMap[y].length || seatMap[y][newRow] == EMPTY_SEAT) {
                break;
            }

            if (seatMap[y][newRow] == OCCUPIED_SEAT) {
                numVisible++;
                break;
            }
        }

        // Going down and left
        for (int y = col + 1; y < seatMap.length; y++) {
            final int newRow = row - (y - col);
            if (newRow < 0 || seatMap[y][newRow] == EMPTY_SEAT) {
                break;
            }

            if (seatMap[y][newRow] == OCCUPIED_SEAT) {
                numVisible++;
                break;
            }
        }

        // Going down and right
        for (int y = col + 1; y < seatMap.length; y++) {
            final int newRow = row + (y - col);
            if (newRow == seatMap[y].length || seatMap[y][newRow] == EMPTY_SEAT) {
                break;
            }

            if (seatMap[y][newRow] == OCCUPIED_SEAT) {
                numVisible++;
                break;
            }
        }

        return numVisible;
    }

    private static long countAdjacentOccupiedSeats(final char[][] seatMap, final int row, final int col) {
        final Set<Coordinate> adjacent = new HashSet<>();
        final int left = row - 1;
        final int right = row + 1;
        final int above = col - 1;
        final int below = col + 1;

        adjacent.add(new Coordinate(left, above));
        adjacent.add(new Coordinate(row, above));
        adjacent.add(new Coordinate(right, above));
        adjacent.add(new Coordinate(left, col));
        adjacent.add(new Coordinate(right, col));
        adjacent.add(new Coordinate(left, below));
        adjacent.add(new Coordinate(row, below));
        adjacent.add(new Coordinate(right, below));

        return adjacent.stream().filter(checkSeatIsValid(seatMap, col)).filter(coord -> seatMap[coord.getY()][coord.getX()] == OCCUPIED_SEAT).count();
    }

    private static Predicate<Coordinate> checkSeatIsValid(char[][] seatMap, int col) {
        return seat -> seat.getX() >= 0 && seat.getX() < seatMap[col].length && seat.getY() >= 0 && seat.getY() < seatMap.length;
    }

    @FunctionalInterface
    interface SeatCountFunction<A, B, C, R> {
        R apply(A a, B b, C c);
    }
}
