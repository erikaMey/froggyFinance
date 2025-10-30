package edu.utsa.cs3443.froggyfinance;

/**
 * Represents a multiple-choice question with two answer options (A and B),
 * and the correct answer.
 *
 * @author erikamey
 */
public class Question {
    private String questionText;
    private String optionA;
    private String optionB;
    private String correctAnswer;

    /**
     * Constructs a new question
     *
     * @param questionText the text of the question
     * @param optionA the test of optionA
     * @param optionB the text of optionB
     * @param correctAnswer the correct answer
     */
    public Question(String questionText, String optionA, String optionB, String correctAnswer){
       this.questionText = questionText;
       this.optionA = optionA;
       this.optionB = optionB;
       this.correctAnswer = correctAnswer;
    }

    /**
     * gets the question
     *
     * @return the question
     */
    public String getQuestionText(){
        return questionText;
    }

    /**
     * gets optionA
     *
     * @return optionA
     */
    public String getOptionA(){
        return optionA;
    }

    /**
     * gets optionB
     *
     * @return optionB
     */
    public String getOptionB(){
        return optionB;
    }

    /**
     * gets answer
     *
     * @return the correct answer
     */
    public String getCorrectAnswer(){
        return correctAnswer;
    }
}
