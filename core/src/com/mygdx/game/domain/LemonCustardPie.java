package com.mygdx.game.domain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.math.BigInteger;

public class LemonCustardPie extends ConsumableItem {

    private static LemonCustardPie instance;

    public LemonCustardPie() {
        this.name = "Lemon Custard Pie";
        this.texture = new Texture(Gdx.files.internal("resources/Consumables/lemon-custard-pie.png"));
        this.healthPower = 100;
        this.cooldown = 10;
        this.price = new BigInteger("1000");
        this.type = HealthConsumableEnum.LEMON_CUSTARD_PIE;
    }

    public static LemonCustardPie getInstance() {
        if (instance == null) {
            instance = new LemonCustardPie();
        }
        return instance;
    }
}
