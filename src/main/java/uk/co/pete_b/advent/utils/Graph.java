package uk.co.pete_b.advent.utils;

import java.util.Set;
import java.util.TreeSet;

public class Graph<T extends Comparable<T>> implements Comparable<Graph<T>> {

    private final T name;
    private Set<Graph<T>> parentNodes = new TreeSet<>();
    private Set<Graph<T>> children = new TreeSet<>();

    public Graph(final T name) {
        this.name = name;
    }

    public T getName() {
        return name;
    }

    public void addChild(final Graph<T> child) {
        this.children.add(child);
        child.addParent(this);
    }

    public Set<Graph<T>> getChildren() {
        return children;
    }

    private void addParent(final Graph<T> parentNode) {
        this.parentNodes.add(parentNode);
    }

    public Set<Graph<T>> getParentNodes() {
        return parentNodes;
    }

    @Override
    public int compareTo(final Graph<T> o) {
        return name.compareTo(o.getName());
    }
}