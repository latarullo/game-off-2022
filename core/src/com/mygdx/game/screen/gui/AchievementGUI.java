package com.mygdx.game.screen.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.GameOff2022;
import com.mygdx.game.controller.GameAchievements;
import com.mygdx.game.controller.GameSoundPlayer;
import com.mygdx.game.domain.GameConstants;
import com.mygdx.game.domain.GameData;
import com.mygdx.game.screen.GameOverScreen;
import com.mygdx.game.util.GameFontGenerator;
import com.mygdx.game.util.GameFontSizeEnum;

public class AchievementGUI extends Table implements Disposable {
    private float stateTime = 0;
    private Label achievementLabel;
    private Label achievementDetailLabel;
    private Label achievementTitleLabel;
    private Sound achievementSound = Gdx.audio.newSound(Gdx.files.internal("sounds/achievement-unlocked.wav"));
    private GameFontGenerator gameFontGenerator = GameFontGenerator.getInstance();

    public AchievementGUI(String title, String description) {
        Label.LabelStyle labelStyle = gameFontGenerator.generateLabelStyle(GameFontSizeEnum.NORMAL, GameConstants.GUI_ACHIEVEMENT_HEADER_COLOR);
        Label.LabelStyle labelSmallStyle = gameFontGenerator.generateLabelStyle(GameFontSizeEnum.SMALL, GameConstants.GUI_ACHIEVEMENT_DESCRIPTION_COLOR);

        achievementLabel = new Label("Achievement", labelStyle);
        achievementTitleLabel = new Label(title, labelSmallStyle);
        Image image = new Image(new Texture(Gdx.files.internal("resources/Icons/achievements.png")));
        achievementDetailLabel = new Label(description, labelSmallStyle);

        //TODO: THIS SHOULD WORK THIS WAY? - BEGIN
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGB888);
        pixmap.setColor(Color.DARK_GRAY);
        pixmap.fill();
        TextureRegionDrawable textureRegionDrawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        this.setBackground(textureRegionDrawable);
        pixmap.dispose();
        //TODO: THIS SHOULD WORK THIS WAY? - END

        this.add(achievementLabel).colspan(2);
        this.row();
        this.add(image).size(32, 32);
        this.add(achievementTitleLabel);
        this.row();
        this.add(achievementDetailLabel).colspan(2);

        this.setSize(400, 300);
        this.setPosition(Gdx.graphics.getWidth() - 400, -300);
        GameSoundPlayer.playSound(achievementSound);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (this.getY() < 0) {
            this.setY(this.getY() + 500 * delta);
        } else {
            stateTime += delta;
            if (stateTime > 3) {
                this.getParent().removeActor(this);
                if (GameData.getInstance().isLemonadeDone()){
                    GameOff2022.getInstance().changeScreen(new GameOverScreen());
                }
            }
        }
    }

    @Override
    public void dispose() {
        achievementSound.dispose();
    }
}