package edu.utsa.cs3443.froggyfinance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 *
 * @author erikamey
 * @since 10/30/25
 */
public class DialogManager {
    private List<Dialog> openingDialogs;
    private List<Dialog> feedbackDialogs;
    private int openIndex = 0;


    public DialogManager(){
        openingDialogs = new ArrayList<>();
        feedbackDialogs = new ArrayList<>();
    }
    public void loadDialogsForLevel(String filePath, int level){
        List<Dialog> allDialogs = DialogLoader.loadDialog(filePath, level);

        openingDialogs.clear();
        feedbackDialogs.clear();

        for (Dialog d : allDialogs){
            openingDialogs.add(new Dialog(d.getOpen(), "",""));

            feedbackDialogs.add(new Dialog("", d.getRight(), d.getWrong()));
        }
        openIndex = 0;
    }

    public Dialog getNextOpeningDialog() {
        if (openIndex < openingDialogs.size()) {
            Dialog next = openingDialogs.get(openIndex);
            openIndex++;
            return next;
        }
        return null;
    }
    public Dialog getFeedbackDialog(int questionIndex){
        if (questionIndex >= 0 && questionIndex < feedbackDialogs.size()) {
            return feedbackDialogs.get(questionIndex);
        }
        return null;
    }
    public void resetOpenDialogs(){
       openIndex = 0;
    }
}
