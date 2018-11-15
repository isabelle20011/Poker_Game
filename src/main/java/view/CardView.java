package view;

import javafx.scene.layout.StackPane;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.control.Label;
import model.Card;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import model.Suit;
import model.CardValue;

/**
 * @author CS1331 TAs
 * @version 1.0
 */
public class CardView extends StackPane {

    private static final int CARD_HEIGHT = 150;
    private static final int CARD_WIDTH = 107;
    private static final String BACK_LOCATION = "File:./src/main/res/"
        + "playing-card-back.png";
    private static final String FRONT_LOCATION = "File:./src/main/res/"
        + "playing-card-front.png";
    private static Image backIm;
    private static Image frontIm;

    private static Image kingSpades;
    private static Image kingHearts;
    private static Image kingDiamonds;
    private static Image kingClubs;

    private static Image queenSpades;
    private static Image queenHearts;
    private static Image queenDiamonds;
    private static Image queenClubs;

    private static Image jackSpades;
    private static Image jackHearts;
    private static Image jackDiamonds;
    private static Image jackClubs;

    private static final String KING_SPADES = "File:./src/main/res/"
        + "kingSpades.png";
    private static final String KING_HEARTS = "File:./src/main/res/"
        + "kingHearts.png";
    private static final String KING_DIAMONDS = "File:./src/main/res/"
        + "kingDiamonds.png";
    private static final String KING_CLUBS = "File:./src/main/res/"
        + "kingClubs.png";

    private static final String QUEEN_SPADES = "File:./src/main/res/"
        + "queenSpades.png";
    private static final String QUEEN_HEARTS = "File:./src/main/res/"
        + "queenHearts.png";
    private static final String QUEEN_DIAMONDS = "File:./src/main/res/"
        + "queenDiamonds.png";
    private static final String QUEEN_CLUBS = "File:./src/main/res/"
        + "queenClubs.png";

    private static final String JACK_SPADES = "File:./src/main/res/"
        + "jackSpades.png";
    private static final String JACK_HEARTS = "File:./src/main/res/"
        + "jackHearts.png";
    private static final String JACK_DIAMONDS = "File:./src/main/res/"
        + "jackDiamonds.png";
    private static final String JACK_CLUBS = "File:./src/main/res/"
        + "jackClubs.png";

    // statically loads Images
    static {
        backIm = new Image(BACK_LOCATION);
        frontIm = new Image(FRONT_LOCATION);

        kingSpades = new Image(KING_SPADES);
        kingHearts = new Image(KING_HEARTS);
        kingDiamonds = new Image(KING_DIAMONDS);
        kingClubs = new Image(KING_CLUBS);

        queenSpades = new Image(QUEEN_SPADES);
        queenHearts = new Image(QUEEN_HEARTS);
        queenDiamonds = new Image(QUEEN_DIAMONDS);
        queenClubs = new Image(QUEEN_CLUBS);

        jackSpades = new Image(JACK_SPADES);
        jackHearts = new Image(JACK_HEARTS);
        jackDiamonds = new Image(JACK_DIAMONDS);
        jackClubs = new Image(JACK_CLUBS);

    }

    // the background image of the card
    private ImageView background;
    // the top left label where card value is displayed
    private Label topL;
    // the bottom left label where card value is displayed
    private Label botR;
    // the middle label where card suit is displayed
    private Label mid;
    // the card where card info is found
    private Card card;

    /**
     * Constructor for CardView
     */
    public CardView() {
        background = new ImageView(frontIm);
        background.setFitHeight(CARD_HEIGHT);
        background.setFitWidth(CARD_WIDTH);
        topL = new Label();
        mid = new Label();
        botR = new Label();
        getChildren().addAll(background, topL, mid, botR);
    }

    /**
     * Gives the CardView a Card object which contains information on the Card
     * @param c The Card to display
     */
    public void setCard(Card c) {
        card = c;
        topL.setText(c.getCardValue().getStr());
        mid.setText(c.getSuit().getStr());
        mid.setFont(new Font(40));
        botR.setText(c.getCardValue().getStr());

        if (c.getSuit().equals(Suit.HEART)) {
            if (c.getSuit().equals(Suit.DIAMOND)) {
                topL.setTextFill(Color.RED);
                mid.setTextFill(Color.RED);
                botR.setTextFill(Color.RED);
            }
        }
        topL.setTranslateX(15 - CARD_WIDTH / 2);
        topL.setTranslateY(topL.getLayoutBounds().getHeight() / 2 + 5
            - CARD_HEIGHT / 2);
        botR.setTranslateX(CARD_WIDTH / 2 - 15);
        botR.setTranslateY(botR.getLayoutBounds().getHeight() / -2 - 5
            + CARD_HEIGHT / 2);

    }
    /**
     * checks if the card is supposed to have a face
     * @param c card being checked
     * @return boolean representing whether or not it has a face
     */
    public boolean checkFace(Card c) {
        boolean result = false;
        if (c.getCardValue().equals(CardValue.KING)) {
            result = true;
            if (c.getSuit().equals(Suit.SPADE)) {
                background.setImage(kingSpades);
            } else if (c.getSuit().equals(Suit.HEART)) {
                background.setImage(kingHearts);
            } else if (c.getSuit().equals(Suit.DIAMOND)) {
                background.setImage(kingDiamonds);
            } else if (c.getSuit().equals(Suit.CLUB)) {
                background.setImage(kingClubs);
            }
        }

        if (c.getCardValue().equals(CardValue.QUEEN)) {
            result = true;
            if (c.getSuit().equals(Suit.SPADE)) {
                background.setImage(queenSpades);
            } else if (c.getSuit().equals(Suit.HEART)) {
                background.setImage(queenHearts);
            } else if (c.getSuit().equals(Suit.DIAMOND)) {
                background.setImage(queenDiamonds);
            } else if (c.getSuit().equals(Suit.CLUB)) {
                background.setImage(queenClubs);
            }
        }

        if (c.getCardValue().equals(CardValue.JACK)) {
            result = true;
            if (c.getSuit().equals(Suit.SPADE)) {
                background.setImage(jackSpades);
            } else if (c.getSuit().equals(Suit.HEART)) {
                background.setImage(jackHearts);
            } else if (c.getSuit().equals(Suit.DIAMOND)) {
                background.setImage(jackDiamonds);
            } else if (c.getSuit().equals(Suit.CLUB)) {
                background.setImage(jackClubs);
            }
        }
        topL.setVisible(false);
        botR.setVisible(false);
        mid.setVisible(false);
        return result;
    }

    /**
     * Shows the front of the Card
     */
    public void show() {
        if (!checkFace(card)) {
            background.setImage(frontIm);
            topL.setVisible(true);
            botR.setVisible(true);
            mid.setVisible(true);
        }
    }

    /**
     * Makes the card not display at all
     */
    public void hide() {
        background.setImage(null);
        topL.setVisible(false);
        botR.setVisible(false);
        mid.setVisible(false);
    }

    /**
     * Shows the back of the card.
     */
    public void hideDetails() {
        background.setImage(backIm);
        topL.setVisible(false);
        botR.setVisible(false);
        mid.setVisible(false);
    }
}
