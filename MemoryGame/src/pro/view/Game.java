package pro.view;

import pro.controller.CardListener;
import pro.model.Card;
import pro.model.Database;
import pro.model.Score;
import pro.view.HighScores;
import pro.view.Menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game extends JFrame {

    private static int secondsElapsed;
    Timer timeCounter;
    JLabel timer;
    int gridSize;
    int additionalPoints = 0;

    public Game(int gridSize) {
        super("Memory game");

        this.gridSize = gridSize;
        secondsElapsed = 0;
        setPreferredSize(new Dimension(800, 600));

        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel cardsPanel = new JPanel(new GridLayout(gridSize, gridSize, 5, 5));
        JPanel timerPanel = new JPanel(new BorderLayout());

        mainPanel.add(cardsPanel, BorderLayout.CENTER);
        mainPanel.add(timerPanel, BorderLayout.PAGE_END);


        List<Card> cards = new ArrayList<>();
        for(int i = 1; i <= gridSize*gridSize/2; i++) {
           cards.add(new Card(i));
           cards.add(new Card(i));
        }

        Collections.shuffle(cards);

        ActionListener cardActionListener = new CardListener(this, gridSize*gridSize);

        for(Card card : cards) {
            cardsPanel.add(card);
            card.addActionListener(cardActionListener);
        }

        timer = new JLabel("Timer 00:00", SwingConstants.CENTER);
        timer.setFont(new Font("Verdana", Font.BOLD, 25));
        timer.setAlignmentX(Component.CENTER_ALIGNMENT);
        timerPanel.add(timer, BorderLayout.CENTER);

        add(mainPanel);
        pack();
        setVisible(true);

        timeCounter = new Timer(1000, e -> {
            timer.setText(formatSecondsToTimer(secondsElapsed));
            secondsElapsed += 1;
        });

        this.getRootPane().registerKeyboardAction( e -> {
            timeCounter.stop();
            dispose();
            new pro.view.Menu();
        }, KeyStroke.getKeyStroke( KeyEvent.VK_Q, InputEvent.SHIFT_MASK | InputEvent.CTRL_MASK), JComponent.WHEN_IN_FOCUSED_WINDOW );

        timeCounter.setRepeats(true);
        timeCounter.start();
    }

    private String formatSecondsToTimer(int secs){
        int minutes = secs / 60;
        int seconds = secs % 60;

        String minutesStr;
        if(minutes < 10)
            minutesStr = "0"+minutes;
        else
            minutesStr = ""+minutes;

        String secondsStr;
        if(seconds < 10)
            secondsStr = "0"+seconds;
        else
            secondsStr = ""+seconds;

        return minutesStr+":"+secondsStr;
    }

    public void addAdditionalPoints() {
        additionalPoints += 5;
    }

    public double getScore() {
        //123.456789
        //124

        //123.4567 * 100 = 12345.67
        //12346 / 100 => 123.46
        return (Math.round(gridSize*gridSize / (double)secondsElapsed * 100 * 100) / 100d) + additionalPoints;
    }

    public void finishGame() {
        String nick = JOptionPane.showInputDialog(this,
                "Gratulacje!\n " +
                        "Twoj czas to: " + formatSecondsToTimer(secondsElapsed) + " sekund\n" +
                        "Twoj wynik to: " + getScore() + "\n" +
                        "Podaj swoj nick:",
                    "Gratulacje!", JOptionPane.INFORMATION_MESSAGE);

        Score score = new Score(nick, secondsElapsed, getScore(), gridSize);

        try {
            List<Score> scores = Database.load();
            scores.add(score);
            Database.save(scores);
            System.out.println(scores);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "Blad podczas zapisu/odczytu rankignu: "+e.getMessage(),
                    "Błąd", JOptionPane.ERROR_MESSAGE);
        }
        catch (ClassNotFoundException e) { }

        dispose();
        new Menu();
        new HighScores();
    }

    public Timer getTimeCounter() {
        return timeCounter;
    }
}
