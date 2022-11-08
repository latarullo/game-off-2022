package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
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
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.BigNumberNotation;
import com.mygdx.game.GameData;
import com.mygdx.game.GameOff2022;
import com.mygdx.game.domain.GreatHealthPotion;
import com.mygdx.game.domain.GreaterHealthPotion;
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

public class ClickerScreen implements Screen {

    private GameOff2022 game;
    private Stage stage;
    private Timer timer;
    private int autoClickers;

    private BigInteger value = new BigInteger("0");

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


        Enemy enemy = new Enemy();
        enemy.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.getCurrentWizard().setCurrentState(WizardState.ATTACK);
            }
        });
        enemy.setPosition(500, GameData.HEIGHT/2-70);
        enemy.setSize(50, 50);
        enemy.setCurrentState(EnemyState.HURT);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = game.getGameFont();

        Label label = new Label("texto", labelStyle);
        label.setPosition(50 , GameData.HEIGHT - 70);
        label.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                Label l = (Label) this.actor;
                l.setText(BigNumberNotation.getPrintableValue(value));
                return false;
            }
        });

        stage.addActor(createAutoClicker);
        stage.addActor(label);
        stage.addActor(enemy);

        createFooterGUI();
    }

    private void createFooterGUI() {
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = game.getGameFont();

        TextButton healthPotionButton = new TextButton("Shop", textButtonStyle);

        healthPotionButton.setPosition(0, 100);
        healthPotionButton.setSize(500,50);

        healthPotionButton.addListener(new ClickListener(){
            public void clicked (InputEvent event, float x, float y) {
                game.changeScreen(new HealthPotionShopScreen(game));
            }
        });

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

        Label minorHealthPotionQuantityLabel = new Label("13", labelStyle);
        Label smallHealthPotionQuantityLabel = new Label("44", labelStyle);
        Label greatHealthPotionQuantityLabel = new Label("100", labelStyle);
        Label greaterHealthPotionQuantityLabel = new Label("987", labelStyle);

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
        t.add(healthPotionButton);
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


        Wizard wizard = game.getCurrentWizard();
        wizard.addListener(new ClickListener() {
            public void clicked (InputEvent event, float x, float y) {
                Actor actor = event.getListenerActor();
                imageClick(actor);
            }
        });
        wizard.setPosition(100, GameData.HEIGHT/2-70);
        wizard.setCurrentState(WizardState.IDLE);
        stage.addActor(wizard);
    }

    private void imageClick(Actor actor) {
        Float increaseValue = 10000 * Gdx.graphics.getDeltaTime();
        value = value.add(new BigInteger(String.valueOf(increaseValue.intValue())));
        actor.setOrigin(Align.center);
        actor.rotateBy(90);
    }

    @Override
    public void render(float delta) {
		ScreenUtils.clear(0, 0, 0, 1);

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)){
            value = BigInteger.ZERO;
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
