package model;

import java.util.Random;

/**
 * @author CS1331 TAs
 * @version 1.0
 */
public class AIPlayer extends Player {

    private static Random rand = new Random();

    private static final String[] SAYINGS = {
        "Hmmm let me think",
        "I have a royal flush this game is all mine!",
        "I can't believe you haven't folded yet...",
        "I swear I'm not bluffing just fold now.",
        "You've made your point.",
        "I see you.",
        "No hard feelings.",
        "Are you still there!",
        "Target lost...",
        "Malfunctioning",
        "I don't hate you.",
        "I don't blame you.",
        "All right, I've been thinking, when life gives you bad cards, "
            + "don't fold! Make life take those cards back and go all in! "
            + "I don't want your 2 of diamonds! What am I supposed to do with "
            + "this? Demand to see this game's programmer!",
        "I am NOT! A MORON!",
        "I'm gonna go all Lady Gaga on this game",
        "You can't beat me.",
        "I'm stacking penny stocks.",
        "Jordan is my idol.",
        "Bet you won't fold. Bet. What are the odds? Bet.",
        "I'm on the highway to the danger zone.",
        "I can read you like a book.",
        "Call me maybe.",
        "This game has been pretty tame and tepid so far.",
        "Is this your card? No? No? oh okay...awkward",
        "Cards on the table, we're both showing hearts."
    };

    private static final String[] FOLD_SAYINGS = {
        "skrrt skrrt",
        "Marie Curie invented the theory of radioactivity, the treatment of "
            + "radioactivity, and dying of radioactivity.",
        "I thought we could put our differences behind us... for CS..."
            + "you monster.",
        "Ehhhh",
        "Maybe I should just switch to DuelMosters abridged (tm).",
        "This is sadder than when Goose dies.",
        "Too rich for my blood.",
        "I can't sustain this any longer.",
        "System.exit(1);"
    };

    private static final String[] WIN_SAYINGS = {
        "Your mother was a hamster and your father smells of elderberries",
        "Bow down!",
        "Get Ramblin' Rekt",
        "Winning!",
        "Ganando!",
        "At least 13 people in the US are named donut.",
        "The glory is all mine.",
        "I can afford school now!",
        "You've fallen victim to one of the classic blunders! Never go in "
            + "against a Sicilian when totally fake virtual currency is on the"
            + " line!",
        "You've made your bed now lie in it."
    };

    /**
     * AIPlayer's constructor
     * @param  name  The name of the AI
     * @param  money The amount of money the AI starts with
     */
    public AIPlayer(String name, int money) {
        super(name, money);
    }

    /**
     * A method that return a Turn that the AI wants to take
     * @param  minBet     The minimum the AI can bet
     * @param  maxBet     The maximum the AI can bet
     * @param  boardCards The cards that are on the board
     * @return            The turn that the AI should take
     */
    public Turn getTurn(int minBet, int maxBet, Card[] boardCards) {

        figurePokerHand(boardCards);
        PokerHand hand = getPokerHand();
        double perc = 0;
        double wiggleCall = 0;
        double wiggleFold = 5;

        switch (hand.getType()) {
        case ROYAL_FLUSH:
        case STRAIGHT_FLUSH:
        case FOUR_OF_A_KIND:
        case FULL_HOUSE:
        case FLUSH:
        case STRAIGHT:
            perc += 90;
            break;
        case THREE_OF_A_KIND:
            perc += 50;
            break;
        case TWO_PAIR:
            perc += 30;
            break;
        case PAIR:
            perc += 15;
            break;
        default:
            perc += 0;
        }

        if (boardCards.length == 0) {
            perc += 2;
            wiggleCall = 2;
            wiggleFold = 10;
        } else if (boardCards.length == 3) {
            perc += 1;
            wiggleCall = 5;
            wiggleFold = 5;
        } else if (boardCards.length == 4) {
            perc += 0;
            wiggleCall = 10;
            wiggleFold = 5;
        }

        perc = Math.min(100, perc);
        perc = Math.max(0, perc);

        if (minBet > (double) getMoney()
            * ((perc + wiggleFold) / 100.0)) {
            return Turn.getFoldTurn();
        }

        int amnt = minBet;

        if (minBet < (double) getMoney()
            * ((perc - wiggleCall) / 100.0)) {
            amnt = Math.min(maxBet, (int) ((double) getMoney()
                * (perc / 100.0)));
        }

        if (amnt <= minBet || amnt <= 0) {
            minBet = Math.max(0, minBet);
            return Turn.getCallTurn(placeBet(minBet));
        } else {
            int raise = amnt - minBet;
            return Turn.getRaiseTurn(placeBet(amnt), raise);
        }

    }

    /**
     * Returns a String for a random thing for the AI to say
     * @return The random thing for the AI to say
     */
    public String getSaying() {
        if (rand.nextInt(4) < 1) {
            return toString() + ": hmmm let me think.";
        } else {
            return toString() + ": " + SAYINGS[rand.nextInt(SAYINGS.length)];
        }
    }

    /**
     * Returns a String for a random thing for the AI to say before folding
     * @return The random thing for the AI to say before folding
     */
    public String getFoldSaying() {
        return toString() + ": "
            + FOLD_SAYINGS[rand.nextInt(FOLD_SAYINGS.length)];
    }

    /**
     * Returns a String for a random thing for the AI to say after winning
     * @return The random thing for the AI to say after winning
     */
    public String getWinSaying() {
        return toString() + ": "
            + WIN_SAYINGS[rand.nextInt(WIN_SAYINGS.length)];
    }

}
