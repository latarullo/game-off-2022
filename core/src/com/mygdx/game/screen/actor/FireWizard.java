package com.mygdx.game.screen.actor;

import com.badlogic.gdx.Gdx;

public class FireWizard extends Wizard {

    public FireWizard() {
        this.wizardType = WizardType.FIRE;
        this.spellSound = Gdx.audio.newSound(Gdx.files.internal("sounds/wizard-fire-spell.wav"));
        loadAnimations();
    }
}
