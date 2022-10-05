package com.ai.astar.util;

import com.ai.astar.domain.Path;

import java.util.Comparator;

public class PriorityQueueComparator implements Comparator<Path> {

    @Override
    public int compare(Path o1, Path o2) {
        if(o1.fn < o2.fn) return -1;
        else if (o1.fn > o2.fn) return 1;
        return 0;
    }
}
