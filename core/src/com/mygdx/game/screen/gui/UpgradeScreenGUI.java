package com.mygdx.game.screen.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.GameOff2022;
import com.mygdx.game.controller.GameAchievements;
import com.mygdx.game.controller.GameUpgrades;
import com.mygdx.game.domain.GameConstants;
import com.mygdx.game.screen.UpgradeScreen;
import com.mygdx.game.screen.actor.FireWizard;
import com.mygdx.game.screen.actor.IceWizard;
import com.mygdx.game.screen.actor.LightningWizard;
import com.mygdx.game.screen.actor.Wizard;
import com.mygdx.game.screen.actor.WizardType;
import com.mygdx.game.util.GameFontGenerator;
import com.mygdx.game.util.GameFontSizeEnum;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class UpgradeScreenGUI {
    private GameOff2022 game;
    private UpgradeScreen screen;
    private GameFontGenerator gameFontGenerator = GameFontGenerator.getInstance();

    public UpgradeScreenGUI(GameOff2022 game, UpgradeScreen screen) {
        this.game = game;
        this.screen = screen;
    }

    public Table createGUI() {
        Label.LabelStyle labelStyle = gameFontGenerator.generateLabelStyle(GameFontSizeEnum.NORMAL, GameConstants.GUI_UPGRADE_HEADER_2_COLOR);

        Label titleLabel = new Label("Upgrade Center", labelStyle);
        Label wizardSubtitleLabel = new Label("Wizards", labelStyle);
        Label potionSubtitleLabel = new Label("Potions", labelStyle);

        TextButton.TextButtonStyle textButtonStyle = gameFontGenerator.generateTextButtonStyle(GameFontSizeEnum.NORMAL, GameConstants.GUI_ACTION_COLOR);

        TextButton whosYourDaddy = new TextButton("Who is your daddy?", textButtonStyle);
        whosYourDaddy.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                GameUpgrades.getInstance().setGodMode(true);
                GameAchievements.getInstance().setCurrentStage(screen.getStage());
                GameAchievements.getInstance().unlockGodMode();
            }
        });

        //TextButton backButton = new TextButton("Back", textButtonStyle);
        ImageButton backButton = new ImageButton(new TextureRegionDrawable(new Texture(Gdx.files.internal("resources/Icons/back.png"))));
        backButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.changePreviousScreen();
            }
        });

        LemonMoneyGUI lemonMoneyGUI = new LemonMoneyGUI(game);
        Table tableLemonMoney = lemonMoneyGUI.createTable();

        Table table = new Table();
        table.setFillParent(true);
