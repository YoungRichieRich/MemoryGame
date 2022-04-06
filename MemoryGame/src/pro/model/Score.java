package pro.model;

import java.io.Serializable;


public class Score implements Serializable {

    private String nick;
    private int time;
    private double points;
    private int gridSize;

    public Score(String nick, int time, double points, int gridSize) {
        this.nick = nick;
        this.time = time;
        this.points = points;
        this.gridSize = gridSize;
    }

    public double getPoints() {
        return points;
    }

    @Override
    public String toString() {
        return nick + " | czas w sekundach: "+time+" | punkty: "+ points + " | rozmiar siatki: "+gridSize+"x"+gridSize;
    }
}
