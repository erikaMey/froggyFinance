package edu.utsa.cs3443.froggyfinance;


import javafx.scene.layout.VBox;
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
        private List<Question> questions;
        private int currentIndex = 0;
        private ScoreBox scoreBox;
        private QuestionBox questionBox;
        private KeyHandler keyHandler;
        private GameState gameState;
        private DialogBox dialogBox;
        private DialogManager dialogManager;
        private List<Dialog> dialogs;
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
            this.dialogs = dialogManager.getDialogsForLevel(level);
            this.questionBox = new QuestionBox();
            showNextQuestion();
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
            return currentIndex >= questions.size();
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
            if (!questionBox.isVisible() || isFinished()) return;

            if (keyHandler.aPressed || keyHandler.bPressed) {
                String selected = keyHandler.aPressed ? "A" : "B";
                Question current = questions.get(currentIndex);
                if (selected.equalsIgnoreCase(current.getCorrectAnswer())) {
                    gameState.incrementCorrect();
                    SoundPlayer.playSound("sparkle.wav");
                    if (dialogBox != null && dialogs != null) {
                        Dialog currentDialog = dialogs.get(currentIndex);
                        dialogBox.showdialog(new Dialog("", currentDialog.getRight(), ""));
                    }
                } else {
                    gameState.incrementWrong();
                    SoundPlayer.playSound("bruh.wav");
                    if (dialogBox != null && dialogs != null) {
                        Dialog currentDialog = dialogs.get(currentIndex);
                        dialogBox.showdialog(new Dialog("", "", currentDialog.getWrong()));
                    }
                }

                keyHandler.aPressed = false;
                keyHandler.bPressed = false;

                scoreBox.showScore(gameState.getCorrectAnswers(), gameState.getWrongAnswers());

                currentIndex++;
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
            } else {
                questionBox.hide();
            }
        }
    }


