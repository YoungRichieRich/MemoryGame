package pro.model;

import pro.model.Database;
import pro.model.Score;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HighScoresModel extends DefaultListModel<String> {
    private List<Score> scores = new ArrayList<>();

    public HighScoresModel(){
        try {
            scores = Database.load();
            scores.sort((score1, score2) -> Double.compare(score2.getPoints(), score1.getPoints()));

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "Blad podczas odczytu rankignu: "+e.getMessage(),
                    "Błąd", JOptionPane.ERROR_MESSAGE);
        }
        catch (ClassNotFoundException ignored) { }
    }

    @Override
    public int getSize() {
        return scores.size();
    }

    @Override
    public String getElementAt(int index) {
        return scores.get(index).toString();
    }
}
