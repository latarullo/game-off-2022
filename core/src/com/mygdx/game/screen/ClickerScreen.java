package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
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
import com.mygdx.game.screen.actor.Enemy;
import com.mygdx.game.screen.actor.EnemyState;
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

    public ClickerScreen(GameOff2022 game){
        this.game = game;
        stage = new Stage(new ScreenViewport());

        timer = new Timer();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = game.getGameFont();

        TextButton createAutoClicker = new TextButton("Create Auto-Clicker", textButtonStyle);

        createAutoClicker.setPosition(0, 100);
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

        Wizard wizard = new LightningWizard();
        wizard.addListener(new ClickListener() {
            public void clicked (InputEvent event, float x, float y) {
                Actor actor = event.getListenerActor();
                imageClick(actor);
            }
        });
        wizard.setPosition(100, GameData.HEIGHT/2-70);
        wizard.setCurrentState(WizardState.ATTACK);

        Enemy enemy = new Enemy();
        enemy.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Enemy enemy = (Enemy) event.getListenerActor();
                enemy.setCurrentState(EnemyState.DIE);
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
//        stage.addActor(image);
        stage.addActor(wizard);
        stage.addActor(enemy);

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
