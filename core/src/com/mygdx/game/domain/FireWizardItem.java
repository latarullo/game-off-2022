package com.mygdx.game.domain;

import com.mygdx.game.screen.actor.FireWizard;

import java.math.BigInteger;

public class FireWizardItem extends UnlockableItem {
    private static FireWizardItem instance = new FireWizardItem();

    public static FireWizardItem getInstance() {
        return instance;
    }

    private FireWizardItem() {
        this.name = "Fire Wizard";
        this.texture = FireWizard.getInstance().getIdleSprite().getTexture();
        this.price = BigInteger.ONE;//new BigInteger("10000");
        this.type = UnlockableEnum.FIRE_WIZARD;
    }
}
