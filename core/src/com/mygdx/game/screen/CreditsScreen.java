package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.GameOff2022;
import com.mygdx.game.screen.gui.CreditsScreenGUI;
import com.mygdx.game.screen.gui.TypingLabel;

public class CreditsScreen implements Screen {
    private GameOff2022 game;
    private Stage stage;
    private Sound typingSound = Gdx.audio.newSound(Gdx.files.internal("sounds/keyboard-typing.wav"));
    private Sound creditsSound = Gdx.audio.newSound(Gdx.files.internal("resources/CreditsScreen/music.mp3"));

    private TextureAtlas atlasDancingLemons = new TextureAtlas(Gdx.files.internal("animations/dancing-lemon.atlas"));
    private TextureAtlas atlasDancingWizards = new TextureAtlas(Gdx.files.internal("animations/dancing-wizard.atlas"));
    private Animation<TextureRegion> dancingLemonAnimation;
    private Animation<TextureRegion> dancingWizardAnimation;
    private float stateTime;

    private CreditsScreenGUI gui;

    public CreditsScreen(GameOff2022 game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        gui = new CreditsScreenGUI(this);
        stage.addActor(gui.createGUI());

        dancingLemonAnimation = new Animation<TextureRegion>(0.1f, atlasDancingLemons.findRegions("dancing-lemon"), Animation.PlayMode.LOOP);
        dancingWizardAnimation = new Animation<TextureRegion>(0.06f, atlasDancingWizards.findRegions("dancing-wizard"), Animation.PlayMode.LOOP);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        creditsSound.loop();
       // typingSound.loop();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);

        stateTime += delta;

        TextureRegion dancingLemonAnimationKeyFrame = dancingLemonAnimation.getKeyFrame(stateTime);
        TextureRegion dancingWizardAnimationKeyFrame = dancingWizardAnimation.getKeyFrame(stateTime);

        //more wizards and lemons? moving wizards/lemons? flipping wizards/lemons?
        game.getBatch().begin();
        game.getBatch().draw(dancingLemonAnimationKeyFrame, 0,0, 128, 128);
        game.getBatch().draw(dancingWizardAnimationKeyFrame, Gdx.graphics.getWidth() - 128, 0, 128, 128);
        game.getBatch().end();

        stage.act();
        stage.draw();

        checkAndStopTypingShound();
    }

    private void checkAndStopTypingShound() {
        boolean shouldStopTypingSound = true;
        for (Actor actor : stage.getActors()) {
            if (actor instanceof Table) {
                Table table = (Table) actor;
                for (Actor tableChild : table.getChildren()) {
                    if (tableChild instanceof TypingLabel) {
                        TypingLabel typingLabel = (TypingLabel) tableChild;
                        if (!typingLabel.isTypingComplete()) {
                            shouldStopTypingSound = false;
                        }
                    }
                }
            }
        }
        if (shouldStopTypingSound) {
            typingSound.stop();
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
        typingSound.stop();
        creditsSound.stop();
    }

    @Override
    public void dispose() {
        typingSound.dispose();
        stage.dispose();
        creditsSound.dispose();
    }

    public Stage getStage() {
        return stage;
    }
}
