package com.mygdx.game.screen.actor;

import com.badlogic.gdx.Gdx;

public class FireWizard extends Wizard {
    private static FireWizard instance;

    public static FireWizard getInstance() {
        if (instance == null){
            instance = new FireWizard();
        }
        return instance;
    }

    private FireWizard() {
        this.wizardType = WizardType.FIRE;
        this.spellSound = Gdx.audio.newSound(Gdx.files.internal("sounds/wizard-fire-spell.wav"));
        loadAnimations();
    }
}
