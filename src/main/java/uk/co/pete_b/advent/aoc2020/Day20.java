package uk.co.pete_b.advent.aoc2020;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.tuple.Pair;
import uk.co.pete_b.advent.utils.Direction;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day20 {

    private static final Pattern MONSTER_LINE_ONE = Pattern.compile("..................#.");
    private static final Pattern MONSTER_LINE_TWO = Pattern.compile("#....##....##....###");
    private static final Pattern MONSTER_LINE_THREE = Pattern.compile(".#..#..#..#..#..#...");

    public static Answer calculateBorders(final String input) {
        final String[] data = input.split("\n");

        final Map<Long, MapTile> tiles = getMapTiles(data);

        final Map<MapTile, Set<MapTile>> cornerTiles = new HashMap<>();
        final Map<MapTile, Set<MapTile>> allTiles = new HashMap<>();

        for (MapTile tile : tiles.values()) {
            int matchingEdge = 0;
            final Set<MapTile> matchingTiles = new HashSet<>();
            for (String edge : tile.getEdges()) {
                for (MapTile matchingTile : tiles.values()) {
                    if (matchingTile.getTileId() == tile.getTileId()) {
                        continue;
                    }

                    if (matchingTile.hasMatchingEdge(edge)) {
                        matchingTiles.add(matchingTile);
                        matchingEdge++;
                    }
                }
            }

            // Corner pieces only touch 2 other tiles, edge tiles touch 3, middle tiles touch 4 - double these to cover the clockwise variant
            if (matchingEdge == 4) {
                cornerTiles.put(tile, matchingTiles);
            }

            allTiles.put(tile, matchingTiles);
        }

        // Calculate corner product before rebuilding grid
        final long cornerProduct = cornerTiles.keySet().stream().mapToLong(MapTile::getTileId).reduce(1, (a, b) -> a * b);
        int nonSeaMonsterTiles = 0;

        final List<String> completedMap = pieceTogetherMap(cornerTiles, allTiles);

        final List<List<String>> orientations = new ArrayList<>();
        orientations.add(rotateGrid(completedMap, new TileOrientation(Direction.UP, false)));
        orientations.add(rotateGrid(completedMap, new TileOrientation(Direction.RIGHT, false)));
        orientations.add(rotateGrid(completedMap, new TileOrientation(Direction.DOWN, false)));
        orientations.add(rotateGrid(completedMap, new TileOrientation(Direction.LEFT, false)));
        orientations.add(rotateGrid(completedMap, new TileOrientation(Direction.UP, true)));
        orientations.add(rotateGrid(completedMap, new TileOrientation(Direction.RIGHT, true)));
        orientations.add(rotateGrid(completedMap, new TileOrientation(Direction.DOWN, true)));
        orientations.add(rotateGrid(completedMap, new TileOrientation(Direction.LEFT, true)));

        final int numberOfWaves = StringUtils.join(completedMap, "").replaceAll("[.]", "").length();

        boolean monsterFound = false;
        for (List<String> orientation : orientations) {
            int numMonstersFound = 0;
            for (int i = 1; i < orientation.size() - 1; i++) {
                final String line = orientation.get(i);
                final Matcher matcher = MONSTER_LINE_TWO.matcher(line);
                while (matcher.find()) {
                    final String lineAbove = orientation.get(i - 1).substring(matcher.start(), matcher.end());
                    final String lineBelow = orientation.get(i + 1).substring(matcher.start(), matcher.end());
                    if (lineBelow.matches(MONSTER_LINE_THREE.pattern()) && lineAbove.matches(MONSTER_LINE_ONE.pattern())) {
                        numMonstersFound++;
                        monsterFound = true;
                    }
                }
            }

            if (monsterFound) {
                nonSeaMonsterTiles = numberOfWaves - (15 * numMonstersFound);
                break;
            }
        }

        return new Answer(cornerProduct, nonSeaMonsterTiles);
    }

    private static List<String> pieceTogetherMap(final Map<MapTile, Set<MapTile>> corners, final Map<MapTile, Set<MapTile>> allTiles) {
        final int gridSize = (int) Math.sqrt(allTiles.size());
        final MapTile[][] grid = new MapTile[gridSize][gridSize];

        // Select the first corner piece
        final MapTile topLeft = new ArrayList<>(corners.keySet()).get(0);
        final List<MapTile> topLeftNeighbours = new ArrayList<>(corners.remove(topLeft));

        // Arbitrarily assign the two adjacent pieces
        grid[0][0] = topLeft;
        grid[0][1] = topLeftNeighbours.get(0);
        grid[1][0] = topLeftNeighbours.get(1);

        // Now orient these pieces - first the piece to the right
        TileOrientation tileOrientation = grid[0][1].getMatchingEdge(grid[0][0].getRightEdge(), Direction.LEFT);
        while (tileOrientation == null) {
            tileOrientation = grid[0][1].getMatchingEdge(grid[0][0].rotateTile().getRightEdge(), Direction.LEFT);
        }

        grid[0][1].tileOrientation = tileOrientation;

        // Now the piece below - if the bottom edge doesn't match rotate the two tiles we've set 180 degrees and flip them
        TileOrientation belowMatch = grid[1][0].getMatchingEdge(grid[0][0].getBottomEdge(), Direction.UP);
        if (belowMatch == null) {
            grid[0][0].rotateTile().rotateTile().flipTile();
            grid[0][1].rotateTile().rotateTile().flipTile();

            belowMatch = grid[1][0].getMatchingEdge(grid[0][0].getBottomEdge(), Direction.UP);
        }

        grid[1][0].tileOrientation = belowMatch;

        grid[0][0].finalisePosition();
        grid[0][1].finalisePosition();
        grid[1][0].finalisePosition();

        // Remove the tile we've now set
        allTiles.remove(topLeft);

        Set<MapTile> tilesTouching;

        for (int y = 0; y < gridSize; y++) {
            for (int x = 0; x < gridSize - 1; x++) {
                if (y == 0 && x == 0) {
                    continue;
                }

                final MapTile tile = grid[y][x];
                tilesTouching = allTiles.remove(tile);

                if (x == 0 && y + 1 < gridSize) {
                    tilesTouching.remove(grid[y - 1][x]);
                    final String bottomEdge = tile.getBottomEdge();
                    for (MapTile touchingTile : tilesTouching) {
                        if (touchingTile.isPlaced()) {
                            continue;
                        }
                        final TileOrientation matchesBottomEdge = touchingTile.getMatchingEdge(bottomEdge, Direction.UP);
                        if (matchesBottomEdge != null) {
                            touchingTile.tileOrientation = matchesBottomEdge;
                            grid[y + 1][x] = touchingTile;
                            tilesTouching.remove(touchingTile);
                            touchingTile.finalisePosition();
                            break;
                        }
                    }
                }

                final String rightEdge = tile.getRightEdge();
                for (MapTile touchingTile : tilesTouching) {
                    if (touchingTile.isPlaced()) {
                        continue;
                    }
                    final TileOrientation matchesRightEdge = touchingTile.getMatchingEdge(rightEdge, Direction.LEFT);
                    if (matchesRightEdge != null) {
                        touchingTile.tileOrientation = matchesRightEdge;
                        grid[y][x + 1] = touchingTile;
                        touchingTile.finalisePosition();
                        if (x + 2 == gridSize) {
                            allTiles.remove(touchingTile);
                        }
                        break;
                    }
                }
            }
        }

        final List<String> completedMap = new ArrayList<>();
        for (int y = 0; y < gridSize; y++) {
            for (int i = 1; i < topLeft.tileData.size() - 1; i++) {
                final StringBuilder mapLine = new StringBuilder();
                for (int x = 0; x < gridSize; x++) {
                    if (grid[y][x] != null) {
                        final String row = grid[y][x].tileData.get(i);
                        mapLine.append(row, 1, row.length() - 1);
                    }
                }

                completedMap.add(mapLine.toString());
            }
        }

        return completedMap;
    }

    public static List<String> rotateGrid(final List<String> originalTileData, final TileOrientation tileOrientation) {
        final List<String> tileData = new ArrayList<>(originalTileData);

        if (tileOrientation.flipped) {
            switch (tileOrientation.direction) {
                case LEFT -> {
                    final List<String> newData = new ArrayList<>();
                    for (int i = 0; i < tileData.get(0).length(); i++) {
                        final StringBuilder sb = new StringBuilder();
                        for (String tileDatum : tileData) {
                            sb.append(tileDatum.charAt(i));
                        }
                        newData.add(sb.toString());
                    }

                    return newData;
                }
                case UP -> {
                    return tileData.stream().map(StringUtils::reverse).collect(Collectors.toList());
                }
                case RIGHT -> {
                    final List<String> newData = new ArrayList<>();
                    for (int i = tileData.get(0).length(); i > 0; i--) {
                        final StringBuilder sb = new StringBuilder();
                        for (int j = tileData.size(); j > 0; j--) {
                            sb.append(tileData.get(j - 1).charAt(i - 1));
                        }
                        newData.add(sb.toString());
                    }

                    return newData;
                }
                case DOWN -> {
                    Collections.reverse(tileData);
                    return tileData;
                }
            }
        } else {
            switch (tileOrientation.direction) {
                case LEFT -> {
                    final List<String> newData = new ArrayList<>();

                    for (int i = 0; i < tileData.get(0).length(); i++) {
                        final StringBuilder sb = new StringBuilder();
                        for (int j = tileData.size(); j > 0; j--) {
                            sb.append(tileData.get(j - 1).charAt(i));
                        }

                        newData.add(sb.toString());
                    }

                    return newData;
                }
                case UP -> {
                    return originalTileData;
                }
                case RIGHT -> {
                    final List<String> newData = new ArrayList<>();

                    for (int i = tileData.get(0).length(); i > 0; i--) {
                        final StringBuilder sb = new StringBuilder();
                        for (String tileDatum : tileData) {
                            sb.append(tileDatum.charAt(i - 1));
                        }

                        newData.add(sb.toString());
                    }

                    return newData;
                }
                case DOWN -> {
                    Collections.reverse(tileData);
                    return tileData.stream().map(StringUtils::reverse).collect(Collectors.toList());
                }
            }
        }

        return tileData;
    }

    private static Map<Long, MapTile> getMapTiles(String[] data) {
        final Map<Long, MapTile> tiles = new HashMap<>();
        final Iterator<String> dataIter = Arrays.stream(data).iterator();

        long tileId = -1;
        final List<String> tileData = new ArrayList<>();

        while (dataIter.hasNext()) {
            String line = dataIter.next();
            if (line.startsWith("Tile")) {
                tileId = Long.parseLong(line.substring(5, line.length() - 1));
            } else if (line.isEmpty()) {
                tiles.put(tileId, new MapTile(tileId, new ArrayList<>(tileData)));
                tileData.clear();
            } else {
                tileData.add(line);
            }

            if (!dataIter.hasNext()) {
                tiles.put(tileId, new MapTile(tileId, tileData));
            }
        }

        return tiles;
    }

    private static class MapTile {
        private final long tileId;
        private List<String> tileData;

        private final Map<TileOrientation, Map<Direction, String>> orientationToEdge;
        private final Map<Pair<String, Direction>, TileOrientation>  tileEdgeToOrientation;

        private final Set<String> allEdges;

        private TileOrientation tileOrientation;

        private boolean placed = false;

        @SuppressWarnings("SuspiciousNameCombination")
        private MapTile(final long tileId, final List<String> tileData) {
            this.tileId = tileId;
            this.tileData = tileData;
            this.tileOrientation = new TileOrientation(Direction.UP, false);

            final String topLTR = tileData.get(0);
            final String topRTL = StringUtils.reverse(topLTR);
            final String rightTTB = tileData.stream().map(x -> x.substring(x.length() - 1)).collect(Collectors.joining());
            final String rightBTT = StringUtils.reverse(rightTTB);
            final String bottomLTR = tileData.get(tileData.size() - 1);
            final String bottomRTL = StringUtils.reverse(bottomLTR);
            final String leftTTB = tileData.stream().map(x -> x.substring(0, 1)).collect(Collectors.joining());
            final String leftBTT = StringUtils.reverse(leftTTB);

            this.orientationToEdge = new HashMap<>();
            this.tileEdgeToOrientation = new HashMap<>();

            this.orientationToEdge.put(new TileOrientation(Direction.UP, false), getOrientationMap(topLTR, rightTTB, bottomLTR, leftTTB));
            this.orientationToEdge.put(new TileOrientation(Direction.RIGHT, false), getOrientationMap(rightTTB, bottomRTL, leftTTB, topRTL));
            this.orientationToEdge.put(new TileOrientation(Direction.DOWN, false), getOrientationMap(bottomRTL, leftBTT, topRTL, rightBTT));
            this.orientationToEdge.put(new TileOrientation(Direction.LEFT, false), getOrientationMap(leftBTT, topLTR, rightBTT, bottomLTR));

            this.orientationToEdge.put(new TileOrientation(Direction.UP, true), getOrientationMap(topRTL, leftTTB, bottomRTL, rightTTB));
            this.orientationToEdge.put(new TileOrientation(Direction.LEFT, true), getOrientationMap(leftTTB, bottomLTR, rightTTB, topLTR));
            this.orientationToEdge.put(new TileOrientation(Direction.DOWN, true), getOrientationMap(bottomLTR, rightBTT, topLTR, leftBTT));
            this.orientationToEdge.put(new TileOrientation(Direction.RIGHT, true), getOrientationMap(rightBTT, topRTL, leftBTT, bottomRTL));

            this.allEdges = new HashSet<>(Arrays.asList(topLTR, topRTL, rightTTB, rightBTT, bottomLTR, bottomRTL, leftTTB, leftBTT));

            for (Map.Entry<TileOrientation, Map<Direction, String>> entry : this.orientationToEdge.entrySet()) {
                this.tileEdgeToOrientation.put(Pair.of(entry.getValue().get(Direction.UP), Direction.UP), entry.getKey());
                this.tileEdgeToOrientation.put(Pair.of(entry.getValue().get(Direction.RIGHT), Direction.RIGHT), entry.getKey());
                this.tileEdgeToOrientation.put(Pair.of(entry.getValue().get(Direction.DOWN), Direction.DOWN), entry.getKey());
                this.tileEdgeToOrientation.put(Pair.of(entry.getValue().get(Direction.LEFT), Direction.LEFT), entry.getKey());
            }
        }

        private Map<Direction, String> getOrientationMap(final String top, final String right, final String bottom, final String left) {
            final Map<Direction, String> orientations = new HashMap<>();
            orientations.put(Direction.UP, top);
            orientations.put(Direction.RIGHT, right);
            orientations.put(Direction.DOWN, bottom);
            orientations.put(Direction.LEFT, left);

            return orientations;
        }

        private void finalisePosition() {
            this.placed = true;

            this.tileData = rotateGrid(this.tileData, this.tileOrientation);
        }

        private boolean isPlaced() {
            return this.placed;
        }

        public long getTileId() {
            return tileId;
        }

        public boolean hasMatchingEdge(final String edge) {
            return this.allEdges.contains(edge);
        }

        public TileOrientation getMatchingEdge(final String edge, final Direction direction) {
            return this.tileEdgeToOrientation.get(Pair.of(edge, direction));
        }

        private Set<String> getEdges() {
            return this.allEdges;
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder(17, 31).append(tileId).build();
        }

        @Override
        public boolean equals(final Object otherTile) {
            boolean isEqual = false;
            if (otherTile instanceof MapTile) {
                isEqual = ((MapTile) otherTile).tileId == this.tileId;
            }

            return isEqual;
        }

        public String getRightEdge() {
            return this.orientationToEdge.get(this.tileOrientation).get(Direction.RIGHT);
        }

        public String getBottomEdge() {
            return this.orientationToEdge.get(this.tileOrientation).get(Direction.DOWN);
        }

        private MapTile rotateTile() {
            this.tileOrientation = new TileOrientation(this.tileOrientation.direction.getLeft(), this.tileOrientation.flipped);
            return this;
        }

        public void flipTile() {
            this.tileOrientation = new TileOrientation(this.tileOrientation.direction, !this.tileOrientation.flipped);
        }
    }

    private static class TileOrientation extends Pair<Direction, Boolean> {
        private final Direction direction;
        private Boolean flipped;

        public TileOrientation(final Direction direction, final Boolean flipped) {
            this.direction = direction;
            this.flipped = flipped;
        }

        public Direction getDirection() {
            return direction;
        }

        @Override
        public Direction getLeft() {
            return direction;
        }

        @Override
        public Boolean getRight() {
            return flipped;
        }

        @Override
        public Boolean setValue(Boolean value) {
            this.flipped = value;
            return value;
        }
    }

    public static class Answer {
        private final long cornerProduct;
        private final int nonSeaMonsterTiles;

        Answer(long cornerProduct, int nonSeaMonsterTiles) {
            this.cornerProduct = cornerProduct;
            this.nonSeaMonsterTiles = nonSeaMonsterTiles;
        }

        @Override
        public int hashCode() {
            return HashCodeBuilder.reflectionHashCode(this, false);
        }

        @Override
        public boolean equals(final Object otherAnswer) {
            boolean isEqual = false;
            if (otherAnswer instanceof Answer) {
                isEqual = ((Answer) otherAnswer).cornerProduct == this.cornerProduct && ((Answer) otherAnswer).nonSeaMonsterTiles == this.nonSeaMonsterTiles;
            }

            return isEqual;
        }

        @Override
        public String toString() {
            return String.format("Corner Product: %d, Non-Sea Monster Tiles: %d", cornerProduct, nonSeaMonsterTiles);
        }
    }
}
