package edu.utsa.cs3443.froggyfinance;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

/**
 * A custom JavaFX UI component that displays a multiple-choice question,
 * including the question text and two answer options (A and B).
 *
 * <p>This class extends {@link VBox} and lays out its elements vertically.
 * It is hidden by default and becomes visible when a question is displayed
 *
 * @author erikamey
 */
public class QuestionBox extends VBox {
    private Label questionLabel;
    private Label optionALabel;
    private Label optionBLabel;

    /**
     * Constructs a QuestionBox
     */
    public QuestionBox() {
        BackgroundFill bgFill = new BackgroundFill(
                Color.rgb(0, 0, 0, 0.0),
                new CornerRadii(0),
                Insets.EMPTY
        );
        setBackground(new Background(bgFill));

        setSpacing(10);
        setAlignment(Pos.CENTER_LEFT);

        setPrefWidth(700);
        setMinWidth(700);
        setMaxWidth(700);
        setPrefHeight(220);
        setMinHeight(220);
        setMaxHeight(220);
        questionLabel = createLabel();
        optionALabel = createLabel();
        optionBLabel = createLabel();

        getChildren().addAll(questionLabel, optionALabel, optionBLabel);
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
     * Displays the given question
     * @param question the question object to display
     */
    public void showQuestion(Question question) {
        questionLabel.setText("Q: " + question.getQuestionText());
        optionALabel.setText("A) " + question.getOptionA());
        optionBLabel.setText("B) " + question.getOptionB());
        setVisible(true);
    }

    /**
     * Hides the question box from view
     */
    public void hide() {
        setVisible(false);
    }
}

