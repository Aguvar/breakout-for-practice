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
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Dingo on 09-Jan-17.
 */
public class StartScreen implements Screen, InputProcessor {
    private final BreakoutGame game;
    private Sprite playBtnSprite;
    private Sprite scoreBtnSprite;
    private OrthographicCamera camera;
//    private Stage stage;
//    private Button playBtn;
//    private Button highBtn;
    private GlyphLayout layout;
    private BitmapFont titleFont;
    private Viewport viewport;

    public StartScreen(BreakoutGame game) {
        this.game = game;
        playBtnSprite = new Sprite(new Texture(Gdx.files.internal("play_panel.png")));
        scoreBtnSprite = new Sprite(new Texture(Gdx.files.internal("score_panel.png")));
        this.titleFont = new BitmapFont(Gdx.files.local("StartFont.fnt"));
        layout = new GlyphLayout();
        layout.setText(titleFont,"COSO");
        camera = new OrthographicCamera();
        camera.position.x = Gdx.graphics.getWidth()*0.5f;
        camera.position.y = Gdx.graphics.getHeight()*0.5f;
        viewport = new StretchViewport(360,640,camera);
        playBtnSprite.setPosition(Gdx.graphics.getWidth()*0.5f- playBtnSprite.getRegionWidth()*0.5f,240);
        playBtnSprite.setBounds(playBtnSprite.getX(), playBtnSprite.getY(), playBtnSprite.getWidth(), playBtnSprite.getHeight());
        scoreBtnSprite.setPosition(Gdx.graphics.getWidth()*0.5f- scoreBtnSprite.getRegionWidth()*0.5f,80);
        scoreBtnSprite.setBounds(scoreBtnSprite.getX(), scoreBtnSprite.getY(), scoreBtnSprite.getWidth(), scoreBtnSprite.getHeight());

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        playBtnSprite.draw(game.batch);
        scoreBtnSprite.draw(game.batch);
        titleFont.draw(game.batch,"COSO",Gdx.graphics.getWidth()*0.5f-layout.width*0.5f,Gdx.graphics.getHeight()*0.9f);
        game.batch.end();

//        if (scoreBtnSprite.getBoundingRectangle()){
//            game.setScreen(new PlayScreen(game));
//            dispose();
//        }
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
        camera.zoom =- 0.5f;
        return true;
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
        if (playBtnSprite.getBoundingRectangle().contains(input.x,input.y)){
            game.setScreen(new PlayScreen(game));
            dispose();
        }
        else if (scoreBtnSprite.getBoundingRectangle().contains(input.x,input.y)){
            game.setScreen(new HighscoreScreen(game));
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
