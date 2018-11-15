package view;

import javafx.scene.layout.HBox;
import model.Board;

import javafx.scene.control.Label;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.media.AudioClip;

/**
 * @author CS1331 TAs
 * @version 1.0
 */
public class BoardArea {

    private HBox pane;

    private Board board;
    private CardView cardb1, cardb2, cardb3, cardb4, cardb5;
    private Label pot;
    private AudioClip cardDealing;
    private boolean playedAlredy1, playedAlredy2, playedAlredy3;

    /**
     * Constructor for the board's display
     * @param  board The Board object that contains data associated with the
     * board
     */
    public BoardArea(Board board) {
        playedAlredy1 = false;
        playedAlredy2 = false;
        playedAlredy3 = false;
        String n = "cardDealing.wav";
        String m = this.getClass().getResource(n).toExternalForm();
        cardDealing = new AudioClip(m);

        pane = new HBox();
        pane.setSpacing(30);
        pane.setPadding(new Insets(0, 100, 0, 100));
        pane.setAlignment(Pos.CENTER);

        this.board = board;

        cardb1 = new CardView();
        cardb2 = new CardView();
        cardb3 = new CardView();
        cardb4 = new CardView();
        cardb5 = new CardView();

        pane.getChildren().addAll(cardb1, cardb2, cardb3, cardb4, cardb5);

        pot = new Label("Pot: " + board.getPot());

        pane.getChildren().add(pot);
    }

    /**
     * Getter for the HBox that all UI elements are on
     * @return the HBox that all Board UI elements are on
     */
    public HBox getPane() {
        return pane;
    }

    /**
     * Updates UI elements
     */
    public void update() {
        cardb1.hide();
        cardb2.hide();
        cardb3.hide();
        cardb4.hide();
        cardb5.hide();
        if (board.getNumCards() == 3) {
            cardb1.setCard(board.getTableCard(0));
            cardb2.setCard(board.getTableCard(1));
            cardb3.setCard(board.getTableCard(2));
            cardb1.show();
            cardb2.show();
            cardb3.show();
            if (!playedAlredy1) {
                cardDealing.play();
                playedAlredy1 = true;
            }
        } else if (board.getNumCards() == 4) {
            cardb4.setCard(board.getTableCard(3));
            cardb1.show();
            cardb2.show();
            cardb3.show();
            cardb4.show();
            if (!playedAlredy2) {
                cardDealing.play();
                playedAlredy2 = true;
            }
        } else if (board.getNumCards() == 5) {
            cardb5.setCard(board.getTableCard(4));
            cardb1.show();
            cardb2.show();
            cardb3.show();
            cardb4.show();
            cardb5.show();
            if (!playedAlredy3) {
                cardDealing.play();
                playedAlredy3 = true;
            }
        }

        pot.setText("Pot: " + board.getPot());
    }

}
