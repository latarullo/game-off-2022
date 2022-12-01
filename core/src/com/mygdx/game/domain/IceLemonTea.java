package com.mygdx.game.domain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.math.BigInteger;

public class IceLemonTea extends ConsumableItem {
    private static IceLemonTea instance;

    private IceLemonTea() {
        this.name = "Ice Lemon Tea";
        this.texture = new Texture(Gdx.files.internal("resources/Consumables/ice-lemon-tea.png"));
        this.healthPower = 5;
        this.cooldown = 5;
        this.price =  GameConstants.ICE_LEMON_TEA_COST;
        this.type = HealthConsumableEnum.ICE_LEMON_TEA;
    }

    public static IceLemonTea getInstance() {
        if (instance == null) {
            instance = new IceLemonTea();
        }
        return instance;
    }
}
