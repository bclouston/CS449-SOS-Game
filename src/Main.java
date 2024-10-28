import controllers.*;
import views.*;

public class Main {

    public static void main(String[] args) {
        GUI gui = new GUI();
        GUIController gc = new GUIController(gui);
    }
}