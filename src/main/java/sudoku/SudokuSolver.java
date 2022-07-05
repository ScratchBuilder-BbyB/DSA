package sudoku;

import java.util.HashSet;
import java.util.Set;

public class SudokuSolver {

  private static int n = 9;
  private static Set<Integer>[] setArrayRow = new HashSet[n];
  private static  Set<Integer>[] setArrayCol = new HashSet[n];
  private static  Set<Integer>[] setArraySubSquare = new HashSet[n];

  public static void main(String[] args) {
    int[][] board = new int[9][9];

    board[2][0] = 5;
    board[6][0] = 6;

    board[5][1] = 2;

    board[7][2] = 3;
    board[7][3] = 4;
    board[8][3] = 2;
    board[0][3] = 8;




    initSets(board, setArrayRow, setArrayCol, setArraySubSquare);
    if(solveSudoku(board)){
      System.out.println("Solved :: ");
      display(board);
    }else{
      System.out.println("Not solved");
    }
  }

  private static void display(int[][] board) {
    for(int [] row : board){
      for(int n : row){
        System.out.print(n+" ");
      }
      System.out.println();
    }
  }

  private static boolean solveSudoku(int[][] board) {
    for(int i = 0; i < n; i++){
      for(int j = 0; j < n; j++){
        if(board[i][j] == 0){
          int num = 1;
          while(num <= 9){
            if(!setArrayRow[i].contains(num) && !setArrayCol[j].contains(num) && !setArraySubSquare[getSquareIndex(i, j)].contains(num)){
              board[i][j] = num;
              setArrayRow[i].add(num);
              setArrayCol[j].add(num);
              setArraySubSquare[getSquareIndex(i, j)].add(num);
              if(solveSudoku(board)){
                System.out.println("Got it ::");
                display(board);
                return true;
              }else{
                board[i][j] = 0;
                setArrayRow[i].remove(num);
                setArrayCol[j].remove(num);
                setArraySubSquare[getSquareIndex(i, j)].remove(num);
              }
            }
            num++;
          }
        }
      }
    }

    return false;
  }

  private static void initSets(int[][] board, Set<Integer>[] setArrayRow,
      Set<Integer>[] setArrayCol, Set<Integer>[] setArraySubSquare) {

      for(int i = 0; i < board.length; i++){
        setArrayCol[i] = new HashSet<>();
        setArrayRow[i] = new HashSet<>();
        setArraySubSquare[i] = new HashSet<>();
      }

    for(int i = 0; i < board.length; i++){
      for(int j = 0; j < board.length; j++){
        if(board[i][j] != 0){
          int num = board[i][j];
          setArrayRow[i].add(num);
          setArrayCol[j].add(num);
          setArraySubSquare[getSquareIndex(i, j)].add(num);
        }
      }
    }

  }

  private static int getSquareIndex(int i, int j){
    int dI = i / 3;
    int dJ = j / 3;

    if(dJ == 0){
      if(dI == 0){
          return 0;
      }
      if(dI == 1){
        return 3;
      }
      if(dI == 2){
        return 6;
      }
    }

    if(dJ == 1){
      if(dI == 0){
        return 1;
      }
      if(dI == 1){
        return 4;
      }
      if(dI == 2){
        return 7;
      }
    }

    if(dJ == 2){
      if(dI == 0){
        return 2;
      }
      if(dI == 1){
        return 5;
      }
      if(dI == 2){
        return 8;
      }
    }

    return 0;
  }
}
