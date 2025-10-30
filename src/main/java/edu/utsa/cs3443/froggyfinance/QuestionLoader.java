package edu.utsa.cs3443.froggyfinance;

import java.io.*;
import java.util.*;

/**
 * Utility class responsible for loading quiz questions from a text file resource.
 *
 * @author erikamey
 */
public class QuestionLoader {
    public static List<Question> loadQuestions(String filepath, int level){
        List<Question> questions = new ArrayList<>();

        InputStream input = QuestionLoader.class.getResourceAsStream("/edu/utsa/cs3443/froggyfinance/Credit & Debit Questions.txt");
        if (input == null) {
            System.out.println("File not found!");
            return questions;
        }
        try (BufferedReader br = new BufferedReader(new InputStreamReader(input))){
            String line;
            String q = null, a = null, b = null, answer = null;
            boolean inLevel = false;

            while ((line = br.readLine ()) != null){
                line = line.trim();

                if (line.toLowerCase().startsWith("# level")) {

                    String[] parts = line.split(" ");
                    if (parts.length >= 3) {
                        int currentLevel = Integer.parseInt(parts[2]);
                        inLevel = (currentLevel == level);
                    }
                    continue;
                }

                if (!inLevel) {
                    continue;
                }
                if (line.startsWith("Q:")){
                    q = line.substring(2).trim();
                } else if (line.startsWith("A:")){
                    a = line.substring(2).trim();
                } else if (line.startsWith("B:")){
                    b = line.substring(2).trim();
                }else if  (line.startsWith("ANSWER")){
                    answer = line.substring(7).trim().toUpperCase();

                    if ( q != null && a != null && b != null && answer != null){
                        questions.add(new Question(q, a, b, answer));
                        q = a = b = answer = null;
                    }
                }
            }
        } catch (IOException e){
            System.out.println("Failed to load questions:" + e.getMessage());
        }
        return questions;
    }
}
