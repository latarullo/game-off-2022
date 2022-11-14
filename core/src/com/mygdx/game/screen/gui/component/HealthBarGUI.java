package com.mygdx.game.screen.gui.component;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

public class HealthBarGUI implements Disposable {
    private final Texture greenBar;
    private final Texture redBar;
    private final Texture blackBar;

    public HealthBarGUI(){
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

    public void draw(Batch batch, TextureRegion currentAnimation, float x, float y, float height, float currentLife, float maxLife){
        int animationRegionWidth = currentAnimation.getRegionWidth();
        batch.draw(this.blackBar, x-1, y + currentAnimation.getRegionHeight() + 30-1, animationRegionWidth+2, height+2);
        batch.draw(this.greenBar, x, y + currentAnimation.getRegionHeight() + 30, animationRegionWidth, height);
        batch.draw(this.redBar, x, y + currentAnimation.getRegionHeight() + 30, (1 - currentLife/maxLife) * animationRegionWidth, height);
    }

    @Override
    public void dispose() {
        blackBar.dispose();
        greenBar.dispose();
        redBar.dispose();
    }
}
