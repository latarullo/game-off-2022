package com.mygdx.game.screen.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.controller.GameUpgrades;
import com.mygdx.game.domain.GameConstants;
import com.mygdx.game.domain.GameData;
import com.mygdx.game.domain.GodModeItem;
import com.mygdx.game.domain.IceWizardItem;
import com.mygdx.game.domain.WizardHealthPowerItem;
import com.mygdx.game.domain.WizardSpellPowerItem;
import com.mygdx.game.screen.AchievementsScreen;
import com.mygdx.game.screen.actor.FireWizard;
import com.mygdx.game.screen.actor.IceWizard;
import com.mygdx.game.screen.actor.LightningWizard;
import com.mygdx.game.util.GameFontGenerator;
import com.mygdx.game.util.GameFontSizeEnum;

public class AchievementsScreenGUI implements Disposable {
    private AchievementsScreen screen;
    private Texture background = new Texture(Gdx.files.internal("resources/AchievementsScreen/background.png"));
    private GameFontGenerator gameFontGenerator = GameFontGenerator.getInstance();

    public AchievementsScreenGUI(AchievementsScreen screen) {
        this.screen = screen;
    }

    public void createGUI() {
        createBackground();
        createBackButton();
        createHeader();
        createAchievementsTable();
    }

    private void createAchievementsTable() {
        Table table = new Table();

        Texture lemonSlayerTexture1 = new Texture(Gdx.files.internal("resources/AchievementsScreen/lemon-slayer-001.png"));
        Texture lemonSlayerTexture2 = new Texture(Gdx.files.internal("resources/AchievementsScreen/lemon-slayer-002.png"));
        Texture lemonSlayerTexture3 = new Texture(Gdx.files.internal("resources/AchievementsScreen/lemon-slayer-003.png"));
        Texture lemonSlayerTexture4 = new Texture(Gdx.files.internal("resources/AchievementsScreen/lemon-slayer-004.png"));
        Texture lemonSlayerTexture5 = new Texture(Gdx.files.internal("resources/AchievementsScreen/lemon-slayer-005.png"));
        Texture lemonSlayerTexture6 = new Texture(Gdx.files.internal("resources/AchievementsScreen/lemon-slayer-006.png"));
        Texture lemonSlayerTexture7 = new Texture(Gdx.files.internal("resources/AchievementsScreen/lemon-slayer-007.png"));
        Texture lemonSlayerTexture8 = new Texture(Gdx.files.internal("resources/AchievementsScreen/lemon-slayer-008.png"));

        Texture wizardLongLifeLearnerTexture = new Texture(Gdx.files.internal("resources/AchievementsScreen/wizard-long-life-learner.png"));
        Texture wizardHardAsARockTexture = new Texture(Gdx.files.internal("resources/AchievementsScreen/hard-as-a-rock.png"));
        Texture wizardsNeverGiveUpTexture = new Texture(Gdx.files.internal("resources/AchievementsScreen/wizards-never-give-up.png"));

        Texture allWizardsUnlockedTexture = new Texture(Gdx.files.internal("resources/AchievementsScreen/unlocked-all-wizards.png"));
        Texture wizardMakingLemonadeTexture = new Texture(Gdx.files.internal("resources/AchievementsScreen/wizard-making-lemonade.png"));


        Image image11 = composeImage(lemonSlayerTexture1, GameData.getInstance().getEnemyKilledCounter() >= 5);
        Image image12 = composeImage(lemonSlayerTexture2, GameData.getInstance().getEnemyKilledCounter() >= 10);
        Image image13 = composeImage(lemonSlayerTexture3, GameData.getInstance().getEnemyKilledCounter() >= 25);
        Image image14 = composeImage(lemonSlayerTexture4, GameData.getInstance().getEnemyKilledCounter() >= 50);
        Image image15 = composeImage(lemonSlayerTexture5, GameData.getInstance().getEnemyKilledCounter() >= 100);
        Image image16 = composeImage(lemonSlayerTexture6, GameData.getInstance().getEnemyKilledCounter() >= 500);

        Image image21 = composeImage(lemonSlayerTexture7, GameData.getInstance().getEnemyKilledCounter() >= 1000);
        Image image22 = composeImage(wizardLongLifeLearnerTexture, WizardSpellPowerItem.getInstance().getUpgradeCount() > 0);
        Image image23 = composeImage(wizardHardAsARockTexture, WizardHealthPowerItem.getInstance().getUpgradeCount() > 0);
        Image image24 = composeImage(wizardsNeverGiveUpTexture, GameData.getInstance().getResetCount() > 0);
        Image image25 = composeImage(new Texture(Gdx.files.internal("resources/ShopScreen/hover.png")), false);
        Image image26 = composeImage(new Texture(Gdx.files.internal("resources/ShopScreen/hover.png")), false);

        Image image31 = composeImage(LightningWizard.getInstance().getPortraitTexture(), IceWizardItem.getInstance().isUnlocked());
        Image image32 = composeImage(FireWizard.getInstance().getPortraitTexture(), IceWizardItem.getInstance().isUnlocked());
        Image image33 = composeImage(IceWizard.getInstance().getPortraitTexture(), IceWizardItem.getInstance().isUnlocked());
        Image image34 = composeImage(allWizardsUnlockedTexture, GameUpgrades.getInstance().isAllWizardsAvailable());
        Image image35 = composeImage(GodModeItem.getInstance().getTexture(), GodModeItem.getInstance().isUnlocked());
        Image image36 = composeImage(wizardMakingLemonadeTexture, GameData.getInstance().isLemonadeDone());

        Label.LabelStyle labelStyle = gameFontGenerator.generateLabelStyle(15, Color.CYAN);
        Label labelLemonSlayerLabel = new Label("Lemon Slayer", labelStyle);
        Label labelLemonSlayerLevel1Label = new Label("Killed 5 lemons", labelStyle);
        Label labelLemonSlayerLevel2Label = new Label("Killed 10 lemons", labelStyle);
        Label labelLemonSlayerLevel3Label = new Label("Killed 25 lemons", labelStyle);
        Label labelLemonSlayerLevel4Label = new Label("Killed 50 lemons", labelStyle);
        Label labelLemonSlayerLevel5Label = new Label("Killed 100 lemons", labelStyle);
        Label labelLemonSlayerLevel6Label = new Label("Killed 500 lemons", labelStyle);
        Label labelLemonSlayerLevel7Label = new Label("Killed 1000 lemons", labelStyle);

        final Label learnerLabelValue = new Label("0", labelStyle);
        learnerLabelValue.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                learnerLabelValue.setText("Spell learned - " + WizardSpellPowerItem.getInstance().getUpgradeCount());
                return false;
            }
        });

        final Label lifeLabelValue = new Label("0", labelStyle);
        lifeLabelValue.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                lifeLabelValue.setText("Life increased  - " + WizardHealthPowerItem.getInstance().getUpgradeCount());
                return false;
            }
        });

        final Label neverGiveUpLabel = new Label("0", labelStyle);
        neverGiveUpLabel.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                neverGiveUpLabel.setText("Game reset - " + GameData.getInstance().getResetCount());
                return false;
            }
        });

        Label unlockedLightningWizardLabel = new Label("Wizard unlocked", labelStyle);
        Label unlockedFireWizardLabel = new Label("Wizard unlocked", labelStyle);
        Label unlockedIceWizardLabel = new Label("Wizard unlocked", labelStyle);
        Label allWizardsUnlockedLabel = new Label("Wizards unlocked", labelStyle);
        Label unlockedGodModeLabel = new Label("Lemonade TIME", labelStyle);
        Label lemonadeMadeLabel = new Label("Lemonade complete", labelStyle);

        table.add(image11).size(180, 180);
        table.add(image12).size(180, 180);
        table.add(image13).size(180, 180);
        table.add(image14).size(180, 180);
        table.add(image15).size(180, 180);
        table.add(image16).size(180, 180);
        table.row();
        table.add(labelLemonSlayerLevel1Label).align(Align.right);
        table.add(labelLemonSlayerLevel2Label).align(Align.right);
        table.add(labelLemonSlayerLevel3Label).align(Align.right);
        table.add(labelLemonSlayerLevel4Label).align(Align.right);
        table.add(labelLemonSlayerLevel5Label).align(Align.right);
        table.add(labelLemonSlayerLevel6Label).align(Align.right);
        table.row();
        table.add(image21).size(180, 180);
        table.add(image22).size(180, 180);
        table.add(image23).size(180, 180);
        table.add(image24).size(180, 180);
        table.add(image25).size(180, 180);
        table.add(image26).size(180, 180);
        table.row();
        table.add(labelLemonSlayerLevel7Label).align(Align.right);
        table.add(learnerLabelValue).align(Align.right);
        table.add(lifeLabelValue).align(Align.right);
        table.add(neverGiveUpLabel).align(Align.right);
        table.add().align(Align.right);
        table.add().align(Align.right);
        table.row();
        table.add(image31).size(180, 180);
        table.add(image32).size(180, 180);
        table.add(image33).size(180, 180);
        table.add(image34).size(180, 180);
        table.add(image35).size(180, 180);
        table.add(image36).size(180, 180);
        table.row();
        table.add(unlockedLightningWizardLabel).align(Align.right);
        table.add(unlockedFireWizardLabel).align(Align.right);
        table.add(unlockedIceWizardLabel).align(Align.right);
        table.add(allWizardsUnlockedLabel).align(Align.right);
        table.add(unlockedGodModeLabel).align(Align.right);
        table.add(lemonadeMadeLabel).align(Align.right);

        table.setPosition(40, 20);
        table.setSize(1200, 600);
        screen.getStage().addActor(table);
    }

    private Image composeImage(Texture texture, boolean achieved) {
        texture.getTextureData().prepare();
        Pixmap texturePixMap = texture.getTextureData().consumePixmap();

        Texture markTexture;
        if (achieved) {
            markTexture = new Texture(Gdx.files.internal("resources/AchievementsScreen/check-mark.png"));
        } else {
            markTexture = new Texture(Gdx.files.internal("resources/AchievementsScreen/cross-mark.png"));
        }
        markTexture.getTextureData().prepare();

        Pixmap checkMarkMap = markTexture.getTextureData().consumePixmap();
        texturePixMap.drawPixmap(checkMarkMap, 0, 0);
        Texture resultTexture = new Texture(texturePixMap);

        texturePixMap.dispose();
        checkMarkMap.dispose();
        return new Image(resultTexture);
    }

    private void createBackground() {
        Image backgroundImage = new Image(background);
        backgroundImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        screen.getStage().addActor(backgroundImage);
    }

    private void createHeader() {
        Label.LabelStyle headerStyle = gameFontGenerator.generateLabelStyle(GameFontSizeEnum.NORMAL, GameConstants.GUI_UPGRADE_HEADER_2_COLOR);
        Label headerLabel = new Label("Achievements", headerStyle);
        headerLabel.setPosition(GameConstants.WIDTH / 2 - headerLabel.getWidth() / 2, GameConstants.HEIGHT - headerLabel.getHeight() - 50);
        screen.getStage().addActor(headerLabel);
    }

    private void createBackButton() {
        ImageButton backButton = new ImageButton(new TextureRegionDrawable(new Texture(Gdx.files.internal("resources/Icons/back.png"))));
        backButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                screen.getGame().changePreviousScreen();
            }
        });
        backButton.setPosition(10, GameConstants.HEIGHT - backButton.getHeight());
        screen.getStage().addActor(backButton);
    }

    @Override
    public void dispose() {
        background.dispose();
    }
}