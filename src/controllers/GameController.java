package controllers;

import models.*;

import java.awt.*;
import java.util.HashMap;
/*
    coordinates game logic between models and player interactions
*/
public class GameController {
    private SOSGame game;

    public GameController() {
    }

    public void startGame(int size, char mode) {
        if (mode == 'S') {
            game = new SOSSimple(size);
        }
        else if (mode == 'G') {
            game = new SOSGeneral(size);
        }
    }

    public void outputBoard() {
        game.printBoard();
    }

    public boolean handleMove(char player, String move, int[] cell) {
        return game.makeMove(cell[0], cell[1], move.charAt(0), player);
    }

    public boolean isGameOver() {
        return game.isGameOver();
    }

    public boolean scoringMoveCheck(int[] cell, char player) {
        return game.checkMove(cell[0], cell[1], player);
    }

    public HashMap<int[][],Color> getScoreMap() {
        return game.getScoreMap();
    }

    public String getScore(char player) {
        return game.getScore(player);
    }

    public char getWinner() {
        return game.getWinner();
    }

    public char[] getLLMMove() {
        return game.getLLMMove();
    }

    public char[] getComputerMove() {
        return game.getComputerMove();
    }

}
