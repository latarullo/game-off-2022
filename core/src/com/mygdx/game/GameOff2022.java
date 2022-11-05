package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.ScreenUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GameOff2022 extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	BigInteger value = BigInteger.ZERO;
	FreeTypeFontGenerator generator;
	FreeTypeFontGenerator.FreeTypeFontParameter parameter;
	BitmapFont font12;

	@Override
	public void create () {
		batch = new SpriteBatch();
		generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/AvQest.ttf"));
		parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 50;
		font12 = generator.generateFont(parameter); // font size 12 pixels
	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 1);
		batch.begin();


//		font12.draw(this.batch, BigNumberNotation.getPrintableValue(new BigInteger("21000000000000000000000001")) , 50, GameData.HEIGHT - 50);

//		if (Math.random() > 0.5 || Gdx.input.isKeyPressed(Input.Keys.X)){
		for (int i =0;i< 100; i++)
			value = value.add(BigInteger.valueOf (new Random().nextInt(1000000000)));
//		}

		font12.draw(this.batch, value.toString() , 50, GameData.HEIGHT);

		font12.draw(this.batch, BigNumberNotation.getPrintableValue(value) , 50, GameData.HEIGHT - 50);

		batch.end();
	}

	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
		generator.dispose(); // don't forget to dispose to avoid memory leaks!
		font12.dispose();
	}
}
