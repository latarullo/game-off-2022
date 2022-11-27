package com.mygdx.game.screen.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.domain.GameConstants;
import com.mygdx.game.screen.AchievementsScreen;
import com.mygdx.game.util.GameFontGenerator;
import com.mygdx.game.util.GameFontSizeEnum;

public class AchievementsScreenGUI implements Disposable {
    private AchievementsScreen screen;
    private Texture background = new Texture(Gdx.files.internal("resources/AchievementsScreen/background.png"));
    private GameFontGenerator gameFontGenerator = GameFontGenerator.getInstance();

    public AchievementsScreenGUI(AchievementsScreen screen) {
        this.screen = screen;
    }

    public Table createGUI() {
        Image backgroundImage = new Image(background);
        backgroundImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        screen.getStage().addActor(backgroundImage);

        Label.LabelStyle labelStyle = gameFontGenerator.generateLabelStyle(GameFontSizeEnum.NORMAL, GameConstants.GUI_UPGRADE_HEADER_2_COLOR);
        Label titleLabel = new Label("Achievements", labelStyle);

        TextButton.TextButtonStyle textButtonStyle = gameFontGenerator.generateTextButtonStyle(GameFontSizeEnum.NORMAL, GameConstants.GUI_ACTION_COLOR);

        ImageButton backButton = new ImageButton(new TextureRegionDrawable(new Texture(Gdx.files.internal("resources/Icons/back.png"))));
        backButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                screen.getGame().changePreviousScreen();
            }
        });

        LemonMoneyGUI lemonMoneyGUI = new LemonMoneyGUI(screen.getGame());
        Table tableLemonMoney = lemonMoneyGUI.createTable();

        Table table = new Table();
        table.setFillParent(true);
//        table.setDebug(true);

        table.left().top();
        table.row().expandX();
        table.add(backButton).fill();
        table.add().colspan(6);
        table.add(tableLemonMoney).fill();
        table.row();
        table.add(titleLabel).colspan(8).align(Align.center);

        return table;
    }

    @Override
    public void dispose() {
        background.dispose();
    }
}