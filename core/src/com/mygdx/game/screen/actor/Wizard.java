package com.mygdx.game.screen.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
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
    private float maxLife = 100;
    private float currentLife = maxLife;
    private float damage;
    protected Sound spellSound;

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
        Animation<TextureRegion> valueAnimation = animations.get(state);
        TextureRegion currentAnimation = animations.get(state).getKeyFrame(stateTime, true);
        this.setWidth(currentAnimation.getRegionWidth());
        this.setHeight(currentAnimation.getRegionWidth());

        //////////Draw healthbar - begin
        int animationRegionWidth = currentAnimation.getRegionWidth();
        Texture greenBar = HealthBar.getInstance().getGreenBar();
        Texture redBar = HealthBar.getInstance().getRedBar();
        batch.draw(greenBar, this.getX(), this.getY()+ currentAnimation.getRegionHeight() + 30, animationRegionWidth, 10);
        batch.draw(redBar, this.getX(), this.getY()+ currentAnimation.getRegionHeight() + 30, (1 - currentLife/maxLife) * animationRegionWidth, 10);
        //////////Draw healthbar - end


        if (!valueAnimation.isAnimationFinished(stateTime)) {
            batch.draw(currentAnimation, this.getX(), this.getY());
        } else {
            if (currentState == WizardState.ATTACK){
                Enemy enemy = (Enemy) this.getUserObject();
                this.attack(enemy);
            } else if (currentState == WizardState.DIE){
                currentState = WizardState.IDLE;
            }
            stateTime = 0;
            currentState = WizardState.IDLE;
            currentAnimation = animations.get(state).getKeyFrame(stateTime, true);
            batch.draw(currentAnimation, this.getX(), this.getY());

        }
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

    public TextureRegion getIdleSprite() {
        TextureRegion[] keyFrames = animations.get(WizardState.IDLE.getState()).getKeyFrames();
        return keyFrames[0];
    }

    public void attack(Enemy enemy){
        spellSound.play();
        enemy.takeDamage(10f);
    }

//    public void useHealthPotion(HealthPotion healthPotion){
//
//    }
//
    public void takeDamage(float damageDealt){
        this.currentLife -= damageDealt;
        if (currentLife < 0){
            this.currentState = WizardState.DIE;
        }
    }
//
//    private void playDead() {
//        //takes no more damage
//
//        //deals no damage
//
//        //play animation dead
//
//        //then changes currentWizard (if available)
//    }

}
