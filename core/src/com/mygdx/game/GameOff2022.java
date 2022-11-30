package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.mygdx.game.domain.GameData;
import com.mygdx.game.domain.WizardHealthPowerItem;
import com.mygdx.game.screen.MainMenuScreen;
import com.mygdx.game.screen.actor.EnemyFactory;
import com.mygdx.game.screen.actor.Wizard;

import java.util.List;

public class GameOff2022 extends Game {
    private final static GameOff2022 instance = new GameOff2022();;

    public static GameOff2022 getInstance() {
        return instance;
    }

    private GameOff2022() {

    }

    private SpriteBatch batch;
    private Screen previousScreen;

    private GameData gameData = GameData.getInstance();

    @Override
    public void create() {
        batch = new SpriteBatch();

        this.getGameData().reset();

        this.setScreen(new MainMenuScreen(this));
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    public void changeScreen(Screen screen) {
        this.previousScreen = getScreen();
        this.setScreen(screen);
    }

    public void changePreviousScreen() {
        this.changeScreen(getPreviousScreen());
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    private Screen getPreviousScreen() {
        return previousScreen;
    }

    public void createNewEnemy(Group actorHolder) {
        actorHolder.addActor(EnemyFactory.createEnemy());
    }

    public GameData getGameData() {
        return gameData;
    }

    public void restart() {
        gameData.reset();

        List<Wizard> availableWizards = gameData.getAvailableWizards();
        for (Wizard availableWizard : availableWizards) {
            availableWizard.reset();
        }

        //achievements and achievement counters reset
    }

    public void wizardHelthUp(WizardHealthPowerItem wizardHealthPowerItem) {
        List<Wizard> availableWizards = gameData.getAvailableWizards();
        for (Wizard availableWizard : availableWizards) {
            availableWizard.wizardHealthUp(wizardHealthPowerItem.getBonusHealthPower());
        }

        //achievements and achievement counters reset
    }

    public void gameOver(){
        this.restart();
    }
}
