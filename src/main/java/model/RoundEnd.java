package model;

/**
 * @author CS1331 TAs
 * @version 1.0
 */
public class RoundEnd {

    private Player[] winners;
    private WinType type;
    private PokerHand hand;

    /**
     * private constructor for the RoundEnd class
     * @param  winners The people who won
     * @param  hand    The hand
     */
    private RoundEnd(Player[] winners, PokerHand hand) {
        this.winners = winners;
        this.hand = hand;
    }

    /**
     * Getter for the winners
     * @return the winners
     */
    public Player[] getWinners() {
        return winners;
    }

    /**
     * Getter for the type
     * @return the type
     */
    public WinType getType() {
        return type;
    }

    /**
     * Getter for the hand
     * @return the hand
     */
    public PokerHand getPokerHand() {
        return hand;
    }

    /**
     * Creates RoundEnd object for the winners and sets the type
     * @param  winners The Players who won
     * @return         The created RoundEnd object
     */
    public static RoundEnd getWinner(Player[] winners) {
        RoundEnd ret = new RoundEnd(winners, winners[0].getPokerHand());
        if (winners.length > 1) {
            ret.type = WinType.MULTIPLE;
        } else {
            ret.type = WinType.SINGLE;
        }

        return ret;
    }

    /**
     * Creates a RoundEnd object for when all Players but one fold
     * @param  winner The winner
     * @return        The created RoundEnd object
     */
    public static RoundEnd getFoldWin(Player winner) {
        RoundEnd ret = new RoundEnd(new Player[]{winner}, null);
        ret.type = WinType.LAST_LEFT;
        return ret;
    }

    /**
     * Returns a String representation of the RoundEnd
     * @return a String representation of the RoundEnd
     */
    public String toString() {
        String s = "";
        for (Player winner: winners) {
            s += winner + ", ";
        }

        if (type == WinType.LAST_LEFT) {
            s += "won because everyone else folded";
        } else {

            s += "win(s) with ";
            s += winners[0].getPokerHand().toString();

        }

        return s;
    }

}