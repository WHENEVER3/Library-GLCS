package com.example.myapplication;

import android.content.Intent;

import android.os.Bundle;

import android.widget.Button;
import android.widget.Switch;
import androidx.appcompat.app.AppCompatActivity;
public class settings extends AppCompatActivity {
    private Button time5, time8, time10;
    private Switch soundSwitch;
    private Button saveButton, backButton;
    private int selectedTime = 5; // 기본값 5초
    private SettingsManger settingsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        settingsManager = new SettingsManger(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // UI 요소 초기화
        time5 = findViewById(R.id.시간);
        time8 = findViewById(R.id.시간2);
        time10 = findViewById(R.id.시간3);
        soundSwitch = findViewById(R.id.soundSwitch);
        saveButton = findViewById(R.id.saveButton);
        backButton = findViewById(R.id.back2);

        // 기존 설정 값 불러오기
        selectedTime = settingsManager.getQuizTime() / 1000; // 초 단위 변환
        soundSwitch.setChecked(settingsManager.isSoundEnabled());

        // 버튼 클릭 이벤트 - 제한 시간 변경
        time5.setOnClickListener(v -> selectedTime = 5);
        time8.setOnClickListener(v -> selectedTime = 8);
        time10.setOnClickListener(v -> selectedTime = 15);

        // 저장 버튼 클릭 시 설정 저장
        saveButton.setOnClickListener(v -> {
            settingsManager.setQuizTime(selectedTime);
            settingsManager.setSoundEnabled(soundSwitch.isChecked());

            // 저장 후 메인 화면으로 이동
            Intent intent = new Intent(settings.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        // 뒤로 가기 버튼 (back2) 클릭 시 메인 화면 이동
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(settings.this, MainActivity.class);
            startActivity(intent);
        });
    }
}
