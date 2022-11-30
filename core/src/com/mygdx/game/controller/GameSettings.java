package com.mygdx.game.controller;

public class GameSettings {
    private static GameSettings instance = new GameSettings();

    public static GameSettings getInstance() {
        return instance;
    }

    private GameSettings() {
    }

    private boolean soundEnabled = true;
    private boolean musicEnabled = true;
    private float soundVolume = 0.5f;
    private float musicVolume = 0.5f;
    private boolean achievementsNotificationEnabled = true;
    private int popUpPosition = 0;

    public boolean isSoundEnabled() {
        return soundEnabled;
    }

    public void setSoundEnabled(boolean soundEnabled) {
        this.soundEnabled = soundEnabled;
    }

    public boolean isMusicEnabled() {
        return musicEnabled;
    }

    public void setMusicEnabled(boolean musicEnabled) {
        this.musicEnabled = musicEnabled;
    }

    public float getSoundVolume() {
        return soundVolume;
    }

    public void setSoundVolume(float soundVolume) {
        this.soundVolume = soundVolume;
    }

    public float getMusicVolume() {
        return musicVolume;
    }

    public void setMusicVolume(float musicVolume) {
        this.musicVolume = musicVolume;
    }

    public boolean isAchievementsNotificationEnabled() {
        return achievementsNotificationEnabled;
    }

    public void setAchievementsNotificationEnabled(boolean achievementsNotificationEnabled) {
        this.achievementsNotificationEnabled = achievementsNotificationEnabled;
    }

    public int getPopUpPosition() {
        return popUpPosition;
    }

    public void setPopUpPosition(int popUpPosition) {
        this.popUpPosition = popUpPosition;
    }
}
