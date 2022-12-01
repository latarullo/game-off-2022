package com.mygdx.game.screen.gui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.GameOff2022;
import com.mygdx.game.domain.GameConstants;
import com.mygdx.game.screen.CreditsScreen;
import com.mygdx.game.screen.MainMenuScreen;
import com.mygdx.game.screen.NewGameScreen;
import com.mygdx.game.screen.SettingsScreen;
import com.mygdx.game.util.GameFontGenerator;
import com.mygdx.game.util.GameFontSizeEnum;

public class MainMenuScreenGUI implements Disposable {

    private GameOff2022 game = GameOff2022.getInstance();
    private MainMenuScreen screen;
    private Texture menuBackgroundTexture = new Texture(Gdx.files.internal("resources/MainMenu/background.png"));
    private Texture gameTitleTexture = new Texture(Gdx.files.internal("resources/MainMenu/game-title.png"));
    private GameFontGenerator gameFontGenerator = GameFontGenerator.getInstance();

    public MainMenuScreenGUI(MainMenuScreen screen) {
        this.screen = screen;
    }

    public Table createGUI() {
        createBackground();
        Image gameTitleImage = createGameTitle();

        Table table = new Table();
        table.setFillParent(true);

        TextButton.TextButtonStyle textButtonStyle = gameFontGenerator.generateTextButtonStyle(GameFontSizeEnum.NORMAL, GameConstants.GUI_MAIN_MENU_ACTION_COLOR);
        textButtonStyle.disabledFontColor = GameConstants.GUI_MAIN_MENU_DISABLED_ACTION_COLOR;

        TextButton newGameButton = new TextButton("New Game", textButtonStyle);
        TextButton settingsButton = new TextButton("Settings", textButtonStyle);
        TextButton creditsButton = new TextButton("Credits", textButtonStyle);
        TextButton exitButton = new TextButton("Exit", textButtonStyle);
        //settingsButton.setDisabled(true);

        //add buttons to table
        table.pad(10, 0, 10, 0);
        table.add(gameTitleImage);
        table.row();
        table.add(newGameButton);
        table.row();
        table.add(settingsButton);
        table.row();
        table.add(creditsButton);
        table.row();
        table.add(exitButton);

        // create button listeners
        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        newGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                game.changeScreen(new NewGameScreen(game, false));
            }
        });

        creditsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                game.changeScreen(new CreditsScreen(false));
            }
        });

        settingsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                game.changeScreen(new SettingsScreen(game));
            }
        });

        return table;
    }

    private Image createGameTitle() {
        Image gameTitleImage = new Image(gameTitleTexture);
        //gameTitleImage.setSize(666,375);
        gameTitleImage.setPosition(Gdx.graphics.getWidth()/2-gameTitleImage.getWidth(), Gdx.graphics.getHeight()/2-gameTitleImage.getImageHeight());
        //gameTitleImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        return gameTitleImage;
        //screen.getStage().addActor(gameTitleImage);
    }

    private void createBackground() {
        Image backgroundImage = new Image(menuBackgroundTexture);
        backgroundImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        screen.getStage().addActor(backgroundImage);
    }

    @Override
    public void dispose() {
        menuBackgroundTexture.dispose();
    }
}
