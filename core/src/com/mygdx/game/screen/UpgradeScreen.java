package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.GameOff2022;
import com.mygdx.game.screen.gui.UpgradeScreenGUI;

public class UpgradeScreen implements Screen {
    private GameOff2022 game;
    private Stage stage;
    private UpgradeScreenGUI gui;

    public UpgradeScreen(GameOff2022 game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());

        gui = new UpgradeScreenGUI(game, this);
        stage.addActor(gui.createGUI());
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

    public Stage getStage() {
        return stage;
    }

    public GameOff2022 getGame() {
        return game;
    }

    public UpgradeScreenGUI getGui() {
        return gui;
    }

//TODO: AUTO-CLICKER code is here - BEGIN
//
//        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
//        textButtonStyle.font = game.getGameFont();

//        TextButton createAutoClicker = new TextButton("Create Auto-Clicker", textButtonStyle);

//        createAutoClicker.setPosition(0, 200);
//        createAutoClicker.setSize(500,50);

//        createAutoClicker.addListener(new ClickListener(){
//            public void clicked (InputEvent event, float x, float y) {
//                autoClickers++;
//                Timer.Task clickerTask = new Timer.Task() {
//                    @Override
//                    public void run() {
//                        Array.ArrayIterator<Actor> iterator = stage.getActors().iterator();
//                        while(iterator.hasNext()){
//                            Actor actor = iterator.next();
//                            if (actor instanceof Wizard){
//                                imageClick(actor);
//                            }
//                        }
//
//                    }
//                };
//                timer.scheduleTask(clickerTask, 1f, 1);
//                TextButton textButton = (TextButton) event.getListenerActor();
//                //textButton.setText(String.format("Create new Auto-Clicker (%s running)", autoClickers));
//                textButton.setText("Create new Auto-Clicker (" + autoClickers + " running)");
//            }
//        });

//        Enemy enemy = new Enemy(game);
//        enemy.addListener(new ClickListener() {
//            public void clicked(InputEvent event, float x, float y) {
//                game.getCurrentWizard().setCurrentState(WizardState.ATTACK);
//            }
//        });
//        enemy.setPosition(500, 200);
//        enemy.setSize(50, 50);
//        enemy.setCurrentState(EnemyState.ATTACK);
//        stage.addActor(enemy);
//TODO: AUTO-CLICKER code is here - END

}
