package com.dingogames;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Dingo on 09-Jan-17.
 */
public class HighscoreScreen implements Screen {

    private final Highscore scores;
    private final BreakoutGame game;

    private Viewport viewport;
    private OrthographicCamera camera;

    private BitmapFont titleFont;
    private GlyphLayout titleLayout;
    private GlyphLayout scoreLayout;

    public HighscoreScreen(BreakoutGame game) {
        Json json = new Json();
        this.game = game;
        if (Gdx.files.local("scores.json").exists()){
            scores = json.fromJson( Highscore.class ,Gdx.files.internal("scores.json"));
        } else{
            scores = new Highscore( new Array<String>(true, 20));
            scores.add("60 - AUG");
            Gdx.files.local("scores.json").writeString(json.toJson(scores),false);
        }

        camera = new OrthographicCamera();
        viewport = new FitViewport(360,640,camera);
        camera.position.set(Gdx.graphics.getWidth()*0.5f,Gdx.graphics.getHeight()*0.5f,0);


        titleFont = new BitmapFont(Gdx.files.internal("overFont.fnt"));
        titleLayout = new GlyphLayout(titleFont,"HIGH\nSCORES");

        scoreLayout = new GlyphLayout(game.gameFont,"EXAMPLE SCORE");
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0.5f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        titleFont.draw(game.batch,titleLayout,180-titleLayout.width*0.5f,640-20);
        printScores();
        game.batch.end();
    }

    private void printScores() {
        for (int x = 0; x < scores.quantity(); x++) {
            game.gameFont.draw(game.batch,Integer.toString(x+1) + ") " + scores.workArray.get(x),30,640*0.6f-(scoreLayout.height + 5)*x);
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);
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
