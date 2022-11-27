package com.mygdx.game.screen.actor;

import com.badlogic.gdx.Gdx;

public class LightningWizard extends Wizard {
    private static LightningWizard instance;

    public static LightningWizard getInstance() {
        if (instance == null) {
            instance = new LightningWizard();
        }
        return instance;
    }

    private LightningWizard() {
        this.wizardType = WizardType.LIGHTNING;
        this.spellSound = Gdx.audio.newSound(Gdx.files.internal("sounds/wizard-lightning-spell.wav"));
        loadAnimations();
    }
}
