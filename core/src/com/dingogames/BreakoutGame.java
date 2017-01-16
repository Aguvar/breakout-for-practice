package com.dingogames;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BreakoutGame extends Game {
	SpriteBatch batch;
	BitmapFont gameFont;

	
	@Override
	public void create () {
		batch = new SpriteBatch();
		gameFont = new BitmapFont(Gdx.files.internal("gameFont.fnt"));
		this.setScreen(new StartScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();

	}
}
