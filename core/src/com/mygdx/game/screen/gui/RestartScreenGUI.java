package com.mygdx.game.screen.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.GameOff2022;
import com.mygdx.game.domain.GameConstants;
import com.mygdx.game.screen.NewGameScreen;
import com.mygdx.game.util.GameFontGenerator;
import com.mygdx.game.util.GameFontSizeEnum;

public class RestartScreenGUI {

    private GameOff2022 game;
    private Sound restartSound = Gdx.audio.newSound(Gdx.files.internal("sounds/restart.wav"));
    private GameFontGenerator gameFontGenerator = GameFontGenerator.getInstance();

    public RestartScreenGUI(GameOff2022 game) {
        this.game = game;
    }

    public Table createGUI() {
        Table table = new Table();
        table.setWidth(Gdx.graphics.getWidth());
        table.setHeight(Gdx.graphics.getHeight());

        Label.LabelStyle labelStyle = gameFontGenerator.generateLabelStyle(GameFontSizeEnum.LARGE, GameConstants.GUI_DISPLAY_RESTART_COLOR);

        Label restartLabel1 = new Label("You never lose", labelStyle);
        Label restartLabel2 = new Label("You either win", labelStyle);
        Label restartLabel3 = new Label("Or you learn", labelStyle);

        TextButton.TextButtonStyle textButtonStyle = gameFontGenerator.generateTextButtonStyle(GameFontSizeEnum.NORMAL, GameConstants.GUI_ACTION_COLOR);

        TextButton restartButton = new TextButton("Restart Game", textButtonStyle);
        restartButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                restartSound.play();
                game.changeScreen(new NewGameScreen(game, true));
            }
        });

        table.center();
        table.add(restartLabel1);
        table.row();
        table.add(restartLabel2);
        table.row();
        table.add(restartLabel3);
        table.row();
        table.add(restartButton);
        return table;
    }
}
