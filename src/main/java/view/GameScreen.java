package view;

import javafx.scene.layout.BorderPane;
import viewcontroller.PokerGameController;



/**
 * @author CS1331 TAs
 * @version 1.0
 */
public class GameScreen extends BorderPane {

    private HorizontalPlayer top, bottom;
    private VerticalPlayer left, right;
    private BoardArea board;

    /**
     * GameScreen's constructor
     * @param controller The PokerGameController to interact with
     */
    public GameScreen(PokerGameController controller) {

        this.setPrefWidth(1100);

        top = new HorizontalPlayer(controller.getTopPlayer());
        bottom = new HorizontalPlayer(controller.getBottomPlayer());
        left = new VerticalPlayer(controller.getLeftPlayer());
        right = new VerticalPlayer(controller.getRightPlayer());

        board = new BoardArea(controller.getBoard());

        this.setTop(top.playerPane());
        this.setLeft(left.playerPane());
        this.setRight(right.playerPane());
        this.setBottom(bottom.playerPane());

        this.setCenter(board.getPane());
    }

    /**
     * This method is called whenever normal updates to the UI need to be made.
     */
    public void updatesMade() {
        top.update(false);
        left.update(false);
        right.update(false);
        bottom.update(true);
        board.update();
    }

    /**
     * This method is called whenever a round of poker ends
     */
    public void endOfRound() {
        top.update(true);
        left.update(true);
        right.update(true);
        bottom.update(true);
        board.update();

    }
}
