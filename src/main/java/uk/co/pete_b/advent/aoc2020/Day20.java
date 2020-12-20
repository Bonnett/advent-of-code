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

            // Corner pieces only touch 2 other tiles, edge tiles touch 3, middle tiles touch 4 - double these to cover the flipped variant
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
        orientations.add(rotateGrid(completedMap, false, Direction.UP));
        orientations.add(rotateGrid(completedMap, false, Direction.RIGHT));
        orientations.add(rotateGrid(completedMap, false, Direction.DOWN));
        orientations.add(rotateGrid(completedMap, false, Direction.LEFT));
        orientations.add(rotateGrid(completedMap, true, Direction.UP));
        orientations.add(rotateGrid(completedMap, true, Direction.RIGHT));
        orientations.add(rotateGrid(completedMap, true, Direction.DOWN));
        orientations.add(rotateGrid(completedMap, true, Direction.LEFT));

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

        // So weirdly it only works if I get the last corner and I mark it as flipped
        final MapTile topLeft = new ArrayList<>(corners.keySet()).get(3);
        final List<MapTile> topLeftNeighbours = new ArrayList<>(corners.remove(topLeft));
        topLeft.flipped = true;

        grid[0][0] = topLeft;
        grid[0][1] = topLeftNeighbours.get(0);
        grid[1][0] = topLeftNeighbours.get(1);

        setLeftGridSpot(grid);
        topLeft.finalisePosition();
        grid[0][1].finalisePosition();

        final MapTile below = grid[1][0];
        final MatchingEdge belowMatch = below.getMatchingEdge(topLeft.getBottomEdge());

        below.flipped = belowMatch.getFlipped();
        below.topEdge = belowMatch.getDirection();
        below.finalisePosition();

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
                        final MatchingEdge matchesBottomEdge = touchingTile.getMatchingEdge(bottomEdge);
                        if (matchesBottomEdge != null) {
                            touchingTile.flipped = matchesBottomEdge.getFlipped();
                            touchingTile.topEdge = matchesBottomEdge.getDirection();
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
                    final MatchingEdge matchesRightEdge = touchingTile.getMatchingEdge(rightEdge);
                    if (matchesRightEdge != null) {
                        touchingTile.flipped = matchesRightEdge.getFlipped();
                        touchingTile.topEdge = matchesRightEdge.getDirection().getRight();
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

    @SuppressWarnings("SuspiciousNameCombination")
    private static void setLeftGridSpot(MapTile[][] grid) {
        final MapTile tile = grid[0][0];
        MatchingEdge matchingEdge = grid[0][1].getMatchingEdge(tile.getRightEdge());
        if (matchingEdge != null) {
            grid[0][1].flipped = matchingEdge.getFlipped();
            grid[0][1].topEdge = matchingEdge.getDirection().getRight();
            return;
        }

        tile.topEdge = Direction.RIGHT;
        matchingEdge = grid[0][1].getMatchingEdge(tile.getRightEdge());
        if (matchingEdge != null) {
            grid[0][1].flipped = matchingEdge.getFlipped();
            grid[0][1].topEdge = matchingEdge.getDirection().getRight();
            return;
        }

        tile.topEdge = Direction.DOWN;
        matchingEdge = grid[0][1].getMatchingEdge(tile.getRightEdge());
        if (matchingEdge != null) {
            grid[0][1].flipped = matchingEdge.getFlipped();
            grid[0][1].topEdge = matchingEdge.getDirection().getRight();
            return;
        }

        tile.topEdge = Direction.LEFT;
        matchingEdge = grid[0][1].getMatchingEdge(tile.getRightEdge());
        if (matchingEdge != null) {
            grid[0][1].flipped = matchingEdge.getFlipped();
            grid[0][1].topEdge = matchingEdge.getDirection().getRight();
        }
    }

    private static List<String> rotateGrid(final List<String> originalTileData, final boolean flipped, final Direction orientation) {
        List<String> tileData = new ArrayList<>(originalTileData);

        if (flipped) {
            tileData = tileData.stream().map(StringUtils::reverse).collect(Collectors.toList());
        }

        if (orientation == Direction.DOWN) {
            Collections.reverse(tileData);
            return tileData.stream().map(StringUtils::reverse).collect(Collectors.toList());
        } else if (orientation == Direction.LEFT) {
            List<String> newData = new ArrayList<>();
            for (int i = 0; i < tileData.get(0).length(); i++) {
                StringBuilder sb = new StringBuilder();
                for (int j = tileData.size(); j > 0; j--) {
                    sb.append(tileData.get(j - 1).charAt(i));
                }
                newData.add(sb.toString());
            }

            return newData;
        } else if (orientation == Direction.RIGHT) {
            List<String> newData = new ArrayList<>();
            for (int i = tileData.get(0).length(); i > 0; i--) {
                StringBuilder sb = new StringBuilder();
                for (String tileDatum : tileData) {
                    sb.append(tileDatum.charAt(i - 1));
                }
                newData.add(sb.toString());
            }

            return newData;
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

        private final Map<String, Direction> normalEdgeToDirection;
        private final Map<Direction, String> directionToNormalEdge;

        private final Map<String, Direction> flippedEdgeToDirection;
        private final Map<Direction, String> directionToFlippedEdge;

        private final List<String> allEdges;

        private boolean flipped = false;
        private Direction topEdge = Direction.UP;
        private boolean placed = false;

        private MapTile(final long tileId, final List<String> tileData) {
            this.tileId = tileId;
            this.tileData = tileData;

            final String top = tileData.get(0);
            final String right = tileData.stream().map(x -> x.substring(x.length() - 1)).collect(Collectors.joining());
            final String bottom = StringUtils.reverse(tileData.get(tileData.size() - 1));
            final String left = StringUtils.reverse(tileData.stream().map(x -> x.substring(0, 1)).collect(Collectors.joining()));

            this.normalEdgeToDirection = new HashMap<>();
            this.directionToNormalEdge = new HashMap<>();

            this.normalEdgeToDirection.put(top, Direction.UP);
            this.directionToNormalEdge.put(Direction.UP, top);

            this.normalEdgeToDirection.put(right, Direction.RIGHT);
            this.directionToNormalEdge.put(Direction.RIGHT, right);

            this.normalEdgeToDirection.put(bottom, Direction.DOWN);
            this.directionToNormalEdge.put(Direction.DOWN, bottom);

            this.normalEdgeToDirection.put(left, Direction.LEFT);
            this.directionToNormalEdge.put(Direction.LEFT, left);

            this.flippedEdgeToDirection = new HashMap<>();
            this.directionToFlippedEdge = new HashMap<>();

            this.flippedEdgeToDirection.put(StringUtils.reverse(top), Direction.UP);
            this.directionToFlippedEdge.put(Direction.UP, StringUtils.reverse(top));

            this.flippedEdgeToDirection.put(StringUtils.reverse(right), Direction.LEFT);
            this.directionToFlippedEdge.put(Direction.LEFT, StringUtils.reverse(right));

            this.flippedEdgeToDirection.put(StringUtils.reverse(bottom), Direction.DOWN);
            this.directionToFlippedEdge.put(Direction.DOWN, StringUtils.reverse(bottom));

            this.flippedEdgeToDirection.put(StringUtils.reverse(left), Direction.RIGHT);
            this.directionToFlippedEdge.put(Direction.RIGHT, StringUtils.reverse(left));

            this.allEdges = new ArrayList<>(this.normalEdgeToDirection.keySet());
            this.allEdges.addAll(this.flippedEdgeToDirection.keySet());
        }

        private void finalisePosition() {
            this.placed = true;

            this.tileData = rotateGrid(this.tileData, this.flipped, this.topEdge);
        }

        private boolean isPlaced() {
            return this.placed;
        }

        public long getTileId() {
            return tileId;
        }

        public boolean hasMatchingEdge(final String edge) {
            return this.normalEdgeToDirection.containsKey(edge) || this.flippedEdgeToDirection.containsKey(edge);
        }

        public MatchingEdge getMatchingEdge(final String edge) {
            if (this.normalEdgeToDirection.containsKey(edge)) {
                return new MatchingEdge(this.normalEdgeToDirection.get(edge), false);
            } else if (this.flippedEdgeToDirection.containsKey(edge)) {
                return new MatchingEdge(this.flippedEdgeToDirection.get(edge), true);
            } else {
                return null;
            }
        }

        private List<String> getEdges() {
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
            if (flipped) {
                return StringUtils.reverse(this.directionToFlippedEdge.get(topEdge.getRight()));
            }

            return StringUtils.reverse(this.directionToNormalEdge.get(topEdge.getRight()));
        }

        public String getBottomEdge() {
            if (flipped) {
                if (topEdge == Direction.RIGHT) {
                    return StringUtils.reverse(this.directionToFlippedEdge.get(topEdge.getLeft().getLeft()));
                }

                return this.directionToNormalEdge.get(topEdge.getLeft().getLeft());
            }

            return this.directionToFlippedEdge.get(topEdge.getLeft().getLeft());
        }
    }

    private static class MatchingEdge extends Pair<Direction, Boolean> {
        private final Direction direction;
        private Boolean flipped;

        public MatchingEdge(final Direction direction, final Boolean flipped) {
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

        public Boolean getFlipped() {
            return flipped;
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
