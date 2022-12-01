package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.mygdx.game.controller.GameSoundPlayer;
import com.mygdx.game.domain.GameData;
import com.mygdx.game.domain.WizardHealthPowerItem;
import com.mygdx.game.screen.GameScreen;
import com.mygdx.game.screen.MainMenuScreen;
import com.mygdx.game.screen.actor.EnemyFactory;
import com.mygdx.game.screen.actor.FireWizard;
import com.mygdx.game.screen.actor.IceWizard;
import com.mygdx.game.screen.actor.LightningWizard;
import com.mygdx.game.screen.actor.Wizard;

import java.util.Arrays;
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

        this.getGameData().newGame();

        this.setScreen(MainMenuScreen.getInstance());
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    public void changeScreen(Screen screen) {
        if (screen instanceof GameScreen){
            GameScreen gameScreen = (GameScreen) screen;
            Sound music = gameScreen.getMusic();
            GameSoundPlayer.playMusic(music);
        }
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

    public void newGame(){
        gameData.newGame();
    }

    public void restart() {
        gameData.reset();
    }

    public void allWizardsHealthUp(WizardHealthPowerItem wizardHealthPowerItem) {
        List<Wizard> allWizards = Arrays.asList(LightningWizard.getInstance(), FireWizard.getInstance(), IceWizard.getInstance());
        for (Wizard wizard : allWizards) {
            wizard.wizardHealthUp(wizardHealthPowerItem.getBonusHealthPower());
        }
    }

    public void gameOver(){
        this.restart();
    }
}
