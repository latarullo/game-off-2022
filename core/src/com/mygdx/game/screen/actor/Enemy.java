package com.mygdx.game.screen.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Disposable;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Enemy extends Actor implements Disposable {

    private TextureAtlas atlas;
    private Map<String, Animation<TextureRegion>> animations = new HashMap<>();
    private float stateTime;
    private EnemyState currentState = EnemyState.IDLE;
    private boolean shouldFaceLeft = true;

    public Enemy(){
        loadAnimations();
    }

    protected void loadAnimations() {
        this.atlas = new TextureAtlas(Gdx.files.internal("animations/Enemy.atlas"));
        for (EnemyState region : EnemyState.values()) {
            String regionName = region.getState();
            Animation<TextureRegion> animation;
            if (region == EnemyState.DIE){
                animation = new Animation<TextureRegion>(1f/20, atlas.findRegions(regionName), Animation.PlayMode.NORMAL);
            }
            else {
                animation = new Animation<TextureRegion>(1f/20, atlas.findRegions(regionName), Animation.PlayMode.LOOP);
            }
            animations.put(regionName, animation);
        }

        if (shouldFaceLeft){
            Iterator<String> iterator = animations.keySet().iterator();
            while (iterator.hasNext()){
                String next = iterator.next();
                TextureRegion[] keyFrames = animations.get(next).getKeyFrames();
                for (TextureRegion textureRegion : keyFrames) {
                    textureRegion.flip(true, false);
                }
            }
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        stateTime += Gdx.graphics.getDeltaTime();
        String state = currentState.getState();
        TextureRegion currentAnimation;
        if (currentState == EnemyState.DIE) {
            currentAnimation = animations.get(state).getKeyFrame(stateTime, false);
        } else {
            currentAnimation = animations.get(state).getKeyFrame(stateTime, true);
        }
        this.setWidth(currentAnimation.getRegionWidth());
        this.setHeight(currentAnimation.getRegionWidth());
        batch.draw(currentAnimation, this.getX(), this.getY());
    }

    @Override
    public void dispose() {
        this.atlas.dispose();
    }

    public void setCurrentState(EnemyState currentState) {
        this.currentState = currentState;
    }
}
