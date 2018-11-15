package view;

import javafx.scene.layout.StackPane;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextInputDialog;
import java.util.Optional;
import javafx.scene.media.AudioClip;


/**
 * @author CS1331 TAs
 * @version 1.0
 */
public class StartScreen extends StackPane {

    private Image img;
    private Button btnscene1, load;
    private Optional<String> result, first;
    private ImageView background;
    private HBox root;
    private AudioClip buttonSound;

    // Path to the image file for the background
    private static final String BACK_LOCATION = "File:./src/main/res"
        + "/poker-game-background.png";

    /**
     * StartScreen's constructor
     * @param cont The PokerGame to interact with
     */
    public StartScreen(PokerGame cont) {
        String n = "button-16.mp3";
        String m = this.getClass().getResource(n).toExternalForm();
        buttonSound = new AudioClip(m);

        root = new HBox();
        root.setPadding(new Insets(90));
        root.setSpacing(8);
        root.setAlignment(Pos.BOTTOM_LEFT);
        img = new Image(BACK_LOCATION);
        background = new ImageView(img);
        background.setFitWidth(1100);
        background.setFitHeight(750);
        this.getChildren().add(background);

        btnscene1 = new Button("Start New Game");

        btnscene1.setOnAction(e-> {
                if (e.getSource() == btnscene1) {
                    buttonSound.play();
                    makeNameInput().ifPresent(name -> cont.startGame(name));
                }
            });

        root.getChildren().add(btnscene1);
        this.getChildren().add(root);

        load = new Button("  Load    ");
        root.getChildren().addAll(load);

    }
    /**
     * makes the name dialog
     * @return the name
     */
    public Optional<String> makeNameInput() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("New Game");
        dialog.setHeaderText("Confirmation");
        dialog.setContentText("Enter your name: ");
        result = dialog.showAndWait();
        return result;
    }
    /**
     * makes the chips dialog
     * @return returns the number of chips
     */
    public int getChips() {
        TextInputDialog dialog = new TextInputDialog("100");
        dialog.setTitle("New Game");
        dialog.setHeaderText("Number of Chips");
        dialog.setContentText("Enter desired number of chips: ");
        first = dialog.showAndWait();
        int chipNum = Integer.parseInt(first.get());
        return chipNum;
    }
    /**
     * @return load button
     */
    public Button getLoad() {
        return load;
    }
}
