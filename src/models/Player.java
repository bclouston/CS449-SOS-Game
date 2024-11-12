package models;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

public class Player {

    private char color;
    private boolean ai;
    private boolean computer;

    public Player(char color) {
        this.color = color;
    }

    public void setAI(boolean ai) {
        this.ai = ai;
    }

    public void setComputer(boolean computer) {
        this.computer = computer;
    }

    public boolean ai() {
        return ai;
    }

    public boolean computer() {
        return computer;
    }

    public char getColor() {
        return color;
    }

    public char[] getMoveLLM() {
        String move = "";
        String reasoning = "";
        try {
            Process p = new ProcessBuilder("C:\\Users\\bmclo\\OneDrive\\Documents\\JavaPrograms\\SOS\\.venv\\Scripts\\python.exe", "C:\\Users\\bmclo\\OneDrive\\Documents\\JavaPrograms\\SOS\\src\\LLMAgent.py").redirectErrorStream(true).start();
            p.getInputStream().transferTo(System.out);
            int rc = p.waitFor();
        } catch (IOException e) {
            System.out.println("i/o error");
        } catch (InterruptedException e) {
            System.out.println("error");
        }
        try {
            File f = new File("C:\\Users\\bmclo\\OneDrive\\Documents\\JavaPrograms\\SOS\\src\\llm_move.txt");
            Scanner s = new Scanner(f);
            int n = 0;
            while (s.hasNextLine()) {
                if (n == 0) {
                    move = s.nextLine();
                }
                else {
                    reasoning = s.nextLine();
                }
                n++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("i/o error");
        }
        System.out.println("+-------------------------------------------------------------------");
        System.out.println("| LLM Player Move: " + move);
        System.out.println("+-------------------------------------------------------------------");
        System.out.println("| Reasoning:");
        System.out.println("+-------------------------------------------------------------------");
        String[] lines = reasoning.split("\\. ");
        for (String line : lines) {
            System.out.println("| " + line);
        }
        System.out.println("+-------------------------------------------------------------------");
        return new char[]{move.charAt(1), move.charAt(4), move.charAt(7)};
    }

    public char[] getMoveAI(Board board) {
        int size = board.getSize();
        // complete SOS sequence if one is available
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board.isMoveValid(i, j)) {
                    board.playMove(i, j, 'S');
                    LinkedList<int[][]> seqList = board.findSequences();
                    if (seqList != null) {
                        for (int[][] sequence : seqList) {
                            for (int[] cell : sequence) {
                                if (cell[0] == i && cell[1] == j) {
                                    char[] move = {(char)(i + '0'), (char)(j + '0'), 'S'};
                                    return move;
                                }
                            }
                        }
                    }
                    board.deleteMove(i, j);
                    board.playMove(i, j, 'O');
                    seqList = board.findSequences();
                    if (seqList != null) {
                        for (int[][] sequence : seqList) {
                            for (int[] cell : sequence) {
                                if (cell[0] == i && cell[1] == j) {
                                    char[] move = {(char)(i + '0'), (char)(j + '0'), 'O'};
                                    return move;
                                }
                            }
                        }
                    }
                    board.deleteMove(i, j);
                }
            }
        }

        // play random move
        LinkedList<int[]> moves = new LinkedList<>();
        //char[] move =
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board.isMoveValid(i, j)) {
                    int[] temp = {i, j};
                    moves.add(temp);
                }
            }
        }
        int n = moves.size();
        Random rand = new Random();
        int index = rand.nextInt(n);
        if ((index + 1) % 2 == 0) {
            int r = moves.get(index)[0];
            int c = moves.get(index)[1];
            return new char[] {(char)(r + '0'), (char)(c + '0'), 'S'};
        }
        else {
            int r = moves.get(index)[0];
            int c = moves.get(index)[1];
            return new char[] {(char)(r + '0'), (char)(c + '0'), 'O'};
        }
    }

}
