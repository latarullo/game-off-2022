package com.mygdx.game.screen.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.GameOff2022;
import com.mygdx.game.domain.GameConstants;
import com.mygdx.game.screen.CreditsScreen;
import com.mygdx.game.screen.GameOverScreen;
import com.mygdx.game.util.GameFontGenerator;
import com.mygdx.game.util.GameFontSizeEnum;

public class GameOverScreenGUI {

    private GameOverScreen screen;
    private GameOff2022 game = GameOff2022.getInstance();
    private Sound typingSound;
    private GameFontGenerator gameFontGenerator = GameFontGenerator.getInstance();
    private float stateTime;

    public GameOverScreenGUI(GameOverScreen screen) {
        this.screen = screen;
    }

    public void createGUI() {
        //createBackToMainMenuButton();
        createHeader();
        createDetails();
        createFooter();
    }

    private void createFooter() {
        Label.LabelStyle footerLabelStyle = gameFontGenerator.generateLabelStyle(GameFontSizeEnum.SMALL, Color.RED);
        final Label footerLabel = new Label("", footerLabelStyle);

        footerLabel.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                stateTime += delta;
                int creditsCountDown =  15 - (int) stateTime;
                footerLabel.setText("Going to credits in " + creditsCountDown);
                if (stateTime > 15){
                    GameOff2022 gameOff2022 = GameOff2022.getInstance();
                    gameOff2022.gameOver();
                    game.setScreen(new CreditsScreen(true));
                    return true;
                }
                return false;
            }
        });

        Table table = new Table();
        table.setFillParent(true);
        table.center().bottom();
        table.add(footerLabel);
        screen.getStage().addActor(table);
    }

    private void createDetails() {
        Image image1 = new Image(new Texture(Gdx.files.internal("resources/GameOverScreen/wizard-drinking-lemonade-01.png")));
        Image image2 = new Image(new Texture(Gdx.files.internal("resources/GameOverScreen/wizard-drinking-lemonade-02.png")));
        Image image3 = new Image(new Texture(Gdx.files.internal("resources/GameOverScreen/wizard-drinking-lemonade-03.png")));
        Image image4 = new Image(new Texture(Gdx.files.internal("resources/GameOverScreen/wizard-drinking-lemonade-04.png")));

        Label.LabelStyle labelStyle = gameFontGenerator.generateLabelStyle(GameFontSizeEnum.SMALL, Color.GREEN);

        TypingLabel thanksLabel = new TypingLabel("Thanks for helping us with those lemons!\n" +
                "At last, we can have some lemonade!",labelStyle );

        Table table = new Table();
        table.setFillParent(true);
        table.add(thanksLabel).width(500).colspan(2);
        table.row();
        table.add(image1).size(200,200);
        table.add(image2).size(200,200);
        table.row();
        table.add(image3).size(200,200);
        table.add(image4).size(200,200);
        table.row();
//        table.add(timeToGetSomeLemonadeLabel).colspan(2);

        screen.getStage().addActor(table);
    }

    private void createHeader() {
        Label.LabelStyle labelHeaderStyle = gameFontGenerator.generateLabelStyle(GameFontSizeEnum.NORMAL, Color.RED);
        Label header = new Label("Congratulations!", labelHeaderStyle);
        Label.LabelStyle labelSubHeaderStyle = gameFontGenerator.generateLabelStyle(GameFontSizeEnum.SMALL, Color.RED);
        Label subheader = new Label("Life gave you lemons and you made a lemonade.", labelSubHeaderStyle);

        Table table = new Table();
        table.setFillParent(true);
        table.center().top();
        table.add(header);
        table.row();
        table.add(subheader);
        screen.getStage().addActor(table);
    }

    private void createBackToMainMenuButton() {
        ImageButton backButton = new ImageButton(new TextureRegionDrawable(new Texture(Gdx.files.internal("resources/Icons/back.png"))));
        backButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                GameOff2022 gameOff2022 = GameOff2022.getInstance();
                gameOff2022.gameOver();
                game.setScreen(new CreditsScreen(true));
            }
        });
        backButton.setPosition(10, GameConstants.HEIGHT - backButton.getHeight());
        screen.getStage().addActor(backButton);
    }
}
