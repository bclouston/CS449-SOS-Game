package controllers;

import views.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameController {

    private final GameView gameView;
    private int selectedCell, redScore, blueScore;
    private boolean redTurn, blueTurn;
    private char gameMode;
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

    // returns true if game cell is occupied by a previous move
    public boolean isOccupied(int cell) {
        return gameboard[cell] == 'S' || gameboard[cell] == 'O';
    }

    // generates new game of selected board size and game mode
    public void newGame(int size, char mode) {
        gameView.createGameBoard(size, new gameBoardButtonListener());
        gameboard = new char[size * size];
        gameMode = mode;
        redTurn = true;
        blueTurn = false;
        gameView.setInfoLabel("Red Player's turn...", Color.RED);
        selectedCell = -1;
    }

    // plays a move if valid cell is selected and it is the players turn
    public void playMove(char player, String move, int cell) {
        if (cell == -1) {
            JOptionPane.showMessageDialog(null, "Select a tile to play a move");
        }
        else if (player == 'R' && redTurn) {
            gameView.setGameCell(cell, move);
            gameboard[cell] = move.charAt(0);
            gameView.setInfoLabel("Blue Player's turn...", Color.BLUE);
            redTurn = false;
            blueTurn = true;
            selectedCell = -1;
        }
        else if (player == 'B' && blueTurn) {
            gameView.setGameCell(cell, move);
            gameboard[cell] = move.charAt(0);
            gameView.setInfoLabel("Red Player's turn...", Color.RED);
            redTurn = true;
            blueTurn = false;
            selectedCell = -1;
        }
        else {
            JOptionPane.showMessageDialog(null, "It is not your turn.");
        }
    }

    // returns which player is currently active and allowed to move
    public char getCurrentPlayer() {
        if (redTurn)
            return 'R';
        else if (blueTurn)
            return 'B';
        else
            return '0';
    }

    // returns game mode
    public char getGameMode() {
        return gameMode;
    }

    // button event handlers
    private class NewGameButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (gameView.simpleGameRadio())
                newGame(gameView.getBoardSize(), 'S');
            else if (gameView.generalGameRadio())
                newGame(gameView.getBoardSize(), 'G');
        }
    }

    private class RedSButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            playMove('R', "S", selectedCell);
        }
    }

    private class RedOButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            playMove('R', "O", selectedCell);
        }
    }

    private class BlueSButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            playMove('B', "S", selectedCell);
        }
    }

    private class BlueOButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            playMove('B', "O", selectedCell);
        }
    }

    private class gameBoardButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // clear * from previously selected unoccupied game cell
            if (selectedCell != -1 && !isOccupied(selectedCell)) {
                gameView.setGameCell(selectedCell, "");
            }
            int index = gameView.getSelectedIndex(e.getSource());
            if (!isOccupied(index)) {
                selectedCell = index;
                gameView.setGameCell(selectedCell, "*");
            }
            else {
                JOptionPane.showMessageDialog(null, "Cell already occupied.");
                selectedCell = -1;
            }
        }
    }

}