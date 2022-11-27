package com.mygdx.game.screen.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.GameOff2022;
import com.mygdx.game.screen.gui.HealthBarGUI;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Enemy extends Actor implements Damageable, Disposable {

    private TextureAtlas atlas;
    private Map<String, Animation<TextureRegion>> animations = new HashMap<>();
    private float stateTime;
    private EnemyState currentState = EnemyState.IDLE;
    private boolean shouldFaceLeft = false;
    private float maxLife = 100f;
    private float currentLife = 100f;
    private float damage = 0.5f;
    private GameOff2022 game;
    private BigInteger pointValue = new BigInteger("500");
    private Sound hurtSound = Gdx.audio.newSound(Gdx.files.internal("sounds/lemon-hurt.wav"));
    private Sound dieSound = Gdx.audio.newSound(Gdx.files.internal("sounds/lemon-die.wav"));

    public Enemy(GameOff2022 game) {
        this.game = game;
        loadAnimations();
    }

    protected void loadAnimations() {
        this.atlas = new TextureAtlas(Gdx.files.internal("animations/lemon.atlas"));
        for (EnemyState region : EnemyState.values()) {
            String regionName = region.getState();
            Animation<TextureRegion> animation;
            if (region == EnemyState.DIE) {
                animation = new Animation<TextureRegion>(1f / 5, atlas.findRegions(regionName), Animation.PlayMode.NORMAL);
            } else {
                animation = new Animation<TextureRegion>(1f / 5, atlas.findRegions(regionName), Animation.PlayMode.LOOP);
            }
            animations.put(regionName, animation);
        }

        if (shouldFaceLeft) {
            Iterator<String> iterator = animations.keySet().iterator();
            while (iterator.hasNext()) {
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

        if (!animation.isAnimationFinished(stateTime)) {
            //healthBar.draw(batch, currentAnimation, this.getX(), this.getY(), 10, currentLife, maxLife);
            batch.draw(currentAnimation, this.getX(), this.getY());
        } else {
            if (currentState == EnemyState.DIE) {
                batch.draw(currentAnimation, this.getX(), this.getY());
                game.getGameData().addMoney(this.pointValue);
                Group actorHolder = this.getParent();
                actorHolder.removeActor(this);
                game.createNewEnemy(actorHolder);
            } else if (currentState == EnemyState.ATTACK) {
                stateTime = 0;
                this.attack(game.getGameData().getCurrentWizard());
            }
        }
    }

    @Override
    public void dispose() {
        this.atlas.dispose();
    }

    public void setCurrentState(EnemyState currentState) {
        this.stateTime = 0;
        this.currentState = currentState;
    }

    public void takeDamage(BigInteger damage) {
        hurtSound.play();
        this.currentLife -= damage.floatValue();
        if (this.currentLife < 0) {
            this.currentLife = 0;
            dieSound.play();
            this.setCurrentState(EnemyState.DIE);
        }
    }

    public void attack(Wizard wizard) {
        wizard.takeDamage(this.getDamage());
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    private float getDamage() {
        return this.damage;
    }

    public void setMaxLife(float maxLife) {
        this.maxLife = maxLife;
    }

    public float getMaxLife() {
        return maxLife;
    }

    public void setCurrentLife(float currentLife) {
        this.currentLife = currentLife;
    }

    public float getCurrentLife() {
        return currentLife;
    }

    public BigInteger getPointValue() {
        return pointValue;
    }

    public void setPointValue(BigInteger pointValue) {
        this.pointValue = pointValue;
    }

    public TextureRegion getIdleSprite() {
        TextureRegion[] keyFrames = animations.get(EnemyState.ATTACK.getState()).getKeyFrames();
        return keyFrames[0];
    }

    public Image getImage(){
        return new Image(getIdleSprite());
    }
}