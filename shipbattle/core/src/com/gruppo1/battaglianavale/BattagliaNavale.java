package com.gruppo1.battaglianavale;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;

public class BattagliaNavale extends Game {


	MenuScreen menuScreen;
	GameScreen gameScreen;
	SpriteBatch batch;
	BitmapFont font12;
	BitmapFont font18;
	BitmapFont fontTxtField;
	BitmapFont fontTitle;


	Skin defaultSkin;

	GameLogic theGame;

	@Override
	public void create() {
		theGame = new GameLogic(this);

		initFont();
		defaultSkin = new Skin();
		defaultSkin.add("myfont", fontTxtField, BitmapFont.class);
		defaultSkin.addRegions(new TextureAtlas("skins/Particle Park UI Skin/Particle Park UI.atlas"));
		defaultSkin.load(Gdx.files.internal("skins/Particle Park UI Skin/Particle Park UI.json"));


		batch = new SpriteBatch();

		menuScreen = new MenuScreen(this);
		gameScreen = new GameScreen(this);

		this.setScreen(menuScreen);

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
		FreeTypeFontGenerator.FreeTypeFontParameter biggerParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 12;
		parameter.color = Color.BLACK;
		biggerParameter.size = 18;
		biggerParameter.color = Color.BLACK;
		FreeTypeFontGenerator.FreeTypeFontParameter paramField = new FreeTypeFontGenerator.FreeTypeFontParameter();
		paramField.size = 18;
		paramField.color = Color.WHITE;

		FreeTypeFontGenerator.FreeTypeFontParameter paramTitle = new FreeTypeFontGenerator.FreeTypeFontParameter();
		paramTitle.size = 32;
		paramTitle.color = Color.BLACK;
		font12 = gen.generateFont(parameter);
		font18 = gen.generateFont(biggerParameter);
		fontTxtField = gen.generateFont(paramField);
		fontTitle = gen.generateFont(paramTitle);

	}



}
