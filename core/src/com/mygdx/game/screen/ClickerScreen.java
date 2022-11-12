package com.mygdx.game.screen;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Null;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.BigNumberNotation;
import com.mygdx.game.CooldownTimer;
import com.mygdx.game.GameData;
import com.mygdx.game.GameOff2022;
import com.mygdx.game.domain.GreatHealthPotion;
import com.mygdx.game.domain.GreaterHealthPotion;
import com.mygdx.game.domain.HealthPotionEnum;
import com.mygdx.game.domain.MinorHealthPotion;
import com.mygdx.game.domain.SmallHealthPotion;
import com.mygdx.game.screen.actor.Enemy;
import com.mygdx.game.screen.actor.EnemyState;
import com.mygdx.game.screen.actor.IceWizard;
import com.mygdx.game.screen.actor.Wizard;
import com.mygdx.game.screen.actor.FireWizard;
import com.mygdx.game.screen.actor.LightningWizard;
import com.mygdx.game.screen.actor.WizardState;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class ClickerScreen implements Screen {

    private GameOff2022 game;
    private Stage stage;
    private Timer timer;
    private int autoClickers;

//    private BigInteger value = new BigInteger("0");
//    List<CooldownTimer> cooldownTimers = new ArrayList<>();
//    private long lastUpdate = 0L;
//    private float remainingPercentage = 1.0f;

    public ClickerScreen(final GameOff2022 game){
        this.game = game;
        stage = new Stage(new ScreenViewport());

        timer = new Timer();

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = game.getGameFont();

        TextButton createAutoClicker = new TextButton("Create Auto-Clicker", textButtonStyle);

        createAutoClicker.setPosition(0, 200);
        createAutoClicker.setSize(500,50);

        createAutoClicker.addListener(new ClickListener(){
            public void clicked (InputEvent event, float x, float y) {
                autoClickers++;
                Timer.Task clickerTask = new Timer.Task() {
                    @Override
                    public void run() {
                        Array.ArrayIterator<Actor> iterator = stage.getActors().iterator();
                        while(iterator.hasNext()){
                            Actor actor = iterator.next();
                            if (actor instanceof Wizard){
                                imageClick(actor);
                            }
                        }

                    }
                };
                timer.scheduleTask(clickerTask, 1f, 1);
                TextButton textButton = (TextButton) event.getListenerActor();
                //textButton.setText(String.format("Create new Auto-Clicker (%s running)", autoClickers));
                textButton.setText("Create new Auto-Clicker (" + autoClickers + " running)");
            }
        });

        Enemy enemy = new Enemy(game);
        enemy.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.getCurrentWizard().setCurrentState(WizardState.ATTACK);
            }
        });
        enemy.setPosition(500, GameData.HEIGHT/2-70);
        enemy.setSize(50, 50);
        enemy.setCurrentState(EnemyState.ATTACK);

        Wizard wizard = game.getCurrentWizard();
        wizard.addListener(new ClickListener() {
            public void clicked (InputEvent event, float x, float y) {
                Actor actor = event.getListenerActor();
                imageClick(actor);
            }
        });
        wizard.setPosition(100, GameData.HEIGHT/2-70);
        wizard.setCurrentState(WizardState.IDLE);
        wizard.setUserObject(enemy);
        stage.addActor(wizard);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = game.getGameFont();

        Label label = new Label("texto", labelStyle);
        label.setPosition(50 , GameData.HEIGHT - 70);
        label.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                Label l = (Label) this.actor;
                l.setText(BigNumberNotation.getPrintableValue(game.getMoney()));
                return false;
            }
        });

        stage.addActor(createAutoClicker);
        stage.addActor(label);
        stage.addActor(enemy);

        createFooterGUI();
    }

    private void createFooterGUI() {
        Wizard lightningWizard = new LightningWizard();
        Wizard fireWizard = new FireWizard();
        Wizard iceWizard = new IceWizard();

        TextureRegion lightningIdleSprite = lightningWizard.getIdleSprite();
        TextureRegion fireIdleSprite = fireWizard.getIdleSprite();
        TextureRegion iceIdleSprite = iceWizard.getIdleSprite();

        Image lightningWizardImage = new Image(lightningIdleSprite);
        Image fireWizardImage = new Image(fireIdleSprite);
        Image iceWizardImage = new Image(iceIdleSprite);

        lightningWizardImage.setUserObject(lightningWizard);
        fireWizardImage.setUserObject(fireWizard);
        iceWizardImage.setUserObject(iceWizard);

        ClickListener clickListener = new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Wizard wizard = (Wizard) event.getListenerActor().getUserObject();
                game.setCurrentWizard(wizard);

                Array<Actor> actors = stage.getActors();
                int changeIndex = -1;
                for (int i = 0; i < actors.size; i++){
                    Actor actor = actors.get(i);
                    if (actor instanceof Wizard){
                        changeIndex = i;
                    }
                }
                float actorX = actors.get(changeIndex).getX();
                float actorY = actors.get(changeIndex).getY();
                wizard.setPosition(actorX, actorY);
                wizard.setCurrentState(WizardState.IDLE);

                actors.set(changeIndex, wizard);

                System.out.println("Click wizard" + wizard);
            }
        };

        lightningWizardImage.addListener(clickListener);
        fireWizardImage.addListener(clickListener);
        iceWizardImage.addListener(clickListener);

        lightningWizardImage.setBounds(0,10, 64, 64);
        fireWizardImage.setBounds(70,10, 64, 64);
        iceWizardImage.setBounds(140,10, 64, 64);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = game.getGameFontSmall();

        Label chooseWizardLabel = new Label("Change Current Wizard", labelStyle);
        Label healthPotionLabel = new Label("Use Health Potion", labelStyle);
        Label actionsLabel = new Label("Other Actions", labelStyle);

        Image minorHealthPotionButton = new MinorHealthPotion().getImage();
        Image smallHealthPotionButton = new SmallHealthPotion().getImage();
        Image greatHealthPotionButton = new GreatHealthPotion().getImage();
        Image greaterHealthPotionButton = new GreaterHealthPotion().getImage();

        minorHealthPotionButton.setUserObject(HealthPotionEnum.MinorHealthPotion);
        smallHealthPotionButton.setUserObject(HealthPotionEnum.SmallHealthPotion);
        greatHealthPotionButton.setUserObject(HealthPotionEnum.GreatHealthPotion);
        greaterHealthPotionButton.setUserObject(HealthPotionEnum.GreaterHealthPotion);

        final Label minorHealthPotionQuantityLabel = new Label("0", labelStyle);
        Label smallHealthPotionQuantityLabel = new Label("0", labelStyle);
        Label greatHealthPotionQuantityLabel = new Label( "0", labelStyle);
        Label greaterHealthPotionQuantityLabel = new Label("0", labelStyle);

        minorHealthPotionQuantityLabel.setUserObject(HealthPotionEnum.MinorHealthPotion);
        smallHealthPotionQuantityLabel.setUserObject(HealthPotionEnum.SmallHealthPotion);
        greatHealthPotionQuantityLabel.setUserObject(HealthPotionEnum.GreatHealthPotion);
        greaterHealthPotionQuantityLabel.setUserObject(HealthPotionEnum.GreaterHealthPotion);

        minorHealthPotionQuantityLabel.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                Label label = (Label) this.getActor();
                HealthPotionEnum healthPotionEnum = (HealthPotionEnum) label.getUserObject();
                label.setText(game.getPotions().get(healthPotionEnum).toString());
                return false;
            }
        });

        smallHealthPotionQuantityLabel.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                Label label = (Label) this.getActor();
                HealthPotionEnum healthPotionEnum = (HealthPotionEnum) label.getUserObject();
                label.setText(game.getPotions().get(healthPotionEnum).toString());
                return false;
            }
        });

        greatHealthPotionQuantityLabel.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                Label label = (Label) this.getActor();
                HealthPotionEnum healthPotionEnum = (HealthPotionEnum) label.getUserObject();
                label.setText(game.getPotions().get(healthPotionEnum).toString());
                return false;
            }
        });

        greaterHealthPotionQuantityLabel.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                Label label = (Label) this.getActor();
                HealthPotionEnum healthPotionEnum = (HealthPotionEnum) label.getUserObject();
                label.setText(game.getPotions().get(healthPotionEnum).toString());
                return false;
            }
        });

        minorHealthPotionButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                HealthPotionEnum healthPotionEnum = (HealthPotionEnum) event.getListenerActor().getUserObject();
                game.useHealthPotion(healthPotionEnum);

