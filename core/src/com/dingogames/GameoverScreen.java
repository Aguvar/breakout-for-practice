package com.dingogames;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Dingo on 09-Jan-17.
 */
public class GameoverScreen implements Screen, Input.TextInputListener, InputProcessor {

    private final BreakoutGame game;
    private final int score;

    private OrthographicCamera camera;
    private Viewport viewport;

    private BitmapFont overFont;
    private GlyphLayout layout;
    private GlyphLayout scoreLayout;


    private Sprite submitSprite;
    private Sprite menuSprite;
    private boolean submitFlag;

    public GameoverScreen(BreakoutGame game, int score) {
        this.game = game;
        this.score = score;

        submitSprite = new Sprite(new Texture(Gdx.files.local("submit_panel.png")));
        submitSprite.setPosition(360*0.5f-submitSprite.getWidth()*0.5f,640*0.35f);
        menuSprite = new Sprite(new Texture(Gdx.files.internal("mmenu_panel.png")));
        menuSprite.setPosition(360*0.5f-menuSprite.getWidth()*0.5f,640*0.35f-submitSprite.getHeight()-20);



        camera = new OrthographicCamera();
        viewport = new FitViewport(360,640, camera);
        camera.position.set(Gdx.graphics.getWidth()*0.5f,Gdx.graphics.getHeight()*0.5f,0);

        overFont = new BitmapFont(Gdx.files.internal("overFont.fnt"));
        layout = new GlyphLayout(overFont,"GAME\nOVER");

        scoreLayout = new GlyphLayout(game.gameFont,"YOUR SCORE: " + Integer.toString(score) + " POINTS");
        submitFlag = true;

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.8f, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        overFont.draw(game.batch,"GAME\nOVER",180 - layout.width*0.5f,640*0.8f);
        game.gameFont.draw(game.batch,scoreLayout,180 - scoreLayout.width*0.5f,640 * 0.5f);
        if (submitFlag)
            submitSprite.draw(game.batch);
        menuSprite.draw(game.batch);
        game.batch.end();

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
        overFont.dispose();

    }

    @Override
    public void input(String text) {
        game.scores.add(score,text);
    }

    @Override
    public void canceled() {

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
        Vector3 input = new Vector3(screenX,screenY,0);
        camera.unproject(input);
        if (submitSprite.getBoundingRectangle().contains(input.x,input.y) && submitFlag){
            submitFlag = false;
            game.touchSound.play();
            Gdx.input.getTextInput(this,"SUBMIT SCORE", "", "Your name here!");
        }
        else if (menuSprite.getBoundingRectangle().contains(input.x,input.y)){
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
