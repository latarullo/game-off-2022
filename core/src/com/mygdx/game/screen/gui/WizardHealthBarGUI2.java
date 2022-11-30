package com.mygdx.game.screen.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.GameOff2022;
import com.mygdx.game.controller.GameSoundPlayer;
import com.mygdx.game.domain.FireWizardItem;
import com.mygdx.game.domain.GameData;
import com.mygdx.game.domain.IceWizardItem;
import com.mygdx.game.domain.LightningWizardItem;
import com.mygdx.game.screen.actor.FireWizard;
import com.mygdx.game.screen.actor.IceWizard;
import com.mygdx.game.screen.actor.LightningWizard;
import com.mygdx.game.screen.actor.Wizard;
import com.mygdx.game.screen.actor.WizardState;
import com.mygdx.game.screen.actor.WizardType;
import com.mygdx.game.util.GameFontGenerator;

public class WizardHealthBarGUI2 {
    private Sound wizardNotAvailableSound = Gdx.audio.newSound(Gdx.files.internal("resources/GameScreen/wizard-not-available.mp3"));
    private GameFontGenerator gameFontGenerator = GameFontGenerator.getInstance();

    public Table createGUI() {
        Wizard wizard = GameData.getInstance().getCurrentWizard();

        final Label.LabelStyle lightningWizardLabelStyle = gameFontGenerator.generateLabelStyle(30, LightningWizard.getInstance().getWizardColor());
        final Label.LabelStyle fireWizardLabelStyle = gameFontGenerator.generateLabelStyle(30, FireWizard.getInstance().getWizardColor());
        final Label.LabelStyle iceWizardLabelStyle = gameFontGenerator.generateLabelStyle(30, IceWizard.getInstance().getWizardColor());

        Label.LabelStyle labelStyle = gameFontGenerator.generateLabelStyle(30, Color.WHITE);
        String name = wizard.getName();

        Label nameLabel = new Label(name, labelStyle);
        nameLabel.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                Wizard currentWizard = GameData.getInstance().getCurrentWizard();
                Label.LabelStyle wizardLabelStyle;
                if (currentWizard.getWizardType() == WizardType.LIGHTNING) {
                    wizardLabelStyle = lightningWizardLabelStyle;
                } else if (currentWizard.getWizardType() == WizardType.FIRE) {
                    wizardLabelStyle = fireWizardLabelStyle;
                } else if (currentWizard.getWizardType() == WizardType.ICE) {
                    wizardLabelStyle = iceWizardLabelStyle;
                } else {
                    throw new RuntimeException("Too many wizards...?");
                }
                Label label = (Label) this.getActor();
                label.setStyle(wizardLabelStyle);
                label.setText(currentWizard.getName());
                return false;
            }
        });

        WizardHealthBarGUI healthBarGUI = new WizardHealthBarGUI(wizard);
        healthBarGUI.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                Wizard currentWizard = GameData.getInstance().getCurrentWizard();
                WizardHealthBarGUI wizardHealthBarGUI = (WizardHealthBarGUI) this.getActor();
                wizardHealthBarGUI.setDamageable(currentWizard);
                return false;
            }
        });

        Table nameHealthBarTable = new Table();
        nameHealthBarTable.add(nameLabel).align(Align.left);
        nameHealthBarTable.row();
        nameHealthBarTable.add(healthBarGUI).width(300).height(10).align(Align.left);

        final Table wizardSwitcherGUI = createWizardSwitcherGUI();
        Image wizardImage = wizard.getImage();
        wizardImage.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                Image currentWizardImage = GameData.getInstance().getCurrentWizard().getImage();
                Image label = (Image) this.getActor();
                label.setDrawable(currentWizardImage.getDrawable());
                return false;
            }
        });

        wizardImage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                wizardSwitcherGUI.setVisible(!wizardSwitcherGUI.isVisible());
            }
        });

        Table table = new Table();
        table.add(wizardSwitcherGUI).height(64).colspan(2).align(Align.left);
        table.row();
        table.add(wizardImage).size(128, 128);
        table.add(nameHealthBarTable);

        return table;

