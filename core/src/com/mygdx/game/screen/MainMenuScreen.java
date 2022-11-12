package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.GameData;
import com.mygdx.game.GameOff2022;

public class MainMenuScreen implements Screen {

    private GameOff2022 game;
    private Stage stage;

    private float stateTime;
    private TextureAtlas atlas;
    private Animation<TextureRegion> wizardAttackAnimation;
    Texture menuTexture = new Texture(Gdx.files.internal("menu.png"));

    public MainMenuScreen(final GameOff2022 game){
        this.game = game;
        stage = new Stage(new ScreenViewport());
        atlas = new TextureAtlas(Gdx.files.internal("animations/Wizard.atlas"));
        wizardAttackAnimation = new Animation<TextureRegion>(0.25f, atlas.findRegions("5_ATTACK"), Animation.PlayMode.LOOP);

        // Create a table that fills the screen. Everything else will go inside this table.
        Table table = new Table();
        table.setFillParent(true);
        table.setDebug(true);
        stage.addActor(table);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = game.getGameFont();
        textButtonStyle.fontColor = Color.BLUE;
        textButtonStyle.disabledFontColor = Color.GRAY;

        TextButton newGame = new TextButton("New Game", textButtonStyle);
        TextButton upgradeScreen = new TextButton("Testing Screen", textButtonStyle);
        TextButton settings = new TextButton("Settings", textButtonStyle);
        TextButton exit = new TextButton("Exit", textButtonStyle);
        settings.setDisabled(true);

        //add buttons to table
        table.add(newGame).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(upgradeScreen).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(settings).fillX().uniformX();
        table.row();
        table.add(exit).fillX().uniformX();

        // create button listeners
        exit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        newGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.changeScreen(new NewGameScreen(game));
            }
        });

        upgradeScreen.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.changeScreen(new UpgradeScreen(game));
            }
        });

        settings.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.changeScreen(new SettingsScreen(game));
            }
        });
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        stateTime += Gdx.graphics.getDeltaTime();

        game.getBatch().begin();
        game.getBatch().draw(menuTexture,0,0, GameData.WIDTH, GameData.HEIGHT);
        game.getBatch().end();


        // tell our stage to do actions and draw itself
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        // change the stage's viewport when teh screen size is changed
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
        menuTexture.dispose();
    }
}