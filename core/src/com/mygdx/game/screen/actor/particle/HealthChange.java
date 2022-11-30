package com.mygdx.game.screen.actor.particle;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.mygdx.game.GameOff2022;
import com.mygdx.game.domain.GameConstants;
import com.mygdx.game.screen.actor.Enemy;
import com.mygdx.game.screen.actor.Wizard;
import com.mygdx.game.util.BigNumberNotation;
import com.mygdx.game.util.GameFontGenerator;

import java.math.BigInteger;

public class HealthChange extends Actor {
    private GameFontGenerator gameFontGenerator = GameFontGenerator.getInstance();
    private GameOff2022 game = GameOff2022.getInstance();

    public HealthChange(BigInteger damage, Actor takingDamageUnit) {
        new HealthChange(damage, takingDamageUnit, false);
    }

    public HealthChange(BigInteger damage, Actor takingDamageUnit, boolean isHealing) {
        Enemy enemy = game.getGameData().getCurrentEnemy();
        Wizard wizard = game.getGameData().getCurrentWizard();

        String labelValue = "";
        Color color = null;

        if (!isHealing) {
            labelValue = "-";
            color = wizard.getWizardType().getColor();
        } else {
            labelValue = "+";
            color = Color.GREEN;
        }

        labelValue += BigNumberNotation.getPrintableValue(damage);
        Label.LabelStyle labelStyle = gameFontGenerator.generateLabelStyle(40, color);

        MyLabel label = new MyLabel(labelValue, labelStyle, takingDamageUnit, isHealing);
        wizard.getParent().addActor(label);
    }
}

class MyLabel extends Label {

    protected float stateTime = 0;
    private Group actorHolder;

    public MyLabel(String text, LabelStyle labelStyle, Actor actor, boolean isHealing) {
        super(text, labelStyle);
        this.setX(actor.getX() + actor.getWidth() / 2);

        if (isHealing) {
            this.setX(getX() - 150);
        }

        this.setY(actor.getY() + actor.getHeight() / 2);
        this.actorHolder = actor.getParent();
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