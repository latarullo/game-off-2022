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
        this.price = GameConstants.WIZARD_COST;
        this.type = UnlockableEnum.FIRE_WIZARD;
    }
}
