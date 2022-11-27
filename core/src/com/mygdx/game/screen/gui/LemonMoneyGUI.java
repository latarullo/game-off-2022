package com.mygdx.game.screen.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.GameOff2022;
import com.mygdx.game.domain.GameConstants;
import com.mygdx.game.util.BigNumberNotation;
import com.mygdx.game.util.GameFontGenerator;
import com.mygdx.game.util.GameFontSizeEnum;

public class LemonMoneyGUI implements Disposable {

    private GameOff2022 game;
    private Texture lemonTexture = new Texture(Gdx.files.internal("resources/Icons/lemon.png"));
    private Image image = new Image(lemonTexture);

    private GameFontGenerator gameFontGenerator = GameFontGenerator.getInstance();

    public LemonMoneyGUI(GameOff2022 game) {
        this.game = game;
    }

    public Table createTable() {
        Table table = new Table();
        table.add(image).size(64, 64);

        Label.LabelStyle labelStyle = gameFontGenerator.generateLabelStyle(GameFontSizeEnum.NORMAL, GameConstants.GUI_MONEY_COLOR);

        Label currentMoney = new Label(game.getGameData().getMoney().toString(), labelStyle);

        currentMoney.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                Label label = (Label) this.getActor();
                CharSequence printableValue = game.getGameData().getMoney().toString();
                printableValue = BigNumberNotation.getPrintableValue(game.getGameData().getMoney(), true);
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
