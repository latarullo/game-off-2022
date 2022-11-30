package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.GameOff2022;
import com.mygdx.game.controller.GameAchievements;
import com.mygdx.game.controller.GameSoundPlayer;
import com.mygdx.game.domain.GameData;
import com.mygdx.game.domain.GodModeItem;
import com.mygdx.game.screen.actor.Enemy;
import com.mygdx.game.screen.actor.EnemyState;
import com.mygdx.game.screen.actor.Wizard;
import com.mygdx.game.screen.actor.WizardState;
import com.mygdx.game.screen.gui.AchievementGUI;
import com.mygdx.game.screen.gui.GameScreenGUI;
import com.mygdx.game.screen.gui.HealthBarGUI;

import java.util.Arrays;
import java.util.List;

public class GameScreen implements Screen {

    private final static GameScreen instance = new GameScreen();

    private GameOff2022 game = GameOff2022.getInstance();
    private Stage stage;
    private Timer timer;
    private int autoClickers;
    private GameScreenGUI gameScreenGUI;
    private AchievementGUI achievementGUI;
    private Sound music = Gdx.audio.newSound(Gdx.files.internal("resources/GameScreen/music.wav"));


    private GameScreen() {
        stage = new Stage(new ScreenViewport());

        timer = new Timer();
        gameScreenGUI = new GameScreenGUI(this);

        stage.addActor(gameScreenGUI.createGUI());
    }

    public static GameScreen getInstance() {
        return instance;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        GameSoundPlayer.playMusic(music);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

//        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
//            game.getGameData().reset();
//            Array<Actor> actors = stage.getActors();
//            for (Actor actor : actors) {
//                if (actor instanceof Wizard) {
//                    Wizard wizard = (Wizard) actor;
//                    wizard.setCurrentState(WizardState.IDLE);
//                } else if (actor instanceof Enemy) {
//                    Enemy enemy = (Enemy) actor;
//                    enemy.setCurrentState(EnemyState.ATTACK);
//                }
//            }
//        }

//        if(Gdx.input.isKeyPressed(Input.Keys.X)){
//            GodModeItem.getInstance().unlock();
//            while (!GameData.getInstance().isLemonadeDone()){
//                GameData.getInstance().enemyKilled();
//            }
//            GameAchievements.getInstance().lemonadeMade();
//        }

        stage.act();
        stage.draw();



        //checkForAchievements();
    }

    private void drawWizardsHealthBar() {
        HealthBarGUI healthBarGUI = new HealthBarGUI();
        List<Wizard> allAvailableWizards = game.getGameData().getAvailableWizards();
        game.getBatch().begin();
        //healthBarGUI.drawWizardType(game.getBatch(), allAvailableWizards);
        game.getBatch().end();

    }

    private void checkForAchievements() {
        List<Integer> lemonSlayerLevels = Arrays.asList(5, 10, 25, 50, 100, 500, 1000);
        Long enemyKilledCounter = game.getGameData().getEnemyKilledCounter();

        if (lemonSlayerLevels.contains(enemyKilledCounter.intValue())) {
            GameAchievements.getInstance().setCurrentStage(stage);
            GameAchievements.getInstance().lemonSlayer("You have squeezed " + enemyKilledCounter + " lemons");
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
        GameSoundPlayer.stop(music);
    }

    @Override
    public void dispose() {
        timer.stop();
        gameScreenGUI.dispose();
        achievementGUI.dispose();
    }
}
