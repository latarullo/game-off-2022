package com.mygdx.game.screen.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.GameOff2022;
import com.mygdx.game.controller.GameAchievements;
import com.mygdx.game.controller.GameSoundPlayer;
import com.mygdx.game.controller.GameUpgrades;
import com.mygdx.game.domain.ConsumableItem;
import com.mygdx.game.domain.FoodHealthPowerItem;
import com.mygdx.game.domain.GameData;
import com.mygdx.game.domain.WizardSpellPowerItem;
import com.mygdx.game.screen.RestartScreen;
import com.mygdx.game.screen.actor.particle.HealthChange;
import com.mygdx.game.screen.actor.particle.WizardSpell;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Wizard extends Actor implements Damageable, Disposable {

    private TextureAtlas atlas;
    protected Texture portraitTexture;
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
    private Sound switchWizardSound = Gdx.audio.newSound(Gdx.files.internal("sounds/switch-wizard.wav"));
    protected Sound easyPeasyLemonsSqueezeSound;
    private WizardSpell wizardSpellParticle;
    private HealthChange damageParticle;
    private HealthChange healingParticle;

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

        Enemy enemy = GameData.getInstance().getCurrentEnemy();

        if (!valueAnimation.isAnimationFinished(stateTime)) {
            batch.draw(currentAnimation, this.getX(), this.getY());
        } else {
            if (currentState == WizardState.ATTACK) {
                this.attack(enemy);
                this.getParent().addActor(wizardSpellParticle);
            } else if (currentState == WizardState.DIE) {
                killWizard();
            }
            this.setCurrentState(WizardState.IDLE);
            currentAnimation = animations.get(state).getKeyFrame(stateTime, true);
            batch.draw(currentAnimation, this.getX(), this.getY());
        }
    }

    private void killWizard() {
        Group parent = this.getParent();

        List<Wizard> availableWizards = GameData.getInstance().getAvailableWizards();
        boolean allWizardsDead = true;
        for (Wizard availableWizard : availableWizards) {
            if (availableWizard.getCurrentLife() > 0) {
                allWizardsDead = false;
                parent.addActor(availableWizard);
                GameData.getInstance().setCurrentWizard(availableWizard);
                break;
            }
        }
        if (allWizardsDead) {
            GameOff2022.getInstance().changeScreen(new RestartScreen(GameOff2022.getInstance()));
        }

        parent.removeActor(this);

    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (damageParticle != null) {
            this.damageParticle.act(delta);
        }
        if (healingParticle != null) {
            this.healingParticle.act(delta);
        }
    }

    public void setCurrentState(WizardState currentState) {
        if (currentState == WizardState.DIE) {
            GameSoundPlayer.playSound(dieSound);
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

    public Image getImage() {
        return new Image(getIdleSprite());
    }

    public void attack(Enemy enemy) {
        WizardSpellPowerItem wizardSpellPowerItem = WizardSpellPowerItem.getInstance();
        long bonusDamage = wizardSpellPowerItem.getBonusSpellPowerDamage();
        BigInteger damage = this.damage.add(BigInteger.valueOf(bonusDamage));
        GameSoundPlayer.playSound(spellSound);
        boolean godMode = GameUpgrades.getInstance().isGodMode();
        if (godMode) {
            Float maxLife = enemy.getMaxLife() + 1;
            BigInteger bigInteger = new BigInteger(String.valueOf(maxLife.intValue()));
            damage = bigInteger;
        }
        enemy.takeDamage(damage);
        wizardSpellParticle = new WizardSpell(this);
        damageParticle = new HealthChange(damage, enemy);
    }

    public void useConsumable(ConsumableItem healthPotion) {
        FoodHealthPowerItem foodHealthPowerItem = FoodHealthPowerItem.getInstance();
        long bonusHealth = foodHealthPowerItem.getBonusHealthPower();
        GameSoundPlayer.playSound(drinkPotionSound);
        float healedValue = healthPotion.getHealthPower() + bonusHealth;
        this.currentLife += healedValue;

        healingParticle = new HealthChange(BigDecimal.valueOf(healedValue).toBigInteger(), this, true);
        if (this.currentLife > this.maxLife) {
            this.currentLife = this.maxLife;
        }
    }

    public void takeDamage(BigInteger damageDealt) {
        this.currentLife -= damageDealt.floatValue();
        if (this.currentLife < 0) {
            this.currentLife = 0;
            if (currentState != WizardState.DIE) {
                this.setCurrentState(WizardState.DIE);
            }
        } else {
            if (Math.random() < 0.5f) {
                GameSoundPlayer.playSound(wizardHurtSound);
            }
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
        this.atlas.dispose();
        this.spellSound.dispose();
        this.drinkPotionSound.dispose();
        this.wizardHurtSound.dispose();
        this.dieSound.dispose();
        this.easyPeasyLemonsSqueezeSound.dispose();
        this.switchWizardSound.dispose();
    }

    public float getCurrentLife() {
        return currentLife;
    }

    public float getMaxLife() {
        return maxLife;
    }

    public void reset() {
        this.currentLife = this.maxLife;
        this.stateTime = 0;
    }

    public void wizardSwitched() {
        GameSoundPlayer.playSound(switchWizardSound);
    }

    public void wizardHealthUp(long bonusHealthPower) {
        currentLife += bonusHealthPower;
        maxLife += bonusHealthPower;
    }

    public void killedLemon() {
        GameData.getInstance().enemyKilled();

        List<Integer> lemonSlayerLevels = Arrays.asList(5, 10, 25, 50, 100, 500, 1000);
        Long enemyKilledCounter = GameData.getInstance().getEnemyKilledCounter();

        if (lemonSlayerLevels.contains(enemyKilledCounter.intValue())) {
            GameAchievements.getInstance().lemonSlayer("You have squeezed " + enemyKilledCounter + " lemons");
        }

        if (easyPeasyLemonsSqueezeSound != null && Math.random() < 0.25f) {
            GameSoundPlayer.playSound(easyPeasyLemonsSqueezeSound);
        }
    }

    public Color getWizardColor() {
        return wizardType.getColor();
    }

    public Texture getTexture() {
        return getIdleSprite().getTexture();
    }

    public Texture getPortraitTexture() {
        return portraitTexture;
    }
}
