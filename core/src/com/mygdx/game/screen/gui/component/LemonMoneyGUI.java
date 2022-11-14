package com.mygdx.game.screen.gui.component;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.GameOff2022;

public class LemonMoneyGUI implements Disposable {

    private GameOff2022 game;
    private Texture lemonTexture = new Texture(Gdx.files.internal("GUI/lemon-emoji.png"));
    private Image image = new Image(lemonTexture);

    public LemonMoneyGUI(GameOff2022 game){
        this.game = game;
    }

    public Table createTable(){
        Table table = new Table();
        table.add(image).size(64,64);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = game.getGameFont();
        labelStyle.fontColor = Color.GREEN;

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

        table.add(currentMoney);
        return table;
    }

    @Override
    public void dispose() {
        lemonTexture.dispose();
    }
}
