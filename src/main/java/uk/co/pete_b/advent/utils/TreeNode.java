package uk.co.pete_b.advent.utils;

import java.util.*;

public class TreeNode<T extends Comparable<T>> {

    private final T name;
    private TreeNode<T> parentNode = null;
    private int size;
    private final Map<T, TreeNode<T>> children = new TreeMap<>();

    public TreeNode(final T name) {
        this.name = name;
    }

    public T getName() {
        return name;
    }

    public void addChild(final TreeNode<T> child) {
        this.children.put(child.name, child);
        child.setParent(this);
    }

    public void getChildren(final Set<T> allChildren) {
        for (TreeNode<T> child : children.values()) {
            if (!allChildren.contains(child.name)) {
                allChildren.add(child.name);
                child.getChildren(allChildren);
            }
        }
    }

    public Map<T, TreeNode<T>> getChildren() {
        return children;
    }

    public void setSize(final int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public void setParent(final TreeNode<T> parentNode) {
        this.parentNode = parentNode;
    }

    public TreeNode<T> getParentNode() {
        return parentNode;
    }

    public int getTotalSize() {
        if (children.isEmpty()) {
            return size;
        } else {
            return size + children.values().stream().mapToInt(TreeNode::getTotalSize).sum();
        }
    }

    public boolean isBalanced() {
        if (children.isEmpty()) {
            return true;
        } else {
            List<TreeNode<T>> childList = new ArrayList<>(children.values());
            int size = childList.get(0).getTotalSize();
            for (TreeNode<T> child : childList) {
                if (child.getTotalSize() != size) {
                    return false;
                }
            }

            return true;
        }
    }
}