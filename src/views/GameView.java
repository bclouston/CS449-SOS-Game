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
    private JButton[] gameBoardButtons;

    public GameView() {
        setTitle("Play SOS Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 850);
        setLocationRelativeTo(null);
        setContentPane(gamePanel);
        buildComboBox();
    }

    // builds dropdown menu for board size selection
    public void buildComboBox() {
        for (int i = 3; i <= 15; i++) {
            sizeComboBox.addItem(i);
        }
    }

    // generates game board of selected size
    public void createGameBoard(int size, ActionListener listener) {
        gameBoardButtons = new JButton[size * size];
        JPanel board = new JPanel();
        for (int i = 0; i < size * size; i++) {
            JButton button = new JButton();
            if (size <= 7) {
                button.setFont(new Font("Dialog", Font.BOLD,28));
            }
            button.addActionListener(listener);
            gameBoardButtons[i] = button;
            board.add(button);
        }
        board.setLayout(new GridLayout(size,size));
        board.setSize(650,650);
        board.setVisible(true);
        gameBoardPanel.add(board, "gb");
        gameBoardPanel.add(board, 0);
        CardLayout c1 = (CardLayout)(gameBoardPanel.getLayout());
        c1.first(gameBoardPanel);
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

    public void setInfoLabel(String s, Color color) {
        infoLabel.setText(s);
        infoLabel.setForeground(color);
    }

    public void setGameCell(int i, String s) {
        gameBoardButtons[i].setText(s);
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

    public String getGameCell(int index) {
        return gameBoardButtons[index].getText();
    }

    public int getSelectedIndex(Object selected) {
        for (int i = 0; i < gameBoardButtons.length; i++) {
            if (selected.equals(gameBoardButtons[i])) {
                return i;
            }
        }
        return -1;
    }
}