package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WordList extends AppCompatActivity {
    private static List<Word> wordList = new ArrayList<>();

    static {
        wordList.add(new Word("Abundant", "풍부한"));
        wordList.add(new Word("Benevolent", "자비로운"));
        wordList.add(new Word("Contradict", "모순되다"));
        wordList.add(new Word("Diligent", "부지런한"));
        wordList.add(new Word("Eloquent", "유창한"));
        wordList.add(new Word("Fragile", "깨지기 쉬운"));
        wordList.add(new Word("Hostile", "적대적인"));
        wordList.add(new Word("Immense", "거대한"));
        wordList.add(new Word("Jubilant", "기쁨에 찬"));
        wordList.add(new Word("Meticulous", "꼼꼼한"));
    }

    public static List<Word> getShuffledWords() {
        List<Word> shuffledList = new ArrayList<>(wordList);
        Collections.shuffle(shuffledList);
        return shuffledList;
    }
}
