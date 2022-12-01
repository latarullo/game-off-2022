package com.mygdx.game.domain;

import com.mygdx.game.screen.actor.LightningWizard;

public class LightningWizardItem extends UnlockableItem {
    private final static LightningWizardItem instance = new LightningWizardItem();

    public static LightningWizardItem getInstance() {
        return instance;
    }

    private LightningWizardItem() {
        this.name = LightningWizard.getInstance().getName();
        this.texture = LightningWizard.getInstance().getIdleSprite().getTexture();
        this.price = GameConstants.WIZARD_COST;
        this.type = UnlockableEnum.LIGHTNING_WIZARD;
    }
}