//        table.setDebug(true);

        table.left().top();
        table.row().expandX();
        table.add(backButton).fill();
        table.add().colspan(6);
        table.add(tableLemonMoney).fill();
        table.row();
        table.add(titleLabel).colspan(8).align(Align.center);
        table.row();
        table.add(wizardSubtitleLabel).colspan(4).align(Align.center);
        table.add(potionSubtitleLabel).colspan(4).align(Align.center);
        table.row();
        table.add(createWizardDetails()).colspan(4);
        table.add(createPotionDetails()).colspan(4);
        table.row();
        table.add(createUnlockWizardDetails()).colspan(4);
        table.add(whosYourDaddy).colspan(8);

        return table;
    }

    private Table createWizardDetails() {
        Table table = new Table();

        Label.LabelStyle labelHeaderStyle = gameFontGenerator.generateLabelStyle(GameFontSizeEnum.SMALL, GameConstants.GUI_UPGRADE_HEADER_2_COLOR);
        Label.LabelStyle labelSmallStyle = gameFontGenerator.generateLabelStyle(GameFontSizeEnum.SMALL, Color.WHITE);
        TextButton.TextButtonStyle buyTextButtonStyle = gameFontGenerator.generateTextButtonStyle(GameFontSizeEnum.NORMAL, GameConstants.GUI_ACTION_COLOR);

        Label damageLabel = new Label("Damage", labelHeaderStyle);
        Label damageIncreaseLabel = new Label("Increased by", labelSmallStyle);
        Label damageCostLabel = new Label("Cost", labelSmallStyle);
        Label damageOwnedLabel = new Label("Owned", labelSmallStyle);

        Label damageValueLabel = new Label("10", labelSmallStyle);
        Label damageOwnedValueLabel = new Label("0", labelSmallStyle);
        damageOwnedValueLabel.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                Label label = (Label) getActor();
                label.setText(GameUpgrades.getInstance().getWizardDamageCounter());
                return false;
            }
        });

        LemonMoneyFieldGUI damageLemonMoneyValue = new LemonMoneyFieldGUI();
        Table damageCostValueLabel = damageLemonMoneyValue.createTable(BigInteger.valueOf(10));

        TextButton damageBuyButton = new TextButton("Buy", buyTextButtonStyle);
        damageBuyButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Actor actor = event.getListenerActor();
                System.out.println("Upgraded Wizard Damage+");
            }
        });

        Label lifeLabel = new Label("Life", labelHeaderStyle);
        Label lifeIncreasedLabel = new Label("Increased by", labelSmallStyle);
        Label lifeCostLabel = new Label("Cost", labelSmallStyle);
        Label lifeOwnedLabel = new Label("Owned", labelSmallStyle);

        Label lifeValueLabel = new Label("+10", labelSmallStyle);
        Label lifeOwnedValueLabel = new Label("0", labelSmallStyle);
        lifeOwnedValueLabel.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                Label label = (Label) getActor();
                label.setText(GameUpgrades.getInstance().getWizardMaxLifeCounter());
                return false;
            }
        });

        LemonMoneyFieldGUI lifeLemonMoneyValue = new LemonMoneyFieldGUI();
        Table lifeCostValueLabel = lifeLemonMoneyValue.createTable(BigInteger.valueOf(10));

        TextButton lifeBuyButton = new TextButton("Buy", buyTextButtonStyle);
        lifeBuyButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Actor actor = event.getListenerActor();
                System.out.println("Upgraded Wizard Health+");
            }
        });

        table.pad(10, 10, 0, 0);
        table.add(damageLabel).colspan(2);
        table.add(lifeLabel).colspan(2);
        table.row();
        table.add(damageIncreaseLabel).align(Align.left);
        table.add(damageValueLabel);
        table.add(lifeIncreasedLabel).align(Align.left);
        table.add(lifeValueLabel);
        table.row();
        table.add(damageCostLabel).align(Align.left);
        table.add(damageCostValueLabel);
        table.add(lifeCostLabel).align(Align.left);
        table.add(lifeCostValueLabel);
        table.row();
        table.add(damageOwnedLabel).align(Align.left);
        table.add(damageOwnedValueLabel);
        table.add(lifeOwnedLabel).align(Align.left);
        table.add(lifeOwnedValueLabel);
        table.row();
        table.add(damageBuyButton).colspan(2).uniform();
        table.add(lifeBuyButton).colspan(2).uniform();

        return table;
    }

    private Table createPotionDetails() {
        Table table = new Table();

        Label.LabelStyle labelHeaderStyle = gameFontGenerator.generateLabelStyle(GameFontSizeEnum.SMALL, GameConstants.GUI_UPGRADE_HEADER_2_COLOR);
        Label.LabelStyle labelSmallStyle = gameFontGenerator.generateLabelStyle(GameFontSizeEnum.SMALL, Color.WHITE);
        TextButton.TextButtonStyle buyTextButtonStyle = gameFontGenerator.generateTextButtonStyle(GameFontSizeEnum.NORMAL, GameConstants.GUI_ACTION_COLOR);

        Label healthPowerLabel = new Label("Health Power", labelHeaderStyle);
        Label healthPowerIncreaseLabel = new Label("Increased by", labelSmallStyle);
        Label healthPowerCostLabel = new Label("Cost", labelSmallStyle);
        Label healthPowerOwnedLabel = new Label("Owned", labelSmallStyle);

        Label healthPowerValueLabel = new Label("10", labelSmallStyle);
        Label healthPowerOwnedValueLabel = new Label("0", labelSmallStyle);
        healthPowerOwnedValueLabel.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                Label label = (Label) getActor();
                label.setText(GameUpgrades.getInstance().getPotionHealthPowerCounter());
                return false;
            }
        });

        LemonMoneyFieldGUI healthPowerLemonMoneyValue = new LemonMoneyFieldGUI();
        Table healthPowerCostValueLabel = healthPowerLemonMoneyValue.createTable(BigInteger.valueOf(10));

        TextButton damageBuyButton = new TextButton("Buy", buyTextButtonStyle);
        damageBuyButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Actor actor = event.getListenerActor();
                System.out.println("Upgraded Wizard Damage+");
            }
        });

        Label cooldownLabel = new Label("Cooldown", labelHeaderStyle);
        Label cooldownIncreasedLabel = new Label("Reduced by", labelSmallStyle);
        Label cooldownCostLabel = new Label("Cost", labelSmallStyle);
        Label cooldownOwnedLabel = new Label("Owned", labelSmallStyle);

        Label cooldownValueLabel = new Label("+10", labelSmallStyle);
        Label cooldownOwnedValueLabel = new Label("0", labelSmallStyle);
        cooldownOwnedValueLabel.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                Label label = (Label) getActor();
                label.setText(GameUpgrades.getInstance().getPotionCoolDownCounter());
                return false;
            }
        });

        LemonMoneyFieldGUI cooldownLemonMoneyValue = new LemonMoneyFieldGUI();
        Table cooldownCostValueLabel = cooldownLemonMoneyValue.createTable(BigInteger.valueOf(10));

        TextButton lifeBuyButton = new TextButton("Buy", buyTextButtonStyle);
        lifeBuyButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Actor actor = event.getListenerActor();
                System.out.println("Upgraded Wizard Health+");
            }
        });

        table.pad(10, 10, 0, 0);
        table.add(healthPowerLabel).colspan(2);
        table.add(cooldownLabel).colspan(2);
        table.row();
        table.add(healthPowerIncreaseLabel).align(Align.left);
        table.add(healthPowerValueLabel);
        table.add(cooldownIncreasedLabel).align(Align.left);
        table.add(cooldownValueLabel);
        table.row();
        table.add(healthPowerCostLabel).align(Align.left);
        table.add(healthPowerCostValueLabel);
        table.add(cooldownCostLabel).align(Align.left);
        table.add(cooldownCostValueLabel);
        table.row();
        table.add(healthPowerOwnedLabel).align(Align.left);
        table.add(healthPowerOwnedValueLabel);
        table.add(cooldownOwnedLabel).align(Align.left);
        table.add(cooldownOwnedValueLabel);
        table.row();
        table.add(damageBuyButton).colspan(2).uniform();
        table.add(lifeBuyButton).colspan(2).uniform();

        return table;
    }

    private Table createUnlockWizardDetails() {
        Table table = new Table();

        Label.LabelStyle labelHeaderStyle = gameFontGenerator.generateLabelStyle(GameFontSizeEnum.NORMAL, GameConstants.GUI_UPGRADE_HEADER_2_COLOR);
        Label.LabelStyle labelSmallStyle = gameFontGenerator.generateLabelStyle(GameFontSizeEnum.SMALL, Color.WHITE);
        TextButton.TextButtonStyle buyTextButtonStyle = gameFontGenerator.generateTextButtonStyle(GameFontSizeEnum.NORMAL, GameConstants.GUI_ACTION_COLOR);

        Label unlockWizardLabel = new Label("Unlock Wizard", labelHeaderStyle);
        List<Wizard> unlockableWizards = new ArrayList<>();

        if (!GameUpgrades.getInstance().isLightningWizardAvailable()) {
            unlockableWizards.add(LightningWizard.getInstance());
        }
        if (!GameUpgrades.getInstance().isFireWizardAvailable()) {
            unlockableWizards.add(FireWizard.getInstance());
        }
        if (!GameUpgrades.getInstance().isIceWizardAvailable()) {
            unlockableWizards.add(IceWizard.getInstance());
        }

        table.pad(10, 10, 0, 0);
        table.add(unlockWizardLabel).colspan(2);
        table.row();
        for (final Wizard wizard : unlockableWizards) {
            Table wizardDetail = new Table();
            wizardDetail.add(wizard.getImage()).size(64, 64);
            wizardDetail.row();

            Label wizardNameLabel = new Label(wizard.getWizardType().toString(), labelSmallStyle);
            wizardDetail.add(wizardNameLabel);
            wizardDetail.row();

            LemonMoneyFieldGUI wizardCostField = new LemonMoneyFieldGUI();
            Table wizardCostLabel = wizardCostField.createTable(BigInteger.valueOf(100000));

            wizardDetail.add(wizardCostLabel);
            wizardDetail.row();

            TextButton wizardBuyButton = new TextButton("Buy", buyTextButtonStyle);
            wizardBuyButton.addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    WizardType wizardType = wizard.getWizardType();
                    if (!GameUpgrades.getInstance().isWizardUnlock(wizardType)) {
                        GameUpgrades.getInstance().unlockWizard(wizardType);
                        GameAchievements.getInstance().setCurrentStage(screen.getStage());
                        GameAchievements.getInstance().unlockWizard(wizardType);
                    }
                }
            });
            wizardDetail.add(wizardBuyButton);
            table.add(wizardDetail);
        }

        return table;
    }
}