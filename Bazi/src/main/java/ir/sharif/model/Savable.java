package ir.sharif.model;

import java.nio.file.Path;
import java.util.ArrayList;

public interface Savable {
    void save(Path path);
    Savable load(Path path);

    static ArrayList<Savable> loadSavalbes(Path path) {
        return null;
    }

    static void saveSavables(ArrayList<Savable> savables, Path path) {
    }
}
