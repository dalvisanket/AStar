package com.ai.astar.algorithm;

import com.ai.astar.domain.Node;
import com.ai.astar.util.Heap;
import com.ai.astar.util.HeapGreaterGn;
import com.ai.astar.util.MatrixUtil;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Adaptive_A_Star {

    private static int totalClosedNodes = 0;

    public static int totalAdaptiveAstarClosedNodes = 0;
    public static void repeatedAStar(String[][] matrix, String[][] memMat, int[] start, int[] end){

        List<Node> closedListPev = new ArrayList<>();

        System.out.println("START: " + start[0] + " : " + start[1]);
        System.out.println("END: " + end[0] + " : " + end[1]);

        int[] initialStart = start;

        //Stopping condition for Repeated A*
        while(start[0] != end[0] || start[1] != end[1]) {
            List<Node> path = findpath(memMat, start, end, closedListPev);

            if(path == null ) {
                System.out.println("********************************** NO POSSIBLE PATH **********************************");
                break;
            }
            System.out.println("********************************** ESTIMATED PATH IN MEMORY MARTIX - ADAPTIVE ASTAR **********************************");
            MatrixUtil.displayTempPath(path, memMat);

            int newStartIndex = MatrixUtil.followAStarPath(path,matrix,memMat);

            System.out.println("********************************** FOLLOWING THE ADAPTIVE ASTAR PATH **********************************");
            MatrixUtil.displayTempPath(path.subList(0,newStartIndex+1),memMat,initialStart,end);

            int[] newStart = path.get(newStartIndex).position;
            start = newStart;
        }

        System.out.println("Total Closed Nodes : " + totalClosedNodes);

        //Resetting the closed nodes count to zero
        totalAdaptiveAstarClosedNodes +=totalClosedNodes;
        totalClosedNodes = 0;
    }

    public static List<Node> findpath(String[][] matrix, int[] start, int[] end, List<Node> closedListPev){

        //Compute Gn and Hn for the first position
        int gn = computeManhattan(start,start);

        int hn = getNode(closedListPev,start) != null ? getNode(closedListPev,start).hn : computeManhattan(start,end);

        //Initialize Closed list
        List<Node> closedList = new ArrayList<>();

        //Initialize the Custom Heap Node Obj
        Heap openList = new HeapGreaterGn();

        //Offer the start position to Priority Queue
        openList.insert(new Node(start,
                gn,
                hn,
                gn+hn,
                null));


        //Define and initialize possible directions
        int[][] dirs = new int[][]{{1,0},{0,1},{-1,0},{0,-1}};

        //Run till the queue is Empty
        while(!openList.isEmpty()){

            //Poll the top priority element from the queue
            Node currHead = openList.poll();

            if(!checkIfNodeInList(currHead.position,closedList)) {
                closedList.add(currHead);

                //Remove currently from the all previous closed nodes to add the updated gn,hn,fn values later
                closedListPev.remove(getNode(closedListPev,currHead.position));
            }

            //Visit all possible direction
            for(int[] dir : dirs){

                //Next co-ordinates wrt current position
                int x = currHead.position[0] + dir[0];
                int y = currHead.position[1] + dir[1];

                //Check for Index out of bound and wall condition
                if(x>=0 && y>=0 && x<matrix.length && y<matrix[0].length && !matrix[x][y].equals("#")){

                    //Check if point already present in closed list
                    if(checkIfNodeInList(new int[]{x,y},closedList)) continue;

                    //Compute new Gn, Hn & Fn
                    int localGn = computeManhattan(new int[]{x,y},currHead.position) + currHead.gn;

                    int localHn;
                    if(checkIfNodeInList(new int[]{x,y},closedListPev)) localHn = getNode(closedListPev,new int[]{x,y}).hn;
                    else localHn = computeManhattan(new int[]{x,y},end);

                    int localFn = localGn + localHn;

                    //Check if the node is on the open list and fn is greater than new fn
                    if(checkIfNodeInList(new int[]{x,y},openList.getHeap())){
                        Node openNode = null;
                        for(Node visited: openList.getHeap()){
                            if(visited.position[0] == x && visited.position[1] == y) {
                                openNode = visited;
                                break;
                            }
                        }
                        if(openNode.fn > localFn){
                            openNode.gn =localGn;
                            openNode.hn = localHn;
                            openNode.fn = localFn;
                            openNode.head = currHead;
                            openList.sort();
                        }
                        continue;

                    }

                    //Previous Conditions are satisfied add this new node to open list
                    int[] validNextPosition = new int[]{x,y};

                    Node newNode = new Node(validNextPosition,localGn,localHn,localFn,currHead);

                    //When target is reached
                    if(x == end[0] && y == end[1]){
                        Adaptive_A_Star.totalClosedNodes += closedList.size();
                        List<Node> path = preparePath(newNode);
                        updateNewHeuristics(closedList, closedListPev, path.size()-1);
                        return path;
                    }

                    openList.insert(newNode);

                }
            }
        }
        return null;
    }

    private static void updateNewHeuristics(List<Node> closedList, List<Node> closedListPev, int g_path) {
        for(Node node: closedList){
            node.hn = g_path - node.gn;
        }
        closedListPev.addAll(closedList);
    }

    private static Node getNode(List<Node> list, int[] position) {

        for(Node node: list){
            if(node.position[0] == position[0] && node.position[1] == position[1]) return node;
        }
        return null;
    }

    private static int computeManhattan(int[] point, int[] target){
        return Math.abs(target[0]-point[0]) + Math.abs(target[1]-point[1]);
    }

    private static boolean checkIfNodeInList(int[] coord, List<Node> list){
        for(Node visited: list){
            if(visited.position[0] == coord[0] && visited.position[1] == coord[1]) return true;
        }
        return false;
    }

    private static List<Node> preparePath(Node end){

        List<Node> path = new LinkedList<>();
        while(end != null){
            path.add(0,end);
            end = end.head;
        }
        return path;
    }
}
