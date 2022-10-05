package com.ai.astar.util;

import com.ai.astar.domain.Node;

import java.util.Comparator;

public class OpenListComparator implements Comparator<Node> {

    @Override
    public int compare(Node o1, Node o2) {
        if(o1.fn < o2.fn) return -1;
        else if (o1.fn > o2.fn) return 1;
        return 0;
    }
}
