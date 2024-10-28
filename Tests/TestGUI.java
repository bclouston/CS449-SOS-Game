import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import views.*;
import controllers.*;

public class TestGUI {
    private GUI gui;
    private GUIController gc;

    @Before
    public void setUp() throws Exception {
        gui = new GUI();
        gc = new GUIController(gui);
        gc.newGame(3, 'G');
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    /*  Acceptance Criterion: 1.1, 3.1
        generates every board size and checks if correct number of game cells exist
    */
    public void testBoardSizes() {
        for (int size = 3; size <= 36; size++) {
            gc.newGame(size, 'G');
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    assertEquals("", gui.getGameCell(new int[] {i, j}));
                }

            }
        }
    }

    @Test
    /*  Acceptance Criterion: 1.1, 3.1
        checks if previous moves are removed from GUI board upon new game
    */
    public void testBoardReset() {
        int[] cell = {0, 0};
        gc.playMove('R', "S", cell);
        gc.newGame(3, 'G');
        assertEquals("", gui.getGameCell(cell));
    }
}