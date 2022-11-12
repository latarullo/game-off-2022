package com.mygdx.game.screen.actor;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

public class HealthBar {

    static HealthBar instance;

    private Pixmap pixmapGreen;
    private Pixmap pixmapRed;
    private Texture greenBar;
    private Texture redBar;

    private HealthBar(){
        pixmapGreen = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmapGreen.setColor(0, 1, 0, 1);
        pixmapGreen.fill();
        greenBar = new Texture(pixmapGreen);

        pixmapRed = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmapRed.setColor(1, 0, 0, 1);
        pixmapRed.fill();
        redBar = new Texture(pixmapRed);
    }

    public Texture getGreenBar() {
        return greenBar;
    }

    public Texture getRedBar() {
        return redBar;
    }

    public static HealthBar getInstance(){
        if (instance == null){
            instance = new HealthBar();
        }
        return instance;
    }
}
