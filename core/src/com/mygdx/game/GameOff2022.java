package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.mygdx.game.domain.HealthPotion;
import com.mygdx.game.domain.HealthPotionEnum;
import com.mygdx.game.screen.MainMenuScreen;
import com.mygdx.game.screen.actor.Enemy;
import com.mygdx.game.screen.actor.Wizard;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class GameOff2022 extends Game {
	private SpriteBatch batch;
	private BitmapFont gameFontSmall;
	private BitmapFont gameFont;
	private Screen previousScreen;
	private Wizard currentWizard;
	private Enemy currentEnemy;
	private BigInteger money;
	private Map<HealthPotionEnum, Integer> potions = new HashMap<>();

	@Override
	public void create () {
		batch = new SpriteBatch();
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/AvQest.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 50;
		gameFont = generator.generateFont(parameter);
		FreeTypeFontGenerator.FreeTypeFontParameter parameterSmall = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameterSmall.size = 20;
		gameFontSmall = generator.generateFont(parameterSmall);

		generator.dispose();

		this.resetHealthPotions();
		this.resetMoney();

		this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void dispose () {
		batch.dispose();
		gameFont.dispose();
	}

	public void changeScreen(Screen screen){
		this.previousScreen = getScreen();
		this.setScreen(screen);
	}

	public void changePreviousScreen(){
		this.changeScreen(getPreviousScreen());
	}

	public SpriteBatch getBatch() {
		return batch;
	}

	public BitmapFont getGameFont() {
		return gameFont;
	}

	public BitmapFont getGameFontSmall() {
		return gameFontSmall;
	}

	private Screen getPreviousScreen() {
		return previousScreen;
	}

	public Wizard getCurrentWizard() {
		return currentWizard;
	}

	public void setCurrentWizard(Wizard currentWizard) {
		this.currentWizard = currentWizard;
	}

	public Enemy getCurrentEnemy() {
		return currentEnemy;
	}

	public void setCurrentEnemy(Enemy currentEnemy) {
		this.currentEnemy = currentEnemy;
	}

	public BigInteger getMoney() {
		return money;
	}

	public void addMoney(BigInteger money) {
		this.money = this.money.add(money);
	}

	public void subtractMoney(BigInteger money) {
		this.money = this.money.subtract(money);
	}

	public void resetMoney (){
		this.money = BigInteger.ZERO;
	}

	public void addHealthPotion(HealthPotionEnum healthPotionEnum){
		Integer potion = this.potions.get(healthPotionEnum);
		if (potion == null){
			this.potions.put(healthPotionEnum, 1);
		} else {
			this.potions.put(healthPotionEnum, potion+1);
		}
	}

	public void useHealthPotion(HealthPotionEnum healthPotionEnum){
		Integer availableQuantity = this.potions.get(healthPotionEnum);
		//USE POTION
		this.potions.put(healthPotionEnum, availableQuantity-1);
	}

	public void resetHealthPotions(){
		this.potions = new HashMap<>();
		this.potions.put(HealthPotionEnum.MinorHealthPotion, 0);
		this.potions.put(HealthPotionEnum.SmallHealthPotion, 0);
		this.potions.put(HealthPotionEnum.GreatHealthPotion, 0);
		this.potions.put(HealthPotionEnum.GreaterHealthPotion, 0);
	}

	public Map<HealthPotionEnum, Integer> getPotions() {
		return potions;
	}
}