//        final Wizard currentWizard = GameData.getInstance().getCurrentWizard();
//        List<Wizard> availableWizards = GameData.getInstance().getAvailableWizards();
//
//        Wizard backupWizard1 = null;
//        Wizard backupWizard2 = null;
//        for (Wizard availableWizard : availableWizards) {
//            if (availableWizard.getWizardType() != currentWizard.getWizardType()) {
//                if (backupWizard1 == null) {
//                    backupWizard1 = availableWizard;
//                } else if (backupWizard2 == null) {
//                    backupWizard2 = availableWizard;
//                } else {
//                    throw new RuntimeException("Too many wizards, something is wrong!");
//                }
//            }
//        }
//
//        Table subTable = new Table();
//        subTable.setDebug(false);
//
//        final Image currentWizardImage = currentWizard.getImage();
//
//        WizardHealthBarGUI currentWizardHealthBar = new WizardHealthBarGUI(currentWizard);
//        subTable.add(currentWizardHealthBar).size(300, 20).colspan(2);
//        if (backupWizard1 != null) {
//            Image backWizard1Image = backupWizard1.getImage();
//            WizardHealthBarGUI backWizard1HealthBar = new WizardHealthBarGUI(backupWizard1);
//            subTable.row().height(40);
//            subTable.add(backWizard1Image).size(40, 40);
//            subTable.add(backWizard1HealthBar).size(250, 10).align(Align.left);
//        }
//        if (backupWizard2 != null) {
//            Image backWizard2Image = backupWizard2.getImage();
//            WizardHealthBarGUI backWizard2HealthBar = new WizardHealthBarGUI(backupWizard2);
//            subTable.row().height(40);
//            subTable.add(backWizard2Image).size(40, 40);
//            subTable.add(backWizard2HealthBar).size(250, 10).align(Align.left);
//        }
//
//        final Table table = new Table();
//        final Table wizardSwitcherGUI = createWizardSwitcherGUI();
//        table.add(wizardSwitcherGUI).height(64).colspan(2).align(Align.left);
//        table.row();
//
//        currentWizardImage.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                super.clicked(event, x, y);
//                wizardSwitcherGUI.setVisible(!wizardSwitcherGUI.isVisible());
//            }
//        });
//
//        table.pad(10, 10, 0, 0);
//        table.add(currentWizardImage).size(128, 128);
//        table.add(subTable).align(Align.left);
//
//        return table;
    }

    private Table createWizardSwitcherGUI() {
        Wizard lightningWizard = LightningWizard.getInstance();
        Wizard fireWizard = FireWizard.getInstance();
        Wizard iceWizard = IceWizard.getInstance();

        Image lightningWizardImage = new Image(lightningWizard.getIdleSprite());
        Image fireWizardImage = new Image(fireWizard.getIdleSprite());
        Image iceWizardImage = new Image(iceWizard.getIdleSprite());

        lightningWizardImage.setUserObject(lightningWizard);
        fireWizardImage.setUserObject(fireWizard);
        iceWizardImage.setUserObject(iceWizard);

        final GameOff2022 game = GameOff2022.getInstance();

        ClickListener clickListener = new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Wizard currentWizard = game.getGameData().getCurrentWizard();
                Wizard wizard = (Wizard) event.getListenerActor().getUserObject();

                if (currentWizard.getWizardType().equals(wizard.getWizardType())) {
                    System.out.println("Wizard " + wizard.getWizardType() + " already chosen");
                    return;
                }

                if (wizard.getCurrentState() == WizardState.DIE) {
                    System.out.println("Cannot switch to dead Wizard. " + wizard.getWizardType() + " is dead.");
                    return;
                }

                if (wizard.getWizardType() == WizardType.LIGHTNING && !LightningWizardItem.getInstance().isUnlocked() ||
                        wizard.getWizardType() == WizardType.FIRE && !FireWizardItem.getInstance().isUnlocked() ||
                        wizard.getWizardType() == WizardType.ICE && !IceWizardItem.getInstance().isUnlocked()) {
                    GameSoundPlayer.playSound(wizardNotAvailableSound);
                    return;
                }

                Group actorHolder = game.getGameData().getCurrentWizard().getParent();

                wizard.setX(currentWizard.getX());
                wizard.setY(currentWizard.getY());

                actorHolder.removeActor(currentWizard);
                game.getGameData().setCurrentWizard(wizard);
                wizard.setCurrentState(WizardState.IDLE);
                actorHolder.addActor(wizard);
                currentWizard.wizardSwitched();
            }
        };

        lightningWizardImage.addListener(clickListener);
        fireWizardImage.addListener(clickListener);
        iceWizardImage.addListener(clickListener);

        Table table = new Table();
        int width = 64;
        int height = 64;
        table.add(lightningWizardImage).size(width, height);
        table.add(fireWizardImage).size(width, height);
        table.add(iceWizardImage).size(width, height);
        table.setVisible(false);
        return table;
    }
}