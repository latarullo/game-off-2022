package com.mygdx.game.domain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import java.math.BigInteger;

public class GodModeItem extends UnlockableItem {
    private static GodModeItem instance = new GodModeItem();

    public static GodModeItem getInstance() {
        return instance;
    }

    private Table lemonJarTable;

    private GodModeItem(){
        this.name = "Who is your daddy";
        this.texture = new Texture(Gdx.files.internal("resources/god-mode.png"));
        this.price = BigInteger.ZERO; //new BigInteger("1000000000");
        this.type = UnlockableEnum.GOD_MODE;
    }

    public void setLemonJarTable(Table lemonJarTable) {
        this.lemonJarTable = lemonJarTable;
    }

    @Override
    public void unlock() {
        super.unlock();
        lemonJarTable.setVisible(true);
    }
}
