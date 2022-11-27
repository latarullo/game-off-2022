package com.mygdx.game.screen.actor;

import com.badlogic.gdx.Gdx;

public class IceWizard extends Wizard {
    private static IceWizard instance;

    public static IceWizard getInstance() {
        if (instance == null) {
            instance = new IceWizard();
        }
        return instance;
    }

    private IceWizard() {
        this.wizardType = WizardType.ICE;
        this.spellSound = Gdx.audio.newSound(Gdx.files.internal("sounds/wizard-ice-spell.wav"));
        loadAnimations();
    }
}
