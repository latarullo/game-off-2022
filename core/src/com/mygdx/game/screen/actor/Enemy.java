package com.mygdx.game.screen.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.GameOff2022;
import com.mygdx.game.controller.GameSoundPlayer;
import com.mygdx.game.domain.GameConstants;
import com.mygdx.game.screen.GameScreen;
import com.mygdx.game.screen.actor.particle.HealthChange;

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
    private BigInteger damage = GameConstants.LEMON_BASE_DAMAGE;
    private GameOff2022 game = GameOff2022.getInstance();
    private BigInteger pointValue = GameConstants.LEMON_KILLING_POINTS;
    private Sound hurtSound = Gdx.audio.newSound(Gdx.files.internal("sounds/lemon-hurt.wav"));
    private Sound dieSound = Gdx.audio.newSound(Gdx.files.internal("sounds/lemon-die.wav"));
    private HealthChange healthChangeParticle;

    public Enemy() {
        loadAnimations();
        setName("Lemon");
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
                Group actorHolder = GameScreen.getInstance().getStage().getRoot();
                actorHolder.removeActor(this);
                Wizard wizard = game.getGameData().getCurrentWizard();
                wizard.killedLemon();
                game.createNewEnemy(actorHolder);
            } else if (currentState == EnemyState.ATTACK) {
                stateTime = 0;
                batch.draw(currentAnimation, this.getX(), this.getY());
                Wizard wizard = game.getGameData().getCurrentWizard();
                this.attack(wizard);
                healthChangeParticle = new HealthChange(this.damage, wizard);
            }
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (healthChangeParticle != null) {
            this.healthChangeParticle.act(delta);
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
        GameSoundPlayer.playSound(hurtSound);
        this.currentLife -= damage.floatValue();
        if (this.currentLife < 0) {
            this.currentLife = 0;
            GameSoundPlayer.playSound(dieSound);
            this.setCurrentState(EnemyState.DIE);
        }
    }

    public void attack(Wizard wizard) {
        wizard.takeDamage(this.damage);
    }

    public void setDamage(BigInteger damage) {
        this.damage = damage;
    }

    private BigInteger getDamage() {
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

    public Image getImage() {
        return new Image(getIdleSprite());
    }

    public Color getEnemyColor() {
        return Color.GREEN;
    }

    public void reset() {
        this.currentLife = this.maxLife;
        this.setCurrentState(EnemyState.ATTACK);
    }

    public void wasClicked(){
        Wizard currentWizard = game.getGameData().getCurrentWizard();
        if (currentWizard.getCurrentState() == WizardState.IDLE &&
                currentWizard.isAnimating()) {
            currentWizard.setCurrentState(WizardState.ATTACK);
        }
    }
}