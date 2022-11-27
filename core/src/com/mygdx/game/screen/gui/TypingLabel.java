package com.mygdx.game.screen.gui;

import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class TypingLabel extends Label {
    private float stateTime = 0;
    private CharSequence fullText;
    private boolean typingComplete = false;

    public TypingLabel(CharSequence text, LabelStyle style) {
        super("", style);
        this.fullText = text;
    }

    @Override
    public void act(float delta) {
        if (typingComplete) {
            return;
        }

        super.act(delta);
        stateTime += delta;
        int fullTextSize = this.fullText.length();
        float paceRate = stateTime / 5;

        int charsToWrite;
        charsToWrite = (int) (fullTextSize * paceRate);
        if (charsToWrite > fullTextSize) {
            charsToWrite = fullTextSize;
            typingComplete = true;
        }

        this.setText(fullText.subSequence(0, charsToWrite).toString());
    }

    public boolean isTypingComplete() {
        return typingComplete;
    }
}