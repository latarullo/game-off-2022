package com.mygdx.game.domain;

import com.mygdx.game.screen.actor.IceWizard;

import java.math.BigInteger;

public class IceWizardItem extends UnlockableItem {
    private final static IceWizardItem instance = new IceWizardItem();

    public static IceWizardItem getInstance() {
        return instance;
    }

    private IceWizardItem() {
        this.name = IceWizard.getInstance().getName();
        this.texture = IceWizard.getInstance().getIdleSprite().getTexture();
        this.price = GameConstants.WIZARD_COST;
        this.type = UnlockableEnum.ICE_WIZARD;
    }
}
