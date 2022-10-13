import com.ai.astar.algorithm.A_Star;
import com.ai.astar.algorithm.A_Star_Adaptive_Hn;
import com.ai.astar.domain.MazeGenerator;
import com.ai.astar.util.MatrixUtil;

public class Client {
    public static void main(String[] args) {

        //Get VM arguments for the algorithm - If not provided defaults are set
        int mazeCount = Integer.parseInt(System.getProperty("mazeCount") != null ? System.getProperty("mazeCount"):"1");
        int rows = Integer.parseInt(System.getProperty("rows") != null ? System.getProperty("rows"):"20");
        int columns = Integer.parseInt(System.getProperty("columns") != null ? System.getProperty("columns"):"20");
        boolean isFwd = Boolean.parseBoolean(System.getProperty("isFwd") != null ? System.getProperty("isFwd") : "true");

        String[][][] allMaze = new String[mazeCount][][];

        for(int i = 0; i < mazeCount; i++){
            MazeGenerator mazeGenerator = new MazeGenerator(rows, columns);
            allMaze[i] = mazeGenerator.maze;
        }

        for(int itr = 0; itr < mazeCount; itr++){
            System.out.println("********************************** Starting Execution **********************************");

            MatrixUtil.displayMatrix(allMaze[itr]);

            //Start and End points assignment
            int[] start = new int[]{0,0};
            int[] end = new int[]{rows-1,columns-1};

            //Copy generated maze into matrix and create memory matrix
            String[][] matrix = allMaze[itr];
            String[][] memMat = MatrixUtil.initializeMemoryMatrix(matrix);

            //Update current 4 directions from start position
            MatrixUtil.updateMemoryMatrix(matrix, memMat, start[0], start[1]);

            long startTime = System.nanoTime();

            //Start Repeated A*
            A_Star.repeatedAStar(matrix, memMat, start, end);

            System.out.println("AVG Runtime: "+ (System.nanoTime() - startTime) + " nanoseconds");

            if(!isFwd){

                //Copy generated maze into matrix and create memory matrix
                memMat = MatrixUtil.initializeMemoryMatrix(matrix);

                //Update current 4 directions from start position
                MatrixUtil.updateMemoryMatrix(matrix, memMat, start[0], start[1]);

                startTime = System.nanoTime();

                //Start Backward Repeated A*
                A_Star.repeatedAStarBackward(matrix, memMat, start, end);

                System.out.println("AVG Runtime: "+ (System.nanoTime() - startTime) + " nanoseconds");

            }

        }

    }

}
