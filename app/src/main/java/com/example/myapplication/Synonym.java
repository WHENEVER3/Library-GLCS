package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

public class Synonym {
    private String word;
    private String synonym;

    public Synonym(String word, String synonym) {
        this.word = word;
        this.synonym = synonym;
    }

    public String getWord() {
        return word;
    }

    public String getSynonym() {
        return synonym;
    }
}
