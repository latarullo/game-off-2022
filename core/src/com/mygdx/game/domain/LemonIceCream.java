package com.mygdx.game.domain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class LemonIceCream extends ConsumableItem {

    private static LemonIceCream instance;

    public LemonIceCream() {
        this.name = "Lemon Ice Cream";
        this.texture = new Texture(Gdx.files.internal("resources/Consumables/lemon-ice-cream.png"));
        this.healthPower = 50;
        this.cooldown = 10;
        this.price = GameConstants.LEMON_ICE_CREAM_COST;
        this.type = HealthConsumableEnum.LEMON_ICE_CREAM;
    }

    public static LemonIceCream getInstance() {
        if (instance == null) {
            instance = new LemonIceCream();
        }
        return instance;
    }
}
