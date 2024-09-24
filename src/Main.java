import controllers.*;
import views.*;

public class Main {

    public static void main(String[] args) {
        GameView gv = new GameView();
        GameController gc = new GameController(gv);
    }
}