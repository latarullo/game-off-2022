package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.GameOff2022;
import com.mygdx.game.controller.GameSoundPlayer;
import com.mygdx.game.screen.gui.MainMenuScreenGUI;

public class MainMenuScreen implements Screen {

    private final static MainMenuScreen instance = new MainMenuScreen();

    public static MainMenuScreen getInstance() {
        return instance;
    }

    private MainMenuScreen() {
        stage = new Stage(new ScreenViewport());

        gui = new MainMenuScreenGUI(this);

        stage.addActor(gui.createGUI());
    }

    private GameOff2022 game = GameOff2022.getInstance();
    private Stage stage;
    private MainMenuScreenGUI gui;
    private Sound music = Gdx.audio.newSound(Gdx.files.internal("resources/MainMenu/music.wav"));


    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        GameSoundPlayer.playMusic(music);
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
        GameSoundPlayer.stop(music);
    }

    @Override
    public void dispose() {
        stage.dispose();
        gui.dispose();
        music.dispose();
    }

    public Stage getStage() {
        return stage;
    }
}