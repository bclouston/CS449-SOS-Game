package controllers;

import views.*;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.util.concurrent.TimeUnit;

public class GameController {

    private final GameView gameView;
    private int size, selectedCell, redScore, blueScore;
    private boolean redTurn, blueTurn, simpleGame, generalGame;
    private char[] gameboard;

    public GameController (GameView gv) {
        gameView = gv;
        gameView.setVisible(true);

        gameView.setNewGameButtonListener(new NewGameButtonListener());
        gameView.setSRedButtonListener(new RedSButtonListener());
        gameView.setORedButtonListener(new RedOButtonListener());
        gameView.setSBlueButtonListener(new BlueSButtonListener());
        gameView.setOBlueButtonListener(new BlueOButtonListener());
    }

    public void playGame() {
        redTurn = true;
        blueTurn = false;
        gameView.updateInfoLabel("Red Player's turn...");
    }

    /*
    //check all adjacent cells for SOS sequence
    public boolean sequenceCheck() {
        if (gameboard[selectedCell] == 'S') {
            //adjust for out of bounds exception
            if (gameboard[selectedCell - size] == 'O' && gameboard[selectedCell - (2 * size)] == 'S') {
                return true;
            }
        }
        else if (gameboard[selectedCell] == 'O') {

        }
    }*/

    public boolean occupiedCheck() {
        return gameboard[selectedCell] == 'S' || gameboard[selectedCell] == 'O';
    }

    private class NewGameButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            size = gameView.getBoardSize();
            gameView.createGameBoard(size, new gameBoardButtonListener());
            gameboard = new char[size * size];
            simpleGame = gameView.simpleGameRadio();
            generalGame = gameView.generalGameRadio();
        }
    }

    private class RedSButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (redTurn) {
                if (!occupiedCheck()) {
                    gameView.updateGameCell(selectedCell, "S");
                    gameboard[selectedCell] = 'S';
                    redTurn = false;
                }
            }
            else {
                JOptionPane.showMessageDialog(null, "It is not your turn.");
            }
        }
    }

    private class RedOButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (redTurn) {
                if (!occupiedCheck()) {
                    gameView.updateGameCell(selectedCell, "O");
                    gameboard[selectedCell] = 'O';
                    redTurn = false;
                }
            }
            else {
                JOptionPane.showMessageDialog(null, "It is not your turn.");
            }
        }
    }

    private class BlueSButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (blueTurn) {
                if (!occupiedCheck()) {
                    gameView.updateGameCell(selectedCell, "S");
                    gameboard[selectedCell] = 'S';
                    blueTurn = false;
                }
            }
            else {
                JOptionPane.showMessageDialog(null, "It is not your turn.");
            }
        }
    }

    private class BlueOButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (blueTurn) {
                if (!occupiedCheck()) {
                    gameView.updateGameCell(selectedCell, "O");
                    gameboard[selectedCell] = 'O';
                    blueTurn = false;
                }
            }
            else {
                JOptionPane.showMessageDialog(null, "It is not your turn.");
            }
        }
    }

    private class gameBoardButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            selectedCell = gameView.getSelectedIndex(e.getSource());
        }
    }

}
