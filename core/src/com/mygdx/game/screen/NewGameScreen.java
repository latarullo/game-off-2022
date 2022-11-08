package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
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
import com.mygdx.game.screen.actor.FireWizard;
import com.mygdx.game.screen.actor.IceWizard;
import com.mygdx.game.screen.actor.LightningWizard;
import com.mygdx.game.screen.actor.Wizard;
import com.mygdx.game.screen.actor.WizardState;

public class NewGameScreen implements Screen {

    private GameOff2022 game;
    private Stage stage;

    public NewGameScreen(GameOff2022 game){
        this.game = game;
        stage = new Stage(new ScreenViewport());

        createGuiObjects();
    }

    private void createGuiObjects() {
        Wizard lightning = new LightningWizard();
        Wizard fire = new FireWizard();
        Wizard ice = new IceWizard();

        createUiTable(lightning, fire, ice);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    private void createUiTable(Wizard lightningWizard, Wizard fireWizard, Wizard iceWizard){
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
                game.setCurrentWizard(wizard);
                game.changeScreen(new ClickerScreen(game));
            }
        };

        lightningWizardImage.setUserObject(lightningWizard);
        fireWizardImage.setUserObject(fireWizard);
        iceWizardImage.setUserObject(iceWizard);

        lightningWizardImage.addListener(wizardClick);
        fireWizardImage.addListener(wizardClick);
        iceWizardImage.addListener(wizardClick);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = game.getGameFont();
        labelStyle.fontColor = Color.WHITE;

        Label.LabelStyle lightningStyle = new Label.LabelStyle();
        lightningStyle.font = game.getGameFontSmall();
        lightningStyle.fontColor = Color.PURPLE;

        Label.LabelStyle fireStyle = new Label.LabelStyle();
        fireStyle.font = game.getGameFontSmall();
        fireStyle.fontColor = Color.RED;

        Label.LabelStyle iceStyle = new Label.LabelStyle();
        iceStyle.font = game.getGameFontSmall();
        iceStyle.fontColor = Color.BLUE;

        Label shopLabel = new Label("Choose Your Starting Wizard", labelStyle);

        Table table = new Table();
        table.setFillParent(true);

        table.add(shopLabel).colspan(3);
        table.row().pad(10, 10, 10, 0);
        table.add(lightningWizardImage);
        table.add(fireWizardImage);
        table.add(iceWizardImage);
        table.row();

        table.add(new Label("Lightning Wizard", lightningStyle));
        table.add(new Label("Fire Wizard", fireStyle));
        table.add(new Label("Ice Wizard", iceStyle));

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
