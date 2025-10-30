package edu.utsa.cs3443.froggyfinance;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * A custom JavaFX UI component for displaying a quiz score summary,
 * including the number of correct and incorrect answers, and the overall score percentage.
 *
 * <p>This component is a subclass of {@link VBox} and is styled with a transparent background
 * and centered text labels. It is hidden by default and can be shown or hidden again.
 *
 * @author erikamey
 */
public class ScoreBox extends VBox {

    private Label questionRight;
    private Label questionWrong;
    private Label score;

    /**
     * Constructs a ScoreBox.
     */
    public ScoreBox() {
    BackgroundFill bgFill = new BackgroundFill(
            Color.rgb(0, 0, 0, 0.0),
            new CornerRadii(0),
            Insets.EMPTY
    );
        setBackground(new Background(bgFill));

        setSpacing(10);
        setAlignment(Pos.CENTER_RIGHT);

        setPrefWidth(700);
        setMinWidth(700);
        setMaxWidth(700);
        setPrefHeight(20);
        setMinHeight(20);
        setMaxHeight(20);
        questionRight = createLabel();
        questionWrong = createLabel();
        score = createLabel();

        HBox labelRow = new HBox(20);
        labelRow.setAlignment(Pos.CENTER_RIGHT);
        labelRow.getChildren().addAll(questionRight, questionWrong, score);

    getChildren().addAll(labelRow);
    setVisible(false);
}

    /**
     * Creates and returned a label
     *
     * @return label with set color and set font
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
     * Displays the score with the given number of correct and incorrect answers.
     * Calculates and shows the percentage of correct answers.
     *
     * @param correct correct answer
     * @param wrong wrong answer
     */
    public void showScore(int correct, int wrong) {
   int totalQuestions = correct + wrong;
   int scorePercent = totalQuestions == 0 ? 0 : (correct * 100) / totalQuestions;
    questionRight.setText("Correct: " + correct);
    questionWrong.setText("Wrong " + wrong);
    score.setText("Score: " + scorePercent);
    setVisible(true);
}

    /**
     * Hides ScoreBox
     */
    public void hide() {
    setVisible(false);
}
}
