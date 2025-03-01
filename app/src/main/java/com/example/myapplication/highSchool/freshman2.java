package com.example.myapplication.highSchool;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.QuizActivitySynonym;
import com.example.myapplication.R;
import com.example.myapplication.mean;

public class freshman2 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.freshman2);

        // 액션바에서 뒤로가기 버튼 활성화
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // 버튼 ID 배열
        int[] buttonIds = {R.id.btn20, R.id.btn21};

        // 버튼이 이동할 액티비티 클래스 배열
        Class<?>[] activities = {
                mean.class, QuizActivitySynonym.class
        };

        // 버튼 클릭 리스너 설정 (for 문 사용)
        for (int i = 0; i < buttonIds.length; i++) {
            Button button = findViewById(buttonIds[i]);
            final Class<?> activity = activities[i]; // final 키워드로 익명 클래스에서 사용 가능하게 처리

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), activity);
                    startActivity(intent);
                }
            });
        }

        // 뒤로가기 버튼 (btn11) 추가 - SubActivity로 이동
        Button backButton = findViewById(R.id.btn22);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), high.class);
                startActivity(intent);
            }
        });
    }

    // 기본 하드웨어 뒤로가기 버튼 기능 추가
    @Override
    public void onBackPressed() {
        super.onBackPressed(); // 기본 동작 (현재 액티비티 종료)
    }

    // 액션바의 뒤로가기 버튼 클릭 이벤트 처리
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // 현재 액티비티 종료
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
