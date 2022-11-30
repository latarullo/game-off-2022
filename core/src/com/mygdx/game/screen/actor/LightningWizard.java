package com.mygdx.game.screen.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class LightningWizard extends Wizard {
    private static LightningWizard instance;

    public static LightningWizard getInstance() {
        if (instance == null) {
            instance = new LightningWizard();
        }
        return instance;
    }

    private LightningWizard() {
        this.setName("Lightning Wizard");
        this.wizardType = WizardType.LIGHTNING;
        this.portraitTexture = new Texture(Gdx.files.internal("resources/Wizards/Lightning/portrait.png"));
        this.spellSound = Gdx.audio.newSound(Gdx.files.internal("resources/Wizards/Lightning/wizard-spell.wav"));
        this.easyPeasyLemonsSqueezeSound = Gdx.audio.newSound(Gdx.files.internal("resources/Wizards/Lightning/easy-peasy.wav"));
        loadAnimations();
    }
}
