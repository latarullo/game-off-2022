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
import com.mygdx.game.controller.GameUpgrades;
import com.mygdx.game.domain.GameData;
import com.mygdx.game.domain.ConsumableItem;
import com.mygdx.game.screen.RestartScreen;
import com.mygdx.game.screen.actor.particle.AttackDamage;
import com.mygdx.game.screen.actor.particle.WizardSpell;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Wizard extends Actor implements Damageable, Disposable {

    private TextureAtlas atlas;
    private Map<String, Animation<TextureRegion>> animations = new HashMap<>();
    protected float stateTime;
    private WizardState currentState = WizardState.IDLE;
    protected WizardType wizardType;
    private float maxLife = 100;
    private float currentLife = maxLife;
    private BigInteger damage = new BigInteger("33");
    protected Sound spellSound;
    protected Sound drinkPotionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/drink-potion.wav"));
    protected Sound wizardHurtSound = Gdx.audio.newSound(Gdx.files.internal("sounds/wizard-hurt.wav"));
    protected Sound dieSound = Gdx.audio.newSound(Gdx.files.internal("sounds/wizard-die.wav"));
    private WizardSpell wizardSpellParticle;
    private AttackDamage attackDamageParticle;

    BigInteger accumulator = BigInteger.TEN;

    protected void loadAnimations() {
        this.atlas = new TextureAtlas(Gdx.files.internal(wizardType.getAtlasPath()));
        for (WizardState region : WizardState.values()) {
            String regionName = region.getState();

            Animation<TextureRegion> animation = new Animation<TextureRegion>(1f / 5, atlas.findRegions(regionName), Animation.PlayMode.LOOP);
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

        Enemy enemy = (Enemy) this.getUserObject();

        if (!valueAnimation.isAnimationFinished(stateTime)) {
            batch.draw(currentAnimation, this.getX(), this.getY());
        } else {
            if (currentState == WizardState.ATTACK) {
                this.attack(enemy);
                wizardSpellParticle = new WizardSpell(this);
                attackDamageParticle = new AttackDamage(this, damage);
                this.getParent().addActor(wizardSpellParticle);
            } else if (currentState == WizardState.DIE) {
                die();
            }
            this.setCurrentState(WizardState.IDLE);
            currentAnimation = animations.get(state).getKeyFrame(stateTime, true);
            batch.draw(currentAnimation, this.getX(), this.getY());
        }
    }

    private void die() {
        Group parent = this.getParent();

        List<Wizard> availableWizards = GameData.getInstance().getAvailableWizards();
        boolean allWizardsDead = true;
        for (Wizard availableWizard : availableWizards) {
            if (availableWizard.getCurrentLife() > 0){
                allWizardsDead = false;
                parent.addActor(availableWizard);
                GameData.getInstance().setCurrentWizard(availableWizard);
                break;
            }
        }
        if (allWizardsDead){
            GameOff2022.getInstance().changeScreen(new RestartScreen(GameOff2022.getInstance()));
        }

        parent.removeActor(this);

    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (attackDamageParticle != null) {
            this.attackDamageParticle.act(delta);
        }
    }

    public void setCurrentState(WizardState currentState) {
        if (currentState == WizardState.DIE){
            dieSound.play();
        }
        this.currentState = currentState;
        this.stateTime = 0;
    }

    public WizardState getCurrentState() {
        return currentState;
    }

    public TextureRegion getIdleSprite() {
        TextureRegion[] keyFrames = animations.get(WizardState.IDLE.getState()).getKeyFrames();
        return keyFrames[0];
    }

    public Image getImage(){
        return new Image(getIdleSprite());
    }

    public void attack(Enemy enemy) {
        spellSound.play();
        boolean godMode = GameUpgrades.getInstance().isGodMode();
        if (godMode) {
            Float maxLife = enemy.getMaxLife()+1;
            BigInteger bigInteger = new BigInteger(String.valueOf(maxLife.intValue()));
            enemy.takeDamage(bigInteger);
        } else {
            enemy.takeDamage(this.damage);
        }
    }

    public void useHealthPotion(ConsumableItem healthPotion) {
        drinkPotionSound.play();
        this.currentLife += healthPotion.getHealthPower();
        if (this.currentLife > this.maxLife) {
            this.currentLife = this.maxLife;
        }
    }

    public void takeDamage(float damageDealt) {
        this.currentLife -= damageDealt;
        if (this.currentLife < 0) {
            this.currentLife = 0;
            if (currentState != WizardState.DIE) {
                this.setCurrentState(WizardState.DIE);
            }
        } else {
            //wizardHurtSound.play();
        }
    }

    public boolean canHeal() {
        return currentLife < maxLife;
    }

    public WizardType getWizardType() {
        return wizardType;
    }

    @Override
    public void dispose() {
        this.spellSound.dispose();
        this.atlas.dispose();
        this.drinkPotionSound.dispose();
        this.wizardHurtSound.dispose();
        this.dieSound.dispose();
    }

    public float getCurrentLife() {
        return currentLife;
    }

    public float getMaxLife() {
        return maxLife;
    }

    public void reset(){
        this.currentLife = this.maxLife;
        this.stateTime = 0;
    }
}
