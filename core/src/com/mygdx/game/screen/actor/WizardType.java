package com.mygdx.game.screen.actor;

import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.domain.GameConstants;

public enum WizardType {
    LIGHTNING("animations/Wizard.atlas", GameConstants.GUI_WIZARD_LIGHTNING_COLOR),
    FIRE("animations/Wizard Fire.atlas",  GameConstants.GUI_WIZARD_FIRE_COLOR),
    ICE("animations/Wizard Ice.atlas",  GameConstants.GUI_WIZARD_ICE_COLOR);

    private String atlasPath;
    private Color color;


    WizardType(String atlasPath, Color color) {
        this.atlasPath = atlasPath;
        this.color = color;
    }

    public String getAtlasPath(){
        return this.atlasPath;
    }

    public Color getColor() {
        return color;
    }
}
