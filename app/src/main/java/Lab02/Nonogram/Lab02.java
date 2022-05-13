package Lab02.Nonogram;
//@Rohan Jay Arun
//March 10th, 2022 Version 1
//CS245 Lab02: Nonogram

import java.util.*;

public class Lab02{
  //nonogram method solves the puzzle with the given two parameters
  //@param double array of integers that represent the columns
  //@param double array of integers that represent the rows
  //@returns double boolean array of solution
  public static boolean[][] solveNonogram(int[][] columns, int[][] rows) {
    boolean[][] board = new boolean[rows.length][columns.length];
    return recursion(columns, rows, board, 0, 0);
  }


  /**
   * Recursive call to solve nonogram board
   * @param columns column boxes
   * @param rows row boxes
   * @param board the whole board
   * @param index_row the row index
   * @param index_col the column index
   * @returns  the board if solvable, and null if it is not
   */
  public static boolean[][] recursion(int[][] columns, int[][] rows, boolean[][] board, int index_row, int index_col) {

    int next_row = 0;
    //declaring row
    int next_col = 0;
    //declaring column
    if (index_row == rows.length) {

      return board;
    } else if (index_col == columns.length - 1) {
      next_row = index_row + 1;
      next_col = 0;
    } else {
      next_row = index_row;
      next_col = index_col + 1;
    }
      //recursive call to solve the board
    System.out.println("row = " + index_row + ", column = " + index_col + ", next_row = " + next_row + ", next_col = " + next_col);


    board[index_row][index_col] = true; // test
    if (isSafe(columns, rows, board, index_row, index_col) && (recursion(columns, rows, board, next_row, next_col) != null)) {
      return board;
    }


    board[index_row][index_col] = false;
    if (isSafe(columns, rows, board, index_row, index_col) && (recursion(columns, rows, board, next_row, next_col) != null)) {
      return board;
    }

    return null;
  }

  /**
   * Checks to see if placement is "safe"
   * @param columns column boxes
   * @param rows row boxes
   * @param board board to check
   * @param index_row the rows index
   * @param index_col the col index
   * @return checks the row restrictions and column restrictions and gives allowance on block if its either 1 or 0
   */
  public static boolean isSafe(int[][] columns, int[][] rows, boolean[][] board, int index_row, int index_col) {

    int[] row_restrict = new int[2];

    row_restrict[0] = rows[index_row][0];
    row_restrict[1] = rows[index_row][1];

    int[] col_restrict = new int[2];

    col_restrict[0] = columns[index_col][0];
    col_restrict[1] = columns[index_col][1];
    //checks to see if the placement is safe

    int restrict_index = 0;

    boolean previous_bool = false;


    if (row_restrict[restrict_index] == 0) {
      restrict_index++;
    }
    //counter for index col
    for (int i = 0; i <= index_col; i++) {
      boolean current_bool = board[index_row][i];

      if (!previous_bool && !current_bool) {
        continue;
      }
      else if (previous_bool && !current_bool) {
        if (row_restrict[restrict_index] != 0) {
          return false;
        }
        restrict_index++;
      }
      else {
        if (restrict_index > 1) {
          return false;
        }

        row_restrict[restrict_index] -= 1;

        if (row_restrict[restrict_index] < 0) {
          return false;
        }
      }
    //sets the previous to the current value
      previous_bool = current_bool;
    }

    if (index_col == columns.length - 1 && (row_restrict[0] != 0 || row_restrict[1] != 0)) {
    }

    restrict_index = 0;
    previous_bool = false;

    if (col_restrict[restrict_index] == 0) {
      restrict_index++;
    }//if the first element of restrict is 0 it allows only 1 block

    for (int i = 0; i <= index_row; i++) {
      boolean current_bool = board[i][index_col];

      if (!previous_bool && !current_bool) { //if prev bool is same as current, and current is false
        continue;
      } else if (previous_bool && !current_bool) { //if prev bool is same as current, and current is true
        if (col_restrict[restrict_index] != 0) {
          return false;
        } //false because went next without clearing previous block requirement
        restrict_index++;
      } else {
        if (restrict_index > 1) {
          return false;
        }

        col_restrict[restrict_index] -= 1;
        //if the true block is too big
        if (col_restrict[restrict_index] < 0) {
          return false;
        }
      } //sets the previous boolean to the current one
      previous_bool = current_bool;
    } //if the end of the column is reached but the column restriction isn't fully used
    if (index_row == rows.length - 1 && (col_restrict[0] != 0 || col_restrict[1] != 0)) {
      return false;
    }
    return true;
  }


//main with test numbers
  public static void main(String[] args){
    int[][] columns = {{1,1}, {1,3}, {1,3}, {2,1}, {2,1}, {2,2}, {1,3}, {2,1}};
    int[][] rows = {{1,1}, {1,3}, {2,3}, {4,1}, {2,2}, {1,6}};

    System.out.println(Arrays.deepToString(solveNonogram(columns, rows)));
  }
}
