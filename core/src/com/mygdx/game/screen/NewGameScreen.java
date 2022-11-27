package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.GameOff2022;
import com.mygdx.game.domain.FireWizardItem;
import com.mygdx.game.domain.GameConstants;
import com.mygdx.game.controller.GameUpgrades;
import com.mygdx.game.domain.IceWizardItem;
import com.mygdx.game.domain.LightningWizardItem;
import com.mygdx.game.screen.actor.FireWizard;
import com.mygdx.game.screen.actor.IceWizard;
import com.mygdx.game.screen.actor.LightningWizard;
import com.mygdx.game.screen.actor.Wizard;
import com.mygdx.game.screen.actor.WizardState;
import com.mygdx.game.screen.actor.WizardType;
import com.mygdx.game.util.GameFontGenerator;
import com.mygdx.game.util.GameFontSizeEnum;

public class NewGameScreen implements Screen {

    private GameOff2022 game;
    private Stage stage;
    private GameFontGenerator gameFontGenerator = GameFontGenerator.getInstance();

    public NewGameScreen(GameOff2022 game, boolean isRestart) {
        this.game = game;
        stage = new Stage(new ScreenViewport());

        if (isRestart){
            game.restart();
        }
        createGuiObjects(isRestart);
    }

    private void createGuiObjects(boolean isRestart) {
        Wizard lightning = LightningWizard.getInstance();
        Wizard fire = FireWizard.getInstance();
        Wizard ice = IceWizard.getInstance();

        createUiTable(lightning, fire, ice, isRestart);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    private void createUiTable(Wizard lightningWizard, Wizard fireWizard, Wizard iceWizard, boolean isRestart) {
        TextureRegion lightningIdleSprite = lightningWizard.getIdleSprite();
        TextureRegion fireIdleSprite = fireWizard.getIdleSprite();
        TextureRegion iceIdleSprite = iceWizard.getIdleSprite();

        Image lightningWizardImage = new Image(lightningIdleSprite);
        Image fireWizardImage = new Image(fireIdleSprite);
        Image iceWizardImage = new Image(iceIdleSprite);

        ClickListener wizardClick = new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Wizard wizard = (Wizard) event.getListenerActor().getUserObject();
                wizard.setCurrentState(WizardState.IDLE);
                if (wizard.getWizardType() == WizardType.LIGHTNING) {
                    GameUpgrades.getInstance().setLightningWizardAvailable(true);
                    LightningWizardItem.getInstance().setUnlocked(true);
                } else if (wizard.getWizardType() == WizardType.FIRE) {
                    GameUpgrades.getInstance().setFireWizardAvailable(true);
                    FireWizardItem.getInstance().setUnlocked(true);
                } else if (wizard.getWizardType() == WizardType.ICE) {
                    GameUpgrades.getInstance().setIceWizardAvailable(true);
                    IceWizardItem.getInstance().setUnlocked(true);
                } else {
                    throw new RuntimeException("Invalid Wizard Type");
                }

                game.getGameData().setCurrentWizard(wizard);
                game.changeScreen(new GameScreen(game));
            }
        };

        lightningWizardImage.setUserObject(lightningWizard);
        fireWizardImage.setUserObject(fireWizard);
        iceWizardImage.setUserObject(iceWizard);

        lightningWizardImage.addListener(wizardClick);
        fireWizardImage.addListener(wizardClick);
        iceWizardImage.addListener(wizardClick);

        Label.LabelStyle labelStyle = gameFontGenerator.generateLabelStyle(GameFontSizeEnum.NORMAL,GameConstants.GUI_DISPLAY_COLOR);

        Label.LabelStyle lightningStyle = gameFontGenerator.generateLabelStyle(GameFontSizeEnum.SMALL,GameConstants.GUI_WIZARD_LIGHTNING_COLOR);
        Label.LabelStyle fireStyle = gameFontGenerator.generateLabelStyle(GameFontSizeEnum.SMALL,GameConstants.GUI_WIZARD_FIRE_COLOR);
        Label.LabelStyle iceStyle = gameFontGenerator.generateLabelStyle(GameFontSizeEnum.SMALL,GameConstants.GUI_WIZARD_ICE_COLOR);

        Label shopLabel = new Label("Choose Your Starting Wizard", labelStyle);

        Table table = new Table();
        table.setFillParent(true);

        table.add(shopLabel).colspan(3);
        table.row().pad(10, 10, 10, 0);
        if (!isRestart || GameUpgrades.getInstance().isLightningWizardAvailable()) {
            table.add(lightningWizardImage);
        }
        if (!isRestart || GameUpgrades.getInstance().isFireWizardAvailable()) {
            table.add(fireWizardImage);
        }
        if (!isRestart || GameUpgrades.getInstance().isIceWizardAvailable()) {
            table.add(iceWizardImage);
        }
        table.row();

        if (!isRestart || GameUpgrades.getInstance().isLightningWizardAvailable()) {
            table.add(new Label("Lightning Wizard", lightningStyle));
        }
        if (!isRestart || GameUpgrades.getInstance().isFireWizardAvailable()) {
            table.add(new Label("Fire Wizard", fireStyle));
        }
        if (!isRestart || GameUpgrades.getInstance().isIceWizardAvailable()) {
            table.add(new Label("Ice Wizard", iceStyle));
        }

        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
