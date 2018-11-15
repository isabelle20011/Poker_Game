package viewcontroller;

import model.Board;
import model.Turn;
import model.TurnType;
import model.Player;
import model.AIPlayer;
import model.HumanPlayer;
import model.RoundEnd;
import model.WinType;
import view.Console;
import view.PokerGame;
import javafx.application.Platform;
import java.util.Random;

/**
 * @author CS1331 TAs
 * @version 1.0
 */
public class PokerGameController {

    private Board board;
    private GameState state = GameState.DONE;
    private HumanPlayer human;
    private AIPlayer[] ais;
    private Player[] players;

    private int minBet = 0;
    private boolean firstBetPlaced = false;
    private int bettingTurn;
    private int lastFirstPlayer = -1;
    private int callCount;
    private int maxBet;

    private PokerGame gameView;

    private static Random rand = new Random();
    private static boolean shouldThink = true;

    /**
     * PokerGameController's constructor
     * @param  gameView The PokerGame object to interact with
     * @param  name The human's name
     * @param chips amount of chips desired
     */
    public PokerGameController(PokerGame gameView, String name, int chips) {

        this.gameView = gameView;

        human = new HumanPlayer(name, chips);
        ais = new AIPlayer[3];
        players = new Player[ais.length + 1];
        players[0] = human;
        for (int i = 0; i < ais.length; i++) {
            ais[i] = new AIPlayer("AI_" + (i + 1), chips);
            players[i + 1] = ais[i];
        }
        //ais[2] = new ConservativeAI("ConservativeAI", chips);
        board = new Board(players);
    }

    /**
     * Starts the inital Poker PokerHand
     */
    public void start() {

        Console.putMessage("Welcome to the Extreme Poker Table!");
        maxBet = human.getMoney();
        startNewPokerHand();

    }

    /**
     * Starts a new PokerHand of Poker
     * @return Returns true if a new PokerHand was succesfully started false
     * otherwise
     */
    public boolean startNewPokerHand() {
        if (state != GameState.DONE) {
            return false;
        }

        for (Player pl: players) {
            pl.reset();
        }

        if (numberOutOfPlay() >= players.length - 1) {

            Player winner = null;

            for (Player pl: players) {
                if (!pl.getOutOfPlay()) {
                    winner = pl;
                    break;
                }
            }

            Console.putMessage(winner.toString() + " won all the chips!!!");
            return false;
        } else if (human.getOutOfPlay()) {
            Console.putMessage("Uh oh you ran out of money... You can't play "
                + "anymore.");
            return false;
        }

        board.startNewPokerHand();
        resetBoard();
        bettingTurn = lastFirstPlayer++;
        Console.putMessage("New PokerHand has begun!");
        state = GameState.AI_BET;
        runAI();
        gameView.updatesMade();
        return true;
    }

    /**
     * Deals new Cards to the Board.
     */
    private void dealNewCards() {
        state = GameState.DEALING;
        resetBoard();
        board.placeCards();
        state = GameState.AI_BET;
        updateLater();
    }

    /**
     * Resets values like minBet and callCount.
     * This is called whenever new Cards are dealt or a new PokerHand Starts
     */
    private void resetBoard() {
        minBet = 0;
        firstBetPlaced = false;
        callCount = 1;
        for (Player pl: players) {
            pl.resetBet();
        }
        updateMaxBet();
    }

    /**
     * Called to make the human take a bet
     * @param  bet The amount to bet
     * @return true if the bet was succesfully made. False otherwise.
     */
    public boolean humanBet(int bet) {

        if (human.getOutOfPlay()) {
            Console.putMessage("You're out of play.");
            return false;
        }

        if (state != GameState.HUMAN_BET
            || players[bettingTurn] != human) {
            Console.putMessage("Its not your time to bet!");
            return false;
        }

        Turn turn = null;

        if (bet > human.getMoney()) {
            Console.putMessage("You don't have that many chips left!");
            return false;
        } else if (bet > maxBet) {
            Console.putMessage("You can't raise more than another player has!");
            return false;
        } else if (bet == 0) {
            turn = Turn.getCallTurn(human.placeBet(minBet));
        } else if (bet > 0) {
            turn = Turn.getRaiseTurn(human.placeBet(bet + minBet), bet);
        }

        if (bet < 0) {
            Console.putMessage("You can't raise by a negative!");
            return false;
        } else {
            playTurn(human, turn);
            runAI();
            return true;
        }
    }

