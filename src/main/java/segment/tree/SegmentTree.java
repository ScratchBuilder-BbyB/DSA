package segment.tree;

import java.util.Scanner;

public class SegmentTree {

  /**
   * Building Segment Tree:
   * Time : O(N)
   * Space : O(N)
   *
   * Query:
   * Time : O(log(N))
   */


  private static final Scanner SCANNER = new Scanner(System.in);

  public static void main(String[] args) {
    int[] arr = new int[]{10, 5, 10, 7, 9, 0, 1};
//    int[] arr = new int[]{10, 7, 12, 9, 11, 0, 1};
    int n = arr.length;
    int[] segmentTree = new int[4 * n];
    int[] segmentTreeLazy = new int[4 * n];
    int low = 0, high = n - 1, treeIndex = 0;
    buildSegmentTree(treeIndex, low, high, segmentTree, arr);

//    pointUpdatedSegmentTree(treeIndex, low, high, segmentTree, 2, 10);
      rangeUpdateSegmentTree(treeIndex, low, high, segmentTree,segmentTreeLazy, 1, 4, 2);

    while (true){
//      acceptEagerQueries(treeIndex, low, high, segmentTree);
      acceptLazyQueries(treeIndex, low, high, segmentTree, segmentTreeLazy);
    }


  }

  private static void acceptEagerQueries(int treeIndex, int low, int high, int[] segmentTree) {
    String line = SCANNER.nextLine();
    String[] args = line.split(" ");
    int left = Integer.parseInt(args[0]), right = Integer.parseInt(args[1]);
    int maxElement = performEagerQuery(treeIndex, low, high, segmentTree, left, right);
    System.out.println("Sum :: "+ maxElement);
  }

  private static void acceptLazyQueries(int treeIndex, int low, int high, int[] segmentTree, int[] segmentTreeLazy) {
    String line = SCANNER.nextLine();
    String[] args = line.split(" ");
    int left = Integer.parseInt(args[0]), right = Integer.parseInt(args[1]);
    int maxElement = performLazyQuery(treeIndex, low, high, segmentTree, segmentTreeLazy, left, right);
    System.out.println("Sum :: "+ maxElement);
  }

  private static int performLazyQuery(int treeIndex, int low, int high, int[] segmentTree, int[] segmentTreeLazy, int left, int right){
    // check for lazy updates
    if(segmentTreeLazy[treeIndex] != 0){
      // update the current node in segment tree
      segmentTree[treeIndex] += (high - low + 1) * segmentTreeLazy[treeIndex];
      // propagate lazy update to below nodes if not leaf node
      if(low != high){
        segmentTreeLazy[2 * treeIndex + 1] += segmentTreeLazy[treeIndex];
        segmentTreeLazy[2 * treeIndex + 2] += segmentTreeLazy[treeIndex];
      }

      // clear current lazy update
      segmentTreeLazy[treeIndex] = 0;
    }

    // if no overlap return 0
    if(left > high || right < low){
      return 0;
    }

    // full overlap
    if(low >= left && high <= right){
      return segmentTree[treeIndex];
    }


    // partial overlap
    int mid = (low + high) >> 1;
    int leftSum = performLazyQuery(2 * treeIndex + 1, low, mid, segmentTree, segmentTreeLazy, left, right);
    int rightSum = performLazyQuery(2 * treeIndex + 2, mid + 1, high, segmentTree, segmentTreeLazy, left, right);

    return leftSum + rightSum;

  }

  private static int performEagerQuery(int treeIndex, int low, int high, int[] segmentTree, int left, int right) {
    // full overlap
    if(low >= left && high <= right){
      return segmentTree[treeIndex];
    }

    // No overlap
    if(right < low || left > high){
//      return Integer.MIN_VALUE;
      return 0;
    }

    // partial overlap
    int mid = (low + high) / 2;
    int leftResult = performEagerQuery(2 * treeIndex + 1, low, mid, segmentTree, left, right);
    int rightResult = performEagerQuery(2 * treeIndex + 2, mid + 1, high, segmentTree, left, right);
//    return Math.max(leftResult, rightResult);
    return leftResult + rightResult;
  }

  private static void buildSegmentTree(int treeIndex, int low, int high, int[] segmentTree, int[] arr) {
    if(low == high){
      segmentTree[treeIndex] = arr[high];
      return;
    }
    int mid = (low + high) / 2;
    buildSegmentTree(2 * treeIndex + 1, low, mid, segmentTree, arr);
    buildSegmentTree(2 * treeIndex + 2, mid + 1, high, segmentTree, arr);

//    segmentTree[treeIndex] = Math.max(segmentTree[2 * treeIndex + 1], segmentTree[2 * treeIndex + 2]);
    int leftSum = segmentTree[2 * treeIndex + 1];
    int rightSum = segmentTree[2 * treeIndex + 2];
    segmentTree[treeIndex] = leftSum + rightSum;
  }


  /**
   * Time complexity : O(log n)
   *
   *
   * @param treeIndex
   * @param low
   * @param high
   * @param segmentTree
   * @param updatedIndex
   * @param value
   */
  private static void pointUpdatedSegmentTree(int treeIndex, int low, int high, int[] segmentTree, int updatedIndex, int value){
    if(high == low){
      segmentTree[high] = value;
      return;
    }
    int mid = (high + low) >> 1;
    if(updatedIndex <= mid && updatedIndex >= low){
      pointUpdatedSegmentTree(2 * treeIndex + 1, low, mid, segmentTree, updatedIndex, value);
    }else{
      pointUpdatedSegmentTree(2 * treeIndex + 2, mid + 1, high, segmentTree, updatedIndex, value);
    }

    int leftSum = segmentTree[2 * treeIndex + 1];
    int rightSum = segmentTree[2 * treeIndex + 2];
    segmentTree[treeIndex] = leftSum + rightSum;
  }


  private static void rangeUpdateSegmentTree(int treeIndex, int low, int high, int[] segmentTree, int[] segmentTreeLazy, int updateLeft, int updateRight, int value){

    // check if any lazy update is pending
    if(segmentTreeLazy[treeIndex] != 0){
      // update current value = no. of nodes below * lazyUpdateVal
      segmentTree[treeIndex] += (high - low + 1) * segmentTreeLazy[treeIndex];

      // if not leaf index
      if(high != low){
        // propagate lazy update value to below nodes
        segmentTreeLazy[2 * treeIndex + 1] += segmentTreeLazy[treeIndex];
        segmentTreeLazy[2 * treeIndex + 2] += segmentTreeLazy[treeIndex];
      }

      // clear the lazy update
      segmentTreeLazy[treeIndex] = 0;
    }


    // no overlap
    if(updateRight < low || updateLeft > high){
      return;
    }

    // complete overlap
    if(low >= updateLeft && high <= updateRight){
      // update current value = no. of nodes below * value
      segmentTree[treeIndex] += (high - low + 1) * value;
      // propagate update to below nodes
      if(low != high){
        segmentTreeLazy[2 * treeIndex + 1] += value;
        segmentTreeLazy[2 * treeIndex + 2] += value;
      }
      return;
    }

    // partial overlap
    int mid = (low + high) >> 1;
    rangeUpdateSegmentTree(2 * treeIndex + 1, low, mid, segmentTree, segmentTreeLazy, updateLeft, updateRight, value);
    rangeUpdateSegmentTree(2 * treeIndex + 2, mid + 1, high, segmentTree, segmentTreeLazy, updateLeft, updateRight, value);

    segmentTree[treeIndex] = segmentTree[2 * treeIndex + 1] + segmentTree[2 * treeIndex + 2];
  }

}
