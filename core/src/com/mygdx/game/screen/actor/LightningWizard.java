package com.mygdx.game.screen.actor;

import com.badlogic.gdx.Gdx;

public class LightningWizard extends Wizard {

    public LightningWizard() {
        this.wizardType = WizardType.LIGHTNING;
        this.spellSound = Gdx.audio.newSound(Gdx.files.internal("wizard-lightning-spell.wav"));
        loadAnimations();
    }
}