    /**
     * Called to make the human take a Call Turn.
     * @return true if the call Turn was succesfully made. False otherwise.
     */
    public boolean humanCall() {

        if (human.getOutOfPlay()) {
            Console.putMessage("You're out of play.");
            return false;
        }

        if (state != GameState.HUMAN_BET
            || players[bettingTurn] != human) {
            Console.putMessage("Its not your time to bet!");
            return false;
        }

        Turn turn = Turn.getCallTurn(human.placeBet(minBet));

        playTurn(human, turn);
        runAI();
        return true;
    }

    /**
     * Called to make the human take a Fold Turn.
     * @return true if the Fold Turn was succesfully made. False otherwise.
     */
    public boolean humanFold() {
        if (human.getOutOfPlay()) {
            Console.putMessage("You're already out of play.");
            return false;
        }

        if (state != GameState.HUMAN_BET
            || players[bettingTurn] != human) {
            Console.putMessage("Its not your time to fold!");
            return false;
        }

        Turn turn = Turn.getFoldTurn();
        playTurn(human, turn);
        runAI();

        return true;
    }

    /**
     * PokerHandles making the AI take their turns on a different thread.
     * Does this by creating a different thread and calling runAIHelper on it
     */
    private void runAI() {
        gameView.updatesMade();
        new Thread() {
            public void run() {
                runAIHelper();
            }
        }.start();
    }

    /**
     * PokerHandles taking the AI's turns.
     */
    private void runAIHelper() {
        RoundEnd winner = null;
        do {
            winner = findWinner();
            if (winner == null) {
                setNextUp();
                if (players[bettingTurn] != human) {
                    takeAiTurn((AIPlayer) players[bettingTurn]);
                    think(300, 0);
                }
                updateLater();
            } else {
                handleWinner(winner);
                updateLater();
            }
        } while (players[bettingTurn] != human && winner == null);
    }

    /**
     * PokerHandles a single AI taking it's turn.
     * @param ai The ai whose turn it is.
     */
    private void takeAiTurn(AIPlayer ai) {

        if (ai.getOutOfPlay()) {
            throw new IllegalStateException(
                "A player that is out of play may not bet.");
        }

        if (state != GameState.AI_BET
            || players[bettingTurn] != ai) {
            throw new IllegalArgumentException(
                "It's not this player's turn. Player: " + ai
                + " when its " + players[bettingTurn] + "'s Turn");
        }

        Turn t = ai.getTurn(minBet, maxBet, board.getCards());
        think(1000, 400);
        if (t.getType() == TurnType.FOLD) {
            putLater(((AIPlayer) players[bettingTurn]).getFoldSaying());
        } else {
            putLater(((AIPlayer) players[bettingTurn]).getSaying());
        }
        think(500, 3000);
        playTurn(ai, t);
    }

    /**
     * Takes Player p's Turn t and updates Game information like minBet and
     * callCount
     * @param p The player whose turn it is
     * @param t Their Turn
     */
    private void playTurn(Player p, Turn t) {
        board.takeTurn(t);
        if (t.getType() == TurnType.FOLD) {
            p.fold();
            putLater(p.toString() + " folded.");
        } else if (t.getType() == TurnType.CALL) {
            putLater(p.toString() + " called");
            // This will happen only if call is the first action to occur
            if (!firstBetPlaced) {
                firstBetPlaced = true;
            } else {
                callCount++;
            }
        } else if (t.getType() == TurnType.RAISE) {
            firstBetPlaced = true;
            putLater(p.toString() + " raised " + (t.getDifference())
                + " chips.");
            callCount = 1;
            minBet += t.getDifference();
        }
        updateMaxBet();
    }

    /**
     * Updates maxBet
     */
    private void updateMaxBet() {
        maxBet = Integer.MAX_VALUE;
        for (Player p: players) {
            if (!p.getOutOfPlay()) {
                maxBet = Math.min(p.getMoney(), maxBet);
            }
        }
    }
    /**
     * getter for human
     * @return human
     */
    public HumanPlayer getHuman() {
        return human;
    }
    /**
     * Finds the next Player who needs to take a turn
     */
    private void setNextUp() {
        int nextUp = -1;
        int i = 1;
        while (i < players.length && nextUp == -1) {
            if (!players[(bettingTurn + i) % players.length]
                .getOutOfPlay()) {
                nextUp = (bettingTurn + i) % players.length;
            } else {
                i++;
            }
        }

        if (nextUp == -1) {
            throw new RuntimeException("Next player was unable "
                + "to be found!");
        }
        bettingTurn = nextUp;

        if (callCount >= players.length - numberOutOfPlay()) {
            putLater("Dealing new cards...");
            dealNewCards();
        }

        if (players[bettingTurn] == human) {
            state = GameState.HUMAN_BET;
        } else {
            state = GameState.AI_BET;
        }

        putLater(players[bettingTurn] + " it is your turn.");
    }

