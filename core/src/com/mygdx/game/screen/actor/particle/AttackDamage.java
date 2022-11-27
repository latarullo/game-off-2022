package com.mygdx.game.screen.actor.particle;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.mygdx.game.screen.actor.Enemy;
import com.mygdx.game.screen.actor.Wizard;
import com.mygdx.game.util.BigNumberNotation;
import com.mygdx.game.util.GameFontGenerator;

import java.math.BigInteger;

public class AttackDamage extends Actor {

    private Wizard wizard;
    private GameFontGenerator gameFontGenerator = GameFontGenerator.getInstance();

    public AttackDamage(final Wizard wizard, BigInteger damage) {
        this.wizard = wizard;

        Enemy enemy = (Enemy) wizard.getUserObject();
        Label.LabelStyle labelStyle = gameFontGenerator.generateLabelStyle(40, wizard.getWizardType().getColor());

        MyLabel label = new MyLabel(BigNumberNotation.getPrintableValue(damage, false), labelStyle, enemy);
        wizard.getParent().addActor(label);
    }
}

class MyLabel extends Label {

    protected float stateTime = 0;
    private Group actorHolder;

    public MyLabel(String text, LabelStyle labelStyle, Enemy enemy) {
        super(text, labelStyle);
        this.setX(enemy.getX() + enemy.getWidth() / 2);
        this.setY(enemy.getY() + enemy.getHeight() / 2);
        this.actorHolder = enemy.getParent();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        stateTime += delta;
        this.setY(this.getY() + 1);
        if (stateTime > 2) {
            actorHolder.removeActor(this);
        }
    }
}