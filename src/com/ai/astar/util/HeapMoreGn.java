package com.ai.astar.util;

import com.ai.astar.domain.Node;

import java.util.ArrayList;
import java.util.List;

public class HeapMoreGn {

    public List<Node> nodeHeap;

    public HeapMoreGn(){
        this.nodeHeap = new ArrayList<>();
    }

    public List<Node> insert(Node n){

        if(n != null) nodeHeap.add(n);
        int size = nodeHeap.size();
        int j = size - 1;
        for(int k = j; k > 0; k-- ){
            int i = k;
            while (i >= 1) {
                int rootIndex = i % 2 == 0 ? i / 2 - 1 : i / 2;
                Node leaf = nodeHeap.get(i);
                Node root = nodeHeap.get(rootIndex);
                if (leaf.fn < root.fn) {
                    Node temp = leaf;
                    nodeHeap.remove(i);
                    nodeHeap.add(i, root);
                    nodeHeap.remove(rootIndex);
                    nodeHeap.add(rootIndex, leaf);
                }else if (leaf.fn == root.fn && leaf.gn > root.gn) {
                    Node temp = leaf;
                    nodeHeap.remove(i);
                    nodeHeap.add(i, root);
                    nodeHeap.remove(rootIndex);
                    nodeHeap.add(rootIndex, leaf);
                }
                i = i % 2 == 0 ? i / 2 - 1 : i / 2;
            }
        }
        return this.nodeHeap;
    }


    public Node poll(){
        Node first = nodeHeap.remove(0);
        insert(null);
        return first;
    }

    public boolean isEmpty(){
        if(nodeHeap.isEmpty()) return true;
        return false;
    }

    public void sort(){
        insert(null);
    }

    public void displayHeapNode(){
        int j = 0;
        int temp = 0;
        for(int i = 0; i<= (int) (Math.log(nodeHeap.size())/Math.log(2));i++){
            for(; j < temp + Math.pow(2,i) && j < nodeHeap.size();j++){
                System.out.print(" " + nodeHeap.get(j).fn);
            }
            temp = j;
            System.out.println();
        }

    }


}
