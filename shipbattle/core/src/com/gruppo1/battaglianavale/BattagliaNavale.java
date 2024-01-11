package com.gruppo1.battaglianavale;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.ScreenUtils;

public class BattagliaNavale extends Game {


	SpriteBatch batch;
	BitmapFont font12;

	GameLogic theGame;

	@Override
	public void create() {
		theGame = new GameLogic(this);

		batch = new SpriteBatch();
		initFont();
		this.setScreen(new MenuScreen(this));

	}


	@Override
	public void dispose(){
		super.dispose();
	}

	public void render(){
		super.render();

	}

	private void initFont(){
		FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Comforta.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 12;
		parameter.color = Color.BLACK;
		font12 = gen.generateFont(parameter);

	}



}
