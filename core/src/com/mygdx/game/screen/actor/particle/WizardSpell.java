package com.mygdx.game.screen.actor.particle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.screen.actor.Enemy;
import com.mygdx.game.screen.actor.Wizard;
import com.mygdx.game.screen.actor.WizardType;

import java.util.HashMap;
import java.util.Map;

public class WizardSpell extends Actor implements Disposable {

    private Wizard wizard;
    private TextureAtlas atlas;
    private Map<WizardType, Animation<TextureRegion>> animations = new HashMap<>();
    protected float stateTime;
    private boolean isExploding = true;

    public WizardSpell(Wizard wizard){
        this.wizard = wizard;
        loadAnimations();
    }

    protected void loadAnimations() {
        this.atlas = new TextureAtlas(Gdx.files.internal("animations/explosions.atlas"));

        Animation<TextureRegion> lightningExplosionAnimation = new Animation<TextureRegion>(1f/5, atlas.findRegions("LIGHTNING"), Animation.PlayMode.NORMAL);
        Animation<TextureRegion> fireExplosionAnimation = new Animation<TextureRegion>(1f/5, atlas.findRegions("FIRE"), Animation.PlayMode.NORMAL);
        Animation<TextureRegion> iceExplosionAnimation = new Animation<TextureRegion>(1f/5, atlas.findRegions("ICE"), Animation.PlayMode.NORMAL);
        animations.put(WizardType.LIGHTNING, lightningExplosionAnimation);
        animations.put(WizardType.FIRE, fireExplosionAnimation);
        animations.put(WizardType.ICE, iceExplosionAnimation);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        stateTime += Gdx.graphics.getDeltaTime();

        stateTime += Gdx.graphics.getDeltaTime();
        Animation<TextureRegion> valueAnimation = animations.get(wizard.getWizardType());
        TextureRegion currentAnimation = valueAnimation.getKeyFrame(stateTime,false);
        Enemy enemy = (Enemy) wizard.getUserObject();
        float enemyCenterX = enemy.getX() + enemy.getWidth() / 2;
        float enemyCenterY = enemy.getY() + enemy.getHeight() / 2;
        float width = 256;
        float height = width;

        if (isExploding && !valueAnimation.isAnimationFinished(stateTime)) {
            batch.draw(currentAnimation, enemyCenterX-width/2, enemyCenterY-height/2, width, height);
        } else {
            stateTime = 0;
            isExploding = false;
        }
    }


    @Override
    public void dispose() {
        this.atlas.dispose();
    }
}
