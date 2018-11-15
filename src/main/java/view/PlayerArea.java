package view;

import javafx.scene.layout.Pane;

import model.Player;

import javafx.scene.text.Text;
import javafx.scene.layout.VBox;
/**
 * @author CS1331 TAs
 * @version 1.0
 */
public abstract class PlayerArea {

    private Pane pane;

    private Player player;

    private CardView card1, card2;
    private Text name, chips, outOf;
    private VBox strings;


    /**
     * PlayerArea's constructor
     * @param  pane   The Pane where all UI elements will be added. The type of
     * pane is decided by subclasses
     * @param  player The Player who's information will be tracked
     */
    public PlayerArea(Pane pane, Player player) {
        this.pane = pane;
        this.player = player;

        card1 = new CardView();
        card2 = new CardView();

        pane.getChildren().addAll(card1, card2);

        name = new Text(player.toString());
        chips = new Text("Chips: " + player.getMoney());
        outOf = new Text("Out of Play");

        strings = new VBox();
        strings.getChildren().addAll(name, chips, outOf);
        outOf.setVisible(false);

        pane.getChildren().add(strings);
    }

    /**
     * Getter for the Pane that contains all of the UI elements.
     * @return The Pane that contains all of the UI elements.
     */
    public Pane playerPane() {
        return pane;
    }

    /**
     * This method is called whenever an update to the UI needs to be made.
     * @param showDetails is true whenever the details of the front of the
     * cards are supposed to be shown false otherwise
     */
    public void update(boolean showDetails) {
        chips.setText("Chips: " + player.getMoney());

        card1.setCard(player.getCard(0));
        card2.setCard(player.getCard(1));

        if (player.getOutOfPlay()) {
            card1.hide();
            card2.hide();
            outOf.setVisible(true);
        } else if (showDetails) {
            card1.show();
            card2.show();
        } else {
            card1.hideDetails();
            card2.hideDetails();
        }
    }
}
