import com.ai.astar.algorithm.A_Star;
import com.ai.astar.algorithm.Adaptive_A_Star;
import com.ai.astar.domain.AStarType;
import com.ai.astar.domain.MazeGenerator;
import com.ai.astar.util.MatrixUtil;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class Client {
    public static void main(String[] args) {


        //Get VM arguments for the algorithm - If not provided defaults are set
        int mazeCount = Integer.parseInt(System.getProperty("mazeCount") != null ? System.getProperty("mazeCount"):"1");
        int rows = Integer.parseInt(System.getProperty("rows") != null ? System.getProperty("rows"):"20");
        int columns = Integer.parseInt(System.getProperty("columns") != null ? System.getProperty("columns"):"20");
        Set<AStarType> aStarTypes = System.getProperty("aStarTypes") != null?
                                            Arrays.stream(System.getProperty("aStarTypes").split(","))
                                                .map( str -> AStarType.valueOf(str))
                                                .collect(Collectors.toSet()) :
                                            Set.of(AStarType.FORWARD);

        for(int itr = 0; itr < mazeCount; itr++){
            System.out.println("********************************** Starting Execution **********************************");

            MazeGenerator mazeGenerator = new MazeGenerator(rows, columns);

            //Start and End points assignment
            int[] start = new int[]{0,0};
            int[] end = new int[]{rows-1,columns-1};

            MatrixUtil.displayMatrix(mazeGenerator.maze,start,end);

            for(AStarType aStarType: aStarTypes){
                //Copy generated maze into matrix and create memory matrix
                String[][] matrix = mazeGenerator.maze;
                String[][] memMat = MatrixUtil.initializeMemoryMatrix(matrix);

                //Update current 4 directions from start position
                MatrixUtil.updateMemoryMatrix(matrix, memMat, start[0], start[1]);

                long startTime = System.nanoTime();

                switch (aStarType){
                    case FORWARD : {
                        A_Star.repeatedAStar(matrix, memMat, start, end);
                        break;
                    }
                    case BACKWARD : {
                        A_Star.repeatedAStarBackward(matrix, memMat, start, end);
                        break;
                    }
                    case ADAPTIVE : {
                        Adaptive_A_Star.repeatedAStar(matrix, memMat, start, end);
                        break;
                    }
                    default: {
                        System.out.println("Unknown A-Star Type");
                        System.exit(1);
                    }
                }
                System.out.println("AVG Runtime: "+ (System.nanoTime() - startTime) + " nanoseconds");
            }

        }

    }

}
