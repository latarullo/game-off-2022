package com.mygdx.game.domain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class LemonSquares extends ConsumableItem {
    private static LemonSquares instance;

    private LemonSquares() {
        this.name = "Lemon Squares";
        this.texture = new Texture(Gdx.files.internal("resources/Consumables/lemon-squares.png"));
        this.healthPower = 1;
        this.cooldown = 1;
        this.price = GameConstants.LEMON_SQUARES_COST;
        this.type = HealthConsumableEnum.LEMON_SQUARES;
    }

    public static LemonSquares getInstance() {
        if (instance == null) {
            instance = new LemonSquares();
        }
        return instance;
    }
}
