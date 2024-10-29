package models;

import java.awt.*;
import java.util.LinkedList;
/*
    manages game state and provides additional logic for general games
*/
public class SOSGeneral extends SOSGame {
    public int redScore, blueScore;

    public SOSGeneral(int size) {
        super(size);
    }

    @Override
    /*  checks if move was used to complete an SOS sequence
        adds scoring sequence to parent attribute scoreMap
        adjusts player score and marks game as complete when board is full
    */
    public boolean checkMove(int row, int col, char player) {
        LinkedList<int[][]> sequenceList = board.findSequences();
        boolean flag = false;   // played move completes SOS sequence
        if (isBoardFull()) {
            gameOver = true;
        }
        if (sequenceList != null) {
            for (int[][] sequence : sequenceList) {
                for (int[] cell : sequence) {
                    if (cell[0] == row && cell[1] == col) {
                        if (player == 'R') {
                            scoreMap.put(sequence, Color.RED);
                            redScore++;
                        }
                        if (player == 'B') {
                            scoreMap.put(sequence, Color.BLUE);
                            blueScore++;
                        }
                        flag = true;
                    }
                }
            }
        }
        if (!flag) {
            switchPlayer();
        }
        return flag;
    }

    @Override
    /*  returns players score as string to be inserted into GUI
    */
    public String getScore(char player) {
        if (player == 'R') {
            return "Score: " + redScore;
        }
        else return "Score: " + blueScore;
    }

}
