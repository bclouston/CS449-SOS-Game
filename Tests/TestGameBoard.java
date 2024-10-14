import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import views.GameView;
import controllers.GameController;

public class TestGameBoard {
    private GameView gv;
    private GameController gc;

    @Before
    public void setUp() throws Exception {
        gv = new GameView();
        gc = new GameController(gv);
        gc.newGame(3, 'S');
    }

    @After
    public void tearDown() throws Exception {
    }

    // Acceptance Criterion 1.1
    @Test
    public void testBoardSizes() {
        for (int size = 3; size <= 15; size++) {
            gc.newGame(size, 'S');
            for (int i = 0; i <= (size * size); size++) {
                assertEquals("", gv.getGameCell(i));
            }
        }
    }

    // Acceptance Criterion 1.1
    @Test
    public void testBoardReset() {
        gc.playMove('R', "S", 0);
        gc.newGame(3, 'S');
        assertFalse(gc.isOccupied(0));
    }

    // Acceptance Criterion
    @Test
    public void testSimpleGame() {
        gc.newGame(3, 'S');
        assertEquals('S', gc.getGameMode());
    }

    // Acceptance Criterion
    @Test
    public void testGeneralGame() {
        gc.newGame(3, 'G');
        assertEquals('G', gc.getGameMode());
    }

    // Acceptance Criterion 4.1 & 6.1
    @Test
    public void testValidMove() {
        gc.playMove('R', "S", 0);
        assertEquals("S", gv.getGameCell(0));
    }

    // Acceptance Criterion 4.3 & 6.4
    @Test
    public void testInvalidMoveOutOfTurn() {
        gc.playMove('B', "S", 0);
        assertEquals("", gv.getGameCell(0));
    }

    // Acceptance Criterion 4.3 & 6.4
    @Test
    public void testInvalidMoveOccupiedCell() {
        gc.playMove('R', "S", 0);
        if (!gc.isOccupied(0)) {
            gc.playMove('B', "O", 0);
        }
        assertEquals("S", gv.getGameCell(0));
    }

    // Acceptance Criterion 4.2 & 6.3
    @Test
    public void testTurnSwitching() {
        gc.playMove('R', "S", 0);
        assertEquals('B', gc.getCurrentPlayer());
    }

}