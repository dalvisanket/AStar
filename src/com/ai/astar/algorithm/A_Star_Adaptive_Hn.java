package com.ai.astar.algorithm;

import com.ai.astar.domain.Node;
import com.ai.astar.util.Heap;
import com.ai.astar.util.MatrixUtil;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class A_Star_Adaptive_Hn {

    public static List<Node> repeatedAStar(String[][] matrix, String[][] memMat, int[] start, int[] end){
        //Save full path
        List<Node> fullPath = new LinkedList<>();

        //Stopping condition for Repeated A*
        while(start[0] != end[0] || start[1] != end[1]) {
            List<Node> path = A_Star.findpath(memMat, start, end);
            if(path == null ) {
                System.out.println("********************************** NO POSSIBLE PATH **********************************");
                return null;
            }
            System.out.println("********************************** ESTIMATED PATH IN MEMORY MARTIX **********************************");
            MatrixUtil.displayTempPath(path, memMat);

            int newStartIndex = MatrixUtil.followAStarPath(path,matrix,memMat);
            fullPath.addAll(path.subList(0,newStartIndex+1));

            int[] newStart = path.get(newStartIndex).position;
            start = newStart;
        }
        return fullPath;
    }

    public static List<Node> findpath(String[][] matrix, int[] start, int[] end){

        //Compute Gn and Hn for the first position
        int gn = computeManhattan(start,start);
        int gd_start = computeManhattan(start,end);

        int hn = gd_start - gn;

        //Initialize Closed list
        List<int[]> closedList = new ArrayList<>();

        //Initialize the Custom Heap Node Obj
        Heap openList = new Heap();

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

            if(!checkIfInClosedList(currHead.position,closedList)) closedList.add(currHead.position);

            //Visit all possible direction
            for(int[] dir : dirs){

                //Next co-ordinates wrt current position
                int x = currHead.position[0] + dir[0];
                int y = currHead.position[1] + dir[1];

                //Check for Index out of bound and wall condition
                if(x>=0 && y>=0 && x<matrix.length && y<matrix[0].length && !matrix[x][y].equals("#")){

                    //Check if point already present in closed list
                    if(checkIfInClosedList(new int[]{x,y},closedList)) continue;

                    //Compute new Gn, Hn & Fn
                    int localGn = computeManhattan(new int[]{x,y},currHead.position) + currHead.gn;
                    int localHn = gd_start - localGn;
                    int localFn = localGn + localHn;

                    //Check if the node is on the open list and fn is greater than new fn
                    if(checkIfNodeInOpenList(new int[]{x,y},openList.nodeHeap)){
                        Node openNode = null;
                        for(Node visited: openList.nodeHeap){
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
                        return preparePath(newNode);
                    }

                    openList.insert(newNode);

                }
            }
        }
        return null;
    }

    private static int computeManhattan(int[] point, int[] target){
        return Math.abs(target[0]-point[0]) + Math.abs(target[1]-point[1]);
    }

    private static boolean checkIfNodeInOpenList(int[] coord, List<Node> node){

        for(Node visited: node){
            if(visited.position[0] == coord[0] && visited.position[1] == coord[1]) return true;
        }
        return false;
    }

    private static boolean checkIfInClosedList(int[] coord, List<int[]> closedList){
        for(int[] visited: closedList){
            if(visited[0] == coord[0] && visited[1] == coord[1]) return true;
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
