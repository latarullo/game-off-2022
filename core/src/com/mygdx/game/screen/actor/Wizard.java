package com.mygdx.game.screen.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.domain.HealthPotion;
import com.mygdx.game.screen.gui.component.HealthBarGUI;

import java.math.BigInteger;
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
    private BigInteger damage = new BigInteger("55");
    protected Sound spellSound;
    private HealthBarGUI healthBar = new HealthBarGUI();
    private WizardSpellParticle wizardSpellParticle;
    private AttackDamageParticle attackDamageParticle;

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

        healthBar.draw(batch, currentAnimation, this.getX(), this.getY(), 10, currentLife, maxLife);

        Enemy enemy = (Enemy) this.getUserObject();

        if (!valueAnimation.isAnimationFinished(stateTime)) {
            batch.draw(currentAnimation, this.getX(), this.getY());
        } else {
            if (currentState == WizardState.ATTACK){
                this.attack(enemy);
                wizardSpellParticle = new WizardSpellParticle(this);
                attackDamageParticle = new AttackDamageParticle(this, this.damage);
                this.getParent().addActor(wizardSpellParticle);
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
    public void act(float delta) {
        super.act(delta);
        if (attackDamageParticle != null) {
            this.attackDamageParticle.act(delta);
        }
    }

    @Override
    public void dispose() {
        this.spellSound.dispose();
        this.healthBar.dispose();
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
        enemy.takeDamage(this.damage);
    }

    public void useHealthPotion(HealthPotion healthPotion){
        this.currentLife += healthPotion.getHealthPower();
        if (this.currentLife > this.maxLife){
            this.currentLife = this.maxLife;
        }
    }

    public void takeDamage(float damageDealt){
        this.currentLife -= damageDealt;
        if (this.currentLife < 0){
            this.currentLife = 0;
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
