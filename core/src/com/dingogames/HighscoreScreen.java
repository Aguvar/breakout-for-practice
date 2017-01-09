package com.dingogames;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;

/**
 * Created by Dingo on 09-Jan-17.
 */
public class HighscoreScreen implements Screen {

    private final Highscore scores;
    private final BreakoutGame game;

    public HighscoreScreen(BreakoutGame game) {
        this.game = game;
        if (Gdx.files.local("scores.json").exists()){
            Json json = new Json();
            scores = json.fromJson( Highscore.class ,Gdx.files.internal("scores.json"));
        } else{
            scores = new Highscore( new Array<String>(true, 10));
            scores.add("60 - AUG");
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
