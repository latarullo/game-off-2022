package com.mygdx.game.screen.actor;

public enum WizardType {
    LIGHTNING("animations/Wizard.atlas"),
    FIRE("animations/Wizard Fire.atlas"),
    ICE("animations/Wizard Ice.atlas");

    private String atlasPath;

    WizardType(String atlasPath) {
        this.atlasPath = atlasPath;
    }

    public String getAtlasPath(){
        return this.atlasPath;
    }
}
