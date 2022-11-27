package com.mygdx.game.screen.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.domain.GameData;
import com.mygdx.game.screen.actor.Damageable;
import com.mygdx.game.screen.actor.Wizard;

public class WizardHealthBarGUI extends Actor {
    private Texture greenBar;
    private Texture redBar;
    private Texture blackBar;

    private Damageable damageable;

    public WizardHealthBarGUI(Damageable damageable) {
        this.damageable = damageable;

        Pixmap pixmapGreen = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmapGreen.setColor(0, 1, 0, 1);
        pixmapGreen.fill();
        greenBar = new Texture(pixmapGreen);

        Pixmap pixmapRed = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmapRed.setColor(1, 0, 0, 1);
        pixmapRed.fill();
        redBar = new Texture(pixmapRed);

        Pixmap pixmapBlack = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmapBlack.setColor(1, 1, 1, 1);
        pixmapBlack.fill();
        blackBar = new Texture(pixmapBlack);

        pixmapGreen.dispose();
        pixmapRed.dispose();
        pixmapBlack.dispose();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        batch.draw(blackBar, getX()-1, getY()-1, getWidth()+2, getHeight()+2);
        batch.draw(greenBar, getX(), getY(), getWidth(), getHeight());
        batch.draw(redBar, getX(), getY(), (1 - damageable.getCurrentLife() / damageable.getMaxLife()) * getWidth(), getHeight());

    }

    public Damageable getDamageable() {
        return damageable;
    }

    public void setDamageable(Damageable damageable) {
        this.damageable = damageable;
    }
}