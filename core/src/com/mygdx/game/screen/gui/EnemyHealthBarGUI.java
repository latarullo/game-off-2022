package com.mygdx.game.screen.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.domain.GameData;
import com.mygdx.game.screen.actor.Damageable;
import com.mygdx.game.screen.actor.Enemy;
import com.mygdx.game.util.GameFontGenerator;
import com.mygdx.game.util.GameFontSizeEnum;

public class EnemyHealthBarGUI {
    private GameFontGenerator gameFontGenerator = GameFontGenerator.getInstance();

    public Table createGUI() {
        Enemy enemy = GameData.getInstance().getCurrentEnemy();

        Label.LabelStyle labelStyle = gameFontGenerator.generateLabelStyle(40, enemy.getEnemyColor());

        String name = enemy.getName();

        Label nameLabel = new Label(name, labelStyle);
        WizardHealthBarGUI healthBarGUI = new WizardHealthBarGUI(enemy);
        healthBarGUI.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                WizardHealthBarGUI wizardHealthBarGUI = (WizardHealthBarGUI) this.getActor();
                wizardHealthBarGUI.setDamageable(GameData.getInstance().getCurrentEnemy());
                return false;
            }
        });

        Table nameHealthBarTable = new Table();
        nameHealthBarTable.add(nameLabel).align(Align.right);
        nameHealthBarTable.row();
        nameHealthBarTable.add(healthBarGUI).width(200).height(10).align(Align.right);

        Table table = new Table();
        table.add(nameHealthBarTable);
        table.add(enemy.getImage()).size(200,150);

        return table;
    }
}
