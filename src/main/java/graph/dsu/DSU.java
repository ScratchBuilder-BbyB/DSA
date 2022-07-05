package graph.dsu;

public class DSU {

  private static final int[] parent = new int[100];
  private static final int[] rank = new int[100];

  public static void main(String[] args) {
      init();
      System.out.println(belongToSameComponent(1, 2));
      union(1, 2);
      union(3, 4);
      union(2, 3);
      union(5, 6);

    System.out.println(belongToSameComponent(5, 2));
    union(5, 2);
    System.out.println(belongToSameComponent(5, 3));

  }

  private static void init(){
    for(int i = 0; i < parent.length; i++){
       parent[i] = i;
       rank[i] = 0;
    }
  }

  private static int getParent(int node){
    while(parent[node] != node){
        node = parent[node];
    }
    return node;
  }

  private static void union(int node1, int node2){
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

  private static boolean belongToSameComponent(int node1, int node2){
    return getParent(node1) == getParent(node2);
  }
}
