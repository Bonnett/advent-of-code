package uk.co.pete_b.advent.aoc2017;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day07 {

    private static class TreeNode {
        private final String name;
        private int size;

        private final Map<String, TreeNode> children = new HashMap<>();
        private TreeNode parentNode = null;

        TreeNode(final String name) {
            this.name = name;
        }

        void addChild(final TreeNode child) {
            this.children.put(child.name, child);
            child.parentNode = this;
        }

        void setSize(final int size) {
            this.size = size;
        }

        void setParent(final TreeNode parentNode) {
            this.parentNode = parentNode;
        }

        int getTotalSize() {
            if (children.isEmpty()) {
                return size;
            } else {
                return size + children.values().stream().mapToInt(TreeNode::getTotalSize).sum();
            }
        }

        boolean isBalanced() {
            if (children.isEmpty()) {
                return true;
            } else {
                List<TreeNode> childList = new ArrayList<>(children.values());
                int size = childList.get(0).getTotalSize();
                for (TreeNode child : childList) {
                    if (child.getTotalSize() != size) {
                        return false;
                    }
                }

                return true;
            }
        }
    }

    private static final Pattern MATCHER = Pattern.compile("^([a-z]+) \\(([0-9]+)\\)( -> ([a-z ,]+))?$");

    public static String findRootElement(final String input) {
        final Map<String, TreeNode> tree = generateTree(input);

        return getTopNode(tree).name;
    }

    public static int balanceTree(final String input) {
        final Map<String, TreeNode> tree = generateTree(input);

        TreeNode imbalanced = getTopNode(tree);

        if (!imbalanced.isBalanced()) {
            while (!imbalanced.children.isEmpty()) {
                imbalanced = balanceTree(imbalanced.children.values());

                if (imbalanced.isBalanced()) {
                    break;
                }
            }
        }

        final TreeNode parentImbalanced = imbalanced.parentNode;
        final String imbalancedName = imbalanced.name;
        Optional<TreeNode> node = parentImbalanced.children.values().stream().filter(x -> !imbalancedName.equals(x.name)).findAny();

        return imbalanced.size + (node.get().getTotalSize() - imbalanced.getTotalSize());
    }

    private static TreeNode balanceTree(Collection<TreeNode> children) {
        Map<Integer, List<TreeNode>> sizes = new HashMap<>();

        for (TreeNode child : children) {
            int size = child.getTotalSize();
            if (!sizes.containsKey(size)) {
                sizes.put(size, new ArrayList<>());
            }

            sizes.get(size).add(child);
        }

        List<List<TreeNode>> imbalancedList = sizes.values().stream().filter(x -> x.size() == 1).collect(Collectors.toList());

        return imbalancedList.get(0).get(0);
    }

    private static TreeNode getTopNode(final Map<String, TreeNode> tree) {
        final List<TreeNode> top = tree.values().stream().filter(x -> x.parentNode == null).collect(Collectors.toList());

        return top.get(0);
    }

    private static Map<String, TreeNode> generateTree(final String input) {
        final String[] lines = input.split("\r?\n");

        final Map<String, TreeNode> tree = new HashMap<>();


        for (String line : lines) {
            Matcher match = MATCHER.matcher(line);
            if (match.find()) {
                final String name = match.group(1);
                final int size = Integer.parseInt(match.group(2));
                TreeNode node;

                if (tree.containsKey(name)) {
                    node = tree.get(name);
                } else {
                    node = new TreeNode(name);
                    tree.put(name, node);
                }

                node.setSize(size);

                if (match.group(3) != null) {
                    final String[] children = match.group(4).split(", ");
                    for (String child : children) {
                        if (tree.containsKey(child)) {
                            tree.get(child).setParent(node);
                            node.addChild(tree.get(child));
                        } else {
                            final TreeNode childNode = new TreeNode(child);
                            tree.put(child, childNode);
                            node.addChild(childNode);
                        }
                    }
                }
            }
        }

        return tree;
    }
}
