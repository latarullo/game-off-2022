package com.mygdx.game.domain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.math.BigInteger;

public class GreaterHealthPotion extends HealthPotion{

    public GreaterHealthPotion(){
        this.name = "Greater Health Potion";
        this.texture = new Texture(Gdx.files.internal("potions/04-health-potion.png"));
        this.healthPower = 100;
        this.cooldown = 10;
        this.price = new BigInteger ("1000");
    }
}
