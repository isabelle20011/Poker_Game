package view;

import javafx.scene.layout.HBox;
import viewcontroller.PokerGameController;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.geometry.Pos;
import javafx.scene.media.AudioClip;
import javafx.scene.control.TextInputDialog;
import java.util.Optional;
import java.io.PrintWriter;
import java.io.IOException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.Card;

/**
 * @author CS1331 TAs
 * @version 1.0
 */
public class ControlPane extends HBox {

    private PokerGameController cont;
    private Button raise, call, fold, save, startNew;
    private TextField raiseAmount;
    private AudioClip buttonSound;


    /**
     * Constructor for ControlPane
     * @param  cont The PokerGameController to interact with
     */
    public ControlPane(PokerGameController cont) {
        String n = "button-16.mp3";
        String m = this.getClass().getResource(n).toExternalForm();
        buttonSound = new AudioClip(m);

        this.cont = cont;

        this.setSpacing(5);
        this.setAlignment(Pos.CENTER);

        raiseAmount = new TextField();
        raiseAmount.setPromptText("Raise Amount: ");

        raise = new Button("Raise");
        raise.setDisable(true);
        call = new Button("Call");
        call.setDisable(true);
        fold = new Button("Fold");
        fold.setDisable(true);
        save = new Button("Save");
        startNew = new Button("Start New Round");
        startNew.setVisible(false);

        this.getChildren().addAll(raiseAmount, raise, call, fold);
        this.getChildren().addAll(save, startNew);
    }

    /**
     * Called whenever it becomes the player's turn again
     */
    public void playerTurn() {
        raise.setDisable(false);
        call.setDisable(false);
        fold.setDisable(false);
        save.setDisable(false);
        raise.setOnAction(e -> {
                int savedValue = Integer.parseInt(raiseAmount.getText());
                cont.humanBet(savedValue);
                buttonSound.play();
                raise.setDisable(true);
                call.setDisable(true);
                fold.setDisable(true);
                save.setDisable(true);
                raiseAmount.clear();
            });

        call.setOnAction(e -> {
                cont.humanCall();
                buttonSound.play();
                raise.setDisable(true);
                call.setDisable(true);
                fold.setDisable(true);
                save.setDisable(true);
            });

        fold.setOnAction(e -> {
                cont.humanFold();
                buttonSound.play();
                raise.setDisable(true);
                call.setDisable(true);
                fold.setDisable(true);
                save.setDisable(true);
            });
        startNew.setOnAction(e -> {
                cont.startNewPokerHand();
                buttonSound.play();
                Console.clearLog();
                startNew.setVisible(false);
            });

        save.setOnAction(o -> {
                try {
                    TextInputDialog name = new TextInputDialog();
                    name.setTitle("Save Manager");
                    name.setHeaderText("Save your game!");
                    name.setContentText("Enter the name of your save file: ");
                    Optional<String> result = name.showAndWait();

                    String fileName = result.get() + ".txt";
                    PrintWriter writer = new PrintWriter(fileName, "UTF-8");
                    writer.println(cont.getBottomPlayer().getName());
                    writer.println(cont.getBottomPlayer().getMoney());
                    String t = cont.getBottomPlayer().getCard(0).toString();
                    writer.println(t);
                    String r = cont.getBottomPlayer().getCard(1).toString();
                    writer.println(r);

                    writer.println(cont.getLeftPlayer().getMoney());
                    writer.println(cont.getLeftPlayer().getCard(0).toString());
                    writer.println(cont.getLeftPlayer().getCard(1).toString());

                    writer.println(cont.getRightPlayer().getMoney());
                    writer.println(cont.getRightPlayer().getCard(0).toString());
                    writer.println(cont.getRightPlayer().getCard(1).toString());

                    writer.println(cont.getTopPlayer().getMoney());
                    writer.println(cont.getTopPlayer().getCard(0).toString());
                    writer.println(cont.getTopPlayer().getCard(1).toString());

                    writer.println(cont.getBoard().getPot());
                    writer.println(cont.getBoard().getNumCards());

                    Card[] cards = cont.getBoard().getCards();
                    for (int i = 0; i < cards.length; i++) {
                        writer.println(cards[i].toString());
                    }

                    writer.close();
                } catch (IOException e) {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Save Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Could not save the game");

                    alert.showAndWait();

                }
            });
    }


    /**
     *save disabled
     *
     * player name
     * player money
     * player cards
     *
     * a1 money
     * ai cards
     *
     * a2 --
     * a2 --
     *  a3 --
     *  a3
     *
     * board cards number suits
     * board pot
     *
     * numcards showing
     *
     *
     */

    /**
     * Method called when the round ends.
     */
    public void endOfRound() {
        startNew.setVisible(true);
    }
}
