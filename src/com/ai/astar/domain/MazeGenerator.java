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

        double startx = Math.random()*20;
        int x = (int) startx;

        double starty = Math.random()*20;
        int y = (int) starty;

        this.start = new int[]{x,y};
        maze[start[0]][start[1]] = "S";


        double endx = Math.random()*20;
        x = (int) endx;

        double endy = Math.random()*20;
        y = (int) endy;

        this.end = new int[]{x,y};
        maze[end[0]][end[1]] = "G";

    }


}
