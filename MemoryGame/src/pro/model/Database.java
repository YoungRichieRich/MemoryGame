package pro.model;

import pro.model.Score;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private static final String filename = "wyniki.txt";

    public static void checkIfFileExists() throws IOException {
        File file = new File(filename);

        if(!file.exists()) {
            save(new ArrayList<>());
        }
    }

    public static void save(List<Score> scores) throws IOException {
        FileOutputStream fos = new FileOutputStream(filename);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(scores);
        oos.flush();
        oos.close();
    }

    public static List<Score> load() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(filename);
        ObjectInputStream ois = new ObjectInputStream(fis);
        List<Score> scores = (List<Score>) ois.readObject();
        return scores;
    }
}
