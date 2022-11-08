package com.mygdx.game.screen.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Disposable;

import java.util.HashMap;
import java.util.Map;

public abstract class Wizard extends Actor implements Disposable {

    private TextureAtlas atlas;
    private Map<String, Animation<TextureRegion>> animations = new HashMap<>();
    protected float stateTime;
    private WizardState currentState = WizardState.IDLE;
    protected WizardType wizardType;
    private long life;
    private long potionCount;

    protected void loadAnimations() {
        this.atlas = new TextureAtlas(Gdx.files.internal(wizardType.getAtlasPath()));
        for (WizardState region : WizardState.values()) {
            String regionName = region.getState();

            Animation<TextureRegion> animation = new Animation<TextureRegion>(1f/5, atlas.findRegions(regionName), Animation.PlayMode.LOOP);
            animations.put(regionName, animation);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        stateTime += Gdx.graphics.getDeltaTime();
        String state = currentState.getState();
        TextureRegion currentAnimation = animations.get(state).getKeyFrame(stateTime, true);
        this.setWidth(currentAnimation.getRegionWidth());
        this.setHeight(currentAnimation.getRegionWidth());
        batch.draw(currentAnimation, this.getX(), this.getY());
    }

    @Override
    public void dispose() {
        this.atlas.dispose();
    }

    public void setCurrentState(WizardState currentState) {
        this.currentState = currentState;
    }

    public WizardState getCurrentState() {
        return currentState;
    }

    public TextureRegion getIdleSprite(){
        TextureRegion[] keyFrames = animations.get(WizardState.IDLE.getState()).getKeyFrames();
        return keyFrames[0];
    }
}
