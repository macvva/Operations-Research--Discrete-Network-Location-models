/*******************************************************************************
 * File: Graph.java
 * Author: Andre Berger  
 *
 * Class to represent graphs using adjacency matrix and adjacency lists
 *
 * This program has been modified (changed and added some new functions/formulations)
 * by Maciej in order to
 * solve the Minimum Weight Edge Dominating Set Problem. A few methods have
 * been added and the file reader has been modified.
 ******************************************************************************/
import java.util.*;
import java.io.*;

public class Graph{

    private String instanceName;


    private int numberOfNodes;
    private int numberOfEdges;
    private int s;
    private int t;

    private int[][] capacityMatrix;

    // Constructor for a graph, information is read from file
    // adjacency matrix as well as adjacency list is filled

    public Graph(String filename)
            throws java.io.FileNotFoundException{
        File file = new File(filename);
        Scanner input = new Scanner (file);

        this.instanceName = input.next();

        this.numberOfNodes = input.nextInt();
        this.numberOfEdges = input.nextInt();
        this.s = (input.nextInt()-1);
        this.t = (input.nextInt()-1);

        this.capacityMatrix = new int[this.numberOfNodes][this.numberOfNodes];

        for (int i=0;i<this.numberOfNodes;i++){
            for(int j = 0; j<this.numberOfNodes; j++){
                this.capacityMatrix[i][j] = 0;
            }
        }

        for (int i=0;i<this.numberOfEdges;i++){
            int u = (input.nextInt()-1);
            int v = (input.nextInt()-1);
            int c = input.nextInt();

            this.capacityMatrix[u][v] = c;
            this.capacityMatrix[v][u] = c;
        }
        input.close();
    }

    // returns the name of the instance
    public String getInstanceName(){
        return this.instanceName;
    }

    // returns the number of nodes of this graph
    public int getNumberOfNodes(){
        return this.numberOfNodes;
    }

    // returns the number of edges of this graph
    public int getNumberOfEdges(){
        return this.numberOfEdges;
    }

    // returns whether node i and node j are adjacent in this graph
    public int getS(){
        return this.s;
    }

    // returns weight of vertex which is ith in the list
    public int getT(){
        return this.t;
    }

    // return edge number i of the EdgeList
    public int[][] getCapacityMatrix(){
        return this.capacityMatrix;
    }

    public void printCapacityMatrix(){
        System.out.println("Capacity Matrix");
        for(int i = 0; i<capacityMatrix.length; i++){
            for(int j = 0; j<capacityMatrix[0].length; j++){
                if(this.capacityMatrix[i][j]==0){
                    System.out.print("00 ");
                }
                else{
                    System.out.print(this.capacityMatrix[i][j]+ " ");
                }
            }
            System.out.println();
        }
    }
}