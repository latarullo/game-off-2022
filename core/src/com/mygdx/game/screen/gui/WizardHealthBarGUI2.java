package com.mygdx.game.screen.gui;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.domain.GameData;
import com.mygdx.game.screen.actor.Wizard;

import java.util.List;

public class WizardHealthBarGUI2 {
    public Table createGUI() {
        Wizard currentWizard = GameData.getInstance().getCurrentWizard();
        List<Wizard> availableWizards = GameData.getInstance().getAvailableWizards();

        Wizard backupWizard1 = null;
        Wizard backupWizard2 = null;
        for (Wizard availableWizard : availableWizards) {
            if (availableWizard.getWizardType() != currentWizard.getWizardType()) {
                if (backupWizard1 == null) {
                    backupWizard1 = availableWizard;
                } else if (backupWizard2 == null) {
                    backupWizard2 = availableWizard;
                } else {
                    throw new RuntimeException("Too many wizards, something is wrong!");
                }
            }
        }

        Table subTable = new Table();
        subTable.setDebug(false);
        WizardHealthBarGUI currentWizardHealthBar = new WizardHealthBarGUI(currentWizard);
        subTable.add(currentWizardHealthBar).size(300, 20).colspan(2);
        if (backupWizard1 != null) {
            Image backWizard1Image = backupWizard1.getImage();
            WizardHealthBarGUI backWizard1HealthBar = new WizardHealthBarGUI(backupWizard1);
            subTable.row().height(40);
            subTable.add(backWizard1Image).size(40, 40);
            subTable.add(backWizard1HealthBar).size(250, 10).align(Align.left);
        }
        if (backupWizard2 != null) {
            Image backWizard2Image = backupWizard2.getImage();
            WizardHealthBarGUI backWizard2HealthBar = new WizardHealthBarGUI(backupWizard2);
            subTable.row().height(40);
            subTable.add(backWizard2Image).size(40, 40);
            subTable.add(backWizard2HealthBar).size(250, 10).align(Align.left);
        }
        Table table = new Table();
        table.setDebug(false);
        table.pad(10, 10, 0, 0);
        Image currentWizardImage = currentWizard.getImage();
        table.add(currentWizardImage).size(128, 128);
        table.add(subTable).expandX().align(Align.left);

        return table;
    }
}
