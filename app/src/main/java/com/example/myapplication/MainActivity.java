package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.highSchool.high;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // XML 레이아웃 설정 추가

        // 버튼 ID 배열
        int[] buttonIds = {R.id.btn_test, R.id.btn_settings};

        // 버튼이 이동할 액티비티 클래스 배열
        Class<?>[] activities = {
                SubActivity.class, settings.class
        };

        // 버튼 클릭 리스너 설정 (for문 사용)
        for (int i = 0; i < buttonIds.length; i++) {
            Button button = findViewById(buttonIds[i]);
            final Class<?> activity = activities[i]; // final 키워드 사용하여 익명 클래스에서 참조 가능하게 처리

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), activity);
                    startActivity(intent);
                }
            });
            }
        }
    }