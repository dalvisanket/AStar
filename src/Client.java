import com.ai.astar.algorithm.A_Star;
import com.ai.astar.algorithm.Adaptive_A_Star;
import com.ai.astar.domain.AStarType;
import com.ai.astar.domain.BreakTie;
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

        Set<BreakTie> breakTies = System.getProperty("breakTies") != null?
                Arrays.stream(System.getProperty("breakTies").split(","))
                        .map( str -> BreakTie.valueOf(str))
                        .collect(Collectors.toSet()) :
                Set.of(BreakTie.GREATER_GN);


        //Intialize average runtime
        double avgTimeFwdGreaterGn = 0;
        double avgTimeFwdLesserGn = 0;
        double avgTimeBwd = 0;
        double avgTimeAdaptive = 0;


        for(int itr = 0; itr < mazeCount; itr++){
            System.out.println("********************************** Starting Execution " + itr + " **********************************");

            //Generate a maze
            MazeGenerator mazeGenerator = new MazeGenerator(rows, columns);

            //Start and End points assignment
            int[] start = new int[]{0,0};
            int[] end = new int[]{rows-1,columns-1};

            //Display Initial matrix
            MatrixUtil.displayMatrix(mazeGenerator.maze,start,end);

            for(AStarType aStarType: aStarTypes){
                //Copy generated maze into matrix and create memory matrix
                String[][] matrix = mazeGenerator.maze;

                switch (aStarType){
                    case FORWARD : {
                        for(BreakTie breakTie : breakTies) {
                            String[][] memMat = MatrixUtil.initializeMemoryMatrix(matrix);

                            //Update current 4 directions from start position
                            MatrixUtil.updateMemoryMatrix(matrix, memMat, start[0], start[1]);
                            System.out.println("********************* A* Using Breaking Ties Between Fn in favour of " + breakTie.toString() + " values *********************");

                            long startTime = System.nanoTime();
                            A_Star.repeatedAStar(matrix, memMat, start, end,breakTie);
                            long timeDiff = (System.nanoTime() - startTime);
                            if(breakTie.equals(BreakTie.GREATER_GN)) {
                                avgTimeFwdGreaterGn += timeDiff;
                            }
                            else{
                                avgTimeFwdLesserGn +=timeDiff;
                            }
                            System.out.println("AVG Runtime: " + timeDiff + " nanoseconds");
                        }
                        break;
                    }
                    case BACKWARD : {
                        String[][] memMat = MatrixUtil.initializeMemoryMatrix(matrix);
                        //Update current 4 directions from start position
                        MatrixUtil.updateMemoryMatrix(matrix, memMat, start[0], start[1]);
                        System.out.println("********************* Backward A*  *********************");
                        long startTime = System.nanoTime();
                        A_Star.backwardRepeatedAStar(matrix, memMat, start, end,BreakTie.GREATER_GN);
                        long timeDiff =(System.nanoTime() - startTime);
                        avgTimeBwd += timeDiff;
                        System.out.println("AVG Runtime: "+ timeDiff + " nanoseconds");
                        break;
                    }
                    case ADAPTIVE : {
                        String[][] memMat = MatrixUtil.initializeMemoryMatrix(matrix);
                        //Update current 4 directions from start position
                        MatrixUtil.updateMemoryMatrix(matrix, memMat, start[0], start[1]);
                        System.out.println("********************* Adaptive A*  *********************");

                        long startTime = System.nanoTime();
                        Adaptive_A_Star.repeatedAStar(matrix, memMat, start, end);
                        long timeDiff =(System.nanoTime() - startTime);
                        avgTimeAdaptive += timeDiff;
                        System.out.println("AVG Runtime: "+ timeDiff + " nanoseconds");
                        break;
                    }
                    default: {
                        System.out.println("Unknown A-Star Type");
                        System.exit(1);
                    }
                }

            }

        }

        //Print Avg runtime and nodes expanded values
        if(aStarTypes.contains(AStarType.FORWARD)){
            if(breakTies.contains(BreakTie.GREATER_GN)){
                System.out.println("\nForward A* - Greater GN - AVG Expanded Nodes for " + mazeCount + " mazes : " + A_Star.totalFwdGreaterGnAstarClosedNodes/mazeCount);
                System.out.println("Forward A* - Greater GN - AVG Runtime for " + mazeCount + " mazes : " + avgTimeFwdGreaterGn/mazeCount);
            }
            if(breakTies.contains(BreakTie.LESSER_GN)){
                System.out.println("\nForward A* - Lesser GN - AVG Expanded Nodes for " + mazeCount + " mazes : " + A_Star.totalFwdLesserGnAstarClosedNodes/mazeCount);
                System.out.println("Forward A* - Lesser GN - AVG Runtime for " + mazeCount + " mazes : " + avgTimeFwdLesserGn/mazeCount);
            }
        }
        if(aStarTypes.contains(AStarType.BACKWARD)){
            System.out.println("\nBackward A* - AVG Expanded Nodes for " + mazeCount + " mazes : " + A_Star.totalBwdAstarClosedNodes/mazeCount);
            System.out.println("Backward A* - AVG Runtime for " + mazeCount + " mazes : " + avgTimeBwd/mazeCount);
        }
        if(aStarTypes.contains(AStarType.ADAPTIVE)){
            System.out.println("\nAdaptive A* - AVG Expanded Nodes for " + mazeCount + " mazes : " + Adaptive_A_Star.totalAdaptiveAstarClosedNodes/mazeCount);
            System.out.println("Adaptive A* - AVG Runtime for " + mazeCount + " mazes : " + avgTimeAdaptive/mazeCount);
        }


    }

}
