package com.mygdx.game.domain;

import com.mygdx.game.screen.actor.LightningWizard;

import java.math.BigInteger;

public class LightningWizardItem extends UnlockableItem {
    private final static LightningWizardItem instance = new LightningWizardItem();

    public static LightningWizardItem getInstance() {
        return instance;
    }

    private LightningWizardItem() {
        this.name = LightningWizard.getInstance().getName();
        this.texture = LightningWizard.getInstance().getIdleSprite().getTexture();
        this.price = BigInteger.ONE;//new BigInteger("10000");
        this.type = UnlockableEnum.LIGHTNING_WIZARD;
    }
}
