package edu.utsa.cs3443.froggyfinance;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class responsible for loading dialog from a text file resource.
 *
 * @author erikamey
 * @since 10/30/25
 */
public class DialogLoader {
    public static List<Dialog> loadDialog(String filepath, int level){
        List<Dialog> dialogs = new ArrayList<>();

        InputStream input = DialogLoader.class.getResourceAsStream("/edu/utsa/cs3443/froggyfinance/Dialog.txt");
        if (input == null) {
            System.out.println("File not found!");
            return dialogs;
        }
        try (BufferedReader br = new BufferedReader(new InputStreamReader(input))){
            String line;
            String open = null, right = null, wrong = null;
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
                if (line.startsWith("Open:")){
                    open = line.substring(5).trim();
                } else if (line.startsWith("Right:")){
                    right = line.substring(6).trim();
                } else if (line.startsWith("Wrong:")){
                    wrong = line.substring(6).trim();

                    if ( open != null && right != null && wrong != null){
                        dialogs.add(new Dialog(open, right, wrong));
                        open = right = wrong = null;
                    }
                }
            }
        } catch (IOException e){
            System.out.println("Failed to load dialogs:" + e.getMessage());
        }
        return dialogs;
    }
}

