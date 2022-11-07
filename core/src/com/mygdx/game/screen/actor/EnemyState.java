package com.mygdx.game.screen.actor;

public enum EnemyState {
    IDLE("7_enemies_1_idle"), HURT("7_enemies_1_hurt"), DIE("7_enemies_1_die");

    private final String state;

    EnemyState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
