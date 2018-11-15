package model;

/**
 * @author CS1331 TAs
 * @version 1.0
 */
public enum Suit {
    HEART("\u2665"), DIAMOND("\u2666"), CLUB("\u2663"), SPADE("\u2660");

    private String str;

    /**
     * Constructor for the Suit enum
     */
    Suit(String str) {
        this.str = str;
    }

    /**
     * Getter for str
     * @return str
     */
    public String getStr() {
        return str;
    }
}