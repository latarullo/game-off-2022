package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.GameOff2022;
import com.mygdx.game.screen.gui.MainMenuScreenGUI;

public class MainMenuScreen implements Screen {

    private GameOff2022 game;
    private Stage stage;
    private MainMenuScreenGUI gui;
    private Sound menuSound = Gdx.audio.newSound(Gdx.files.internal("resources/MainMenu/music.wav"));

    public MainMenuScreen(final GameOff2022 game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());

        gui = new MainMenuScreenGUI(this);

        stage.addActor(gui.createGUI());
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        menuSound.loop();
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
        menuSound.stop();
    }

    @Override
    public void dispose() {
        stage.dispose();
        gui.dispose();
        menuSound.dispose();
    }

    public Stage getStage() {
        return stage;
    }
}