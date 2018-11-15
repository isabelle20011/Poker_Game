package view;

import javafx.scene.control.ScrollPane;

import javafx.scene.control.Label;

/**
 * @author CS1331 TAs
 * @version 1.0
 */
public class Console extends ScrollPane {

    private static Console instance;
    private Label lb;

    /**
     * Console's constructor. Set's the static instance variable.
     */
    public Console() {
        instance = this;

        this.setPrefViewportHeight(300);
        lb = new Label();
        lb.setWrapText(true);
        instance.setContent(lb);

    }

    /**
     * Add's text to the top of the console. (Doesn't get rid of
     * text that is already there!)
     * @param newText is the text to add to the top of the console
     */
    public void addText(String newText) {
        lb.setText(newText + "\n" + lb.getText());
        instance.setVvalue(0);
    }

    /**
     * Clears the console of any text
     */
    public void clear() {
        lb.setText("");
    }

    /**
     * Static method that adds a message into the current
     * {@value  instance}
     * @param message The message to add
     */
    public static void putMessage(String message) {
        instance.addText(message);
    }

    /**
     * Clears the console of the current {@value instance}
     */
    public static void clearLog() {
        instance.clear();
    }
}
