package com.mygdx.game.screen.actor;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.GameOff2022;
import com.mygdx.game.domain.GameConstants;

import java.math.BigInteger;

public class EnemyFactory {
    public static Enemy create(final GameOff2022 game) {
        Enemy enemy = new Enemy(game);
        enemy.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.getGameData().getCurrentWizard().setCurrentState(WizardState.ATTACK);
            }
        });
        enemy.setPosition(500, 100);
        enemy.setSize(50, 50);
        enemy.setCurrentState(EnemyState.ATTACK);
        long facedEnemies = game.getGameData().getEnemyKilledCounter() + 1;
        enemy.setDamage(facedEnemies * 0.5f);
        enemy.setMaxLife(facedEnemies * 50);
        enemy.setCurrentLife(enemy.getMaxLife());
        enemy.setPointValue(BigInteger.valueOf(facedEnemies).multiply(BigInteger.valueOf(500)));
        game.getGameData().setCurrentEnemy(enemy);
        game.getGameData().getCurrentWizard().setUserObject(enemy);
        return enemy;
    }
}
