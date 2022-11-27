package com.mygdx.game.domain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.math.BigInteger;

public class LemonSquares extends ConsumableItem {
    private static LemonSquares instance;

    private LemonSquares() {
        this.name = "Lemon Squares";
        this.texture = new Texture(Gdx.files.internal("resources/Consumables/lemon-squares.png"));
        this.healthPower = 1;
        this.cooldown = 1;
        this.price = BigInteger.ONE;
        this.type = HealthConsumableEnum.LEMON_SQUARES;
    }

    public static LemonSquares getInstance() {
        if (instance == null) {
            instance = new LemonSquares();
        }
        return instance;
    }
}
