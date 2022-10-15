package com.ai.astar.util;

import com.ai.astar.domain.Node;

import java.util.ArrayList;
import java.util.List;

public interface Heap {

    public List<Node> insert(Node n);

    public boolean isEmpty();

    public Node poll();

    public void sort();

    public List<Node> getHeap();

}
