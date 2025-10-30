package edu.utsa.cs3443.froggyfinance;

/**
 * Tracks the current game state by keeping count of correct and incorrect answers.
 *
 * @author erikamey
 */
public class GameState {
    private int correctAnswers = 0;
    private int wrongAnswers = 0;

    /**
     * Increments the number of correct answers by one.
     */
    public void incrementCorrect() {
        correctAnswers++;
    }

    /**
     * Increments the number of wrong answers by one.
     */
    public void incrementWrong() {
        wrongAnswers++;
    }

    /**
     * Returns the total number of correct answers.
     *
     * @return the number of correct answers
     */
    public int getCorrectAnswers() {
        return correctAnswers;
    }

    /**
     * Returns the total number of wrong answers.
     * @return thw number of wrong answers
     */
    public int getWrongAnswers() {
        return wrongAnswers;
    }

}


