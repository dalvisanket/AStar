package com.ai.astar.util;

import com.ai.astar.domain.Node;

import java.util.LinkedList;
import java.util.List;

public class MatrixUtil {

    public static String[][] initializeMemoryMatrix(String[][] matrix){
        String[][] memMat = new String[matrix.length][matrix[0].length];

        for(int i = 0; i < matrix.length; i ++){
            for(int j = 0;j < matrix[0].length; j++){
                memMat[i][j] = ".";
            }
        }
        return memMat;
    }

    public static List<Node> invertPath(List<Node> path){

        List<Node> invertedPath = new LinkedList<>();
        for (int i = 0; i < path.size(); i++){
            invertedPath.add(path.get(path.size()-i-1));
        }

        return invertedPath;
    }
    
    public static void displayTempPath(List<Node> path, String[][] matrix){
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
        int[] start = path.get(0).position;
        temp[start[0]][start[1]] = "R";

        int[] end = path.get(path.size()-1).position;
        temp[end[0]][end[1]] = "G";
        displayMatrix(temp);
    }

    public static void displayTempPath(List<Node> path, String[][] matrix, int[] start, int[] end) {
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
        int[] robotStart = path.get(0).position;
        if(robotStart[0] == start[0] && robotStart[1] == start[1]) temp[start[0]][start[1]] = "S/R";
        else{
            temp[start[0]][start[1]] = "S";
            temp[robotStart[0]][robotStart[1]] = "Ro";
        }

        int[] robotEnd = path.get(path.size()-1).position;
        if(robotEnd[0] == end[0] && robotEnd[1] == end[1]) temp[end[0]][end[1]] = "G/Rn";
        else{
            temp[end[0]][end[1]] = "G";
            temp[robotEnd[0]][robotEnd[1]] = "Rn";
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

    public static void displayMatrix(String[][] matrix,int[] start, int[] end){
        matrix[start[0]][start[1]] = "S";
        matrix[end[0]][end[1]] = "G";
        for(String[] row : matrix){
            for(String n : row){
                System.out.print(n + " ");
            }
            System.out.println();
        }
    }

    public static int followAStarPath(List<Node> path,String[][] matrix,String[][] memMat){

        for(int k = 0; k < path.size(); k++){
            int i = path.get(k).position[0];
            int j = path.get(k).position[1];

            if(matrix[i][j].equals("#")) return k-1;
            else{
                updateMemoryMatrix(matrix,memMat,i,j);
            }
        }

        return path.size()-1;
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
