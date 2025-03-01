package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;

public class SettingsManger {
    private static final String PREFS_NAME = "Settings";
    private static final String KEY_QUIZ_TIME = "quiz_time";
    private static final String KEY_SOUND_ENABLED = "sound_enabled";

    private SharedPreferences preferences;

    public SettingsManger(Context context) {
        preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    // 제한 시간 설정 값 저장
    public void setQuizTime(int time) {
        preferences.edit().putInt(KEY_QUIZ_TIME, time).apply();
    }

    // 제한 시간 가져오기 (기본값 5초)
    public int getQuizTime() {
        return preferences.getInt(KEY_QUIZ_TIME, 5) * 1000; // 초 단위로 변환
    }

    // 소리 설정 저장
    public void setSoundEnabled(boolean enabled) {
        preferences.edit().putBoolean(KEY_SOUND_ENABLED, enabled).apply();
    }

    // 소리 설정 가져오기 (기본값 true)
    public boolean isSoundEnabled() {
        return preferences.getBoolean(KEY_SOUND_ENABLED, true);
    }
}
