package pro;

import pro.view.Menu;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(Menu::new);
    }
}
