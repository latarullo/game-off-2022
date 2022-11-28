package com.mygdx.game.domain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.math.BigInteger;

public class FoodHealthPowerItem extends UpgradeableItem {
    private static FoodHealthPowerItem instance = new FoodHealthPowerItem();


    public static FoodHealthPowerItem getInstance() {
        return instance;
    }

    private FoodHealthPowerItem() {
        this.name = "Food Health Power";
        this.texture = new Texture(Gdx.files.internal("resources/food-power.png"));
        this.price = BigInteger.ONE;//new BigInteger("10000");
        this.type = UpgradeableEnum.CONSUMABLE_POWER;
        this.bonusMultiplier = 25;
    }

    public long getBonusHealthPower() {
        return getBonusValue();
    }
}