    /**
     * If someone has won it PokerHandles printing their information
     * @param winner The winner or winners who won (Can be multiple this
     * is PokerHandled within the RoundEnd class)
     */
    private void handleWinner(RoundEnd winner) {
        if (winner.getType() == WinType.SINGLE) {
            putLater(winner.getWinners()[0].toString() + " won! "
                + " with a " + winner.getPokerHand().toString()
                + " and received " + board.getPot() + " chips.");
            winner.getWinners()[0].giveMoney(board.getPot());
        } else if (winner.getType() == WinType.LAST_LEFT) {
            putLater("Everyone else folded ... and so "
                + winner.getWinners()[0].toString() + " won! "
                + "and received " + board.getPot() + " chips.");
            winner.getWinners()[0].giveMoney(board.getPot());
        } else {
            String winnerStr = "";
            Player[] winners = winner.getWinners();
            int amount = board.getPot() / winners.length;
            for (int i = 0; i < winners.length; i++) {
                winners[i].giveMoney(amount);
                if (i < winners.length - 2) {
                    winnerStr += winners[i].toString() + ", ";
                } else if (i < winners.length - 1) {
                    if (winners.length > 2) {
                        winnerStr += winners[i].toString() + ", and ";
                    } else {
                        winnerStr += winners[i].toString() + " and ";
                    }
                } else {
                    winnerStr += winners[i].toString();
                }
            }

            putLater("There was a tie! " + winnerStr
                + " all had " + winner.getPokerHand().toString()
                + " and split the pot. Each got " + amount + " chips.");
        }

        for (Player pla: winner.getWinners()) {
            if (pla != human) {
                putLater(((AIPlayer) pla).getWinSaying());
            }
        }

        state = GameState.DONE;
    }

    /**
     * Looks to see if someone has won
     * @return The RoundEnd object for the winner(s) if someone has
     * won otherwise null
     */
    private RoundEnd findWinner() {

        // Only one player who is not out of play
        if (numberOutOfPlay() >= players.length - 1) {
            for (int i = 0; i < players.length; i++) {
                if (!players[i].getOutOfPlay()) {
                    return RoundEnd.getFoldWin(players[i]);
                }
            }
        }

        // If betting round isn't over because not enough people
        // have called
        if (callCount < players.length - numberOutOfPlay()) {
            return null;
        }

        return board.findWinner();
    }

    /**
     * Figures how many Players are already out of Play for this PokerHand
     * @return [description]
     */
    private int numberOutOfPlay() {
        int outNumber = 0;
        for (Player pl: players) {
            if (pl.getOutOfPlay()) {
                outNumber++;
            }
        }

        return outNumber;
    }

    /**
     * Puts a message to the console and PokerHandles talking between threads.
     * @param message The message to put in the console
     */
    private void putLater(String message) {
        Platform.runLater(new Runnable() {
            public void run() {
                Console.putMessage(message);
            }
        });
    }

    /**
     * Tells the UI to update. PokerHandles talking between threads.
     */
    private void updateLater() {
        Platform.runLater(new Runnable() {
            public void run() {
                gameView.updatesMade();
            }
        });
    }

    /**
     * Returns the player on the Left of the screen.
     * @return The player on the Left of the screen.
     */
    public Player getLeftPlayer() {
        return players[1];
    }

    /**
     * Returns the player on the Top of the screen.
     * @return The player on the Top of the screen.
     */
    public Player getTopPlayer() {
        return players[2];
    }

    /**
     * Returns the player on the Right of the screen.
     * @return The player on the Right of the screen.
     */
    public Player getRightPlayer() {
        return players[3];
    }

    /**
     * Returns the player on the Bottom of the screen.
     * @return The player on the Bottom of the screen.
     */
    public Player getBottomPlayer() {
        return players[0];
    }

    /**
     * Returns the Board.
     * @return The Board
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Returns the GameState that the Game is in
     * @return The GameState that the Game is in
     */
    public GameState getState() {
        return state;
    }

    /**
     * Sets whether or not the AIs should take time making their decision
     * @param think Whether or not the Ais should take time making their
     * decision
     */
    public void setShouldThink(boolean think) {
        shouldThink = think;
    }

    /**
     * Makes the thread sleep to simulate the AI thinking
     * @param low The lowest amount of time for the AI to think
     * @param var The possible variance in time for the AI to think
     */
    private void think(int low, int var) {
        if (!shouldThink) {
            return;
        }
        try {
            int time = 0;
            if (var > 0) {
                time = rand.nextInt(var);
            }
            Thread.sleep(low + time);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
