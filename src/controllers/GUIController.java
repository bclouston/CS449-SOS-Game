package controllers;

import views.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/*
    Links GUI components to the GameController
*/
public class GUIController {
    private final GUI gui;
    private GameController gc;
    private int[] selectedCell;

    public GUIController(GUI gv) {
        selectedCell = new int[] {-1, -1};
        gc = new GameController();
        gui = gv;
        gui.setVisible(true);
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.pack();
        gui.setLocationRelativeTo(null);

        gui.setNewGameButtonListener(new NewGameButtonListener());
        gui.setSRedButtonListener(new RedSButtonListener());
        gui.setORedButtonListener(new RedOButtonListener());
        gui.setSBlueButtonListener(new BlueSButtonListener());
        gui.setOBlueButtonListener(new BlueOButtonListener());
    }

    // starts new game and adjusts GUI components
    public void newGame(int size, char mode) {
        gc.startGame(size, mode);
        gui.createGameBoard(size, new gameBoardListener());
        gui.setInfoLabel("Red Player's turn...", Color.RED);
        gui.setScore('R', "");
        gui.setScore('B', "");
        selectedCell = new int[] {-1, -1};
    }

    // paints move to GUI if valid and update infoLabel text
    public void playMove(char player, String move, int[] cell) {
        String pColor = "";
        Color c = null;
        if (player == 'R') {
            pColor = "Blue";
            c = Color.BLUE;
        }
        if (player == 'B') {
            pColor = "Red";
            c = Color.RED;
        }

        gui.setGameCell(cell, move);
        if (gc.handleMove(player, move, cell)) {
            gui.setInfoLabel(pColor + " Player's turn...", c);
            if (gc.scoringMoveCheck(cell, player)) {
                gui.paintScoreLines(gc.getScoreMap());
                gui.setScore(player, gc.getScore(player));
            }
            if (gc.isGameOver()) {
                char outcome = gc.getWinner();
                if (outcome == 'D') {
                    gui.setInfoLabel("Draw! Start a new game...", Color.BLACK);
                }
                else if (outcome == 'R') {
                    gui.setInfoLabel("Red wins! Start a new game...", Color.RED);
                }
                else if (outcome == 'B') {
                    gui.setInfoLabel("Blue wins! Start a new game...", Color.BLUE);
                }
            }

        }
        else {
            gui.setGameCell(cell, "");
        }
        selectedCell = new int[] {-1, -1};
    }

    // returns true if game cell contains a played move
    public boolean isOccupied(int[] cell) {
        if (gui.getGameCell(cell).isEmpty() || gui.getGameCell(cell).equals("*")) {
            return false;
        }
        return true;
    }

    // button event handlers
    private class NewGameButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (gui.getSimpleGameRadio())
                newGame(gui.getBoardSize(), 'S');
            else if (gui.getGeneralGameRadio())
                newGame(gui.getBoardSize(), 'G');
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

    private class gameBoardListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            // clear * from previously selected unoccupied game cell
            if (selectedCell[0] != -1 && !isOccupied(selectedCell)) {
                gui.setGameCell(selectedCell, "");
            }
            int[] cell = gui.getSelectedIndex(e.getSource());
            if (!isOccupied(cell)) {
                selectedCell = cell;
                gui.setGameCell(selectedCell, "*");
            }
            else {
                JOptionPane.showMessageDialog(null, "Cell already occupied.");
                selectedCell = new int[] {-1, -1};
            }
        }
        @Override
        public void mousePressed(MouseEvent e) {
        }
        @Override
        public void mouseReleased(MouseEvent e) {
        }
        @Override
        public void mouseEntered(MouseEvent e) {
        }
        @Override
        public void mouseExited(MouseEvent e) {
        }
    }
}