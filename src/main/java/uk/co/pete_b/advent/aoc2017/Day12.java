package uk.co.pete_b.advent.aoc2017;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day12 {

    public static class Answers {
        private final int zeroProgramCount;
        private final int numberOfGroups;

        Answers(int score, int nonCancelled) {
            this.zeroProgramCount = score;
            this.numberOfGroups = nonCancelled;
        }

        public int getZeroProgramCount() {
            return zeroProgramCount;
        }

        public int getNumberOfGroups() {
            return numberOfGroups;
        }
    }

    private static class TreeNode {
        private final Integer name;

        private final Map<Integer, TreeNode> children = new HashMap<>();

        TreeNode(final Integer name) {
            this.name = name;
        }

        void addChild(final TreeNode child) {
            this.children.put(child.name, child);
        }

        void getChildren(final Set<Integer> allChildren) {
            for (TreeNode child : children.values()) {
                if (!allChildren.contains(child.name)) {
                    allChildren.add(child.name);
                    child.getChildren(allChildren);
                }
            }

        }
    }

    private static final Pattern MATCHER = Pattern.compile("^([0-9]+) <-> (([0-9]+(, )?)+)$");

    public static Answers getAnswers(final String input) {
        final Map<Integer, TreeNode> tree = generateTree(input);
        final Set<Integer> childrenTraversed = new HashSet<>();
        final Map<Integer, Integer> groupSizes = new HashMap<>();

        for (int i = 0; i < tree.size(); i++) {
            if (!childrenTraversed.contains(i)) {
                Set<Integer> children = new HashSet<>();

                tree.get(i).getChildren(children);
                childrenTraversed.addAll(children);
                groupSizes.put(i, children.size());
            }
        }

        return new Answers(groupSizes.get(0), groupSizes.size());
    }

    private static Map<Integer, TreeNode> generateTree(final String input) {
        final String[] lines = input.split("\r?\n");

        final Map<Integer, TreeNode> tree = new HashMap<>();


        for (String line : lines) {
            Matcher match = MATCHER.matcher(line);
            if (match.find()) {
                final Integer name = Integer.parseInt(match.group(1));
                TreeNode node;

                if (tree.containsKey(name)) {
                    node = tree.get(name);
                } else {
                    node = new TreeNode(name);
                    tree.put(name, node);
                }

                final List<Integer> children = Arrays.stream(match.group(2).split(", ")).map(Integer::parseInt).collect(Collectors.toList());
                for (Integer child : children) {
                    if (tree.containsKey(child)) {
                        node.addChild(tree.get(child));
                    } else {
                        final TreeNode childNode = new TreeNode(child);
                        tree.put(child, childNode);
                        node.addChild(childNode);
                    }
                }
            }
        }

        return tree;
    }
}
