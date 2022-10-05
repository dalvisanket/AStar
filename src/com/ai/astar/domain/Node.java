package com.ai.astar.domain;

public class Node {

    public int[] position;

    public int gn;

    public int hn;

    public int fn;

    public Node head;

    public Node(){}

    public Node(int[] position, int gn, int hn, int fn, Node head){
        this.position = position;
        this.gn = gn;
        this.hn = hn;
        this.fn = fn;
        this.head = head;
    }
}
