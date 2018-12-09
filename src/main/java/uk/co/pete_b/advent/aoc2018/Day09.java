package uk.co.pete_b.advent.aoc2018;

import java.util.Arrays;
import java.util.LinkedList;

public class Day09 {
    public static long calculateWinningScore(final int numberOfPlayers, final int lastMarbleValue) {
        final long[] players = new long[numberOfPlayers];

        final LinkedList<Integer> circle = new LinkedList<>();
        circle.add(0);

        for (int i = 1; i <= lastMarbleValue; i++) {
            if (i % 23 == 0) {
                for (int j=0; j<7; j++) {
                    circle.addLast(circle.removeFirst());
                }

                players[i % numberOfPlayers] += i + circle.removeLast();
            } else {
                for (int j=0; j<2; j++) {
                    circle.addFirst(circle.removeLast());
                }
                circle.add(i);
            }
        }

        return Arrays.stream(players).max().orElse(0);
    }
}
