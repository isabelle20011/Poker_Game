package model;

/**
 * @author CS1331 TAs
 * @version 1.0
 */
public class Turn {

    private int amount;
    private int difference;
    private TurnType type;

    /**
     * Private constructor for the Turn class
     * @param  type       The type of Turn
     * @param  amount     The amount of money betted
     * @param  difference The difference between the amount betted this bet and
     * last bet
     */
    private Turn(TurnType type, int amount, int difference) {
        this.type = type;
        this.amount = amount;
        this.difference = difference;
    }

    /**
     * Private constructor for the Turn class
     * @param  type The type of the Turn
     */
    private Turn(TurnType type) {
        this(type, 0, 0);
    }

    /**
     * Getter for the amount
     * @return The amount
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Getter for the difference
     * @return the difference
     */
    public int getDifference() {
        return difference;
    }

    /**
     * Getter for the type
     * @return the type
     */
    public TurnType getType() {
        return type;
    }

    /**
     * Creates a Raise Turn
     * @param  amount     The amount to Raise by
     * @param  difference The difference
     * @return            The created Turn
     */
    public static Turn getRaiseTurn(int amount, int difference) {
        return new Turn(TurnType.RAISE, amount, difference);
    }

    /**
     * Creates a Call Turn
     * @param  amount     The amount to Call
     * @return            The created Turn
     */
    public static Turn getCallTurn(int amount) {
        return new Turn(TurnType.CALL, amount, 0);
    }

    /**
     * Creates a Fold Turn
     * @return The created Turn
     */
    public static Turn getFoldTurn() {
        return new Turn(TurnType.FOLD);
    }
}