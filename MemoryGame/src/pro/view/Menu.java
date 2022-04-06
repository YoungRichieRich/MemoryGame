package pro.view;

import pro.model.Database;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Menu extends JFrame {

    public Menu() {
        super("Memory game");

        setPreferredSize(new Dimension(800, 600));
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));


        JLabel title = new JLabel("Memory the game");

        title.setFont(new Font("Verdana", Font.BOLD, 40));

        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton newGame = new JButton("New Game");
        newGame.setAlignmentX(Component.CENTER_ALIGNMENT);
        newGame.setMinimumSize(new Dimension(140, 50));
        newGame.setMaximumSize(new Dimension(140, 50));

        JButton highScores = new JButton("High scores");
        highScores.setAlignmentX(Component.CENTER_ALIGNMENT);
        highScores.setMinimumSize(new Dimension(140, 50));
        highScores.setMaximumSize(new Dimension(140, 50));

        JButton exit = new JButton("Exit");
        exit.setAlignmentX(Component.CENTER_ALIGNMENT);
        exit.setMinimumSize(new Dimension(140, 50));
        exit.setMaximumSize(new Dimension(140, 50));


        newGame.addActionListener(e -> {

            String response = JOptionPane.showInputDialog((Component)e.getSource(), "Type grid size: ",
                    "Grid size", JOptionPane.QUESTION_MESSAGE);

            try {
                int size = Integer.parseInt(response);
                if (size <= 0) {
                    throw new Exception("Number must be greater than 0!");
                }
                if (size % 2 == 1) {
                    throw new Exception("Number must be even!");
                }

                new Game(size);
                dispose();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog((Component) e.getSource(),
                        "Invalid number format",
                        "Invalid number", JOptionPane.ERROR_MESSAGE);
            } catch (Exception exception) {
                JOptionPane.showMessageDialog((Component) e.getSource(),
                        exception.getMessage(),
                        "Invalid number", JOptionPane.ERROR_MESSAGE);
            }
        });

        highScores.addActionListener(e -> {
            new HighScores();
        });

        exit.addActionListener(e -> System.exit(0));
        //

        panel.add(Box.createRigidArea(new Dimension(5, 40)));
        panel.add(title);
        panel.add(Box.createRigidArea(new Dimension(5, 30)));
        panel.add(newGame);
        panel.add(Box.createRigidArea(new Dimension(5, 5)));
        panel.add(highScores);
        panel.add(Box.createRigidArea(new Dimension(5, 5)));
        panel.add(exit);

        getContentPane().add(panel);

        pack();
        setVisible(true);

        try {
            Database.checkIfFileExists();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Nie mozna utworzyc pustego pliku z rankingiem. Powod: " + e.getMessage(),
                    "Blad",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

}
