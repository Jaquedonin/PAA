package exer_1;

import java.util.*;
import java.io.*;

class Prim {
    //private static final int V= 10; // mudar o valor de V

    int minKey(int key[], Boolean mstSet[], int V) {
        int min = Integer.MAX_VALUE, min_index = -1;

        for (int v = 0; v < V; v++) {
            if (mstSet[v] == false && key[v] < min) {
                min = key[v];
                min_index = v;
            }
        }

        return min_index;
    }

    void printMST(int parent[], int n, int graph[][], int V) {
        System.out.println("Vertice \tPeso");
        for (int i = 1; i < V; i++) {
            System.out.println(parent[i] + " - " + i + "\t\t"+ graph[i][parent[i]]);
        }
    }

    void primMST(int graph[][], int V) {
        int parent[] = new int[V];
        int key[] = new int[V];
        Boolean mstSet[] = new Boolean[V];
        for (int i = 0; i < V; i++) {
            key[i] = Integer.MAX_VALUE;
            mstSet[i] = false;
        }

        key[0] = 0;
        parent[0] = -1;

        for (int count = 0; count < V - 1; count++) {
            int u = minKey(key, mstSet, V);

            mstSet[u] = true;

            for (int v = 0; v < V; v++) {
                if (graph[u][v] != 0 && mstSet[v] == false
                        && graph[u][v] < key[v]) {
                    parent[v] = u;
                    key[v] = graph[u][v];
                }
            }
        }
        printMST(parent, V, graph, V);
    }

    public static void main(String[] args) throws FileNotFoundException {
        Prim t = new Prim();
        for (int V = 5; V < 31; V = V + 5) {
            long start = System.currentTimeMillis();
            int graph[][] = new int[V][V];
            Scanner input = new Scanner(new File("Matriz" + V + ".txt"));
            for (int i = 0; i < V; i++) {
                for (int j = 0; j < V; j++) {
                    graph[i][j] = input.nextInt();
                }
            }

            // Print the solution 
            t.primMST(graph, V);
            long elapsed = System.currentTimeMillis() - start;
            System.out.println("Tempo de execussão para " + V + " vertices: " + elapsed + " ms\n");
        }
    }
}