//                CooldownTimer cooldownTimerBlue = new CooldownTimer(true);
//                cooldownTimerBlue.setSize(64, 64);
//                cooldownTimerBlue.setPosition(500, 100);
//                cooldownTimerBlue.setColor(Color.BLUE);
//                minorHealthPotionQuantityLabel.getParent().addActor(cooldownTimerBlue);
//
//                cooldownTimers.add(cooldownTimerBlue);
            }
        });

        smallHealthPotionButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                HealthPotionEnum healthPotionEnum = (HealthPotionEnum) event.getListenerActor().getUserObject();
                game.useHealthPotion(healthPotionEnum);
                System.out.println("used potion: " + healthPotionEnum);
            }
        });

        greatHealthPotionButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                HealthPotionEnum healthPotionEnum = (HealthPotionEnum) event.getListenerActor().getUserObject();
                game.useHealthPotion(healthPotionEnum);
                System.out.println("used potion: " + healthPotionEnum);
            }
        });

        greaterHealthPotionButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                HealthPotionEnum healthPotionEnum = (HealthPotionEnum) event.getListenerActor().getUserObject();
                game.useHealthPotion(healthPotionEnum);
                System.out.println("used potion: " + healthPotionEnum);
            }
        });

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = game.getGameFontSmall();
        textButtonStyle.fontColor = Color.BLUE;

        TextButton shopButton = new TextButton("Shop", textButtonStyle);
        shopButton.addListener(new ClickListener(){
            public void clicked (InputEvent event, float x, float y) {
                game.changeScreen(new HealthPotionShopScreen(game));
            }
        });

        TextButton upgradeButton = new TextButton("Upgrade", textButtonStyle);
        upgradeButton.addListener(new ClickListener(){
            public void clicked (InputEvent event, float x, float y) {
                game.changeScreen(new UpgradeScreen(game));
            }
        });

        TextButton achievmentsButton = new TextButton("Achievments", textButtonStyle);

        Table tab = new Table();
        tab.add(shopButton);
        tab.row();
        tab.add(upgradeButton);
        tab.row();
        tab.add(achievmentsButton);
        tab.row();

        Table t = new Table();
        t.setBounds(0,0, Gdx.graphics.getWidth(), 300);
        t.add(chooseWizardLabel).colspan(3);
        t.add(healthPotionLabel).colspan(4);
        t.add(actionsLabel).colspan(1);
        t.row().height(64);
        t.add(lightningWizardImage).width(64);
        t.add(fireWizardImage).width(64);
        t.add(iceWizardImage).width(64);
        t.add(minorHealthPotionButton).width(64);
        t.add(smallHealthPotionButton).width(64);
        t.add(greatHealthPotionButton).width(64);
        t.add(greaterHealthPotionButton).width(64);
        t.add(tab);
        t.row().height(10);
        t.add().colspan(3);
        t.add(minorHealthPotionQuantityLabel).align(Align.right);
        t.add(smallHealthPotionQuantityLabel).align(Align.right);
        t.add(greatHealthPotionQuantityLabel).align(Align.right);
        t.add(greaterHealthPotionQuantityLabel).align(Align.right);

        stage.addActor(t);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);


    }

    private void imageClick(Actor actor) {
        Float increaseValue = 10000 * Gdx.graphics.getDeltaTime();
        game.addMoney(new BigInteger(String.valueOf(increaseValue.intValue())));
        actor.setOrigin(Align.center);
        actor.rotateBy(90);
    }

    @Override
    public void render(float delta) {
		ScreenUtils.clear(0, 0, 0, 1);

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)){
            game.resetMoney();
            game.resetHealthPotions();
            Array<Actor> actors = stage.getActors();
            for (Actor actor : actors) {
                if (actor instanceof Wizard){
                    Wizard wizard = (Wizard) actor;
                    wizard.setCurrentState(WizardState.IDLE);
                } else if (actor instanceof Enemy){
                    Enemy enemy = (Enemy) actor;
                    enemy.setCurrentState(EnemyState.HURT);
                }
            }
        }

//        boolean shouldRemove = false;
//        CooldownTimer cooldownTimerToRemove = null;
//        if (System.currentTimeMillis() - lastUpdate > 25L) {
//            for (CooldownTimer cooldownTimer : this.cooldownTimers) {
//                if (remainingPercentage <= 0.0f) {
//                    shouldRemove = true;
//                    cooldownTimerToRemove = cooldownTimer;
//                } else {
//                    cooldownTimer.update(remainingPercentage);
//                }
//            }
//
//            if (shouldRemove) {
//                cooldownTimerToRemove.update(1);
//                cooldownTimers.remove(cooldownTimerToRemove);
//            }
//
//            remainingPercentage -= 0.01f;
//            lastUpdate = System.currentTimeMillis();
//        }

        stage.act();
        stage.draw();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            this.game.changePreviousScreen();
        }
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
        timer.stop();
    }
}
