package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.elementary_Grade.elementary;
import com.example.myapplication.highSchool.high;
import com.example.myapplication.middle.middle;

public class SubActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subactivity_main);

        // 액션바에서 뒤로가기 버튼 활성화
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // 버튼 ID 배열
        int[] buttonIds = {R.id.btn2, R.id.btn3, R.id.btn4};

        // 버튼이 이동할 액티비티 클래스 배열
        Class<?>[] activities = {elementary.class, middle.class, high.class};

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
        Button backButton = findViewById(R.id.bback);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
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
