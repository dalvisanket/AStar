package com.ai.astar.util;

import com.ai.astar.domain.Node;

import java.util.List;

public class MatrixUtil {

    public static void displayPath(List<Node> path, String[][] matrix){
        String[][] temp = new String[matrix.length][matrix[0].length];
        for(int m = 0; m < matrix.length; m++){
            for(int n = 0; n < matrix[0].length; n++){
                temp[m][n] = matrix[m][n];
            }
        }
        for(Node node : path){
            int i = node.position[0];
            int j = node.position[1];
            temp[i][j] = "*";
        }
        displayMatrix(temp);
    }

    public static void displayMatrix(String[][] matrix){
        for(String[] row : matrix){
            for(String n : row){
                System.out.print(n + " ");
            }
            System.out.println();
        }
    }


    public static Node followAStarPath(List<Node> path,String[][] matrix,String[][] memMat){

        for(int k = 0; k < path.size(); k++){
            int i = path.get(k).position[0];
            int j = path.get(k).position[1];

            if(matrix[i][j].equals("#")) return path.get(k-1);
            else{
                updateMemoryMatrix(matrix,memMat,i,j);
            }
        }

        return path.get(path.size()-1);
    }

    public static void updateMemoryMatrix(String[][] matrix,String[][] memMat,int i, int j){
        int[][] dirs = new int[][]{{1,0},{0,1},{-1,0},{0,-1}};
        for(int[] dir : dirs){

            int x = i + dir[0];
            int y = j + dir[1];

            if(x>=0 && y>=0 && x<matrix.length && y<matrix[0].length){
                memMat[x][y] = matrix[x][y];
            }
        }
    }
}
