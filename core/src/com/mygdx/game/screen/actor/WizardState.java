package com.mygdx.game.screen.actor;

public enum WizardState {
    IDLE("1_IDLE"), WALK("2_WALK"), RUN("3_RUN"), JUMP("4_JUMP"), ATTACK("5_ATTACK"), HURT("6_HURT"), DIE("7_DIE");

    private final String state;

    WizardState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
