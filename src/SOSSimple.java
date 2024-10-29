package models;

import java.awt.*;
import java.util.LinkedList;
/*
    manages game state and provides additional logic for simple games
*/
public class SOSSimple extends SOSGame {

    public SOSSimple(int size) {
        super(size);
    }

    @Override
    /*  checks if move was used to complete an SOS sequence
        adds scoring sequence to parent attribute scoreMap
        marks game as complete when board is full or first SOS scored
    */
    public boolean checkMove(int row, int col, char player) {
        LinkedList<int[][]> sequenceList = board.findSequences();
        boolean flag = false;
        if (isBoardFull()) {
            gameOver = true;
        }
        if (sequenceList != null) {
            for (int[][] sequence : sequenceList) {
                for (int[] cell : sequence) {
                    if (cell[0] == row && cell[1] == col) {
                        if (player == 'R') {
                            scoreMap.put(sequence, Color.RED);
                        }
                        if (player == 'B') {
                            scoreMap.put(sequence, Color.BLUE);
                        }
                        gameOver = true;
                        flag = true;
                    }
                }
            }
        }
        switchPlayer();
        return flag;
    }
}
