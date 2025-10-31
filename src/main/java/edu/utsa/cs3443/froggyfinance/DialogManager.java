package edu.utsa.cs3443.froggyfinance;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author erikamey
 * @since 10/30/25
 */
public class DialogManager {
    private Map<Integer, List<Dialog>> dialogSByLevel;

    public DialogManager(){
        dialogSByLevel = new HashMap<>();
    }

    public void loadDialogsForLevel(String filePath, int level){
      List<Dialog> dialogs = DialogLoader.loadDialog(filePath, level);
      dialogSByLevel.put(level, dialogs);
    }

    public List<Dialog> getDialogsForLevel(int level){
        return dialogSByLevel.get(level);
    }

    public Dialog getDialog(int level, int index){
        List<Dialog> dialogs = dialogSByLevel.get(level);
        if (dialogs !=null && index >= 0 && index < dialogs.size()){
            return dialogs.get(index);
        }
        return null;
    }
}
