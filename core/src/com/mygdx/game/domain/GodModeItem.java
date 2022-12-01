package com.mygdx.game.domain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.screen.GameScreen;

import java.math.BigInteger;
import java.util.List;

public class GodModeItem extends UnlockableItem {
    private static GodModeItem instance = new GodModeItem();

    public static GodModeItem getInstance() {
        return instance;
    }

    private GodModeItem() {
        this.name = "Last but not least";
        this.texture = new Texture(Gdx.files.internal("resources/AchievementsScreen/boss.png"));
        this.price =  GameConstants.GOD_MODE_COST;
        this.type = UnlockableEnum.GOD_MODE;
    }

    @Override
    public void unlock() {
        super.unlock();
        GameScreen.getInstance().makeLemonJarTableVisible();
    }

    public void makeLemonJarInvisible() {
        GameScreen.getInstance().makeLemonJarTableInvisible();
        List<Image> juiceJarImages = GameScreen.getInstance().getJuiceJarImages();
        for (Image juiceJarImage : juiceJarImages) {
            juiceJarImage.setVisible(false);
        }
    }
}
