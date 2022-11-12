package com.mygdx.game.domain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.math.BigInteger;

public class SmallHealthPotion extends HealthPotion{

    public SmallHealthPotion(){
        this.name = "Small Health Potion";
        this.texture = new Texture(Gdx.files.internal("potions/02-health-potion.png"));
        this.healthPower = 5;
        this.cooldown = 5;
        this.price = BigInteger.TEN;
        this.type = HealthPotionEnum.SmallHealthPotion;
    }
}
