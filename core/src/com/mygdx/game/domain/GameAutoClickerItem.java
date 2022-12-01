package com.mygdx.game.domain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Timer;

public class GameAutoClickerItem extends UnlockableItem {
    private static GameAutoClickerItem instance = new GameAutoClickerItem();

    public static GameAutoClickerItem getInstance() {
        return instance;
    }

    private GameAutoClickerItem() {
        this.name = "Auto clicker";
        this.texture = new Texture(Gdx.files.internal("resources/Icons/auto-clicker.png"));
        this.price = GameConstants.AUTO_CLICK_COST;
        this.type = UnlockableEnum.AUTO_CLICK;
    }

    private Timer timer = new Timer();

    private Timer.Task lemonClickerTask = new Timer.Task() {
        @Override
        public void run() {
            GameData.getInstance().getCurrentEnemy().wasClicked();
        }
    };

    public void enableAutoClicker() {
        timer = new Timer();
        timer.scheduleTask(lemonClickerTask, 0, 0.5f);
        timer.start();
    }

    public void disableAutoClicker() {
        timer.stop();
    }

    @Override
    public void unlock() {
        super.unlock();
        enableAutoClicker();
    }

    @Override
    public void reset() {
        super.reset();
        disableAutoClicker();
    }
}
