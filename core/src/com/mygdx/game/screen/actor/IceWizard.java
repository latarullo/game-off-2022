package com.mygdx.game.screen.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class IceWizard extends Wizard {
    private static IceWizard instance;

    public static IceWizard getInstance() {
        if (instance == null) {
            instance = new IceWizard();
        }
        return instance;
    }

    private IceWizard() {
        this.setName("Ice Wizard");
        this.wizardType = WizardType.ICE;
        this.portraitTexture = new Texture(Gdx.files.internal("resources/Wizards/Ice/portrait.png"));
        this.spellSound = Gdx.audio.newSound(Gdx.files.internal("resources/Wizards/Ice/wizard-spell.wav"));
        this.easyPeasyLemonsSqueezeSound = Gdx.audio.newSound(Gdx.files.internal("resources/Wizards/Ice/easy-peasy.wav"));
        loadAnimations();
    }
}
