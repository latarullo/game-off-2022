package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.mygdx.game.screen.MainMenuScreen;
import com.mygdx.game.screen.actor.FireWizard;
import com.mygdx.game.screen.actor.LightningWizard;
import com.mygdx.game.screen.actor.Wizard;

public class GameOff2022 extends Game {
	private SpriteBatch batch;
	private BitmapFont gameFontSmall;
	private BitmapFont gameFont;
	private Screen previousScreen;
	private Wizard currentWizard;

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
		this.setScreen(new MainMenuScreen(this));
	}

//	@Override
//	public void render () {
//		ScreenUtils.clear(1, 0, 0, 1);
//		batch.begin();
//
//
////		font12.draw(this.batch, BigNumberNotation.getPrintableValue(new BigInteger("21000000000000000000000001")) , 50, GameData.HEIGHT - 50);
//
////		if (Math.random() > 0.5 || Gdx.input.isKeyPressed(Input.Keys.X)){
//		for (int i =0;i< 100; i++)
//			value = value.add(BigInteger.valueOf (new Random().nextInt(1000000000)));
////		}
//
//		font12.draw(this.batch, value.toString() , 50, GameData.HEIGHT);
//
//		font12.draw(this.batch, BigNumberNotation.getPrintableValue(value) , 50, GameData.HEIGHT - 50);
//
//		batch.end();
//	}

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
		//if (currentWizard == null) currentWizard = new LightningWizard();
		return currentWizard;
	}

	public void setCurrentWizard(Wizard currentWizard) {
		this.currentWizard = currentWizard;
	}
}
