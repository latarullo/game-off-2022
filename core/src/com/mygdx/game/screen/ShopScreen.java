package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.GameOff2022;
import com.mygdx.game.controller.GameSoundPlayer;
import com.mygdx.game.domain.ConsumableItem;
import com.mygdx.game.domain.UnlockableItem;
import com.mygdx.game.domain.UpgradeableItem;
import com.mygdx.game.screen.gui.LemonMoneyGUI;
import com.mygdx.game.screen.gui.ShopScreenGUI;
import com.mygdx.game.util.GameFontGenerator;

public class ShopScreen implements Screen {

    private GameOff2022 game;
    private Stage stage;
    private ShopScreenGUI shopScreenGUI = new ShopScreenGUI(this);
    private Sound buySound = Gdx.audio.newSound(Gdx.files.internal("sounds/buy.wav"));

    private GameFontGenerator gameFontGenerator = GameFontGenerator.getInstance();
    private LemonMoneyGUI lemonMoneyGUI;

    public ShopScreen(final GameOff2022 game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        shopScreenGUI.createGUI();
    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    public void buyConsumable(ConsumableItem consumableItem) {
        GameSoundPlayer.playSound(buySound);
        this.getGame().getGameData().subtractMoney(consumableItem.getPrice());
        game.getGameData().addConsumable(consumableItem.getType());
    }

    public void buyUnlockable(UnlockableItem unlockableItem) {
        GameSoundPlayer.playSound(buySound);
        this.getGame().getGameData().subtractMoney(unlockableItem.getPrice());
        unlockableItem.unlock();
    }

    public void buyUpgradeable(UpgradeableItem upgradeableItem) {
        GameSoundPlayer.playSound(buySound);
        this.getGame().getGameData().subtractMoney(upgradeableItem.getPrice());
        upgradeableItem.upgrade();
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

    }

    @Override
    public void dispose() {
        stage.dispose();
        shopScreenGUI.dispose();
        buySound.dispose();
    }

    public Stage getStage() {
        return stage;
    }

    public GameOff2022 getGame() {
        return game;
    }
}
