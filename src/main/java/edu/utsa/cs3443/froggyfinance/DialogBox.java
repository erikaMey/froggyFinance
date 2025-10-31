package edu.utsa.cs3443.froggyfinance;

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

        setPrefWidth(600);
        setMinWidth(600);
        setMaxWidth(600);
        setPrefHeight(40);
        setMinHeight(40);
        setMaxHeight(40);
        openLabel = createLabel();
        rightLabel = createLabel();
        wrongLabel = createLabel();

        //HBox labelRow = new HBox(40); need to get this to be on the right side
        //labelRow.setAlignment(Pos.CENTER_RIGHT);

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
        label.setMaxWidth(740);
        return label;
    }

    /**
     * Displays the given dialog
     * @param dialog the dialog object to display
     */
    public void showdialog(Dialog dialog) {
        openLabel.setText("OPEN" + dialog.getOpen());// need to get this to run first thing
        rightLabel.setText("Right" + dialog.getRight());
        wrongLabel.setText("Wrong" + dialog.getWrong());
        setVisible(true);
    }

    /**
     * Hides the dialog box from view
     */
    public void hide() {
        setVisible(false);
    }
}

