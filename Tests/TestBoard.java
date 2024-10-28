import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import models.Board;

public class TestBoard {
    private Board b;

    @Before
    public void setUp() throws Exception {
        b = new Board(3);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    /*  Acceptance Criterion: 4.3, 6.4
        attempts to play move in occupied game cell
    */
    public void testInvalidMoveOccupied() {
        b.playMove(0, 0, 'S');
        assertFalse(b.playMove(0, 0, 'S'));
    }

    @Test
    /*  Acceptance Criterion: 6.2
        check if horizontal SOS sequence is scored
    */
    public void testHorizontalSOS() {
        b.playMove(0, 0, 'S');
        b.playMove(0, 1, 'O');
        b.playMove(0, 2, 'S');
        for (int[][] s : b.findSequences()) {
            assertEquals(s[0][0], 0);
            assertEquals(s[0][1], 0);
            assertEquals(s[1][0], 0);
            assertEquals(s[1][1], 1);
            assertEquals(s[2][0], 0);
            assertEquals(s[2][1], 2);
        }
    }

    @Test
    /*  Acceptance Criterion: 6.2
        check if vertical SOS sequence is scored
    */
    public void testVerticalSOS() {
        b.playMove(0, 0, 'S');
        b.playMove(1, 0, 'O');
        b.playMove(2, 0, 'S');
        for (int[][] s : b.findSequences()) {
            assertEquals(s[0][0], 0);
            assertEquals(s[0][1], 0);
            assertEquals(s[1][0], 1);
            assertEquals(s[1][1], 0);
            assertEquals(s[2][0], 2);
            assertEquals(s[2][1], 0);
        }
    }

    @Test
    /*  Acceptance Criterion: 6.2
        check if diagonal (top-left to bottom-right) SOS sequence is scored
    */
    public void testDiagonalSOSA() {
        b.playMove(0, 0, 'S');
        b.playMove(1, 1, 'O');
        b.playMove(2, 2, 'S');
        for (int[][] s : b.findSequences()) {
            assertEquals(s[0][0], 0);
            assertEquals(s[0][1], 0);
            assertEquals(s[1][0], 1);
            assertEquals(s[1][1], 1);
            assertEquals(s[2][0], 2);
            assertEquals(s[2][1], 2);
        }
    }

    @Test
    /*  Acceptance Criterion: 6.2
        check if diagonal (top-right to bottom-left) SOS sequence is scored
    */
    public void testDiagonalSOSB() {
        b.playMove(0, 2, 'S');
        b.playMove(1, 1, 'O');
        b.playMove(2, 0, 'S');
        for (int[][] s : b.findSequences()) {
            assertEquals(s[0][0], 0);
            assertEquals(s[0][1], 2);
            assertEquals(s[1][0], 1);
            assertEquals(s[1][1], 1);
            assertEquals(s[2][0], 2);
            assertEquals(s[2][1], 0);
        }
    }

    @Test
    /*  Acceptance Criterion: 6.2
        check if multiple sequences are returned when a move scores more than one SOS
    */
    public void testMultiScoreSOS() {
        b.playMove(0, 1, 'O');
        b.playMove(0, 2, 'S');
        b.playMove(1, 0, 'O');
        b.playMove(2, 0, 'S');
        b.playMove(0, 0, 'S');
        assertEquals(2, b.findSequences().size());
    }
}