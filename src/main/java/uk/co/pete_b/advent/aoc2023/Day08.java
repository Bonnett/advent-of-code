package uk.co.pete_b.advent.aoc2023;

import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

import static uk.co.pete_b.advent.utils.Maths.lcm;

public class Day08 {
    public static int countStepsToZZZ(final List<String> network) {
        int steps = 0;
        final String route = network.get(0);

        final Map<String, Pair<String, String>> networkMap = new HashMap<>();
        for (String nodeDetails : network) {
            if (nodeDetails.length() < 15) {
                continue;
            }

            final String nodeName = nodeDetails.substring(0, 3);
            final String left = nodeDetails.substring(7, 10);
            final String right = nodeDetails.substring(12, 15);
            networkMap.put(nodeName, Pair.of(left, right));
        }
        String currentNode = "AAA";
        int currentDirection = 0;
        while (!currentNode.equals("ZZZ")) {
            steps++;
            if (route.charAt(currentDirection) == 'L') {
                currentNode = networkMap.get(currentNode).getLeft();
            } else {
                currentNode = networkMap.get(currentNode).getRight();
            }

            currentDirection = (currentDirection + 1) % route.length();
        }

        return steps;
    }

    public static long countStepsAsGhosts(final List<String> network) {
        int steps = 0;

        final List<String> currentNodes = new ArrayList<>();

        final String route = network.get(0);

        final Map<String, Pair<String, String>> networkMap = new HashMap<>();
        for (String nodeDetails : network) {
            if (nodeDetails.length() < 15) {
                continue;
            }

            final String nodeName = nodeDetails.substring(0, 3);
            final String left = nodeDetails.substring(7, 10);
            final String right = nodeDetails.substring(12, 15);
            networkMap.put(nodeName, Pair.of(left, right));

            if (nodeName.endsWith("A")) {
                currentNodes.add(nodeName);
            }
        }

        final List<Integer> stepsToZ = new ArrayList<>();
        int currentDirection = 0;
        while (!currentNodes.isEmpty()) {
            steps++;
            final List<String> newNodes = new ArrayList<>();
            for (String currentNode : currentNodes) {
                final String newNode = (route.charAt(currentDirection) == 'L') ? networkMap.get(currentNode).getLeft() : networkMap.get(currentNode).getRight();
                if (newNode.endsWith("Z")) {
                    stepsToZ.add(steps);
                } else {
                    newNodes.add(newNode);
                }
            }

            currentDirection = (currentDirection + 1) % route.length();
            currentNodes.clear();
            currentNodes.addAll(newNodes);
        }

        final long[] stepsArray = stepsToZ.stream().mapToLong(Long::valueOf).toArray();

        return lcm(stepsArray);
    }
}
