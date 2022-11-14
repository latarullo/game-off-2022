package com.mygdx.game.screen;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Null;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.BigNumberNotation;
import com.mygdx.game.CooldownTimer;
import com.mygdx.game.GameData;
import com.mygdx.game.GameOff2022;
import com.mygdx.game.domain.GreatHealthPotion;
import com.mygdx.game.domain.GreaterHealthPotion;
import com.mygdx.game.domain.HealthPotionEnum;
import com.mygdx.game.domain.MinorHealthPotion;
import com.mygdx.game.domain.SmallHealthPotion;
import com.mygdx.game.screen.actor.Enemy;
import com.mygdx.game.screen.actor.EnemyState;
import com.mygdx.game.screen.actor.IceWizard;
import com.mygdx.game.screen.actor.Wizard;
import com.mygdx.game.screen.actor.FireWizard;
import com.mygdx.game.screen.actor.LightningWizard;
import com.mygdx.game.screen.actor.WizardState;
import com.mygdx.game.screen.gui.component.ClickerScreenGUI;
import com.mygdx.game.screen.gui.component.LemonMoneyGUI;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class ClickerScreen implements Screen {

    private GameOff2022 game;
    private Stage stage;
    private Timer timer;
    private int autoClickers;
    private ClickerScreenGUI clickerScreenGUI;

    public ClickerScreen(final GameOff2022 game){
        this.game = game;
        stage = new Stage(new ScreenViewport());

        timer = new Timer();
        clickerScreenGUI = new ClickerScreenGUI(this);

        stage.addActor(clickerScreenGUI.createGUI());
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
		ScreenUtils.clear(0, 0, 0, 1);
        stage.getCamera().update();

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)){
            game.resetMoney();
            game.resetHealthPotions();
            Array<Actor> actors = stage.getActors();
            for (Actor actor : actors) {
                if (actor instanceof Wizard){
                    Wizard wizard = (Wizard) actor;
                    wizard.setCurrentState(WizardState.IDLE);
                } else if (actor instanceof Enemy){
                    Enemy enemy = (Enemy) actor;
                    enemy.setCurrentState(EnemyState.ATTACK);
                }
            }
        }

        stage.act();
        stage.draw();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            this.game.changePreviousScreen();
        }
    }

    public GameOff2022 getGame() {
        return game;
    }

    public Stage getStage() {
        return stage;
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
        timer.stop();
    }
}
