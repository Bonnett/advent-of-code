package uk.co.pete_b.advent.aoc2022;

import uk.co.pete_b.advent.utils.Coordinate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Day08 {

    public static int countVisibleTrees(final List<String> inputData) {
        final int height = inputData.size();
        final int width = inputData.get(0).length();
        final int maxHeight = height - 1;
        final int maxWidth = width - 1;

        final Map<Coordinate, Integer> trees = generateTreeMap(inputData, height, width);

        int visibleTrees = 0;

        for (Map.Entry<Coordinate, Integer> entry : trees.entrySet()) {
            if (isTreeVisible(trees, entry.getKey(), entry.getValue(), maxHeight, maxWidth)) {
                visibleTrees++;
            }
        }
        return visibleTrees;
    }

    public static int findMostScenicTree(final List<String> inputData) {
        final int height = inputData.size();
        final int width = inputData.get(0).length();

        final Map<Coordinate, Integer> trees = generateTreeMap(inputData, height, width);

        int mostScenicScore = 0;

        for (Map.Entry<Coordinate, Integer> entry : trees.entrySet()) {
            int scenicScore = calculateScenicScore(trees, entry.getKey(), entry.getValue());
            if (scenicScore > mostScenicScore) {
                mostScenicScore = scenicScore;
            }
        }
        return mostScenicScore;
    }

    private static int calculateScenicScore(final Map<Coordinate, Integer> trees, final Coordinate key, final Integer treeHeight) {
        final int upScore = calculateScenicScore(trees, key, treeHeight, Coordinate::up);
        final int downScore = calculateScenicScore(trees, key, treeHeight, Coordinate::down);
        final int leftScore = calculateScenicScore(trees, key, treeHeight, Coordinate::left);
        final int rightScore = calculateScenicScore(trees, key, treeHeight, Coordinate::right);

        return upScore * downScore * leftScore * rightScore;
    }

    private static int calculateScenicScore(final Map<Coordinate, Integer> trees, final Coordinate key, final Integer treeHeight,
                                            final Function<Coordinate, Coordinate> nextTreeFn) {
        int score = 0;
        Coordinate nextKey = nextTreeFn.apply(key);
        while (trees.containsKey(nextKey)) {
            score++;
            if (trees.get(nextKey) >= treeHeight) {
                break;
            }

            nextKey = nextTreeFn.apply(nextKey);
        }

        return score;
    }

    private static boolean isTreeVisible(final Map<Coordinate, Integer> trees, final Coordinate key, final int treeHeight, final int maxHeight,
                                         final int maxWidth) {
        if (key.getX() == 0 || key.getX() == maxWidth || key.getY() == 0 || key.getY() == maxHeight) {
            return true;
        }

        if (isTreeVisible(trees, key, treeHeight, Coordinate::up)) {
            return true;
        } else if (isTreeVisible(trees, key, treeHeight, Coordinate::down)) {
            return true;
        } else if (isTreeVisible(trees, key, treeHeight, Coordinate::left)) {
            return true;
        } else {
            return isTreeVisible(trees, key, treeHeight, Coordinate::right);
        }
    }

    private static boolean isTreeVisible(final Map<Coordinate, Integer> trees, final Coordinate key, final int treeHeight,
                                         final Function<Coordinate, Coordinate> nextTreeFn) {
        Coordinate nextKey = nextTreeFn.apply(key);
        while (true) {
            if (!trees.containsKey(nextKey)) {
                return true;
            } else if (trees.get(nextKey) >= treeHeight) {
                break;
            }

            nextKey = nextTreeFn.apply(nextKey);
        }

        return false;
    }

    private static Map<Coordinate, Integer> generateTreeMap(final List<String> inputData, final int height, final int width) {
        final Map<Coordinate, Integer> heightMap = new HashMap<>();
        for (int y = 0; y < height; y++) {
            final String row = inputData.get(y);
            for (int x = 0; x < width; x++) {
                final int treeHeight = ((int) row.charAt(x)) - 48;
                heightMap.put(new Coordinate(x, y), treeHeight);
            }
        }

        return heightMap;
    }
}
