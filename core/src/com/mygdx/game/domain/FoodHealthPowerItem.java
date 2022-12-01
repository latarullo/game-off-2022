package com.mygdx.game.domain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class FoodHealthPowerItem extends UpgradeableItem {
    private static FoodHealthPowerItem instance = new FoodHealthPowerItem();


    public static FoodHealthPowerItem getInstance() {
        return instance;
    }

    private FoodHealthPowerItem() {
        this.name = "Food Health Power";
        this.texture = new Texture(Gdx.files.internal("resources/Icons/food-health-power.png"));
        this.price = GameConstants.CONSUMABLE_HEALTH_COST;
        this.type = UpgradeableEnum.CONSUMABLE_POWER;
        this.bonusMultiplier = 25;
    }

    public long getBonusHealthPower() {
        return getBonusValue();
    }
}
