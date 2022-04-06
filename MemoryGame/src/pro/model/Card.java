package pro.model;

import javax.swing.*;
import java.awt.*;

public class Card extends JButton {

    private int id;
    private boolean isUncovered;
    private boolean blocked;
    private ImageIcon cardImg;
    private ImageIcon reverseImg;

    public Card(int id) {
        super("click me");
        this.id = id;
        this.isUncovered = false;
        cardImg = new ImageIcon("cars/car"+id+".PNG");
        reverseImg = new ImageIcon("cars/rewers.PNG");
    }

    public void uncoverCard() {
        isUncovered = true;
        repaint();
    }

    public void coverCard() {
        isUncovered = false;
        repaint();
    }

    public int getId() {
        return id;
    }

    public boolean isUncovered() {
        return isUncovered;
    }

    public void setBlocked() {
        this.blocked = true;
        setEnabled(false);
        repaint();
    }

    public boolean isBlocked() {
        return blocked;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        if(isUncovered())
            g.drawImage(cardImg.getImage(), 0, 0, getWidth(), getHeight(), null);
        else
            g.drawImage(reverseImg.getImage(), 0, 0, getWidth(), getHeight(), null);
    }
}
