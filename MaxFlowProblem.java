/*******************************************************************************
 * Maciej Nowaczyk
 * Date: 14-04-2020
 * This code is my own work, it was developed without using or copying code
 * from other students or other resources.
 ******************************************************************************/

import ilog.concert.*;
import ilog.cplex.*;
import java.util.*;
import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.File;


public class MaxFlowProblem{

    private Graph graph;

    public MaxFlowProblem(String filename) throws java.io.FileNotFoundException{

        this.graph = new Graph(filename);
        // this.graph.printCapacityMatrix();

    }

    // method that solves the LP
    public void solveMaxFlowProblem(){
        try {
            IloCplex cplex = new IloCplex();

            // define the parameters
            int numOfNodes = graph.getNumberOfNodes();
            int numOfEdges = graph.getNumberOfEdges();

            int s = graph.getS();
            int t = graph.getT();

            int[][] capacityMatrix = graph.getCapacityMatrix();
            int maxCapacity = max(capacityMatrix);


            // define a matrix of flow variables
            IloNumVar[][] flowVar = new IloNumVar[numOfNodes][];
            for (int i = 0; i < numOfNodes; i++) {
                flowVar[i] = cplex.numVarArray(numOfNodes, 0, maxCapacity,  IloNumVarType.Int);
            }

            // Define the objective value as a variable for the LP
            IloNumVar v = cplex.numVar(0, Integer.MAX_VALUE ,  IloNumVarType.Int);

            // add objective function (which is equal to variable v)
            cplex.addMaximize(v);

            // set up the flow constraints
            for(int i = 0; i<numOfNodes; i++){
                IloLinearNumExpr expr1 = cplex.linearNumExpr();
                for(int j = 0; j<numOfNodes; j++){
                    expr1.addTerm(flowVar[i][j],1);
                    expr1.addTerm(flowVar[j][i],-1);
                }
                if(i == s){
                    expr1.addTerm(v,-1);
                }
                if(i == t){
                    expr1.addTerm(v,1);
                }
                cplex.addLe(expr1,0);
            }

            // set up the capacity constraints
            for(int i = 0; i<numOfNodes; i++){
                for(int j = 0; j<numOfNodes; j++){
                    cplex.addLe(flowVar[i][j],capacityMatrix[j][i]);
                }
            }

            // Solve the LP
            cplex.solve();

            // output solution value
            System.out.println("\r\n" + "\r\n"  + this.graph.getInstanceName());
            System.out.println("Optimal value  = " + cplex.getObjValue());
      /*
      // output optimal solution
      System.out.println("Edges in optimal solution:");
      for (int i = 0; i < numOfNodes; i++) {
        for (int j = i; j < numOfNodes; j++) {
          if(cplex.getValue(flowVar[i][j])>0){         
            System.out.println("edge:" + (i+1) + " " +(j+1) +"    "+ cplex.getValue(flowVar[i][j]));
          }
        }
      }
      */
        }
        catch (IloException exc) {
            exc.printStackTrace();
        }
    }

    public static int max(int[][] capacityMatrix){
        int max = 0;
        for(int i = 0; i<capacityMatrix.length; i++){
            for(int j = i+1; j<capacityMatrix.length; j++){
                if(max<capacityMatrix[i][j]){
                    max = capacityMatrix[i][j];
                }
            }
        }
        return max;
    }
    public static void main(String[] args) throws java.io.FileNotFoundException {

        MaxFlowProblem instance = new MaxFlowProblem("instance0.txt"); //in case you want to check another instance, just change the number in the name of the file
        double startTime = System.currentTimeMillis();
        instance.solveMaxFlowProblem();
        double endTime = System.currentTimeMillis();
        double runningTime = (endTime - startTime);
        System.out.println("Running Time : " + runningTime);
    }
}