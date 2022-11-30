package com.mygdx.game.screen.actor;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.GameOff2022;

import java.math.BigInteger;

public class EnemyFactory {
    public static Enemy createEnemy() {
        final GameOff2022 game = GameOff2022.getInstance();
        Enemy enemy = new Enemy();
        enemy.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (game.getGameData().getCurrentWizard().getCurrentState() == WizardState.IDLE) {
                    game.getGameData().getCurrentWizard().setCurrentState(WizardState.ATTACK);
                }
            }
        });
        enemy.setPosition(600, 100);
        enemy.setSize(50, 50);
        enemy.setCurrentState(EnemyState.ATTACK);
        long facedEnemies = game.getGameData().getEnemyKilledCounter() + 1;
        enemy.setDamage(BigInteger.valueOf(facedEnemies));
        enemy.setMaxLife(facedEnemies * 50);
        enemy.setCurrentLife(enemy.getMaxLife());
        enemy.setPointValue(BigInteger.valueOf(facedEnemies).multiply(BigInteger.valueOf(500)));
        game.getGameData().setCurrentEnemy(enemy);
        return enemy;
    }
}
