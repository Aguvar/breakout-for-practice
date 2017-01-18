package com.dingogames;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Dingo on 09-Jan-17.
 */
public class HighscoreScreen implements Screen, InputProcessor {


    private final BreakoutGame game;

    private Viewport viewport;
    private OrthographicCamera camera;

    private BitmapFont scoreFont;
    private GlyphLayout scoreTitleLayout;
    private GlyphLayout scoreLayout;

    private Sprite backSprite;

    public HighscoreScreen(BreakoutGame game) {

        this.game = game;


        camera = new OrthographicCamera();
        viewport = new FitViewport(360,640,camera);
        camera.position.set(Gdx.graphics.getWidth()*0.5f,Gdx.graphics.getHeight()*0.5f,0);


        scoreFont = new BitmapFont(Gdx.files.internal("scoreFont.fnt"));
        scoreTitleLayout = new GlyphLayout(scoreFont,"HIGH SCORES");

        scoreLayout = new GlyphLayout(game.gameFont,"EXAMPLE SCORE");

        backSprite = new Sprite(new Texture(Gdx.files.internal("back_arrow.png")));
        backSprite.setPosition(20,20);

        Gdx.input.setInputProcessor(this);
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
        scoreFont.draw(game.batch, scoreTitleLayout,180- scoreTitleLayout.width*0.5f,640-20);
        printScores();
        backSprite.draw(game.batch);
        game.batch.end();
    }

    private void printScores() {
        String aux = "";
        Integer auxKey = 0;
        Array<Integer> keys = game.scores.getKeys();
        for (int x = keys.size-1; x >= 0; x--) {
            auxKey = keys.get(x);
            aux = auxKey.toString() + " - " + game.scores.getScore(auxKey);
            game.gameFont.draw(game.batch, aux,30,640*0.9f-(scoreLayout.height + 5)*(keys.size-1-x));
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

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 touch = new Vector3(screenX,screenY,0);
        camera.unproject(touch);
        if (backSprite.getBoundingRectangle().contains(touch.x,touch.y)){
            game.touchSound.play();
            game.setScreen(new StartScreen(game));
            dispose();
        }

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
