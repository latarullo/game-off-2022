package com.mygdx.game.domain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.math.BigInteger;

public class WizardHealthPowerItem extends UpgradeableItem {
    private static WizardHealthPowerItem instance = new WizardHealthPowerItem();

    public static WizardHealthPowerItem getInstance() {
        return instance;
    }

    private WizardHealthPowerItem() {
        this.name = "Wizard Health Max";
        this.texture = new Texture(Gdx.files.internal("resources/health-up.png"));
        this.price = BigInteger.ONE;//new BigInteger("10000");
        this.type = UpgradeableEnum.WIZARD_HEALTH;
    }

    public long getBonusHealthPower() {
        return getBonusValue();
    }
}
