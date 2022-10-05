package com.ai.astar;

import com.ai.astar.domain.Path;
import com.ai.astar.util.PriorityQueueComparator;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;


public class A_Star {

    public static List<int[]> findpath(String[][] matrix, int[] start, int[] end){

        //Compute Gn and Hn for the first position
        int gn = computeManhattan(start,start);
        int hn = computeManhattan(start, end);

        //Initialize Closed list
        List<int[]> closedList = new ArrayList<>();

        //Initialize the Priority queue of Path Obj
        PriorityQueue<Path> queue = new PriorityQueue<>(new PriorityQueueComparator());

        //Offer the start position to Priority Queue
        queue.offer(new Path(start,
                new LinkedList<>(){{ add(start); }},
                gn,hn,gn+hn,
                new LinkedList<>(){{add(gn);}},
                new LinkedList<>(){{add(hn);}},
                new LinkedList<>(){{add(gn+hn);}}));

        //Define and initialize possible directions
        int[][] dirs = new int[][]{{1,0},{0,1},{-1,0},{0,-1}};

        //Run till the queue is Empty
        while(!queue.isEmpty()){

            //Poll the top priority element from the queue
            Path path = queue.poll();
            if(!checkIfAlreadyVisited(path.position,closedList)) closedList.add(path.position);

            //Visit all possible direction
            for(int[] dir : dirs){

                //Next co-ordinates wrt current position
                int x = path.position[0] + dir[0];
                int y = path.position[1] + dir[1];

                //Check for Index out of bound and wall condition
                if(x>=0 && y>=0 && x<matrix.length && y<matrix[0].length && !matrix[x][y].equals("#")){

                    //Check if the point is already visited for given path
                    if(checkIfAlreadyVisited(new int[]{x,y},path.path)) continue;
                    if(checkIfInClosedList(new int[]{x,y},closedList)) continue;

                    //Compute new Gn and Hn
                    int localGn = computeManhattan(new int[]{x,y},path.position) + path.gn;
                    int localHn = computeManhattan(new int[]{x,y},end);

                    //Previous Conditions are satisfied append this new point in the path and offer it to the queue
                    int[] validNextPosition = new int[]{x,y};
                    List<int[]> newPathList = new LinkedList<>(path.path);
                    newPathList.add(validNextPosition);

                    List<Integer> newAllGn = new LinkedList<>(path.allGn);
                    newAllGn.add(localGn);

                    List<Integer> newAllHn = new LinkedList<>(path.allHn);
                    newAllHn.add(localHn);

                    List<Integer> newAllFn = new LinkedList<>(path.allFn);
                    newAllFn.add(localGn+localHn);

                    Path newPath = new Path(validNextPosition,newPathList,localGn,localHn,localGn+localHn,newAllGn,newAllHn,newAllFn);

                    //When target is reached
                    if(x == end[0] && y == end[1]){
                        return newPath.path;
                    }

                    queue.offer(newPath);

                }
            }
        }
        return null;
    }

    private static int computeManhattan(int[] point, int[] target){
        return Math.abs(target[0]-point[0]) + Math.abs(target[1]-point[1]);
    }

    private static boolean checkIfAlreadyVisited(int[] coord, List<int[]> path){
        for(int[] visited: path){
            if(visited[0] == coord[0] && visited[1] == coord[1]) return true;
        }
        return false;
    }

    private static boolean checkIfInClosedList(int[] coord, List<int[]> closedList){
        for(int[] visited: closedList){
            if(visited[0] == coord[0] && visited[1] == coord[1]) return true;
        }
        return false;
    }


    private static void displayMatrix(int[][] matrix){
        for(int[] row : matrix){
            for(int n : row){
                System.out.print(n + " ");
            }
            System.out.println();
        }
    }

}
