package com.mygdx.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;

public class Hover extends Table implements Disposable {
    private Texture hoverTexture = new Texture(Gdx.files.internal("resources/ShopScreen/hover.png"));

    public Hover(){
        this.setBackground(new TextureRegionDrawable(hoverTexture));
    }

    @Override
    public void dispose() {
        hoverTexture.dispose();
    }
}
