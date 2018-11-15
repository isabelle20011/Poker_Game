package model;

import java.util.Random;

/**
 * @author CS1331 TAs
 * @version 1.0
 */
public class ConservativeAI extends AIPlayer {

    private static Random rand = new Random();

    /**
     * AIPlayer's constructor
     * @param  name  The name of the AI
     * @param  money The amount of money the AI starts with
     */
    public ConservativeAI(String name, int money) {
        super(name, money);
    }

    /**
     * A method that return a Turn that the AI wants to take
     * @param  boardCards The cards that are on the board
     * @return            The turn that the AI should take
     */
    public Turn getTurn(Card[] boardCards) {
        return super.getTurn(1, 3, boardCards);
    }

    /**
     * Returns a String for a random thing for the AI to say
     * @return The random thing for the AI to say
     */
    public String getSaying() {
        return super.getSaying();
    }

    /**
     * Returns a String for a random thing for the AI to say before folding
     * @return The random thing for the AI to say before folding
     */
    public String getFoldSaying() {
        return super.getFoldSaying();
    }

    /**
     * Returns a String for a random thing for the AI to say after winning
     * @return The random thing for the AI to say after winning
     */
    public String getWinSaying() {
        return super.getWinSaying();
    }

}
