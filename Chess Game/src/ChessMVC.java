package src;

import controller.GameController;
import model.GameModel;
import view.GameView;

public class ChessMVC {

    public static void main(String[] args)
    {
        GameModel model = new GameModel("Amaze", "Rob" , false);
        GameView view = new GameView();
        GameController controller = new GameController(model, view);
    }
}
