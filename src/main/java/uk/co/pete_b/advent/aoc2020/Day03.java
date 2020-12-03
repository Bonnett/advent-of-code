package uk.co.pete_b.advent.aoc2020;

import org.apache.commons.lang3.tuple.Pair;

public class Day03 {

    @SafeVarargs
    public static long calculateTreesHit(final String inputMap, final Pair<Integer, Integer>... movement) {
        final String[] lines = inputMap.trim().split("\n");
        final char[][] map = new char[lines.length][];
        for (int i = 0; i < lines.length; i++) {
            map[i] = lines[i].toCharArray();
        }

        long mulHit = 1;

        for (Pair<Integer, Integer> move : movement) {
            int curX = 0;
            int curY = 0;

            int treesHit = 0;

            while (curY < lines.length) {
                curY += move.getRight();
                if (curY >= lines.length) {
                    break;
                }

                curX = (curX + move.getLeft()) % map[curY].length;
                if (map[curY][curX] == '#') {
                    treesHit++;
                }
            }

            mulHit *= treesHit;
        }

        return mulHit;
    }
}
