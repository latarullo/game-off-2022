package com.mygdx.game.domain;

import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class UpgradeableItem extends Item {
    UpgradeableEnum type;
    private int upgradeCount;
    float bonusMultiplier = 1;

    @Override
    public Image getImage() {
        Image image = super.getImage();
        image.setUserObject(this);
        return image;
    }

    public UpgradeableEnum getType() {
        return type;
    }

    public int getUpgradeCount() {
        return upgradeCount;
    }

    public void upgrade(){
        upgradeCount++;
    }

    float getBonusMultiplier() {
        return bonusMultiplier;
    }

    long getBonusValue(){
        return (long) (upgradeCount * bonusMultiplier);
    }
}