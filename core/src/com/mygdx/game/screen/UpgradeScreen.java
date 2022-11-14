package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.GameOff2022;
import com.mygdx.game.screen.gui.component.LemonMoneyGUI;

public class UpgradeScreen implements Screen {
    private GameOff2022 game;
    private Stage stage;

    public UpgradeScreen(GameOff2022 game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());

        createGuiObjects();
    }

    private void createGuiObjects() {
        createUiTable();
    }


    private void createUiTable(){
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = game.getGameFont();
        labelStyle.fontColor = Color.RED;

        Label currentMoney = new Label(game.getMoney().toString(), labelStyle);

        currentMoney.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                Label label = (Label) this.getActor();
                CharSequence printableValue = game.getMoney().toString();
                label.setText(printableValue);
                return false;
            }
        });
        Label shopLabel = new Label("Upgrade", labelStyle);

        Table table = new Table();
        table.setFillParent(true);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = game.getGameFont();
        textButtonStyle.fontColor = Color.WHITE;

        TextButton lifeButton = new TextButton("Enhance Wizard life", textButtonStyle);
        TextButton damageButton = new TextButton("Boost Wizard Damage", textButtonStyle);
        TextButton potionHealthPowerButton = new TextButton("Incrrease Health Potion Power", textButtonStyle);
        TextButton potionCooldownButton = new TextButton("Reduce Potion Cooldown", textButtonStyle);

        TextButton returnButton = new TextButton("Return", textButtonStyle);
        returnButton.addListener(new ClickListener(){
            public void clicked (InputEvent event, float x, float y) {
                game.changePreviousScreen();
            }
        });
        returnButton.setPosition(50, Gdx.graphics.getHeight() - 65);

        LemonMoneyGUI lemonMoneyGUI = new LemonMoneyGUI(game);
        Table tableLemonMoney = lemonMoneyGUI.createTable();

        table.setDebug(true);
        table.left().top().pad(10,10,0,0);
        table.add(returnButton).align(Align.topLeft);
        table.add(tableLemonMoney);
        table.row();
        table.add(shopLabel).colspan(7);
        table.row().pad(10, 10, 10, 0);
        table.add(lifeButton);
        table.row().pad(10, 10, 10, 0);
        table.add(damageButton);
        table.row().pad(10, 10, 10, 0);
        table.add(potionHealthPowerButton);
        table.row().pad(10, 10, 10, 0);
        table.add(potionCooldownButton);

        stage.addActor(table);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);

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
    }
}
