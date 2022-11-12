package com.mygdx.game.domain;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.math.BigInteger;

public abstract class HealthPotion extends Potion {

    float healthPower;
    float cooldown;
    BigInteger price;
    HealthPotionEnum type;

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

    public HealthPotionEnum getType() {
        return type;
    }
}
