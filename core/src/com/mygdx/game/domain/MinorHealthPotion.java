package com.mygdx.game.domain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.math.BigInteger;

public class MinorHealthPotion extends HealthPotion{

    public MinorHealthPotion(){
        this.name = "Minor Health Potion";
        this.texture = new Texture(Gdx.files.internal("potions/01-health-potion.png"));
        this.healthPower = 1;
        this.cooldown = 1;
        this.price = BigInteger.ONE;
    }
}
