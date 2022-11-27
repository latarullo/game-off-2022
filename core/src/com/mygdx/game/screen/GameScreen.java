package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.GameOff2022;
import com.mygdx.game.controller.GameAchievements;
import com.mygdx.game.screen.actor.Enemy;
import com.mygdx.game.screen.actor.EnemyState;
import com.mygdx.game.screen.actor.Wizard;
import com.mygdx.game.screen.actor.WizardState;
import com.mygdx.game.screen.gui.AchievementGUI;
import com.mygdx.game.screen.gui.GameScreenGUI;
import com.mygdx.game.screen.gui.HealthBarGUI;

import java.util.List;

public class GameScreen implements Screen {

    private GameOff2022 game;
    private Stage stage;
    private Timer timer;
    private int autoClickers;
    private GameScreenGUI gameScreenGUI;
    private AchievementGUI achievementGUI;
    private long oldEnemyKilledCounter = 1;

    public GameScreen(final GameOff2022 game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());

        timer = new Timer();
        gameScreenGUI = new GameScreenGUI(this);

        stage.addActor(gameScreenGUI.createGUI());
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.getCamera().update();

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            game.getGameData().reset();
            Array<Actor> actors = stage.getActors();
            for (Actor actor : actors) {
                if (actor instanceof Wizard) {
                    Wizard wizard = (Wizard) actor;
                    wizard.setCurrentState(WizardState.IDLE);
                } else if (actor instanceof Enemy) {
                    Enemy enemy = (Enemy) actor;
                    enemy.setCurrentState(EnemyState.ATTACK);
                }
            }
        }

        stage.act();
        stage.draw();

//        drawWizardsHealthBar();

        checkForAchievements();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            this.game.changePreviousScreen();
        }
    }

    private void drawWizardsHealthBar() {
        HealthBarGUI healthBarGUI = new HealthBarGUI();
        List<Wizard> allAvailableWizards = game.getGameData().getAvailableWizards();
        game.getBatch().begin();
        //healthBarGUI.drawWizardType(game.getBatch(), allAvailableWizards);
        game.getBatch().end();

    }

    private void checkForAchievements() {
        if (game.getGameData().getEnemyKilledCounter() > oldEnemyKilledCounter) {
            GameAchievements.getInstance().setCurrentStage(stage);
            GameAchievements.getInstance().lemonSlayer("You have squeezed " + game.getGameData().getEnemyKilledCounter() + " lemons");
//            achievementGUI = new AchievementGUI("Lemon Slayer", );
//            stage.addActor(achievementGUI);
            oldEnemyKilledCounter = game.getGameData().getEnemyKilledCounter();
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
        gameScreenGUI.dispose();
        achievementGUI.dispose();
    }
}
