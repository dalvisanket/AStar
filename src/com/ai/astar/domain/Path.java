package com.ai.astar.domain;

import java.util.List;

public class Path {
    public int[] position;
    public List<int[]> path;
    public int gn;
    public int hn;
    public int fn;

    public List<Integer> allGn;

    public List<Integer> allHn;

    public List<Integer> allFn;

    public Path(){}

    public Path(int[] position,List<int[]> path , int gn, int hn, int fn, List<Integer> allGn, List<Integer> allHn, List<Integer> allFn ){
        this.position = position;
        this.path = path;
        this.gn = gn;
        this.hn = hn;
        this.fn = fn;
        this.allGn = allGn;
        this.allHn = allHn;
        this.allFn = allFn;
    }

}
