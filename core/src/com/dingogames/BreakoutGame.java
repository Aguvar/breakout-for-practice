package com.dingogames;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Logger;

public class BreakoutGame extends Game {
	SpriteBatch batch;
	BitmapFont gameFont;
	Highscore scores;
	Sound touchSound;

	@Override
	public void create () {
		batch = new SpriteBatch();
		gameFont = new BitmapFont(Gdx.files.internal("gameFont.fnt"));
		this.setScreen(new StartScreen(this));
		Json json = new Json();
		if (Gdx.files.internal("scores.json").exists()){
			try {
				scores = json.fromJson(Highscore.class,Gdx.files.internal("scores.json"));
			} catch (Exception e) {

				scores = new Highscore(true);
			}
		}else{
			scores = new Highscore(true);
		}
		touchSound = Gdx.audio.newSound(Gdx.files.internal("TouchSin.wav"));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		gameFont.dispose();
		Json json = new Json();
		Gdx.files.local("scores.json").writeString(json.toJson(scores),false);
	}
}
