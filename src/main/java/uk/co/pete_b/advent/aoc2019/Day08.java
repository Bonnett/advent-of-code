package uk.co.pete_b.advent.aoc2019;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day08 {
    public static long calculateOneTwoSum(final String imageSpec, final int width, final int height) {
        final List<List<List<Integer>>> layers = new ArrayList<>();
        int layerWithLeast = getLayerWithLeast(imageSpec, width, height, layers);

        final List<List<Integer>> layer = layers.get(layerWithLeast);
        final List<Integer> values = layer.stream().flatMap(List::stream).collect(Collectors.toList());
        long oneCount = values.stream().filter(x -> x == 1).count();
        long twoCount = values.stream().filter(x -> x == 2).count();

        return oneCount * twoCount;
    }

    public static String renderImage(final String imageSpec, final int width, final int height) {
        final List<List<List<Integer>>> layers = new ArrayList<>();
        getLayerWithLeast(imageSpec, width, height, layers);
        final char[][] renderedImage = new char[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                for (final List<List<Integer>> layer : layers) {
                    final int value = layer.get(i).get(j);
                    if (value == 1) {
                        renderedImage[i][j] = '■';
                        break;
                    } else if (value == 0) {
                        renderedImage[i][j] = ' ';
                        break;
                    }
                }
            }
        }

        return StringUtils.join(Arrays.stream(renderedImage).map(String::new).collect(Collectors.toList()), "\r\n");
    }

    private static int getLayerWithLeast(String imageSpec, int width, int height, List<List<List<Integer>>> layers) {
        int index = 0;
        int layerWithLeast = 0;
        int minZeroCount = Integer.MAX_VALUE;
        while (index < imageSpec.length()) {
            final List<List<Integer>> layer = new ArrayList<>();
            int zeroCount = 0;
            for (int i = 0; i < height; i++) {
                final List<Integer> row = new ArrayList<>();
                for (int j = 0; j < width; j++) {
                    final int value = (int) imageSpec.charAt(index++) - 48;
                    row.add(value);
                    if (value == 0) {
                        zeroCount++;
                    }
                }
                layer.add(row);
            }
            if (zeroCount < minZeroCount) {
                layerWithLeast = layers.size();
                minZeroCount = zeroCount;
            }
            layers.add(layer);
        }

        return layerWithLeast;
    }
}
