import com.ai.astar.algorithm.A_Star;
import com.ai.astar.algorithm.A_Star_Adaptive_Hn;
import com.ai.astar.domain.MazeGenerator;
import com.ai.astar.domain.Node;
import com.ai.astar.util.MatrixUtil;

import java.util.List;

public class Client {
    public static void main(String[] args) {

        double sum = 0;
        for(int itr = 0; itr < 1; itr++){
            System.out.println("********************************** Starting Execution **********************************");
            final long startTime = System.nanoTime();
            //Check for forward or backward A*
            boolean isFwd = true;
            if (args.length != 0 && args[0].equalsIgnoreCase("backward")) {
                isFwd = false;
            }

            //Generate and display matrix/maze
            MazeGenerator mazeGenerator = new MazeGenerator(21, 21);
            MatrixUtil.displayMatrix(mazeGenerator.maze);

            System.out.println("START: " + mazeGenerator.start[0] + " : " + mazeGenerator.start[1]);
            System.out.println("END: " + mazeGenerator.end[0] + " : " + mazeGenerator.end[1]);

            //Start and End points assignment
            int[] start;
            int[] end;

            if (isFwd) {
                start = mazeGenerator.start;
                end = mazeGenerator.end;
            } else {
                start = mazeGenerator.end;
                end = mazeGenerator.start;

                //Replace in generator
                int[] temp = mazeGenerator.start;
                mazeGenerator.start = mazeGenerator.end;
                mazeGenerator.end = temp;

                mazeGenerator.maze[start[0]][start[1]] = "S";
                mazeGenerator.maze[end[0]][end[1]] = "G";
            }


            //Copy generated maze into matrix and create memory matrix
            String[][] matrix = mazeGenerator.maze;
            String[][] memMat = MatrixUtil.initializeMemoryMatrix(matrix);


            //Update current 4 directions from start position
            MatrixUtil.updateMemoryMatrix(matrix, memMat, start[0], start[1]);

            //Start Repeated A*
            List<Node> fullPath = A_Star.repeatedAStar(matrix, memMat, start, end);


            if (fullPath != null) {
                System.out.println("********************************** FINAL PATH **********************************");
                if (!isFwd) {
                    fullPath = MatrixUtil.invertPath(fullPath);
                }
                MatrixUtil.displayPath(fullPath, memMat);
            }

            sum += (System.nanoTime() - startTime);
        }

        System.out.println("AVG Runtime: "+ sum/100 + " nanoseconds");


    }

}
