import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import models.*;

public class TestSOSGame {
    private SOSGame g;

    //  combines methods for inserting move into board and checking game state
    private void move(int row, int col, char move, char player) {
        g.makeMove(row, col, move, player);
        g.checkMove(row, col, player);
    }

    @Before
    public void setUp() throws Exception {
        g = new SOSGame(3);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    /*  Acceptance Criterion: 4.1, 6.1
        attempts to play a valid move, checks if successful
    */
    public void testValidMove() {
        assertTrue(g.makeMove(0, 0, 'S', 'R'));
    }

    @Test
    /*  Acceptance Criterion: 4.3, 6.4
        attempts to play a move out of turn, check if unsuccessful
    */
    public void testInvalidMoveOutOfTurn() {
        g.makeMove(0, 0, 'S', 'R');
        assertFalse(g.makeMove(0,0, 'S', 'R'));
    }

    @Test
    /*  Acceptance Criterion: 4.2
        check if active player is switched after successful turn in simple game
    */
    public void testTurnSwitchingSimple() {
        g = new SOSSimple(3);
        move(0, 0, 'S', 'R');
        assertEquals('B', g.getActivePlayer());
    }

    @Test
    /*  Acceptance Criterion: 6.3
        check if active player is NOT switched after SOS sequence played in general game
    */
    public void testTurnSwitchingGeneral() {
        g = new SOSGeneral(3);
        move(0, 0, 'S', 'R');
        move(0, 1, 'O', 'B');
        move(0, 2, 'S', 'R'); // red score
        assertEquals('R', g.getActivePlayer());
    }

    @Test
    /*  Acceptance Criterion: 2.1, 5.1, 5.2
        check if a game is marked as complete when first SOS is scored
        check if correct winner is declared
    */
    public void testSimpleGameWin() {
        g = new SOSSimple(3);
        move(0, 0, 'S', 'R');
        move(0, 1, 'O', 'B');
        move(0, 2, 'S', 'R'); // red score
        assertTrue(g.isGameOver());
        assertEquals('R', g.getWinner());
    }

    @Test
    /*  Acceptance Criterion: 2.1, 5.1, 5.3
        check if a game is marked as complete when board is full
        check if draw is declared
    */
    public void testSimpleGameDraw() {
        g = new SOSSimple(3);
        move(0, 0, 'S', 'R');
        move(0, 1, 'S', 'B');
        move(0, 2, 'S', 'R');
        move(1, 0, 'S', 'B');
        move(1, 1, 'S', 'R');
        move(1, 2, 'S', 'B');
        move(2, 0, 'S', 'R');
        move(2, 1, 'S', 'B');
        move(2, 2, 'S', 'R');
        assertTrue(g.isGameOver());
        assertEquals('D', g.getWinner());
    }

    @Test
    /*  Acceptance Criterion: 2.1, 7.1, 7.2
        check if a game is marked as complete when board is full
        check if correct winner is declared
    */
    public void testGeneralGameWin() {
        g = new SOSGeneral(3);
        move(0, 0, 'S', 'R');
        move(0, 1, 'O', 'B');
        move(0, 2, 'S', 'R'); // red score
        move(2, 2, 'S', 'R');
        move(1, 1, 'O', 'B'); // blue score
        move(2, 0, 'O', 'B');
        move(1, 2, 'O', 'R'); // red score
        move(1, 0, 'O', 'R');
        move(2, 1, 'O', 'B');
        assertTrue(g.isGameOver());
        assertEquals('R', g.getWinner());
    }

    @Test
    /*  Acceptance Criterion: 2.1, 7.1, 7.3
        check if a game is marked as complete when board is full
        check if draw is declared when games ends in tie and/or no score
    */
    public void testGeneralGameDraw() {
        // full board with no scoring sequences
        g = new SOSGeneral(3);
        move(0, 0, 'S', 'R');
        move(0, 1, 'S', 'B');
        move(0, 2, 'S', 'R');
        move(1, 0, 'S', 'B');
        move(1, 1, 'S', 'R');
        move(1, 2, 'S', 'B');
        move(2, 0, 'S', 'R');
        move(2, 1, 'S', 'B');
        move(2, 2, 'S', 'R');
        assertTrue(g.isGameOver());
        assertEquals('D', g.getWinner());

        // full board with score tie
        g = new SOSGeneral(3);
        move(0, 0, 'S', 'R');
        move(0, 1, 'O', 'B');
        move(0, 2, 'S', 'R'); // red score
        move(2, 0, 'O', 'R');
        move(1, 0, 'S', 'B');
        move(1, 1, 'O', 'R');
        move(1, 2, 'S', 'B'); // blue score
        move(2, 1, 'O', 'B');
        move(2, 2, 'O', 'R');
        assertTrue(g.isGameOver());
        assertEquals('D', g.getWinner());
    }
}
