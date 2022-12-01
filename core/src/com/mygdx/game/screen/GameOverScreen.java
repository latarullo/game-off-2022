package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.controller.GameSoundPlayer;
import com.mygdx.game.screen.gui.GameOverScreenGUI;

public class GameOverScreen implements Screen {
    private static final GameOverScreen instance = new GameOverScreen();

    public static GameOverScreen getInstance() {
        return instance;
    }

    private Stage stage;
    private GameOverScreenGUI gui;
    private Sound music = Gdx.audio.newSound(Gdx.files.internal("resources/CreditsScreen/music.wav"));


    private GameOverScreen() {
        stage = new Stage(new ScreenViewport());
        gui = new GameOverScreenGUI(this);
        gui.createGUI();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        GameSoundPlayer.playMusic(music);
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
        music.dispose();
    }

    public Stage getStage() {
        return stage;
    }

    public Sound getMusic() {
        return music;
    }
}
