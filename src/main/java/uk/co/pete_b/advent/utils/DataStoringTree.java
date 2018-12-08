package uk.co.pete_b.advent.utils;

import java.util.ArrayList;
import java.util.List;

public class DataStoringTree<T extends Comparable<T>, D> {

    private final T name;
    private D data;
    private DataStoringTree<T, D> parentNode = null;
    private final List<DataStoringTree<T, D>> children = new ArrayList<>();

    public DataStoringTree(final T name, final D data) {
        this.name = name;
        this.data = data;
    }

    public D getData() {
        return this.data;
    }

    public T getName() {
        return this.name;
    }

    public void addChild(final DataStoringTree<T, D> child) {
        this.children.add(child);
        child.setParent(this);
    }

    public List<DataStoringTree<T, D>> getChildren() {
        return children;
    }

    private void setParent(final DataStoringTree<T, D> parentNode) {
        this.parentNode = parentNode;
    }

    public DataStoringTree<T, D> getParentNode() {
        return parentNode;
    }
}