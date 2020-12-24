package uk.co.pete_b.advent.aoc2020;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import uk.co.pete_b.advent.utils.HexCoordinate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day24 {
    private enum Colour {BLACK, WHITE}

    public static Answer countBlackTiles(final String input) {
        final String[] tiles = input.split("\n");

        Map<HexCoordinate, Colour> tileMap = new HashMap<>();

        for (String tile : tiles) {
            HexCoordinate startTile = new HexCoordinate(0, 0, 0);
            for (int i = 0; i < tile.length(); i++) {
                switch (tile.charAt(i)) {
                    case 'n' -> {
                        i++;
                        if (tile.charAt(i) == 'e') {
                            startTile = startTile.northEast();
                        } else if (tile.charAt(i) == 'w') {
                            startTile = startTile.northWest();
                        }
                    }
                    case 'e' -> startTile = startTile.east();
                    case 's' -> {
                        i++;
                        if (tile.charAt(i) == 'e') {
                            startTile = startTile.southEast();
                        } else if (tile.charAt(i) == 'w') {
                            startTile = startTile.southWest();
                        }
                    }
                    case 'w' -> startTile = startTile.west();
                }
            }

            tileMap.compute(startTile, (hexCoordinate, colour) -> {
                if (colour == null || colour == Colour.WHITE) {
                    colour = Colour.BLACK;
                } else if (colour == Colour.BLACK) {
                    colour = Colour.WHITE;
                }

                return colour;
            });
        }

        final long numberOfBlackTilesToStart = tileMap.values().stream().filter(x -> x == Colour.BLACK).count();

        tileMap = addBorderTiles(tileMap);

        for (int i = 0; i < 100; i++) {
            tileMap = flipTiles(tileMap);
        }

        final long afterIterations = tileMap.values().stream().filter(x -> x == Colour.BLACK).count();

        return new Answer(numberOfBlackTilesToStart, afterIterations);
    }

    private static Map<HexCoordinate, Colour> addBorderTiles(final Map<HexCoordinate, Colour> currentTileMap) {
        final Map<HexCoordinate, Colour> newTileMap = new HashMap<>();

        for (Map.Entry<HexCoordinate, Colour> entry : currentTileMap.entrySet()) {
            for (final HexCoordinate adjacent : getAdjacentTiles(entry.getKey())) {
                if (!currentTileMap.containsKey(adjacent)) {
                    newTileMap.put(adjacent, Colour.WHITE);
                }
            }
            newTileMap.put(entry.getKey(), entry.getValue());
        }

        return newTileMap;
    }

    private static Map<HexCoordinate, Colour> flipTiles(final Map<HexCoordinate, Colour> currentTileMap) {
        final Map<HexCoordinate, Colour> newTileMap = new HashMap<>();

        for (Map.Entry<HexCoordinate, Colour> entry : currentTileMap.entrySet()) {
            flipTile(currentTileMap, newTileMap, entry.getKey(), entry.getValue());
        }

        return newTileMap;
    }

    private static void flipTile(final Map<HexCoordinate, Colour> currentTileMap, final Map<HexCoordinate, Colour> newTileMap, final HexCoordinate tile, final Colour tileColour) {
        final int numberOfBlackTiles = getNumberOfAdjacentBlackTiles(currentTileMap, newTileMap, tile);

        if (tileColour == Colour.BLACK && (numberOfBlackTiles == 0 || numberOfBlackTiles > 2)) {
            newTileMap.put(tile, Colour.WHITE);
        } else if (tileColour == Colour.WHITE && numberOfBlackTiles == 2) {
            newTileMap.put(tile, Colour.BLACK);
        } else {
            newTileMap.put(tile, tileColour);
        }
    }

    private static int getNumberOfAdjacentBlackTiles(final Map<HexCoordinate, Colour> currentTileMap, final Map<HexCoordinate, Colour> newTileMap, final HexCoordinate tile) {
        int numberOfBlackTiles = 0;
        for (final HexCoordinate adjacent : getAdjacentTiles(tile)) {
            if (currentTileMap.containsKey(adjacent)) {
                numberOfBlackTiles += currentTileMap.get(adjacent) == Colour.BLACK ? 1 : 0;
            } else {
                newTileMap.put(adjacent, Colour.WHITE);
            }
        }

        return numberOfBlackTiles;
    }

    private static List<HexCoordinate> getAdjacentTiles(final HexCoordinate key) {
        final List<HexCoordinate> adjacent = new ArrayList<>();
        adjacent.add(key.northEast());
        adjacent.add(key.east());
        adjacent.add(key.southEast());
        adjacent.add(key.southWest());
        adjacent.add(key.west());
        adjacent.add(key.northWest());

        return adjacent;
    }

    public static class Answer {
        private final long numberOfBlackTilesToStart;
        private final long numberOfBlackTilesAfterIterations;

        Answer(long numberOfBlackTilesToStart, long numberOfBlackTilesAfterIterations) {
            this.numberOfBlackTilesToStart = numberOfBlackTilesToStart;
            this.numberOfBlackTilesAfterIterations = numberOfBlackTilesAfterIterations;
        }

        @Override
        public int hashCode() {
            return HashCodeBuilder.reflectionHashCode(this, false);
        }

        @Override
        public boolean equals(final Object otherAnswer) {
            boolean isEqual = false;
            if (otherAnswer instanceof Answer) {
                isEqual = ((Answer) otherAnswer).numberOfBlackTilesToStart == this.numberOfBlackTilesToStart &&
                        ((Answer) otherAnswer).numberOfBlackTilesAfterIterations == this.numberOfBlackTilesAfterIterations;
            }

            return isEqual;
        }

        @Override
        public String toString() {
            return String.format("Starting Black Tiles: %d, After 100 Iterations: %d", numberOfBlackTilesToStart, numberOfBlackTilesAfterIterations);
        }
    }
}
