package view;
import viewcontroller.PokerGameController;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import java.io.IOException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextInputDialog;
import java.util.Optional;
import java.io.BufferedReader;
import java.io.FileReader;

/**
 * @author CS1331 TAs
 * @version 1.0
 */
public class PokerGame extends Application {

    private Scene scene1;
    private StartScreen pane1;
    private PokerGameController cont;
    private GameScreen gameScreen;
    private ControlPane control;
    private Console console;
    private BorderPane root;
    private BufferedReader reader;


    private static Stage primaryStage;

    /**
     * this method is called upon running/launching the application
     * this method should display a scene on the stage
     * @param ps The primary Stage
     */
    public void start(Stage ps) {
        primaryStage = ps;

        pane1 = new StartScreen(this);

        scene1 = new Scene(pane1, 1100, 750);
        ps.setTitle("Extreme Poker");
        ps.setScene(scene1);
        ps.show();


        pane1.getLoad().setOnAction(o -> {
                try {
                    TextInputDialog name = new TextInputDialog();
                    name.setTitle("Load Manager");
                    name.setHeaderText("Loar your previous game!");
                    name.setContentText("Enter the name of your saved file: ");
                    Optional<String> result = name.showAndWait();

                    String filename = result.get();

                    reader = new BufferedReader(new FileReader(filename
                        + ".txt"));
                    String line;

                    String nameT = reader.readLine();

                    int bMon = Integer.parseInt(reader.readLine());
                    String bcd1 = reader.readLine();
                    String bcd2 = reader.readLine();

                    int lMon = Integer.parseInt(reader.readLine());
                    String lcd1 = reader.readLine();
                    String lcd2 = reader.readLine();

                    int rMon = Integer.parseInt(reader.readLine());
                    String rcd1 = reader.readLine();
                    String rcd2 = reader.readLine();

                    int tMon = Integer.parseInt(reader.readLine());
                    String tcd1 = reader.readLine();
                    String tcd2 = reader.readLine();

                    int pot = Integer.parseInt(reader.readLine());
                    int numC = Integer.parseInt(reader.readLine());
                    String[] array = new String[numC];
                    for (int i = 0; i <= numC; i++) {
                        array[i] = reader.readLine();
                    }

                    cont.getBottomPlayer().setName(nameT);
                    cont.getBottomPlayer().setMoney(bMon);
                    //cont.getBottomPlayer().getCard(0).toString();
                    //cont.getBottomPlayer().getCard(1).toString();

                    cont.getLeftPlayer().setMoney(lMon);


                    cont.getRightPlayer().setMoney(rMon);


                    cont.getTopPlayer().setMoney(tMon);
                    //cont.getTopPlayer().getCard(0).toString();
                    //cont.getTopPlayer().getCard(1).toString();

                    cont.getBoard().setPot(pot);
                    //writer.println(cont.getBoard().getNumCards());



                    reader.close();

                } catch (IOException e) {
                    Alert al = new Alert(AlertType.INFORMATION);
                    al.setTitle("Load Error");
                    al.setHeaderText(null);
                    al.setContentText("Could not load the game");

                    al.showAndWait();
                }

            });


    }

    /**
     * Starts the Game
     * This is called by StartScreen whenever it is done and the GameScreen,
     * ControlPane, and Console should be displayed
     * @param name The name of the human player
     */
    public void startGame(String name) {

        cont = new PokerGameController(this, name, pane1.getChips());
        gameScreen = new GameScreen(cont);
        control = new ControlPane(cont);
        console = new Console();

        root = new BorderPane();

        root.setTop(gameScreen);
        root.setCenter(control);
        root.setBottom(console);

        Scene gameScene = new Scene(root);
        primaryStage.setScene(gameScene);

        cont.start();
    }

    /**
     * This is called by PokerGameController whenever updates are made. You
     * must handle updating the UI here.
     */
    public void updatesMade() {
        gameScreen.updatesMade();
        switch (cont.getState()) {
        case HUMAN_BET:
            control.playerTurn();
            break;
        case AI_BET:
            gameScreen.updatesMade();
            break;
        case DEALING:
            System.out.println("dealing");
            break;
        case DONE:
            gameScreen.endOfRound();
            control.endOfRound();
            break;
        default:
            break;
        }
    }

    /**
     * This is the main method that launches the javafx application
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    /**
     * getter for the primary stage
     * @return primaryStage
     */
    public Stage getPrimary() {
        return primaryStage;
    }

}
