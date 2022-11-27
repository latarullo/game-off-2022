package com.mygdx.game;

import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.mygdx.game.domain.GameConstants;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
    public static void main(String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle(GameConstants.TITLE);
        config.setWindowedMode(GameConstants.WIDTH, GameConstants.HEIGHT);
        config.useVsync(true);
        config.setForegroundFPS(60);
        config.setResizable(false);

        new Lwjgl3Application(GameOff2022.getInstance(), config);
    }
}
