package ru.ifmo.ctddev.nechaev.syntaxAnalyzer;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Nechaev Mikhail
 * Since 16/04/14.
 */
public class Tree {
    String node;

    List<Tree> children;

    public Tree(String node, Tree... children) {
        this.node = node;
        this.children = Arrays.asList(children);
    }

    public Tree(String node) {
        this.node = node;
        this.children = Collections.emptyList();
    }

    public String toString() {
        return trace(this, "#");
    }

    private String trace(Tree tree, String spaces) {
        String tmp = tree.node + '\n';
        for (Tree children : tree.children) {
            tmp += spaces + "—-" + trace(children, spaces + "  #");
        }
        return tmp;
    }
}
