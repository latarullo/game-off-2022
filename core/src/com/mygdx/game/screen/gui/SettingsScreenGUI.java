package com.mygdx.game.screen.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.GameOff2022;
import com.mygdx.game.controller.GameSettings;
import com.mygdx.game.domain.GameConstants;
import com.mygdx.game.screen.SettingsScreen;
import com.mygdx.game.util.GameFontGenerator;
import com.mygdx.game.util.GameFontSizeEnum;

public class SettingsScreenGUI {

    private SettingsScreen screen;
    private GameOff2022 game = GameOff2022.getInstance();
    private Sound typingSound;
    private GameFontGenerator gameFontGenerator = GameFontGenerator.getInstance();

    public SettingsScreenGUI(SettingsScreen screen) {
        this.screen = screen;
    }

    public void createGUI() {
        createBackButton();
        createCreditsHeader();
        createOptions();
    }

    private void createOptions() {
        final GameSettings gameSettings = GameSettings.getInstance();

        Label.LabelStyle labelStyle = gameFontGenerator.generateLabelStyle(GameFontSizeEnum.NORMAL, Color.WHITE);
        Label volumeMusicLabel = new Label("Volume", labelStyle);
        Label volumeSoundLabel = new Label("Volume", labelStyle);
        Label musicOnOffLabel = new Label("Music", labelStyle);
        Label soundOnOffLabel = new Label("Sound", labelStyle);
        Label achievementsNotificationLabel = new Label("Achievements Notification", labelStyle);

        Pixmap pixmapGreen = new Pixmap(100, 10, Pixmap.Format.RGBA8888);
        pixmapGreen.setColor(0, 1, 0, 1);
        pixmapGreen.fill();

        Pixmap pixmapBlack = new Pixmap(10, 10, Pixmap.Format.RGBA8888);
        pixmapBlack.setColor(1, 1, 1, 1);
        pixmapBlack.fill();

        Pixmap pixmapWhite = new Pixmap(10, 10, Pixmap.Format.RGBA8888);
        pixmapWhite.setColor(0, 0, 0, 1);
        pixmapWhite.fill();

        Slider.SliderStyle sliderStyle = new Slider.SliderStyle();
        sliderStyle.knob = new TextureRegionDrawable(new Texture(pixmapBlack));
        sliderStyle.knobDown = new TextureRegionDrawable(new Texture(pixmapWhite));
        sliderStyle.knobOver = new TextureRegionDrawable(new Texture(pixmapWhite));
        sliderStyle.background = new TextureRegionDrawable(new Texture(pixmapGreen));

        final Slider volumeMusicSlider = new Slider(0.1f, 1f, 0.1f, false, sliderStyle);
        volumeMusicSlider.setValue(gameSettings.getMusicVolume());
        volumeMusicSlider.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                gameSettings.setMusicVolume(volumeMusicSlider.getValue());
                return false;
            }
        });

        final Slider volumeSoundSlider = new Slider(0.1f, 1f, 0.1f, false, sliderStyle);
        volumeSoundSlider.setValue(gameSettings.getSoundVolume());
        volumeSoundSlider.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                gameSettings.setSoundVolume(volumeSoundSlider.getValue());
                return false;
            }
        });

        CheckBox.CheckBoxStyle checkBoxStyle = new CheckBox.CheckBoxStyle();
        labelStyle = gameFontGenerator.generateLabelStyle(GameFontSizeEnum.NORMAL, Color.WHITE);
        checkBoxStyle.font = labelStyle.font;
        checkBoxStyle.checkboxOn = new TextureRegionDrawable(new Texture(Gdx.files.internal("resources/SettingsScreen/on.png")));
        checkBoxStyle.checkboxOff = new TextureRegionDrawable(new Texture(Gdx.files.internal("resources/SettingsScreen/off.png")));

        final CheckBox musicCheckbox = new CheckBox(null, checkBoxStyle);
        musicCheckbox.setChecked(gameSettings.isMusicEnabled());
        musicCheckbox.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                gameSettings.setMusicEnabled(musicCheckbox.isChecked());
                return false;
            }
        });

        final CheckBox soundCheckBox = new CheckBox(null, checkBoxStyle);
        soundCheckBox.setChecked(gameSettings.isSoundEnabled());
        soundCheckBox.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                gameSettings.setSoundEnabled(soundCheckBox.isChecked());
                return false;
            }
        });

        final CheckBox achievementNotificationCheckbox = new CheckBox(null, checkBoxStyle);
        achievementNotificationCheckbox.setChecked(gameSettings.isAchievementsNotificationEnabled());
        achievementNotificationCheckbox.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                gameSettings.setAchievementsNotificationEnabled(achievementNotificationCheckbox.isChecked());
                return false;
            }
        });

        Table table = new Table();
        table.setFillParent(true);
        table.setDebug(false);

        table.add(musicOnOffLabel).align(Align.left).padRight(20);
        table.add(musicCheckbox).padRight(20);
        table.add(volumeMusicLabel).align(Align.left).padRight(20);
        table.add(volumeMusicSlider).width(200).padRight(20);
        table.row();
        table.add(soundOnOffLabel).align(Align.left).padRight(20);
        table.add(soundCheckBox).padRight(20);
        table.add(volumeSoundLabel).align(Align.left).padRight(20);
        table.add(volumeSoundSlider).width(200).padRight(20);
        table.row();
        table.add(achievementsNotificationLabel).colspan(4).align(Align.left).padRight(20);
        table.add(achievementNotificationCheckbox).padRight(20);
        table.row();


        screen.getStage().addActor(table);
    }

    private void createCreditsHeader() {
        Label.LabelStyle labelHeaderStyle = gameFontGenerator.generateLabelStyle(GameFontSizeEnum.NORMAL, Color.WHITE);
        Label consumablesLabel = new Label("Settings", labelHeaderStyle);
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

    public SettingsScreen getScreen() {
        return screen;
    }
}
