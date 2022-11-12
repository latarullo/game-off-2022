package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
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
        HealthPotion minorHealthPotion = new MinorHealthPotion();
        HealthPotion smallHealthPotion = new SmallHealthPotion();
        HealthPotion greatHealthPotion = new GreatHealthPotion();
        HealthPotion greaterHealthPotion = new GreaterHealthPotion();

        createUiTable(minorHealthPotion, smallHealthPotion, greatHealthPotion, greaterHealthPotion);

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

        Label currentMoney = new Label(game.getMoney().toString(), labelStyle);

        currentMoney.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                Label label = (Label) this.getActor();
                label.setText(game.getMoney().toString());
                return false;
            }
        });
        Label shopLabel = new Label("Health Potion Shop", labelStyle);

        Table table = new Table();
        table.setFillParent(true);

        Table healthPotionTable1 = createPotionDetails(potion1);
        Table healthPotionTable2 = createPotionDetails(potion2);
        Table healthPotionTable3 = createPotionDetails(potion3);
        Table healthPotionTable4 = createPotionDetails(potion4);

        Image potion1Image = potion1.getImage();
        Image potion2Image = potion2.getImage();
        Image potion3Image = potion3.getImage();
        Image potion4Image = potion4.getImage();

        ClickListener clickListener = new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Actor actor = event.getListenerActor();
                HealthPotion healthPotion = (HealthPotion) actor.getUserObject();
                buyHealthPotion(healthPotion);
            }
        };

        potion1Image.addListener(clickListener);
        potion2Image.addListener(clickListener);
        potion3Image.addListener(clickListener);
        potion4Image.addListener(clickListener);

        table.add(currentMoney).colspan(7);
        table.row();
        table.add(shopLabel).colspan(7);
        table.row().pad(10, 10, 10, 0).size(200);
        table.add(potion1Image);
        table.add(healthPotionTable1);
        table.add(potion2Image);
        table.add(healthPotionTable2);
        table.row().pad(10, 10, 10, 0).size(200);
        table.add(potion3Image);
        table.add(healthPotionTable3).fillX().fillY();
        table.add(potion4Image);
        table.add(healthPotionTable4).fillX().fillY();

        stage.addActor(table);
    }

    private void buyHealthPotion(HealthPotion healthPotion) {
        System.out.println("Buying potion " + healthPotion);
        game.addHealthPotion(healthPotion.getType());
        game.subtractMoney(healthPotion.getPrice());
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
                buyHealthPotion(healthPotion);
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
