package com.mygdx.game.domain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.math.BigInteger;

public class FoodCooldownItem extends UpgradeableItem {
    private static FoodCooldownItem instance = new FoodCooldownItem();

    public static FoodCooldownItem getInstance() {
        return instance;
    }

    private FoodCooldownItem() {
        this.name = "Food Cooldown";
        this.texture = new Texture(Gdx.files.internal("resources/Icons/food-cooldown.png"));
        this.price = BigInteger.ONE;//new BigInteger("10000");
        this.type = UpgradeableEnum.CONSUMABLE_COOLDOWN;
    }
}
