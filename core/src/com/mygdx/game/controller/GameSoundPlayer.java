package com.mygdx.game.controller;

import com.badlogic.gdx.audio.Sound;

public abstract class GameSoundPlayer {
    public static void playMusic(Sound music) {
        if (GameSettings.getInstance().isMusicEnabled()) {
            music.loop(GameSettings.getInstance().getMusicVolume());
        }
    }

    public static void playSound(Sound sound) {
        if (GameSettings.getInstance().isSoundEnabled()) {
            sound.play(GameSettings.getInstance().getSoundVolume());
        }
    }

    public static void stop (Sound sound){
        sound.stop();
    }
}
