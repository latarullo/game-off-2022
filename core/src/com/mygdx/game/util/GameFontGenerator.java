package com.mygdx.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class GameFontGenerator {
    private static final GameFontGenerator instance = new GameFontGenerator();

    public static GameFontGenerator getInstance() {
        return instance;
    }

    private FileHandle fontFile;
    private BitmapFont smallFont;
    private BitmapFont font;
    private BitmapFont largeFont;

    private GameFontGenerator() {
        fontFile = Gdx.files.internal("fonts/wheaton capitals.ttf");

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFile);

        FreeTypeFontGenerator.FreeTypeFontParameter parameterSmall = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameterSmall.size = 20;
        smallFont = generator.generateFont(parameterSmall);

        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 50;
        font = generator.generateFont(parameter);

        FreeTypeFontGenerator.FreeTypeFontParameter parameterLarge = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameterLarge.size = 100;
        largeFont = generator.generateFont(parameterLarge);

        generator.dispose();
    }

    public Label.LabelStyle generateLabelStyle(int size, Color color) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFile);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = generator.generateFont(createFontParameter(size, color));
        generator.dispose();

        return labelStyle;
    }

    public TextButton.TextButtonStyle generateTextButtonStyle(GameFontSizeEnum gameFontSizeEnum, Color color) {
        BitmapFont bitmapFont = getFont();

        if (gameFontSizeEnum == GameFontSizeEnum.SMALL) {
            bitmapFont = getSmallFont();
        } else if (gameFontSizeEnum == GameFontSizeEnum.NORMAL) {
            bitmapFont = getFont();
        } else if (gameFontSizeEnum == GameFontSizeEnum.LARGE) {
            bitmapFont = getLargeFont();
        }

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = bitmapFont;
        textButtonStyle.fontColor = color;

        return textButtonStyle;
    }

    public Label.LabelStyle generateLabelStyle(GameFontSizeEnum gameFontSizeEnum, Color color) {
        BitmapFont bitmapFont = getFont();

        if (gameFontSizeEnum == GameFontSizeEnum.SMALL) {
            bitmapFont = getSmallFont();
        } else if (gameFontSizeEnum == GameFontSizeEnum.NORMAL) {
            bitmapFont = getFont();
        } else if (gameFontSizeEnum == GameFontSizeEnum.LARGE) {
            bitmapFont = getLargeFont();
        }

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = bitmapFont;
        labelStyle.fontColor = color;

        return labelStyle;
    }

    public TextButton.TextButtonStyle generateTextButtonStyle(int size, Color color) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFile);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = generator.generateFont(createFontParameter(size, color));
        generator.dispose();

        return textButtonStyle;
    }

    private FreeTypeFontGenerator.FreeTypeFontParameter createFontParameter(int size, Color color) {
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = size;
        parameter.color = color;

        return parameter;
    }

    public BitmapFont getSmallFont() {
        return smallFont;
    }

    public BitmapFont getFont() {
        return font;
    }

    public BitmapFont getLargeFont() {
        return largeFont;
    }
}
