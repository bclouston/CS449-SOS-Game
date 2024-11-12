package models;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
/*
    manages game state of a Board and Players
*/
public class SOSGame {
    protected Board board;
    protected HashMap<int[][], Color> scoreMap;
    protected Player[] players;
    protected Player activePlayer;
    protected boolean gameOver;

    public SOSGame(int size) {
        players = new Player[] {new Player('R'), new Player('B')};
        activePlayer = players[0];
        board = new Board(size);
        scoreMap = new HashMap<>();
        gameOver = false;
    }

    public boolean checkMove(int row, int col, char player) {
        return false;
    }

    public String getScore(char player) {
        return "";
    }

    // returns true if move is successfully played
    public boolean makeMove(int row, int col, char move, char player) {
        if (gameOver) {
            JOptionPane.showMessageDialog(null, "Please start a new game");
            return false;
        }
        if (player != activePlayer.getColor()) {
            JOptionPane.showMessageDialog(null, "It is not your turn.");
            return false;
        }
        if (board.playMove(row, col, move)) {
            board.printBoard();
            return true;
        }
        else return false;
    }

    public char[] getLLMMove() {
        return activePlayer.getMoveLLM();
    }

    public char[] getComputerMove() {
        return activePlayer.getMoveAI(board);
    }

    // parses scoreMap to determine winner or draw
    public char getWinner() {
        int red = 0, blue = 0;
        for (int[][] s : scoreMap.keySet()) {
            if (scoreMap.get(s) == Color.RED) {
                red++;
            }
            else blue++;
        }
        if (red > blue) {
            return 'R';
        }
        if (red < blue) {
            return 'B';
        }
        else return 'D';
    }

    public void printBoard() {
        board.printBoard();
    }

    public HashMap<int[][], Color> getScoreMap() {
        return scoreMap;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean isBoardFull() {
        return board.isFull();
    }

    public char getActivePlayer() {
        return activePlayer.getColor();
    }

    // switches active player
    protected void switchPlayer() {
        for (Player p : players) {
            if (p != activePlayer) {
                activePlayer = p;
                break;
            }
        }
    }


}