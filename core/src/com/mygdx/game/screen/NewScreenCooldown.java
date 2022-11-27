package com.mygdx.game.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.util.CooldownTimer;
import com.mygdx.game.GameOff2022;
import com.mygdx.game.util.RingCooldownTimer;

public class NewScreenCooldown implements Screen {
    private GameOff2022 game;
    private Stage stage;

    private CooldownTimer cooldownTimerBlue;
    private CooldownTimer cooldownTimerGray;
    private RingCooldownTimer ringCooldownTimerYellow;
    private RingCooldownTimer ringCooldownTimerGreen;

    private long lastUpdate = 0L;
    private float remainingPercentage = 1.0f;

    public NewScreenCooldown(GameOff2022 game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());

        cooldownTimerBlue = new CooldownTimer(false);
        cooldownTimerBlue.setSize(100, 100);
        cooldownTimerBlue.setPosition(100, 100);
        cooldownTimerBlue.setColor(Color.BLUE);

        stage.addActor(cooldownTimerBlue);

        cooldownTimerGray = new CooldownTimer(true);
        cooldownTimerGray.setSize(100, 100);
        cooldownTimerGray.setPosition(100, 250);
        cooldownTimerGray.setColor(Color.GRAY);
        cooldownTimerGray.setAlpha(0.25f);

        stage.addActor(cooldownTimerGray);

        ringCooldownTimerYellow = new RingCooldownTimer(false, 25);
        ringCooldownTimerYellow.setSize(100, 100);
        ringCooldownTimerYellow.setPosition(250, 250);
        ringCooldownTimerYellow.setColor(Color.YELLOW);
        ringCooldownTimerYellow.setAlpha(0.75f);

        stage.addActor(ringCooldownTimerYellow);

        ringCooldownTimerGreen = new RingCooldownTimer(true, 10);
        ringCooldownTimerGreen.setSize(100, 100);
        ringCooldownTimerGreen.setPosition(250, 100);
        ringCooldownTimerGreen.setColor(Color.GREEN);
        ringCooldownTimerGreen.setAlpha(0.45f);

        stage.addActor(ringCooldownTimerGreen);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);

        if (System.currentTimeMillis() - lastUpdate > 25L) {
            cooldownTimerBlue.update(remainingPercentage);
            cooldownTimerGray.update(remainingPercentage);
            ringCooldownTimerYellow.update(remainingPercentage);
            ringCooldownTimerGreen.update(remainingPercentage);

            remainingPercentage -= 0.01f;
            lastUpdate = System.currentTimeMillis();

            if (remainingPercentage <= 0.0f) {
                remainingPercentage = 1.0f;
            }
        }

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
