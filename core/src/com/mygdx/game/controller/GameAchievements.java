package com.mygdx.game.controller;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdx.game.domain.GodMode;
import com.mygdx.game.screen.actor.WizardType;
import com.mygdx.game.screen.gui.AchievementGUI;

public class GameAchievements {
    private static GameAchievements instance = new GameAchievements();

    private Stage currentStage;
    private int notificationCount = 0;

    private GameAchievements() {
    }

    public static GameAchievements getInstance() {
        return instance;
    }

    public void unlockWizard(WizardType wizardType) {
        AchievementType achievementType = null;
        if (wizardType == WizardType.LIGHTNING) {
            achievementType = AchievementType.UNLOCKED_LIGHTNING_WIZARD;
        }
        if (wizardType == WizardType.FIRE) {
            achievementType = AchievementType.UNLOCKED_FIRE_WIZARD;
        }
        if (wizardType == WizardType.ICE) {
            achievementType = AchievementType.UNLOCKED_ICE_WIZARD;
        }

        currentStage.addActor(new AchievementGUI(achievementType.name(), "You have unlocked the " + wizardType));

        if (GameUpgrades.getInstance().isAllWizardsAvailable()){
            currentStage.addActor(new AchievementGUI(AchievementType.UNLOCKED_ALL_WIZARDS.name(), "You have unlocked all wizards"));
        }
    }

    public void unlockGodMode() {
        AchievementType achievementType = AchievementType.GOD_MODE;
        currentStage.addActor(new AchievementGUI(achievementType.name(), "Lemonade time"));
    }

    public void lemonSlayer(String message) {
        AchievementType achievementType = AchievementType.LEMON_SLAYER;
        currentStage.addActor(new AchievementGUI(achievementType.name(), message));
    }

    class Achievement {
        AchievementType achievementType;
        String title;
        String description;
        Image image;
        int[] levels;
    }

    enum AchievementType {
        LEMON_SLAYER, // kill x lemons
        NEVER_GIVE_UP, //restart X times
        LONG_LIFE_LEARNER, // upgrade wizard damage
        HARD_AS_A_ROCK, // upgrade wizard life/endurance
        UNLOCKED_LIGHTNING_WIZARD, //unlock wizard
        UNLOCKED_FIRE_WIZARD, //unlock wizard
        UNLOCKED_ICE_WIZARD, //unlock wizard
        UNLOCKED_ALL_WIZARDS, //unlock wizard
        GOD_MODE //who's your daddy upgrade -> LETS KILL THEM ALL AND MAKE THE LEMONADE!
    }

    public void setCurrentStage(Stage currentStage) {
        this.currentStage = currentStage;
    }

    public void notificationAdded(){
        this.notificationCount++;
    }

    public void notificationRemoved(){
        this.notificationCount--;
    }
}
