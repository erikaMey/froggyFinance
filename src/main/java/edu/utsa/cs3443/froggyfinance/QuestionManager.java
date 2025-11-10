package edu.utsa.cs3443.froggyfinance;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import java.util.List;


/**
 * The QuestionManager class handles the logic for presenting and evaluating
 * multiple-choice questions during a game level. It manages loading questions,
 * checking answers, updating the score, and displaying the appropriate question UI
 * when the player is at the center of the screen.
 *
 * @author erikamey
 */
public class QuestionManager {

    private enum State{
        OPENING_DIALOGS,
        QUESTION_ACTIVE,
        FEEDBACK_SHOWING,
        FINISHED
    }
        private List<Question> questions;
        private int currentIndex = 0;
        private ScoreBox scoreBox;
        private QuestionBox questionBox;
        private KeyHandler keyHandler;
        private GameState gameState;
        private DialogBox dialogBox;
        private DialogManager dialogManager;
        private Timeline feedBackTimer;

        private State state = State.OPENING_DIALOGS;

    /**
     * Constructs a new QuestionManager instance
     *
     * @param filePath Path to the question file
     * @param level The current game level (used to filter or load relevant questions)
     * @param keyHandler Handles keyboard input for answer selection
     * @param scoreBox UI component to show updated scores
     * @param gameState Shared game state for tracking progress across levels
     */
        public QuestionManager(String filePath, int level, KeyHandler keyHandler, ScoreBox scoreBox, GameState gameState, DialogBox dialogBox, DialogManager dialogManager) {
            this.questions = QuestionLoader.loadQuestions(filePath, level);
            this.keyHandler = keyHandler;
            this.scoreBox = scoreBox;
            this.gameState = gameState;
            this.dialogBox = dialogBox;
            this.dialogManager = dialogManager;
            dialogManager.loadDialogsForLevel(filePath, level);
            this.questionBox = new QuestionBox();
            this.questionBox.setVisible(false);
            showNextOpeningDialog();
        }

    /**
     * Returns the VBox containing the question UI.
     *
     * @return the question box UI component
     */
    public VBox getQuestionBox() {
            return questionBox;
        }

    /**
     * Checks whether all questions have been answered
     *
     * @return true if there are no more questions to show, otherwise false
     */
        public boolean isFinished() {
            return state == State.FINISHED;
        }

    /**
     * Displays the question box if the player is near the center of the screen
     *
     * @param playerX The player's X-coordinate.
     * @param playerY The player's Y-coordinate
     * @param screenWidth Width of the screen.
     * @param screenHeight Height of the screen.
     */
        public void showIfInCenter(double playerX, double playerY, double screenWidth, double screenHeight) {
            double centerX = screenWidth / 2.0;
            double centerY = screenHeight / 2.0;
            double margin = 30;

            if (Math.abs(playerX - centerX) < margin && Math.abs(playerY - centerY) < margin && !isFinished()) {
                questionBox.setVisible(true);
            }
        }

    /**
     * Updates the question manager logic. If the question box is visible and
     * a key is pressed (A or B), it checks the answer, updates the score,
     * plays a sound, and moves to the next question.
     */
    public void update() {

        switch (state) {
            case OPENING_DIALOGS:
                handleOpeningDialogs();
                break;

            case QUESTION_ACTIVE:
                handleQuestionInput();
                break;

            case FEEDBACK_SHOWING:
                break;
        }
    }

    /**
     * handles the opening dialog
     */
    private void handleOpeningDialogs() {
        if (keyHandler.spacePressed) {
            Dialog next = dialogManager.getNextOpeningDialog();
            if (next != null) {
                dialogBox.showDialog(next);
            } else {
                state = State.QUESTION_ACTIVE;

                showNextQuestion();
            }
            keyHandler.spacePressed = false;
        }
    }

    /**
     * handles the questions, sound and right wrong answer dialog
     */
    private void handleQuestionInput() {
        boolean aWasPressed = keyHandler.aPressed;
        boolean bWasPressed = keyHandler.bPressed;
        keyHandler.aPressed = false;
        keyHandler.bPressed = false;
        if (!questionBox.isVisible() || currentIndex >= questions.size()) {
            state = State.FINISHED;
            return;
        }
        if (aWasPressed || bWasPressed) {
            Question current = questions.get(currentIndex);
            Dialog feedback = dialogManager.getFeedbackDialog(0);
            String selected = keyHandler.aPressed ? "A" : "B";

            boolean correct = selected.equalsIgnoreCase(current.getCorrectAnswer());

                if (correct) {
                    gameState.incrementCorrect();
                    SoundPlayer.playSound("sparkle.wav");

                } else {
                    gameState.incrementWrong();
                    SoundPlayer.playSound("bruh.wav");

                }
                if (dialogBox != null && feedback != null) {
                    dialogBox.showFeedback(correct ? feedback.getRight() : feedback.getWrong(), correct);
                }

            scoreBox.showScore(gameState.getCorrectAnswers(), gameState.getWrongAnswers());
            state = State.FEEDBACK_SHOWING;

            if (feedBackTimer != null){
                feedBackTimer.stop();
            }
            feedBackTimer = new Timeline(new KeyFrame(Duration.seconds(2.0), e -> {
                dialogBox.hide();
                currentIndex++;
                showNextQuestion();
                state = State.QUESTION_ACTIVE;
            }));
            feedBackTimer.setCycleCount(1);
            feedBackTimer.play();
        }
    }

    /**
     * shows the opening dialog
     */
    private void showNextOpeningDialog() {
        Dialog next = dialogManager.getNextOpeningDialog();
        if (next != null) {
            dialogBox.showDialog(next);
            state = State.OPENING_DIALOGS;
        } else {
            state = State.QUESTION_ACTIVE;
            questionBox.setVisible(true);
            showNextQuestion();
        }
    }

    /**
     * Displays the next question in the list, or hides the question box if finished
     */
        private void showNextQuestion() {
            if (currentIndex < questions.size()) {
                Question current = questions.get(currentIndex);
                questionBox.showQuestion(current);
                questionBox.setVisible(true);
            } else {
                questionBox.hide();
                state = State.FINISHED;
            }
        }

    }


