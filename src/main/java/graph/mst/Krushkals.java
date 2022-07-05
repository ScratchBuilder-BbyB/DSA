package graph.mst;

import java.util.Arrays;
import java.util.Comparator;

public class Krushkals {
  private static final int[] parent = new int[1000];
  private static final int[] rank = new int[1000];

  public static void main(String[] args) {
    int[][] graph = new int[][]{
        {5, 4, 9},
        {5, 1, 4},
        {1, 4, 1},
        {4, 3, 5},
        {4, 2, 3},
        {1, 2, 2},
        {2, 3, 3},
        {3, 6, 8},
        {6, 2, 7}

    };

    // create DSU
    initDSU();

    // sort based on weights
    Arrays.sort(graph, Comparator.comparingInt(a -> a[2]));
    int minCost = 0;
    for(int[] edge : graph){
      if(!belongsToSameComponent(edge[0], edge[1])){
        minCost += edge[2];
        union(edge[0], edge[1]);
      }
    }

    System.out.println("MST cost :: "+ minCost);
  }

  private static void union(int node1, int node2) {
    int parent1 = getParent(node1);
    int parent2 = getParent(node2);

    if(rank[parent1] < rank[parent2]){
      parent[parent1] = parent2;
    }else if(rank[parent1] > rank[parent2]){
      parent[parent2] = parent1;
    }else{
      parent[parent2] = parent1;
      rank[parent1]++;
    }

  }

  /**
   * TC : O(4 alpha)
   * */
  private static boolean belongsToSameComponent(int node1, int node2) {
    return getParent(node1) == getParent(node2);
  }

  private static int getParent(int node) {
    while(parent[node] != node){
      node = parent[node];
    }
    return node;
  }

  private static void initDSU(){
    for(int i = 0; i < parent.length; i++){
      parent[i] = i;
      rank[i] = 0;
    }
  }
}
