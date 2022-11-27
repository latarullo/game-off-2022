package com.mygdx.game.domain;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Disposable;

public abstract class Item implements Disposable {
    String name;
    Texture texture;
    private Image image;

    public String getName() {
        return name;
    }

    public Texture getTexture() {
        return texture;
    }

    public Image getImage(){
        if (image == null){
            if (texture == null){
                throw new RuntimeException("texture must be set to get Image");
            }
            image = new Image(texture);
        }
        return image;
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
