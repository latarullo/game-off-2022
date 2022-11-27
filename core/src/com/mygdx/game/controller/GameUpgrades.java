package com.mygdx.game.controller;

import com.mygdx.game.domain.GameData;
import com.mygdx.game.screen.actor.FireWizard;
import com.mygdx.game.screen.actor.IceWizard;
import com.mygdx.game.screen.actor.LightningWizard;
import com.mygdx.game.screen.actor.WizardType;

public class GameUpgrades {
    private static GameUpgrades instance;

    private int wizardDamageCounter = 1;
    private int wizardMaxLifeCounter = 1;
    private int potionHealthPowerCounter = 1;
    private int potionCoolDownCounter = 1;
    private boolean lightningWizardAvailable = false;
    private boolean fireWizardAvailable = false;
    private boolean iceWizardAvailable = false;
    private boolean godMode = false;

    private GameUpgrades() {

    }

    public static GameUpgrades getInstance() {
        if (instance == null) {
            instance = new GameUpgrades();
        }
        return instance;
    }

    public int getWizardDamageCounter() {
        return wizardDamageCounter;
    }

    public int getWizardMaxLifeCounter() {
        return wizardMaxLifeCounter;
    }

    public int getPotionHealthPowerCounter() {
        return potionHealthPowerCounter;
    }

    public int getPotionCoolDownCounter() {
        return potionCoolDownCounter;
    }

    public boolean isGodMode() {
        return godMode;
    }

    public void setWizardDamageCounter(int wizardDamageCounter) {
        this.wizardDamageCounter = wizardDamageCounter;
    }

    public void setWizardMaxLifeCounter(int wizardMaxLifeCounter) {
        this.wizardMaxLifeCounter = wizardMaxLifeCounter;
    }

    public void setPotionHealthPowerCounter(int potionHealthPowerCounter) {
        this.potionHealthPowerCounter = potionHealthPowerCounter;
    }

    public void setPotionCoolDownCounter(int potionCoolDownCounter) {
        this.potionCoolDownCounter = potionCoolDownCounter;
    }

    public void setGodMode(boolean godMode) {
        this.godMode = godMode;
    }

    public boolean isFireWizardAvailable() {
        return fireWizardAvailable;
    }

    public void setFireWizardAvailable(boolean fireWizardAvailable) {
        this.fireWizardAvailable = fireWizardAvailable;
    }

    public boolean isIceWizardAvailable() {
        return iceWizardAvailable;
    }

    public void setIceWizardAvailable(boolean iceWizardAvailable) {
        this.iceWizardAvailable = iceWizardAvailable;
    }

    public boolean isLightningWizardAvailable() {
        return lightningWizardAvailable;
    }

    public void setLightningWizardAvailable(boolean lightningWizardAvailable) {
        this.lightningWizardAvailable = lightningWizardAvailable;
    }

    public void unlockWizard(WizardType wizardType) {
        if (wizardType == WizardType.LIGHTNING) {
            lightningWizardAvailable = true;
            GameData.getInstance().addAvailableWizard(LightningWizard.getInstance());
        } else if (wizardType == WizardType.FIRE) {
            fireWizardAvailable = true;
            GameData.getInstance().addAvailableWizard(FireWizard.getInstance());
        } else if (wizardType == WizardType.ICE) {
            iceWizardAvailable = true;
            GameData.getInstance().addAvailableWizard(IceWizard.getInstance());
        }
    }

    public boolean isWizardUnlock(WizardType wizardType) {
        if (wizardType == WizardType.LIGHTNING) {
            return lightningWizardAvailable;
        } else if (wizardType == WizardType.FIRE) {
            return fireWizardAvailable;
        } else if (wizardType == WizardType.ICE) {
            return iceWizardAvailable;
        }
        return false;
    }

    public boolean isAllWizardsAvailable() {
        return isLightningWizardAvailable() && isFireWizardAvailable() && isIceWizardAvailable();
    }
}