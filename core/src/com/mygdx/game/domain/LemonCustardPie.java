package com.mygdx.game.domain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class LemonCustardPie extends ConsumableItem {

    private static LemonCustardPie instance;

    public LemonCustardPie() {
        this.name = "Lemon Custard Pie";
        this.texture = new Texture(Gdx.files.internal("resources/Consumables/lemon-custard-pie.png"));
        this.healthPower = 100;
        this.cooldown = 10;
        this.price = GameConstants.LEMON_CUSTARD_PIE_COST;
        this.type = HealthConsumableEnum.LEMON_CUSTARD_PIE;
    }

    public static LemonCustardPie getInstance() {
        if (instance == null) {
            instance = new LemonCustardPie();
        }
        return instance;
    }
}
