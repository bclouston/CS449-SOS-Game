package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GameView extends JFrame {

    private JPanel gamePanel, gameBoardPanel;
    private JRadioButton humanRedRadioButton, aiRedRadioButton, humanBlueRadioButton, aiBlueRadioButton, simpleRadioButton, generalRadioButton;
    private JButton oRedButton, sRedButton, oBlueButton, sBlueButton, rulesButton, newGameButton;
    private JComboBox sizeComboBox;
    private JLabel infoLabel;
    private JButton[] gameboardButtons;

    public GameView() {
        setTitle("Play SOS Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setContentPane(gamePanel);
        setSize(900, 600);
        buildComboBox();
    }

    public void buildComboBox() {
        for (int i = 3; i <= 9; i++) {
            sizeComboBox.addItem(i);
        }
    }

    // SETTERS
    public void setORedButtonListener(ActionListener listener) {
        oRedButton.addActionListener(listener);
    }

    public void setSRedButtonListener(ActionListener listener) {
        sRedButton.addActionListener(listener);
    }

    public void setOBlueButtonListener(ActionListener listener) {
        oBlueButton.addActionListener(listener);
    }

    public void setSBlueButtonListener(ActionListener listener) {
        sBlueButton.addActionListener(listener);
    }

    public void setNewGameButtonListener(ActionListener listener) {
        newGameButton.addActionListener(listener);
    }

    public void setRulesButtonListener(ActionListener listener) {
        rulesButton.addActionListener(listener);
    }

    public void updateInfoLabel(String s) {
        infoLabel.setText(s);
    }

    public void updateGameCell(int i, String s) {
        gameboardButtons[i].setText(s);
    }

    // GETTERS
    public int getBoardSize() {
        return sizeComboBox.getSelectedIndex() + 3;
    }
    public boolean generalGameRadio() {
        return generalRadioButton.isEnabled();
    }

    public boolean simpleGameRadio() {
        return simpleRadioButton.isEnabled();
    }

    public int getSelectedIndex(Object selected) {
        for (int i = 0; i < gameboardButtons.length; i++) {
            if (selected.equals(gameboardButtons[i])) {
                return i;
            }
        }
        return -1;
    }

    public void createGameBoard(int size, ActionListener listener) {
        gameboardButtons = new JButton[size * size];
        JPanel board = new JPanel();
        for (int i = 0; i < size * size; i++) {
            JButton button = new JButton();
            button.addActionListener(listener);
            gameboardButtons[i] = button;
            board.add(button);
        }

        board.setLayout(new GridLayout(size,size));
        board.setSize(400,400);
        board.setVisible(true);
        gameBoardPanel.add(board, "gb");
        gameBoardPanel.add(board, 0);
        CardLayout c1 = (CardLayout)(gameBoardPanel.getLayout());
        c1.show(gameBoardPanel, "gb");
    }




}
