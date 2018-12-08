package uk.co.pete_b.advent.aoc2017;

import uk.co.pete_b.advent.utils.TreeNode;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day07 {

    private static final Pattern MATCHER = Pattern.compile("^([a-z]+) \\(([0-9]+)\\)( -> ([a-z ,]+))?$");

    public static String findRootElement(final String input) {
        final Map<String, TreeNode<String>> tree = generateTree(input);

        return getTopNode(tree).getName();
    }

    public static int balanceTree(final String input) {
        final Map<String, TreeNode<String>> tree = generateTree(input);

        TreeNode<String> imbalanced = getTopNode(tree);

        if (!imbalanced.isBalanced()) {
            while (!imbalanced.getChildren().isEmpty()) {
                imbalanced = balanceTree(imbalanced.getChildren().values());

                if (imbalanced.isBalanced()) {
                    break;
                }
            }
        }

        final TreeNode<String> parentImbalanced = imbalanced.getParentNode();
        final String imbalancedName = imbalanced.getName();
        Optional<TreeNode<String>> node = parentImbalanced.getChildren().values().stream().filter(x -> !imbalancedName.equals(x.getName())).findAny();

        return imbalanced.getSize() + (node.get().getTotalSize() - imbalanced.getTotalSize());
    }

    private static TreeNode<String> balanceTree(Collection<TreeNode<String>> children) {
        Map<Integer, List<TreeNode<String>>> sizes = new HashMap<>();

        for (TreeNode<String> child : children) {
            int size = child.getTotalSize();
            if (!sizes.containsKey(size)) {
                sizes.put(size, new ArrayList<>());
            }

            sizes.get(size).add(child);
        }

        List<List<TreeNode<String>>> imbalancedList = sizes.values().stream().filter(x -> x.size() == 1).collect(Collectors.toList());

        return imbalancedList.get(0).get(0);
    }

    private static TreeNode<String> getTopNode(final Map<String, TreeNode<String>> tree) {
        final List<TreeNode<String>> top = tree.values().stream().filter(x -> x.getParentNode() == null).collect(Collectors.toList());

        return top.get(0);
    }

    private static Map<String, TreeNode<String>> generateTree(final String input) {
        final String[] lines = input.split("\r?\n");

        final Map<String, TreeNode<String>> tree = new HashMap<>();


        for (String line : lines) {
            Matcher match = MATCHER.matcher(line);
            if (match.find()) {
                final String name = match.group(1);
                final int size = Integer.parseInt(match.group(2));
                TreeNode<String> node;

                if (tree.containsKey(name)) {
                    node = tree.get(name);
                } else {
                    node = new TreeNode<>(name);
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
                            final TreeNode<String> childNode = new TreeNode<>(child);
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
