package com.mygdx.game.domain;

import com.mygdx.game.screen.actor.FireWizard;

import java.math.BigInteger;

public class FireWizardItem extends UnlockableItem {
    private final static FireWizardItem instance = new FireWizardItem();

    public static FireWizardItem getInstance() {
        return instance;
    }

    private FireWizardItem() {
        this.name = FireWizard.getInstance().getName();
        this.texture = FireWizard.getInstance().getPortraitTexture();
        this.price = BigInteger.ONE;//new BigInteger("10000");
        this.type = UnlockableEnum.FIRE_WIZARD;
    }
}
