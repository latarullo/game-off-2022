package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.GameOff2022;
import com.mygdx.game.screen.gui.AchievementsScreenGUI;

public class AchievementsScreen implements Screen {
    private GameOff2022 game;
    private Stage stage;
    private AchievementsScreenGUI gui;

    public AchievementsScreen(GameOff2022 game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        gui = new AchievementsScreenGUI(this);
        stage.addActor(gui.createGUI());
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);

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

    public GameOff2022 getGame() {
        return game;
    }

    public Stage getStage() {
        return stage;
    }
}
