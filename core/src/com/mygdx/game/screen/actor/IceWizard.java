package com.mygdx.game.screen.actor;

import com.badlogic.gdx.Gdx;

public class IceWizard extends Wizard {

    public IceWizard() {
        this.wizardType = WizardType.ICE;
        this.spellSound = Gdx.audio.newSound(Gdx.files.internal("sounds/wizard-ice-spell.wav"));
        loadAnimations();
    }
}
