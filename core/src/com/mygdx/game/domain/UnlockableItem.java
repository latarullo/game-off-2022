package com.mygdx.game.domain;

import com.badlogic.gdx.scenes.scene2d.ui.Image;

public abstract class UnlockableItem extends Item {
    UnlockableEnum type;
    boolean unlocked = false;

    @Override
    public Image getImage() {
        Image image = super.getImage();
        image.setUserObject(this);
        return image;
    }

    public UnlockableEnum getType() {
        return type;
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public void unlock() {
        this.unlocked = true;
    }

    public void reset() {
        unlocked = false;
    }
}
