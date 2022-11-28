package com.mygdx.game.domain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.math.BigInteger;

public class GodMode extends UnlockableItem {
    private static GodMode instance = new GodMode();

    public static GodMode getInstance() {
        return instance;
    }

    private GodMode(){
        this.name = "Who is your daddy";
        this.texture = new Texture(Gdx.files.internal("resources/god-mode.png"));
        this.price = BigInteger.ZERO; //new BigInteger("1000000000");
        this.type = UnlockableEnum.GOD_MODE;
    }
}
