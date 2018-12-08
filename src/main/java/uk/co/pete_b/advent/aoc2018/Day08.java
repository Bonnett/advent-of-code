package uk.co.pete_b.advent.aoc2018;

import uk.co.pete_b.advent.utils.DataStoringTree;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Day08 {
    public static int sumMetadata(final String input) {
        final List<Integer> numbers = Arrays.stream(input.trim().split(" ")).map(Integer::parseInt).collect(Collectors.toList());
        final Map<Integer, DataStoringTree<Integer, List<Integer>>> tree = buildTree(numbers);
        int sumMetadata = 0;
        for (DataStoringTree<Integer, List<Integer>> node : tree.values()) {
            sumMetadata += node.getData().stream().mapToInt(Integer::intValue).sum();
        }

        return sumMetadata;
    }

    public static int rootNodeValue(final String input) {
        final List<Integer> numbers = Arrays.stream(input.trim().split(" ")).map(Integer::parseInt).collect(Collectors.toList());
        final Map<Integer, DataStoringTree<Integer, List<Integer>>> tree = new HashMap<>();
        final AtomicInteger nodeCounter = new AtomicInteger(0);
        final Iterator<Integer> iterator = numbers.iterator();
        final DataStoringTree<Integer, List<Integer>> rootNode = buildTree(iterator, tree, nodeCounter);

        return getNodeValue(rootNode);
    }

    private static Map<Integer, DataStoringTree<Integer, List<Integer>>> buildTree(List<Integer> numbers) {
        final Map<Integer, DataStoringTree<Integer, List<Integer>>> tree = new HashMap<>();
        final AtomicInteger nodeCounter = new AtomicInteger(0);
        final Iterator<Integer> iterator = numbers.iterator();
        buildTree(iterator, tree, nodeCounter);

        return tree;
    }

    private static DataStoringTree<Integer, List<Integer>> buildTree(final Iterator<Integer> iterator, final Map<Integer, DataStoringTree<Integer, List<Integer>>> tree, final AtomicInteger nodeCounter) {
        final int nodeCount = nodeCounter.getAndIncrement();
        final DataStoringTree<Integer, List<Integer>> rootNode = new DataStoringTree<>(nodeCount, new ArrayList<>());
        tree.put(nodeCount, rootNode);

        final Integer childCount = iterator.next();
        final Integer metadataCount = iterator.next();
        for (int i = 0; i < childCount; i++) {
            final DataStoringTree<Integer, List<Integer>> childNode = buildTree(iterator, tree, nodeCounter);
            tree.put(childNode.getName(), childNode);
            rootNode.addChild(childNode);
        }
        for (int i = 0; i < metadataCount; i++) {
            rootNode.getData().add(iterator.next());
        }

        return rootNode;
    }

    private static int getNodeValue(final DataStoringTree<Integer, List<Integer>> rootNode) {
        if (rootNode.getChildren().isEmpty()) {
            return rootNode.getData().stream().mapToInt(Integer::intValue).sum();
        } else {
            int childSum = 0;
            for (final Integer metadata : rootNode.getData()) {
                if (metadata - 1 < rootNode.getChildren().size()) {
                    childSum += getNodeValue(rootNode.getChildren().get(metadata - 1));
                } else {
                    childSum += 0;
                }
            }

            return childSum;
        }
    }
}
