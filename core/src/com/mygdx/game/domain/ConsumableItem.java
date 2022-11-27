package com.mygdx.game.domain;

import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.math.BigInteger;

public abstract class ConsumableItem extends Item {

    float healthPower;
    float cooldown;
    BigInteger price;
    HealthConsumableEnum type;

    public float getHealthPower() {
        return healthPower;
    }

    public float getCooldown() {
        return cooldown;
    }

    public BigInteger getPrice() {
        return price;
    }

    @Override
    public Image getImage() {
        Image image = super.getImage();
        image.setUserObject(this);
        return image;
    }

    public HealthConsumableEnum getType() {
        return type;
    }
}
