package model;

import java.util.Random;
import java.util.Arrays;
import java.util.ArrayList;

/**
 * @author CS1331 TAs
 * @version 1.0
 */
public class Board {

    private static Random rand = new Random();

    private Deck deck;
    private Card[] tableCards = new Card[5];
    private int numCards;

    private Player[] players;

    private int pot;

    /**
     * Constructor for the Board class
     * @param  players The Players that are playing at this board
     */
    public Board(Player[] players) {
        deck = new Deck();
        this.players = players;
        numCards = 0;
    }

    /**
     * Starts a new PokerHand in the board
     */
    public void startNewPokerHand() {
        if (rand.nextInt(10) < 1) {
            deck.shuffle();
        }

        pot = 0;

        for (int i = 0; i < players.length; i++) {
            if (!players[i].getOutOfPlay()) {
                players[i].giveCard(deck.deal());
                players[i].giveCard(deck.deal());
            }
        }

        numCards = 0;
    }

    /**
     * Updates the pot when a turn is taken
     * @param t The turn that was taken
     */
    public void takeTurn(Turn t) {
        if (t.getType() == TurnType.RAISE) {
            pot += t.getAmount();
        } else if (t.getType() == TurnType.CALL) {
            pot += t.getAmount();
        }
    }

    /**
     * Places cards onto the table
     */
    public void placeCards() {

        int num = 1;

        //First three
        if (numCards <= 0) {
            num = 3;
        }

        for (int i = 0; i < num; i++) {
            tableCards[i + numCards] = deck.deal();
        }

        numCards += num;

    }

    /**
     * Getter for the number of Cards on the Board
     * @return The number of Cards on the Board
     */
    public int getNumCards() {
        return numCards;
    }
    /**
     * set pot
     * @param p pot value
     */
    public void setPot(int p) {
        pot = p;
    }

    /**
     * Returns the ith Card on the Board
     * @param  i The Card to return
     * @return   The ith Card on the table
     */
    public Card getTableCard(int i) {
        return tableCards[i];
    }

    /**
     * Getter for the tableCards as an array with no null values
     * @return the tableCards
     */
    public Card[] getCards() {
        Card[] ret = new Card[numCards];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = tableCards[i];
        }

        return ret;
    }

    /**
     * Returns the winner if there is one null otherwise
     * @return the winner if there is one null otherwise
     */
    public RoundEnd findWinner() {

        // If not enough cards have been played there is no winner
        if (numCards < 5) {
            return null;
        }

        for (Player p: players) {
            p.figurePokerHand(tableCards);
        }

        // New Array so we don't sort the passed in array
        Player[] pls = new Player[players.length];
        for (int i = 0; i < pls.length; i++) {
            pls[i] = players[i];
        }

        // Sort and then find winners
        Arrays.sort(pls);
        Player p = pls[0];
        ArrayList<Player> winners = new ArrayList<Player>();
        winners.add(p);
        for (int i = 1; i < pls.length; i++) {
            if (p.compareTo(pls[i]) == 0) {
                winners.add(pls[i]);
            }
        }

        return RoundEnd.getWinner(winners.toArray(new Player[winners.size()]));
    }

    /**
     * Getter for the pot
     * @return the pot
     */
    public int getPot() {
        return pot;
    }

    /**
     * Returns a textual representation of the cards
     * @return a textual representation of the cards
     */
    public String cardsString() {
        String s = "--------------------------------------\n";
        for (int i = 0; i < numCards; i++) {
            s += tableCards[i].toString() + "|";
        }

        s += "\n--------------------------------------";

        return s;
    }

    /**
     * Returns a textual representation of the board
     * @return a textual representation of the board
     */
    public String toString() {
        String s = String.format(
            "--------------------------------------%n"
            + "Human: %s%n"
            + "AI 1: %s%n"
            + "AI 2: %s%n"
            + "AI 3: %s%n"
            + "--------------------------------------%n",
            players[0].getCardsString(),
            players[1].getCardsString(),
            players[2].getCardsString(),
            players[3].getCardsString()
            );

        for (int i = 0; i < numCards; i++) {
            s += tableCards[i].toString() + "|";
        }

        s += "\n--------------------------------------";

        return s;
    }

}
