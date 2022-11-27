package com.mygdx.game.domain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.screen.actor.IceWizard;

import java.math.BigInteger;

public class IceWizardItem extends UnlockableItem {
    private static IceWizardItem instance = new IceWizardItem();

    public static IceWizardItem getInstance() {
        return instance;
    }

    private IceWizardItem(){
        this.name = "Ice Wizard";
        this.texture = IceWizard.getInstance().getIdleSprite().getTexture();
        this.price = BigInteger.ONE;//new BigInteger("10000");
        this.type = UnlockableEnum.ICE_WIZARD;
    }
}
