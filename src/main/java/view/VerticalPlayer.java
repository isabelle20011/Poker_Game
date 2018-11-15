package view;

import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import model.Player;

/**
 * @author CS1331 TAs
 * @version 1.0
 */
public class VerticalPlayer extends PlayerArea {

    /**
     * Constructor for a VerticalPlayer.
     * @param  player The Player object containing data about the Player to
     * be displayed
     */
    public VerticalPlayer(Player player) {
        super(new VBox(20), player);
        ((VBox) playerPane()).setAlignment(Pos.CENTER);
    }

}