package com.mygdx.game.screen.gui.component;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.GameOff2022;
import com.mygdx.game.screen.NewGameScreen;

public class RestartScreenGUI implements Disposable {

    private GameOff2022 game;

    public RestartScreenGUI(GameOff2022 game){
        this.game = game;
    }

    public Table createGUI(){
        Table table = new Table();
        table.setWidth(Gdx.graphics.getWidth());
        table.setHeight(Gdx.graphics.getHeight());

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = game.getGameFontLarge();
        labelStyle.fontColor = Color.GREEN;

        Label restartLabel1 = new Label("You never lose", labelStyle);
        Label restartLabel2 = new Label("You either win",labelStyle);
        Label restartLabel3 = new Label("Or you learn",labelStyle);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = game.getGameFont();
        textButtonStyle.fontColor = Color.BLUE;
        textButtonStyle.disabledFontColor = Color.GRAY;

        TextButton restartButton = new TextButton("Restart Game", textButtonStyle);
        restartButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.changeScreen(new NewGameScreen(game));
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

    @Override
    public void dispose() {
    }
}
