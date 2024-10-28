package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.util.HashMap;

public class GUI extends JFrame {
    private JPanel gamePanel, containerPanel;
    private JRadioButton humanRedRadioButton, aiRedRadioButton, humanBlueRadioButton, aiBlueRadioButton, simpleRadioButton, generalRadioButton;
    private JButton oRedButton, sRedButton, oBlueButton, sBlueButton, rulesButton, newGameButton;
    private JComboBox sizeComboBox;
    private JLabel infoLabel, redScoreField, blueScoreField;
    private BoardPanel boardPanel;
    private JLabel[][] gameCells;

    public GUI() {
        super();
        boardPanel = new BoardPanel();
        containerPanel.add(boardPanel, 0);
        containerPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        CardLayout c1 = (CardLayout)(containerPanel.getLayout());
        c1.first(containerPanel);
        setTitle("Play SOS Game");
        add(gamePanel);
        buildComboBox();
    }

    // builds dropdown menu for board size selection
    public void buildComboBox() {
        for (int i = 3; i <= 36; i++) {
            sizeComboBox.addItem(i);
        }
    }

    // create a new game board (size x size) grid of JLabel cells
    public void createGameBoard(int size, MouseListener listener) {
        boardPanel = new BoardPanel();
        boardPanel.setSize(650, 650);
        gameCells = new JLabel[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                // create game cell, assign MouseListener, adjust attributes
                JLabel cell = new JLabel();
                cell.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                cell.addMouseListener(listener);
                if (size < 7) {
                    cell.setFont(new Font("Dialog", Font.BOLD,48));
                }
                else if (size < 16) {
                    cell.setFont(new Font("Dialog", Font.BOLD,28));
                }
                else if (size < 24) {
                    cell.setFont(new Font("Dialog", Font.BOLD,20));
                }
                else {
                    cell.setFont(new Font("Dialog", Font.BOLD,14));
                }
                cell.setHorizontalAlignment(SwingConstants.CENTER);
                boardPanel.add(cell);
                gameCells[i][j] = cell;
            }
        }
        boardPanel.setLayout(new GridLayout(size,size));
        containerPanel.add(boardPanel, 0);
        CardLayout c1 = (CardLayout)(containerPanel.getLayout());
        c1.first(containerPanel);
    }

    // paints colored lines marking scoring SOS sequences
    public void paintScoreLines(HashMap<int[][], Color> scoreMap) {
        if (!scoreMap.isEmpty()) {
            double x1, x2, y1, y2;
            int size = gameCells.length;
            for (int[][] s : scoreMap.keySet()) {
                x1 = ((s[0][1] + 1) * (650.0 / size)) - 650.0 / (size * 2);
                y1 = ((s[0][0] + 1) * (650.0 / size)) - 650.0 / (size * 2);
                x2 = ((s[2][1] + 1) * (650.0 / size)) - 650.0 / (size * 2);
                y2 = ((s[2][0] + 1) * (650.0 / size)) - 650.0 / (size * 2);
                boardPanel.addLine(x1, y1, x2, y2, scoreMap.get(s));
            }
            repaint();
        }
    }

    // SETTERS
    public void setInfoLabel(String s, Color color) {
        infoLabel.setText(s);
        infoLabel.setForeground(color);
    }

    public void setGameCell(int[] cell, String s) {
        gameCells[cell[0]][cell[1]].setText(s);
    }

    public void setScore(char player, String s) {
        if (player == 'R') {
            redScoreField.setText(s);
        }
        if (player == 'B') {
            blueScoreField.setText(s);
        }
    }

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

    // GETTERS
    public int getBoardSize() {
        return sizeComboBox.getSelectedIndex() + 3;
    }

    public boolean getGeneralGameRadio() {
        return generalRadioButton.isSelected();
    }

    public boolean getSimpleGameRadio() {
        return simpleRadioButton.isSelected();
    }

    public String getGameCell(int[] cell) {
        return gameCells[cell[0]][cell[1]].getText();
    }

    public int[] getSelectedIndex(Object selected) {
        for (int i = 0; i < gameCells.length; i++) {
            for (int j = 0; j < gameCells.length; j ++) {
                if (selected.equals(gameCells[i][j])) {
                    return new int[] {i, j};
                }
            }
        }
        return new int[] {-1, -1};
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(1000,800);
    }

}

/*  extension of JPanel allowing for scoring sequences to be stored and marked on board
*/
class BoardPanel extends JPanel {

    HashMap<Line2D.Double, Color> scoreLines;

    BoardPanel() {
        super();
        scoreLines = new HashMap<>();
        setSize(650, 650);
    }

    // add scoring sequence to be marked
    public void addLine(double x1, double y1, double x2, double y2, Color c) {
        Line2D.Double line = new Line2D.Double(x1, y1, x2, y2);
        if (!scoreLines.containsKey(line)) {
            scoreLines.put(line, c);
        }
    }

    // paints list of scoring sequences on the game board
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!scoreLines.isEmpty()) {
            Graphics2D g2d = (Graphics2D) g;
            for (Line2D.Double line : scoreLines.keySet()) {
                g2d.setStroke(new BasicStroke(2));
                g2d.setColor(scoreLines.get(line));
                g2d.drawLine((int) line.getX1(), (int) line.getY1(), (int) line.getX2(), (int) line.getY2());
            }
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(650,650);
    }
}