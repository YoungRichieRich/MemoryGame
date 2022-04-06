package pro.view;

import pro.model.HighScoresModel;

import javax.swing.*;
import java.awt.*;

public class HighScores extends JFrame {

    public HighScores() {
        super("High Scores");
        setPreferredSize(new Dimension(500, 400));

        JPanel panel = new JPanel(new BorderLayout());

        HighScoresModel model = new HighScoresModel();

        JList<String> jList = new JList<>(model);

        JScrollPane scrollPane = new JScrollPane(jList);

        panel.add(scrollPane, BorderLayout.CENTER);

        getContentPane().add(panel);
        pack();
        setVisible(true);
    }
}
