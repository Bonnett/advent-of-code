package uk.co.pete_b.advent.aoc2021;

import uk.co.pete_b.advent.utils.Coordinate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day20 {
    private enum Symbol {
        DOT("0"), HASH("1");

        private final String number;

        Symbol(String number) {
            this.number = number;
        }

        public static Symbol fromChar(final char c) {
            if (c == '#') {
                return HASH;
            } else {
                return DOT;
            }
        }

        @Override
        public String toString() {
            return number;
        }
    }


    public static long enhanceImage(final List<String> inputData, int iterations) {
        final String referenceImage = inputData.get(0);

        final Map<Coordinate, Symbol> world = new HashMap<>();

        for (int y = 2; y < inputData.size(); y++) {
            final char[] arr = inputData.get(y).toCharArray();
            for (int x = 0; x < arr.length; x++) {
                world.put(new Coordinate(x, y - 2), Symbol.fromChar(arr[x]));
            }
        }

        int minY = -1;
        int maxY = inputData.size() - 1;
        int minX = -1;
        int maxX = inputData.size() - 1;

        boolean infiniteSpaceFlips = referenceImage.charAt(0) == '#' && referenceImage.charAt(referenceImage.length() - 1) == '.';

        Symbol infiniteSpace = Symbol.DOT;

        for (int i = 0; i < iterations; i++) {
            final Map<Coordinate, Symbol> newWorld = new HashMap<>();

            for (int y = minY; y < maxY; y++) {
                for (int x = minX; x < maxX; x++) {
                    final Coordinate coordinate = new Coordinate(x, y);
                    final String lookup = String.valueOf(world.getOrDefault(coordinate.upLeft(), infiniteSpace)) +
                            world.getOrDefault(coordinate.up(), infiniteSpace) +
                            world.getOrDefault(coordinate.upRight(), infiniteSpace) +
                            world.getOrDefault(coordinate.left(), infiniteSpace) +
                            world.getOrDefault(coordinate, infiniteSpace) +
                            world.getOrDefault(coordinate.right(), infiniteSpace) +
                            world.getOrDefault(coordinate.downLeft(), infiniteSpace) +
                            world.getOrDefault(coordinate.down(), infiniteSpace) +
                            world.getOrDefault(coordinate.downRight(), infiniteSpace);

                    final int index = Integer.parseInt(lookup, 2);
                    final char output = referenceImage.charAt(index);
                    newWorld.put(coordinate, Symbol.fromChar(output));
                }
            }
            world.clear();
            world.putAll(newWorld);

            minX--;
            maxX++;
            minY--;
            maxY++;

            if (infiniteSpaceFlips) {
                infiniteSpace = (infiniteSpace == Symbol.HASH) ? Symbol.DOT : Symbol.HASH;
            }
        }

        return world.values().stream().filter(x -> x == Symbol.HASH).count();
    }
}
