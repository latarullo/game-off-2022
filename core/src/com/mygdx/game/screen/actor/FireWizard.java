package com.mygdx.game.screen.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class FireWizard extends Wizard {
    private static FireWizard instance;

    public static FireWizard getInstance() {
        if (instance == null) {
            instance = new FireWizard();
        }
        return instance;
    }

    private FireWizard() {
        this.setName("Fire Wizard");
        this.wizardType = WizardType.FIRE;
        this.portraitTexture = new Texture(Gdx.files.internal("resources/Wizards/Fire/portrait.png"));
        this.spellSound = Gdx.audio.newSound(Gdx.files.internal("resources/Wizards/Fire/wizard-spell.wav"));
        this.easyPeasyLemonsSqueezeSound = Gdx.audio.newSound(Gdx.files.internal("resources/Wizards/Fire/easy-peasy.wav"));
        loadAnimations();
    }
}
