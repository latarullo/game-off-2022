package com.mygdx.game.domain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.math.BigInteger;

public class WizardSpellPowerItem extends UpgradeableItem {
    private static WizardSpellPowerItem instance = new WizardSpellPowerItem();

    public static WizardSpellPowerItem getInstance() {
        return instance;
    }

    private WizardSpellPowerItem() {
        this.name = "Wizard Spell Power";
        this.texture = new Texture(Gdx.files.internal("resources/Icons/wizard-spell-power.png"));
        this.price = BigInteger.ONE;//new BigInteger("10000");
        this.type = UpgradeableEnum.WIZARD_POWER;
        this.bonusMultiplier = 25;
    }

    public long getBonusSpellPowerDamage() {
        return getBonusValue();
    }
}
