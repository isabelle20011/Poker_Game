package model;

/**
 * @author CS1331 TAs
 * @version 1.0
 */
public class Card {

    private Suit suit;
    private CardValue value;

    /**
     * Constructor for the Card class
     * @param  s The suit
     * @param  v The Value
     */
    public Card(Suit s, CardValue v) {
        suit = s;
        value = v;
    }

    /**
     * Getter for the Suit
     * @return the suit
     */
    public Suit getSuit() {
        return suit;
    }

    /**
     * Getter for the value
     * @return the value
     */
    public CardValue getCardValue() {
        return value;
    }

    /**
     * Returns a String representation of the Card
     * @return a String representation of the Card
     */
    public String toString() {
        return value.name() + " of " + suit.name();
    }

}
