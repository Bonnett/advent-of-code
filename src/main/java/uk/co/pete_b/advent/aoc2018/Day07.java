package uk.co.pete_b.advent.aoc2018;

import uk.co.pete_b.advent.utils.Graph;

import java.util.*;
import java.util.stream.Collectors;

public class Day07 {

    private static final int ALPHABET_OFFSET = 64;

    public static String getPackageOrder(final List<String> input) {
        final Map<Character, Graph<Character>> tree = generateTree(input);

        // Can be multiple start nodes
        final List<Graph<Character>> nodeList = tree.values().stream().filter(node -> node.getParentNodes().isEmpty()).sorted().collect(Collectors.toList());

        final StringBuilder route = getNavigationRoute(tree, nodeList);

        return route.toString();
    }

    public static int getCompletionTime(final List<String> input, int offset, int numberOfWorkers) {
        final Map<Character, Graph<Character>> tree = generateTree(input);

        // Can be multiple start nodes
        final List<Graph<Character>> availableNodes = tree.values().stream().filter(node -> node.getParentNodes().isEmpty()).sorted().collect(Collectors.toList());

        final Set<Graph<Character>> unfinishedNodes = new TreeSet<>(tree.values());
        final Set<Graph<Character>> inProcessNodes = new TreeSet<>();
        final Set<Graph<Character>> completedNodes = new TreeSet<>();
        final List<Graph<Character>> lastCompletedNodes = new ArrayList<>();

        final List<Worker> workers = new ArrayList<>();
        for (int i = 0; i < numberOfWorkers; i++) {
            workers.add(new Worker());
        }

        for (final Worker worker1 : workers) {
            if (!availableNodes.isEmpty()) {
                final Graph<Character> node = availableNodes.remove(0);
                worker1.startProcessing(node, offset);
                inProcessNodes.add(node);
            }
        }

        int second = 0;

        while (!unfinishedNodes.isEmpty()) {
            second++;
            for (final Worker worker : workers) {
                worker.processSecond();
            }

            for (final Worker worker : workers) {
                if (worker.hasFinished()) {
                    lastCompletedNodes.add(worker.getCurrentNode());
                    completedNodes.add(worker.getCurrentNode());
                    unfinishedNodes.remove(worker.getCurrentNode());
                    inProcessNodes.remove(worker.getCurrentNode());
                    worker.reset();
                }
            }

            for (final Worker worker : workers) {
                if (worker.isIdle()) {
                    final Graph<Character> node = getNextAvailableNode(lastCompletedNodes, inProcessNodes, completedNodes, unfinishedNodes);
                    if (node != null) {
                        worker.startProcessing(node, offset);
                        inProcessNodes.add(node);
                    }
                }
            }

            for (final Worker worker : workers) {
                if (worker.getCurrentNode() != null) {
                    lastCompletedNodes.removeAll(worker.getCurrentNode().getParentNodes());
                }
            }
        }

        return second;
    }

    private static Graph<Character> getNextAvailableNode(List<Graph<Character>> lastCompletedNodes, Set<Graph<Character>> inProcessNodes, Set<Graph<Character>> completedNodes, Set<Graph<Character>> unfinishedNodes) {
        Graph<Character> nextNode = null;
        final TreeSet<Graph<Character>> availableNodes = new TreeSet<>();
        for (final Graph<Character> node : lastCompletedNodes) {
            if (!node.getChildren().isEmpty()) {
                for (final Graph<Character> child : node.getChildren()) {
                    boolean canAdd = true;
                    for (final Graph<Character> parent : child.getParentNodes()) {
                        if (unfinishedNodes.contains(parent) || inProcessNodes.contains(child)) {
                            canAdd = false;
                        }
                    }

                    if (canAdd) {
                        availableNodes.add(child);
                    }
                }
            }

            if (!availableNodes.isEmpty()) {
                nextNode = availableNodes.first();
            }
        }

        if (nextNode == null) {
            for (final Graph<Character> node : completedNodes) {
                if (!node.getChildren().isEmpty()) {
                    for (final Graph<Character> child : node.getChildren()) {
                        boolean canAdd = true;
                        for (final Graph<Character> parent : child.getParentNodes()) {
                            if (completedNodes.contains(child) || unfinishedNodes.contains(parent) || inProcessNodes.contains(child)) {
                                canAdd = false;
                            }
                        }

                        if (canAdd) {
                            availableNodes.add(child);
                        }
                    }
                }

                if (!availableNodes.isEmpty()) {
                    nextNode = availableNodes.first();
                }
            }
        }

        return nextNode;
    }

    private static StringBuilder getNavigationRoute(Map<Character, Graph<Character>> tree, List<Graph<Character>> nodeList) {
        final StringBuilder route = new StringBuilder();
        final Graph<Character> startNode = nodeList.remove(0);

        final Set<Graph<Character>> unfinishedNodes = new TreeSet<>(tree.values());
        final TreeSet<Graph<Character>> availableNodes = new TreeSet<>(nodeList);

        navigateTree(route, startNode, unfinishedNodes, availableNodes);

        return route;
    }

    private static void navigateTree(final StringBuilder route, final Graph<Character> node, final Set<Graph<Character>> unfinishedNodes, final TreeSet<Graph<Character>> availableNodes) {
        availableNodes.remove(node);
        unfinishedNodes.remove(node);
        route.append(node.getName());

        if (!node.getChildren().isEmpty()) {
            for (final Graph<Character> child : node.getChildren()) {
                boolean canAdd = true;
                for (final Graph<Character> parent : child.getParentNodes()) {
                    if (unfinishedNodes.contains(parent)) {
                        canAdd = false;
                    }
                }

                if (canAdd) {
                    availableNodes.add(child);
                }
            }
        }

        if (!availableNodes.isEmpty()) {
            navigateTree(route, availableNodes.first(), unfinishedNodes, availableNodes);
        }
    }

    private static Map<Character, Graph<Character>> generateTree(List<String> input) {
        final Map<Character, Graph<Character>> tree = new HashMap<>();
        for (final String line : input) {
            final Character parentName = line.charAt(5);
            final Character dependentNode = line.charAt(36);
            if (!tree.containsKey(parentName)) {
                tree.put(parentName, new Graph<>(parentName));
            }

            if (!tree.containsKey(dependentNode)) {
                tree.put(dependentNode, new Graph<>(dependentNode));
            }

            tree.get(parentName).addChild(tree.get(dependentNode));
        }

        return tree;
    }

    private static class Worker {
        private Graph<Character> currentNode;
        private int processTimeRemaining;

        public void startProcessing(Graph<Character> node, int offset) {
            this.currentNode = node;
            this.processTimeRemaining = this.currentNode.getName() - ALPHABET_OFFSET + offset;
        }

        public boolean hasFinished() {
            return currentNode != null && processTimeRemaining == 0;
        }

        public boolean isIdle() {
            return currentNode == null;
        }

        public void processSecond() {
            if (currentNode != null) {
                processTimeRemaining--;
            }
        }

        public Graph<Character> getCurrentNode() {
            return currentNode;
        }

        public void reset() {
            currentNode = null;
        }
    }
}
