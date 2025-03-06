package com.example.myapplication.highSchool;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SynonymList extends AppCompatActivity {
    private static List<Synonym> synonymList = new ArrayList<>();

    static {
        synonymList.add(new Synonym("Happy", "Joyful"));
        synonymList.add(new Synonym("Fast", "Quick"));
        synonymList.add(new Synonym("Smart", "Intelligent"));
        synonymList.add(new Synonym("Big", "Large"));
        synonymList.add(new Synonym("Small", "Tiny"));
        synonymList.add(new Synonym("Strong", "Powerful"));
        synonymList.add(new Synonym("Weak", "Feeble"));
        synonymList.add(new Synonym("Hard", "Difficult"));
        synonymList.add(new Synonym("Soft", "Gentle"));
        synonymList.add(new Synonym("Angry", "Furious"));
    }

    public static List<Synonym> getShuffledWords() {
        List<Synonym> shuffledList = new ArrayList<>(synonymList);
        Collections.shuffle(shuffledList);
        return shuffledList;
    }
}
