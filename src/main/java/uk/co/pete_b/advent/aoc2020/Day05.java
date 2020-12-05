package uk.co.pete_b.advent.aoc2020;

import java.util.List;
import java.util.stream.Collectors;

public class Day05 {
    public static int getHighestSeatId(final List<String> seats) {
        return seats.stream().mapToInt(Day05::getSeatId).max().orElseThrow();
    }

    public static int findYourSeatId(final List<String> seats) {
        final List<Integer> seating = seats.stream().mapToInt(Day05::getSeatId).boxed().sorted().collect(Collectors.toList());

        for (int i = 1; i < seating.size(); i++) {
            final int left = seating.get(i - 1);
            final int seat = seating.get(i);

            if (seat - 1 != left) {
                return seat - 1;
            }
        }

        return -1;
    }


    public static int getSeatId(final String seat) {
        final int row = Integer.parseInt(seat.substring(0, 7).replaceAll("F", "0").replaceAll("B", "1"), 2);
        final int col = Integer.parseInt(seat.substring(7).replaceAll("L", "0").replaceAll("R", "1"), 2);

        return (row * 8) + col;
    }
}
