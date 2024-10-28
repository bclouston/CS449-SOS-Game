package models;

import java.util.LinkedList;
/*
    logistical representation of the SOS game board
    provides methods for move validation and finding scoring SOS sequences
*/
public class Board {
    private int size;
    private char[][] board;

    public Board(int size) {
        this.size = size;
        board = new char[size][size];
    }

    public boolean isMoveValid(int row, int col) {
        if (row == -1) {
            return false;
        }
        else if (board[row][col] == 'S' || board[row][col] == 'O')
            return false;
        else
            return true;
    }

    public boolean playMove(int row, int col, char move) {
        if (isMoveValid(row, col)) {
            board[row][col] = move;
            return true;
        }
        else return false;
    }

    // checks if a sequence is found from the starting position passed, returns all scoring sequences
    public LinkedList<int[][]> sequenceCheck(int row, int col) {
        LinkedList<int[][]> sequenceList = new LinkedList<>();
        // check horizontal sequence (left to right)
        if (col + 2 < size && board[row][col] == 'S' && board[row][col + 1] == 'O' && board[row][col + 2] == 'S') {
            int[][] sequence = new int[3][2];
            sequence[0] = new int[] {row, col};
            sequence[1] = new int[] {row, col + 1};
            sequence[2] = new int[] {row, col + 2};
            sequenceList.add(sequence);
        }
        // check vertical sequence (top to bottom)
        if (row + 2 < size && board[row][col] == 'S' && board[row + 1][col] == 'O' && board[row + 2][col] == 'S') {
            int[][] sequence = new int[3][2];
            sequence[0] = new int[] {row, col};
            sequence[1] = new int[] {row + 1, col};
            sequence[2] = new int[] {row + 2, col};
            sequenceList.add(sequence);
        }
        // check diagonal sequence (top-left to bottom-right)
        if (row + 2 < size && col + 2 < size && board[row][col] == 'S' && board[row + 1][col + 1] == 'O' && board[row + 2][col + 2] == 'S') {
            int[][] sequence = new int[3][2];
            sequence[0] = new int[] {row, col};
            sequence[1] = new int[] {row + 1, col + 1};
            sequence[2] = new int[] {row + 2, col + 2};
            sequenceList.add(sequence);
        }
        // check diagonal sequence (top-right to bottom-left)
        if (row + 2 < size && col - 2 >= 0 && board[row][col] == 'S' && board[row + 1][col - 1] == 'O' && board[row + 2][col - 2] == 'S') {
            int[][] sequence = new int[3][2];
            sequence[0] = new int[] {row, col};
            sequence[1] = new int[] {row + 1, col - 1};
            sequence[2] = new int[] {row + 2, col - 2};
            sequenceList.add(sequence);
        }
        return sequenceList;
    }

    // returns list of all scoring sequences on the board
    public LinkedList<int[][]> findSequences() {
        LinkedList<int[][]> sequenceList = new LinkedList<>();
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                LinkedList<int[][]> scoreList = sequenceCheck(row, col);
                if (!scoreList.isEmpty()) {
                    sequenceList.addAll(scoreList);
                }
            }
        }
        if (sequenceList.isEmpty()) {
            return null;
        }
        else return sequenceList;
    }

    public boolean isFull() {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (board[row][col] != 'S' && board[row][col] != 'O') {
                    return false;
                }
            }
        }
        return true;
    }

}