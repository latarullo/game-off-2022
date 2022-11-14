package com.mygdx.game.screen.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.GameOff2022;
import com.mygdx.game.screen.NewGameScreen;

import java.math.BigInteger;

public class AttackDamageParticle extends Actor {

    private Wizard wizard;

    public AttackDamageParticle (final Wizard wizard, BigInteger damage){
        this.wizard = wizard;

        Enemy enemy = (Enemy) wizard.getUserObject();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/AvQest.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 40;
        BitmapFont gameFont = generator.generateFont(parameter);

        generator.dispose();

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = gameFont;
        labelStyle.fontColor = Color.RED;

        MyLabel label = new MyLabel(damage.toString(), labelStyle, enemy);
        wizard.getParent().addActor(label);
    }
}

class MyLabel extends Label {

    protected float stateTime = 0;
    private Enemy enemy;

    public MyLabel(String text, LabelStyle labelStyle, Enemy enemy) {
        super(text, labelStyle);
        this.enemy = enemy;
        this.setX(enemy.getX() + enemy.getWidth() / 2);
        this.setY(enemy.getY() + enemy.getHeight() / 2);
        enemy.getParent().addActor(this);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        stateTime += delta;
        this.setY(this.getY()+1);
        if (stateTime > 2){
            enemy.getParent().removeActor(this);
        }
    }
}