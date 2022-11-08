package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
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
import com.mygdx.game.domain.GreatHealthPotion;
import com.mygdx.game.domain.GreaterHealthPotion;
import com.mygdx.game.domain.HealthPotion;
import com.mygdx.game.domain.MinorHealthPotion;
import com.mygdx.game.domain.SmallHealthPotion;

public class HealthPotionShopScreen implements Screen {

    private GameOff2022 game;
    private Stage stage;

    public HealthPotionShopScreen(GameOff2022 game){
        this.game = game;
        stage = new Stage(new ScreenViewport());

        createGuiObjects();
    }

    private void createGuiObjects() {
        HealthPotion potion1 = new MinorHealthPotion();
        HealthPotion potion2 = new SmallHealthPotion();
        HealthPotion potion3 = new GreatHealthPotion();
        HealthPotion potion4 = new GreaterHealthPotion();

        createUiTable(potion1, potion2, potion3, potion4);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = game.getGameFont();
        textButtonStyle.fontColor = Color.WHITE;

        TextButton returnButton = new TextButton("Return", textButtonStyle);
        returnButton.addListener(new ClickListener(){
            public void clicked (InputEvent event, float x, float y) {
                game.changePreviousScreen();
            }
        });
        returnButton.setPosition(50,Gdx.graphics.getHeight() - 65);

        stage.addActor(returnButton);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    private void createUiTable(HealthPotion potion1, HealthPotion potion2, HealthPotion potion3, HealthPotion potion4){
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = game.getGameFont();
        labelStyle.fontColor = Color.RED;

        Label shopLabel = new Label("Health Potion Shop", labelStyle);

        Table table = new Table();
        table.setFillParent(true);

        Table healthPotionTable1 = createPotionDetails(potion1);
        Table healthPotionTable2 = createPotionDetails(potion2);
        Table healthPotionTable3 = createPotionDetails(potion3);
        Table healthPotionTable4 = createPotionDetails(potion4);

        table.add(shopLabel).colspan(7);
        table.row().pad(10, 10, 10, 0).size(200);
        table.add(potion1.getImage());
        table.add(healthPotionTable1);
        table.add(potion2.getImage());
        table.add(healthPotionTable2);
        table.row().pad(10, 10, 10, 0).size(200);
        table.add(potion3.getImage());
        table.add(healthPotionTable3).fillX().fillY();
        table.add(potion4.getImage());
        table.add(healthPotionTable4).fillX().fillY();

        stage.addActor(table);
    }

    private Table createPotionDetails(HealthPotion healthPotion) {
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = game.getGameFontSmall();
        labelStyle.fontColor = Color.RED;

        Label.LabelStyle labelStyleWhite = new Label.LabelStyle();
        labelStyleWhite.font = game.getGameFontSmall();
        labelStyleWhite.fontColor = Color.WHITE;

        Table table = new Table();

        Label nameLabel = new Label(healthPotion.getName(), labelStyle);
        Label healthPowerLabel = new Label("Health Power", labelStyle);
        Label cooldownLabel = new Label("Cooldown", labelStyle);
        Label priceLabel = new Label("Price", labelStyle);

        Label healthPowerValueLabel = new Label(String.valueOf(healthPotion.getHealthPower()), labelStyleWhite);
        Label cooldownValueLabel = new Label(String.valueOf(healthPotion.getCooldown())+"s", labelStyleWhite);
        Label priceValueLabel = new Label(String.valueOf(healthPotion.getPrice())+"$", labelStyleWhite);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = game.getGameFont();
        textButtonStyle.fontColor = Color.WHITE;

        TextButton buyButton = new TextButton("Buy", textButtonStyle);
        buyButton.setUserObject(healthPotion);
        buyButton.addListener(new ClickListener(){
            public void clicked (InputEvent event, float x, float y) {
                Actor actor = event.getListenerActor();
                HealthPotion healthPotion = (HealthPotion) actor.getUserObject();
                System.out.println("BUYING : " + healthPotion.getName());
            }
        });

        table.add(nameLabel).colspan(3);
        table.row();
        table.add(healthPowerLabel).colspan(1).align(Align.left);
        table.add(healthPowerValueLabel).colspan(1).align(Align.right);
        table.row();
        table.add(cooldownLabel).colspan(1).align(Align.left);
        table.add(cooldownValueLabel).colspan(1).align(Align.right);
        table.row();
        table.add(priceLabel).colspan(1).align(Align.left);
        table.add(priceValueLabel).colspan(1).align(Align.right);
        table.row();
        table.add(buyButton).colspan(2).align(Align.center);

        return table;
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
    }
}
