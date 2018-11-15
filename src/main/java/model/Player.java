package model;

/**
 * @author CS1331 TAs
 * @version 1.0
 */
public class Player implements Comparable<Player> {

    private Card[] cards = new Card[2];
    private int numCards;

    private String name;
    private int money;
    private int lastBet;
    private boolean outOfPlay = false;

    private PokerHand hand = null;

    /**
     * Constructor for the Player class
     * @param  name  The Player's name
     * @param  money The Player's starting money
     */
    public Player(String name, int money) {
        this.name = name;
        this.money = money;
    }

    /**
     * Makes the player fold
     */
    public void fold() {
        setOutOfPlay(true);
    }
    /**
     * get name
     * @return name
     */
    public String getName() {
        return name;
    }
    /**
     * set name
     * @param n name
     */
    public void setName(String n) {
        name = n;
    }
    /**
     * Getter for the Player's remaining money
     * @return the Player's money
     */
    public int getMoney() {
        return money;
    }
    /**
     * set money
     * @param m amount to be set
     */
    public void setMoney(int m) {
        money = m;
    }

    /**
     * Places a bet
     * @param  bet The amount of money to bet
     * @return     The difference in this bet versus the last bet
     */
    public int placeBet(int bet) {

        if (bet > money) {
            throw new IllegalArgumentException("Bet cannot excede the amount "
                + "of money left. Bet: " + bet + " Money Left: " + money);
        }

        int bef = money;
        money -= bet - lastBet;
        lastBet = bet;

        return bef - money;
    }

    /**
     * Gives the Player money
     * @param mon The amount to give
     */
    public void giveMoney(int mon) {
        if (mon < 0) {
            throw new IllegalArgumentException("Money must be greater than or"
            + " equal to zero. Money: " + mon);
        }

        money += mon;
    }

    /**
     * Gives the player a Card
     * @param c The Card to give
     */
    public void giveCard(Card c) {
        cards[numCards++] = c;
    }

    /**
     * Makes the player figure out what type of hand it has
     * @param tableCards The cards on the board
     */
    public void figurePokerHand(Card[] tableCards) {
        Card[] handArr = new Card[tableCards.length + cards.length];
        for (int i = 0; i < cards.length; i++) {
            handArr[i] = cards[i];
        }

        for (int i = 0; i < tableCards.length; i++) {
            handArr[i + cards.length] = tableCards[i];
        }

        hand = PokerHand.figurePokerHand(handArr);
    }

    /**
     * Compares the player to another
     * @param  op The other player
     * @return    Negative if the Player is better positive if the Player is
     * worse and 0 if they are the same
     */
    public int compareTo(Player op) {
        if (op.outOfPlay && outOfPlay) {
            return 0;
        } else if (outOfPlay) {
            return 1;
        } else if (op.outOfPlay) {
            return -1;
        } else {
            return op.hand.compareTo(hand);
        }
    }

    /**
     * Getter for the player's PokerHand
     * @return the player's PokerHand
     */
    public PokerHand getPokerHand() {
        return hand;
    }

    /**
     * Setter for outOfPlay
     * @param play what to set outOfPlay to
     */
    public void setOutOfPlay(boolean play) {
        outOfPlay = play;
    }

    /**
     * Getter for outOfPlay
     * @return outOfPlay
     */
    public boolean getOutOfPlay() {
        return outOfPlay;
    }

    /**
     * Resets the player fo each new hand
     */
    public void reset() {
        resetBet();
        numCards = 0;
        outOfPlay = (money <= 0);
    }

    /**
     * Resets player's bet for each new betting round
     */
    public void resetBet() {
        lastBet = 0;
    }

    /**
     * Returns a String representation of the Player's Cards
     * @return a String representation of the Player's Cards
     */
    public String getCardsString() {
        if (outOfPlay) {
            return "Out of play";
        } else {
            return cards[0].toString() + "|"
                + cards[1].toString();
        }
    }

    /**
     * Returns the Player's name
     * @return the Player's name
     */
    public String toString() {
        return name;
    }

    /**
     * Return the cth Card if the Player's hand
     * @param  c the index of Card to return
     * @return   The cth Card
     */
    public Card getCard(int c) {
        return cards[c];
    }

    /**
     * Returns the Player's money at the start of the round
     * @return the Player's money at the start of the round
     */
    public int getLastBet() {
        return lastBet;
    }
}
