package com.mygdx.game.domain;

import com.mygdx.game.controller.GameUpgrades;
import com.mygdx.game.screen.actor.Enemy;
import com.mygdx.game.screen.actor.Wizard;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameData {
    private static GameData instance;

    public static GameData getInstance() {
        if (instance == null) {
            instance = new GameData();
        }
        return instance;
    }

    private List<Wizard> availableWizards = new ArrayList<>();
    private Wizard currentWizard;
    private Enemy currentEnemy;
    private BigInteger money;
    private Map<HealthConsumableEnum, Integer> consumables = new HashMap<>();
    private long enemyKilledCounter = 0;
    private GameUpgrades gameUpgrade = GameUpgrades.getInstance();

    private GameData() {

    }

    public Wizard getCurrentWizard() {
        return currentWizard;
    }

    public void setCurrentWizard(Wizard wizard) {
        if (!availableWizards.contains(wizard)) {
            availableWizards.add(wizard);
        }
        this.currentWizard = wizard;
    }

    public Enemy getCurrentEnemy() {
        return currentEnemy;
    }

    public void setCurrentEnemy(Enemy currentEnemy) {
        this.currentEnemy = currentEnemy;
    }

    public BigInteger getMoney() {
        return money;
    }

    public void addMoney(BigInteger money) {
        this.money = this.money.add(money);
    }

    public void subtractMoney(BigInteger money) {
        this.money = this.money.subtract(money);
    }

    public void reset() {
        resetMoney();
        resetConsumables();
    }

    private void resetMoney() {
        this.money = BigInteger.ZERO;
    }

    public void addConsumable(HealthConsumableEnum healthConsumableEnum) {
        Integer potion = this.consumables.get(healthConsumableEnum);
        if (potion == null) {
            this.consumables.put(healthConsumableEnum, 1);
        } else {
            this.consumables.put(healthConsumableEnum, potion + 1);
        }
    }

    public void useConsumable(HealthConsumableEnum healthConsumableEnum) {
        Integer availableQuantity = this.consumables.get(healthConsumableEnum);
        this.consumables.put(healthConsumableEnum, availableQuantity - 1);
    }

    private void resetConsumables() {
        this.consumables = new HashMap<>();
        this.consumables.put(HealthConsumableEnum.LEMON_SQUARES, 0);
        this.consumables.put(HealthConsumableEnum.ICE_LEMON_TEA, 0);
        this.consumables.put(HealthConsumableEnum.LEMON_ICE_CREAM, 0);
        this.consumables.put(HealthConsumableEnum.LEMON_CUSTARD_PIE, 0);
    }

    public Map<HealthConsumableEnum, Integer> getConsumables() {
        return consumables;
    }


    public long getEnemyKilledCounter() {
        return enemyKilledCounter;
    }

    public void setEnemyKilledCounter(long enemyKilledCounter) {
        this.enemyKilledCounter = enemyKilledCounter;
    }

    public void addAvailableWizard(Wizard wizard) {
        availableWizards.add(wizard);
    }

    public List<Wizard> getAvailableWizards() {
        return availableWizards;
    }

}
