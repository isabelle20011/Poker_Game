package model;

/**
 * @author CS1331 TAs
 * @version 1.0
 */
public enum CardValue {
    ACE("A"), KING("K"), QUEEN("Q"), JACK("J"), TEN("10"), NINE("9"),
    EIGHT("8"), SEVEN("7"), SIX("6"), FIVE("5"), FOUR("4"), THREE("3"),
    TWO("2");

    private String str;

    /**
     * Constructor for the CardValue
     */
    CardValue(String str) {
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