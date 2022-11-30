package com.mygdx.game.screen.gui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.GameOff2022;
import com.mygdx.game.domain.GameConstants;
import com.mygdx.game.screen.CreditsScreen;
import com.mygdx.game.util.GameFontGenerator;
import com.mygdx.game.util.GameFontSizeEnum;

public class CreditsScreenGUI {

    private final String developedByString = "Developed by: Latarullo";
    private final String artsByString = "Arts by: hamburger & nooders";
    private final String composedByString = "Composed by: Dolphinflavored";
    private final String voiceActingByString = "Voice acting by: samsamsoup";


    private CreditsScreen screen;
    private GameOff2022 game = GameOff2022.getInstance();
    private GameFontGenerator gameFontGenerator = GameFontGenerator.getInstance();

    public CreditsScreenGUI(CreditsScreen screen) {
        this.screen = screen;
    }

    public Table createGUI() {
        createBackButton();
        createCreditsHeader();

        Table table = new Table();
        table.setFillParent(true);

        Label.LabelStyle labelStyle = gameFontGenerator.generateLabelStyle(GameFontSizeEnum.NORMAL, GameConstants.GUI_CREDITS_TYPED_COLOR);

        TypingLabel gameDeveloperLabel = new TypingLabel(developedByString, labelStyle);
        TypingLabel gameArtistLabel = new TypingLabel(artsByString, labelStyle);
        TypingLabel gameComposerLabel = new TypingLabel(composedByString, labelStyle);
        TypingLabel voiceActingLabel = new TypingLabel(voiceActingByString, labelStyle);

        table.pad(10, 0, 10, 0);
        table.add(gameDeveloperLabel).fill().uniformX();
        table.row();
        table.add(gameArtistLabel).fill().uniformX();
        table.row();
        table.add(gameComposerLabel).fill();
        table.row();
        table.add(voiceActingLabel).fill();

        return table;
    }

    private void createCreditsHeader() {
        Label.LabelStyle labelHeaderStyle = gameFontGenerator.generateLabelStyle(GameFontSizeEnum.NORMAL, Color.WHITE);
        Label consumablesLabel = new Label("Credits", labelHeaderStyle);
        consumablesLabel.setPosition(GameConstants.WIDTH / 2 - consumablesLabel.getWidth() / 2, GameConstants.HEIGHT - consumablesLabel.getHeight() - 10);
        screen.getStage().addActor(consumablesLabel);
    }

    private void createBackButton() {
        ImageButton backButton = new ImageButton(new TextureRegionDrawable(new Texture(Gdx.files.internal("resources/Icons/back.png"))));
        backButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.changePreviousScreen();
            }
        });
        backButton.setPosition(10, GameConstants.HEIGHT - backButton.getHeight());
        screen.getStage().addActor(backButton);
    }

    public CreditsScreen getScreen() {
        return screen;
    }
}
