package com.mygdx.game.screen.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.domain.GameConstants;
import com.mygdx.game.util.BigNumberNotation;
import com.mygdx.game.util.GameFontGenerator;
import com.mygdx.game.util.GameFontSizeEnum;

import java.math.BigInteger;

public class LemonMoneyFieldGUI implements Disposable {
    private Texture lemonTexture = new Texture(Gdx.files.internal("resources/Icons/lemon.png"));
    private Image lemonImage = new Image(lemonTexture);
    private GameFontGenerator gameFontGenerator = GameFontGenerator.getInstance();

    public Table createTable(BigInteger fieldValue) {
        Label.LabelStyle labelStyle = gameFontGenerator.generateLabelStyle(GameFontSizeEnum.NORMAL, GameConstants.GUI_MONEY_COLOR);

        String printableValue = BigNumberNotation.getPrintableValue(fieldValue);
        Label lemonValue = new Label(printableValue, labelStyle);

        Table table = new Table();
        table.add(lemonImage).size(32, 32);
        table.add(lemonValue);

        return table;
    }

    @Override
    public void dispose() {
        lemonTexture.dispose();
    }
}
