package model;

import java.util.Arrays;

/**
 * @author CS1331 TAs
 * @version 1.0
 */
public class PokerHand implements Comparable<PokerHand> {

    private HandType type;

    /**
     * Constructor for the PokerHand class
     * @param  type The type of PokerHand
     */
    public PokerHand(HandType type) {
        this.type = type;
    }

    /**
     * Compares PokerHands based on HandType
     * @param  oH The PokerHand to compareTo
     * @return    negative if a PokerHand is worse positive if a PokerHand
     * is better 0 if they are the same
     */
    public int compareTo(PokerHand oH) {
        return oH.type.ordinal() - type.ordinal();
    }

    /**
     * Figures out what is the HandType of a PokerHand based on the Cards
     * passed in
     * @param  cards The cards to determine the HandType of
     * @return       A PokerHand object of the right type
     */
    public static PokerHand figurePokerHand(Card[] cards) {

        boolean flush = false;
        boolean straight = false;
        Card[] flushArr = new Card[7];

        if (cards.length >= 5) {
            Arrays.sort(cards, (Card o1, Card o2) -> {
                    return o1.getSuit().compareTo(o2.getSuit());
                });

            //Check for flush
            flushArr[0] = cards[0];
            int fSameCount = 1;
            Suit lSuit = cards[0].getSuit();
            for (int i = 1; i < cards.length; i++) {
                if (cards[i].getSuit() == lSuit) {
                    flushArr[fSameCount] = cards[i];
                    if (fSameCount >= 4) {
                        flush = true;
                    }
                    fSameCount++;
                } else {
                    if (flush) {
                        break;
                    }
                    fSameCount = 1;
                    flushArr[0] = cards[i];
                }

                lSuit = cards[i].getSuit();
            }

            //Check for straight
            Card[] sCheckArr = (flush ? flushArr : cards);
            Arrays.sort(sCheckArr, (Card o1, Card o2) -> {
                    if (o1 != null && o2 != null) {
                        return o1.getCardValue().compareTo(o2.getCardValue());
                    } else {
                        return 1;
                    }
                });

            int stCount = 1;
            int cardC = (flush ? fSameCount : cards.length);
            CardValue lCard = sCheckArr[0].getCardValue();
            for (int i = 1; ((i <= cardC || stCount > 1) && !straight); i++) {
                int comp = lCard.compareTo(sCheckArr[i % cardC]
                    .getCardValue());
                if (comp == 1 || comp == -1
                    || (lCard == CardValue.TWO && comp == 12)) {
                    stCount++;
                    if (stCount >= 5) {
                        straight = true;
                    }
                } else if (comp > 1 || comp < -1) {
                    stCount = 1;
                }

                lCard = sCheckArr[i % cardC].getCardValue();
            }
        }

        //Check for multiple cards of a kind
        Arrays.sort(cards, (Card o1, Card o2) -> {
                return o1.getCardValue().compareTo(o2.getCardValue());
            });

        int ofAKindBig = 0;
        int ofAKindSmall = 0;
        int curr = 1;

        CardValue lCard = cards[0].getCardValue();
        for (int i = 1; i < cards.length; i++) {
            if (cards[i].getCardValue() == lCard) {
                curr++;
            } else {
                if (curr > ofAKindBig) {
                    ofAKindSmall = ofAKindBig;
                    ofAKindBig = curr;
                } else if (curr > ofAKindSmall) {
                    ofAKindSmall = curr;
                }
                curr = 1;
            }
            lCard = cards[i].getCardValue();
        }

        if (curr > ofAKindBig) {
            ofAKindSmall = ofAKindBig;
            ofAKindBig = curr;
        } else if (curr > ofAKindSmall) {
            ofAKindSmall = curr;
        }

        //Finished finding all that we need to assign HandType

        // System.out.println("STRAIGHT: " + straight);
        // System.out.println("FLUSH: " + flush);
        // System.out.println("High: " + ofAKindBig);
        // System.out.println("Low: " + ofAKindSmall);

        //Straight Flush
        if (flush && straight) {
            if (flushArr[0].getCardValue() == CardValue.ACE
                && flushArr[4].getCardValue() == CardValue.TEN) {
                return new PokerHand(HandType.ROYAL_FLUSH);
            }
            return new PokerHand(HandType.STRAIGHT_FLUSH);
        } else if (ofAKindBig >= 4) { //Four of a kind
            return new PokerHand(HandType.FOUR_OF_A_KIND);
        } else if (ofAKindBig >= 3 && ofAKindSmall >= 2) { //Full House
            return new PokerHand(HandType.FULL_HOUSE);
        } else if (flush) { //Flush
            return new PokerHand(HandType.FLUSH);
        } else if (straight) { //Straight
            return new PokerHand(HandType.STRAIGHT);
        } else if (ofAKindBig >= 3) { //Three of a kind
            return new PokerHand(HandType.THREE_OF_A_KIND);
        } else if (ofAKindBig >= 2 && ofAKindSmall >= 2) { //Two pair
            return new PokerHand(HandType.TWO_PAIR);
        } else if (ofAKindBig >= 2) { //Pair
            return new PokerHand(HandType.PAIR);
        } else { //High card
            return new PokerHand(HandType.HIGH_CARD);
        }
    }

    /**
     * String representation of a PokerHand
     * @return the String representation of a PokerHand
     */
    public String toString() {
        return type.name();
    }

    /**
     * Getter for the type
     * @return The HandType
     */
    public HandType getType() {
        return type;
    }
}