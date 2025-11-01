package edu.utsa.cs3443.froggyfinance;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * A custom JavaFX UI component that displays a dialog,
 * including the opening dialog text and two answer options (right and wrong).
 *
 * <p>This class extends {@link VBox} and lays out its elements vertically.
 * It is hidden by default and becomes visible when a dialog is displayed
 *
 * @author erikamey
 * @since 10/30/25
 */
public class DialogBox extends VBox {
    private Label openLabel;
    private Label rightLabel;
    private Label wrongLabel;

    /**
     * Constructs a QuestionBox
     */
    public DialogBox() {
        BackgroundFill bgFill = new BackgroundFill(
                Color.rgb(0, 0, 0, 0.0),
                new CornerRadii(0),
                Insets.EMPTY
        );
        setBackground(new Background(bgFill));

        setSpacing(10);
        setAlignment(Pos.CENTER_RIGHT);

        setPrefWidth(110);
        setPrefHeight(200);

        openLabel = createLabel();
        rightLabel = createLabel();
        wrongLabel = createLabel();

        getChildren().addAll(openLabel, rightLabel, wrongLabel);
        setVisible(false);
    }

    /**
     * Creates and returns a styled label
     *
     * @return label
     */
    private Label createLabel() {
        Label label = new Label();
        label.setTextFill(Color.WHITE);
        label.setFont(Font.font("Verdana", 16));
        label.setWrapText(true);
        label.setMaxWidth(580);
        return label;
    }

    /**
     * Displays the given dialog
     * @param d the dialog object to display
     */
    public void showDialog(Dialog d) {
        openLabel.setVisible(false);
        rightLabel.setVisible(false);
        wrongLabel.setVisible(false);

        if (d.getOpen() != null && !d.getOpen().isEmpty()) {
            openLabel.setText(d.getOpen());
            openLabel.setVisible(true);

        }
        Platform.runLater(() -> {
            setVisible(true);
            toFront();
            layout();
        });
    }

    public void showFeedback(String text, boolean isCorrect) {
        Platform.runLater(() -> {
                    openLabel.setText("");
                    rightLabel.setText("");
                    wrongLabel.setText("");
                    openLabel.setVisible(false);
                    rightLabel.setVisible(false);
                    wrongLabel.setVisible(false);

                    if (text != null && !text.isEmpty()) {
                        if (isCorrect) {
                            rightLabel.setText(text);
                            rightLabel.setVisible(true);
                        } else {
                            wrongLabel.setText(text);
                            wrongLabel.setVisible(true);
                        }
                    }
                    //Platform.runLater(() -> {
                        setVisible(true);
                        toFront();
                        layout();
                    });
               // });
    }
    /**
     * Hides the dialog box from view
     */
    public void hide() {
        openLabel.setText("");
        rightLabel.setText("");
        wrongLabel.setText("");
        openLabel.setVisible(false);
        rightLabel.setVisible(false);
        wrongLabel.setVisible(false);
        setVisible(false);
    }
}

