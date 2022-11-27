package com.mygdx.game.domain;

import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.math.BigInteger;

public abstract class UnlockableItem extends Item {
    BigInteger price;
    UnlockableEnum type;
    boolean unlocked = false;

    public BigInteger getPrice() {
        return price;
    }

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

    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
    }
}
