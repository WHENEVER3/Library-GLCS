package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuizActivitySynonym extends AppCompatActivity {
    private TextView txtQuestion;
    private Button btnOption1, btnOption2, btnOption3, btnOption4;
    private List<Synonym> quizWords;
    private int currentIndex = 0;
    private int score = 0;
    private Synonym currentWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quz);

        txtQuestion = findViewById(R.id.txtQuestion);
        btnOption1 = findViewById(R.id.bo1);
        btnOption2 = findViewById(R.id.bo2);
        btnOption3 = findViewById(R.id.bo3);
        btnOption4 = findViewById(R.id.bo4);

        quizWords = SynonymList.getShuffledWords();
        loadNextQuestion();
    }

    private void loadNextQuestion() {
        if (currentIndex >= quizWords.size()) {
            Intent intent = new Intent(QuizActivitySynonym.this, ResultActivity.class);
            intent.putExtra("SCORE", score);
            startActivity(intent);
            finish();
            return;
        }

        currentWord = quizWords.get(currentIndex);
        txtQuestion.setText("단어: " + currentWord.getWord());

        List<String> options = new ArrayList<>();
        options.add(currentWord.getSynonym());

        while (options.size() < 4) {
            String randomSynonym = SynonymList.getShuffledWords().get((int) (Math.random() * quizWords.size())).getSynonym();
            if (!options.contains(randomSynonym)) {
                options.add(randomSynonym);
            }
        }

        Collections.shuffle(options);

        btnOption1.setText(options.get(0));
        btnOption2.setText(options.get(1));
        btnOption3.setText(options.get(2));
        btnOption4.setText(options.get(3));

        setButtonClickListener(btnOption1, options.get(0));
        setButtonClickListener(btnOption2, options.get(1));
        setButtonClickListener(btnOption3, options.get(2));
        setButtonClickListener(btnOption4, options.get(3));
    }

    private void setButtonClickListener(Button button, String answer) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer.equals(currentWord.getSynonym())) {
                    score++;
                    Toast.makeText(QuizActivitySynonym.this, "정답!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(QuizActivitySynonym.this, "오답!", Toast.LENGTH_SHORT).show();
                }
                currentIndex++;
                loadNextQuestion();
            }
        });
    }
}
