package com.ai.astar.domain;

public class MazeGenerator {

    int rows;
    int columns;
    public String[][] maze;

    public MazeGenerator(int rows, int columns){
        this.rows = rows;
        this.columns = columns;
        this.maze = new String[rows][columns];

        generate(maze);
    }

    private void generate(String[][] maze){

        for(int i = 0; i < maze.length; i++){
            for(int j = 0; j < maze[0].length; j++){
                double prob = Math.random();
                if(prob > 0.80) maze[i][j] = "#";
                else maze[i][j] = ".";
            }
        }
        maze[0][0] = ".";
        maze[rows-1][columns-1] = ".";

    }

}
