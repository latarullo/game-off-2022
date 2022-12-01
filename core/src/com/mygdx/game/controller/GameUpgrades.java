package com.mygdx.game.controller;

import com.mygdx.game.domain.FireWizardItem;
import com.mygdx.game.domain.FoodHealthPowerItem;
import com.mygdx.game.domain.GodModeItem;
import com.mygdx.game.domain.IceWizardItem;
import com.mygdx.game.domain.LightningWizardItem;
import com.mygdx.game.domain.WizardHealthPowerItem;
import com.mygdx.game.domain.WizardSpellPowerItem;
import com.mygdx.game.screen.actor.WizardType;

public class GameUpgrades {
    private static GameUpgrades instance = new GameUpgrades();

    private GameUpgrades() {
    }

    public static GameUpgrades getInstance() {
        return instance;
    }

    public int getWizardDamageCounter() {
        return WizardSpellPowerItem.getInstance().getUpgradeCount();
    }

    public int getWizardMaxLifeCounter() {
        return WizardHealthPowerItem.getInstance().getUpgradeCount();
    }

    public int getPotionHealthPowerCounter() {
        return FoodHealthPowerItem.getInstance().getUpgradeCount();
    }

    public int getPotionCoolDownCounter() {
        return FoodHealthPowerItem.getInstance().getUpgradeCount();
    }

    public boolean isGodMode() {
        return GodModeItem.getInstance().isUnlocked();
    }

    public boolean isFireWizardAvailable() {
        return FireWizardItem.getInstance().isUnlocked();
    }

    public boolean isIceWizardAvailable() {
        return IceWizardItem.getInstance().isUnlocked();
    }

    public boolean isLightningWizardAvailable() {
        return LightningWizardItem.getInstance().isUnlocked();
    }

    public boolean isAllWizardsAvailable() {
        return LightningWizardItem.getInstance().isUnlocked() && FireWizardItem.getInstance().isUnlocked() && FireWizardItem.getInstance().isUnlocked();
    }

    public boolean isWizardUnlock(WizardType wizardType) {
        if (wizardType == WizardType.LIGHTNING) {
            return LightningWizardItem.getInstance().isUnlocked();
        } else if (wizardType == WizardType.FIRE) {
            return FireWizardItem.getInstance().isUnlocked();
        } else if (wizardType == WizardType.ICE) {
            return FireWizardItem.getInstance().isUnlocked();
        }
        return false;
    }

//    public void unlockWizard(WizardType wizardType) {
//        if (wizardType == WizardType.LIGHTNING) {
//            LightningWizardItem.getInstance().unlock();
//        } else if (wizardType == WizardType.FIRE) {
//            FireWizardItem.getInstance().unlock();
//        } else if (wizardType == WizardType.ICE) {
//            IceWizardItem.getInstance().unlock();
//        }
//    }

    public void unlockGodMode(boolean godMode) {
        GodModeItem.getInstance().unlock();
    }

}