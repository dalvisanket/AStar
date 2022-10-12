package com.ai.astar.domain;

public class MazeGenerator {

    int rows;
    int columns;
    public String[][] maze;
    public int[] start;
    public int[] end;

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

        double startx = Math.random()*(rows-1);
        int x = (int) startx;

        double starty = Math.random()*(columns-1);
        int y = (int) starty;

        this.start = new int[]{x,y};
        maze[start[0]][start[1]] = "S";


        double endx = Math.random()*(rows-1);
        x = (int) endx;

        double endy = Math.random()*(columns-1);
        y = (int) endy;

        this.end = new int[]{x,y};
        maze[end[0]][end[1]] = "G";

    }


}
