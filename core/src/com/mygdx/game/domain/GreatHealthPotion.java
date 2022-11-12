package com.mygdx.game.domain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.math.BigInteger;

public class GreatHealthPotion extends HealthPotion{

    public GreatHealthPotion(){
        this.name = "Great Health Potion";
        this.texture = new Texture(Gdx.files.internal("potions/03-health-potion.png"));
        this.healthPower = 50;
        this.cooldown = 10;
        this.price = new BigInteger ("100");
        this.type = HealthPotionEnum.GreatHealthPotion;
    }
}
