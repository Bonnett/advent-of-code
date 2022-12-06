package uk.co.pete_b.advent.aoc2022;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;

public class Day06 {

    public static int findFirstMarker(final String input, final int bufferSize) {
        int pointer = 0;
        final Deque<Character> buffer = new ArrayDeque<>();
        for (; pointer < input.length(); pointer++) {
            buffer.add(input.charAt(pointer));
            if (buffer.size() > bufferSize) {
                buffer.remove();
            }

            if ((new HashSet<>(buffer)).size() == bufferSize) {
                break;
            }
        }

        // Zero indexed
        return pointer + 1;
    }
}
