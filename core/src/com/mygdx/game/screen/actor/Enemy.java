package com.mygdx.game.screen.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.GameOff2022;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Enemy extends Actor implements Disposable {

    private TextureAtlas atlas;
    private Map<String, Animation<TextureRegion>> animations = new HashMap<>();
    private float stateTime;
    private EnemyState currentState = EnemyState.IDLE;
    private boolean shouldFaceLeft = true;
    private float maxLife = 100f;
    private float currentLife = 100f;
    private float damage = 1;
    private GameOff2022 game;

    public Enemy(GameOff2022 game){
        this.game = game;
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
        Animation<TextureRegion> animation = animations.get(state);
        if (currentState == EnemyState.DIE) {
            currentAnimation = animation.getKeyFrame(stateTime, false);
        } else {
            currentAnimation = animation.getKeyFrame(stateTime, true);
        }
        this.setWidth(currentAnimation.getRegionWidth());
        this.setHeight(currentAnimation.getRegionWidth());
        Rectangle healthBar = new Rectangle();
        healthBar.setPosition(this.getX(), this.getY() + currentAnimation.getRegionHeight() + 10);
        healthBar.setWidth(100);


        //////////Draw healthbar - begin
        int animationRegionWidth = currentAnimation.getRegionWidth();
        Texture greenBar = HealthBar.getInstance().getGreenBar();
        Texture redBar = HealthBar.getInstance().getRedBar();
        batch.draw(greenBar, this.getX(), this.getY()+ currentAnimation.getRegionHeight() + 30, animationRegionWidth, 10);
        batch.draw(redBar, this.getX(), this.getY()+ currentAnimation.getRegionHeight() + 30, (1 - currentLife/maxLife) * animationRegionWidth, 10);
        //////////Draw healthbar - end

        if (!animation.isAnimationFinished(stateTime)) {
            batch.draw(currentAnimation, this.getX(), this.getY());
        } else {
            if (currentState == EnemyState.DIE){
                //this.moveBy(0,0);
                batch.draw(currentAnimation, this.getX(), this.getY());
            } else if (currentState == EnemyState.ATTACK){
                stateTime = 0;
                this.attack(game.getCurrentWizard());
            }
        }

        //if (currentLife > 0 ) currentLife--;
        //batch.draw(currentAnimation, this.getX(), this.getY());
    }

    @Override
    public void dispose() {
        this.atlas.dispose();
    }

    public void setCurrentState(EnemyState currentState) {
        this.currentState = currentState;
    }

    public void takeDamage(float damage){
        this.currentLife -= damage;
        if (this.currentLife < 0 ){
            this.currentState=EnemyState.DIE;
            //wont attack animore
            //no more taking damage

            //play dead
            //remove enemy
            //points for the player

            //create next enemy
        }
    }

    public void attack (Wizard wizard){
        wizard.takeDamage(this.getDamage());
    }

    private float getDamage() {
        return this.damage;
    }
}