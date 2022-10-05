package com.ai.astar.util;

import java.util.ArrayList;
import java.util.List;

public class Heap {

    List<Integer> heap;

    public Heap(){
        this.heap = new ArrayList<>();
    }


    public List<Integer> insert(Integer n){

        heap.add(n);
        int size = heap.size();
        int i = size - 1;
        while(i >= 1){
            int rootIndex = i%2 == 0? i/2-1:i/2;
            Integer leaf = heap.get(i);
            Integer root = heap.get(rootIndex);
            if( leaf.intValue() < root.intValue()){
                int temp = leaf;
                heap.remove(i);
                heap.add(i,root);
                heap.remove(rootIndex);
                heap.add(rootIndex,leaf);
            }
            i = i%2 == 0? i/2-1:i/2;
        }

        return this.heap;
    }

    public void displayHeap(){
        int j = 0;
        int temp = 0;
        for(int i = 0; i<= (int) (Math.log(heap.size())/Math.log(2));i++){
            for(; j < temp + Math.pow(2,i) && j < heap.size();j++){
                System.out.print(" " + heap.get(j));
            }
            temp = j;
            System.out.println();
        }

    }


}
