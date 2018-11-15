package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * @author CS1331 TAs
 * @version 1.0
 */
public class Deck {

    private Card[] cards = new Card[52];
    private int ind = 0;

    /**
     * Constructor for the Deck
     */
    public Deck() {

        Suit[] suits = Suit.values();
        CardValue[] values = CardValue.values();

        for (int i = 0; i < cards.length; i++) {
            cards[i] = new Card(suits[i / (cards.length / suits.length)],
                values[i % values.length]);
        }

        shuffle();
    }

    /**
     * Returns the next card
     * @return the next card
     */
    public Card deal() {
        return cards[(ind++) % cards.length];
    }

    /**
     * Shuffles the deck
     */
    public void shuffle() {
        ArrayList<Card> newCs = new ArrayList<Card>(Arrays.asList(cards));
        Random rand = new Random();

        int i = 0;
        while (!newCs.isEmpty()) {
            cards[i++] = newCs.remove(rand.nextInt(newCs.size()));
        }

        ind = 0;
    }
}