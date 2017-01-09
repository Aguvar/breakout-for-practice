package com.dingogames;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Dingo on 09-Jan-17.
 */
public class StartScreen implements Screen {
    private final BreakoutGame game;
    private Sprite playButton;
    private Sprite scoreButton;
    private OrthographicCamera camera;
    private GlyphLayout layout;
    private BitmapFont titleFont;
    private Viewport viewport;

    public StartScreen(BreakoutGame game) {
        this.game = game;
        playButton = new Sprite(new Texture(Gdx.files.internal("play_panel.png")));
        scoreButton = new Sprite(new Texture(Gdx.files.internal("score_panel.png")));
        this.titleFont = new BitmapFont(Gdx.files.local("StartFont.fnt"));
        layout = new GlyphLayout();
        layout.setText(titleFont,"COSO");
        camera = new OrthographicCamera();
        playButton.setPosition(Gdx.graphics.getWidth()*0.5f-playButton.getRegionWidth()*0.5f,240);
        playButton.setBounds(playButton.getX(),playButton.getY(),playButton.getWidth(),playButton.getHeight());
        scoreButton.setPosition(Gdx.graphics.getWidth()*0.5f-scoreButton.getRegionWidth()*0.5f,80);
        scoreButton.setBounds(scoreButton.getX(),scoreButton.getY(),scoreButton.getWidth(),scoreButton.getHeight());
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        playButton.draw(game.batch);
        scoreButton.draw(game.batch);
        titleFont.draw(game.batch,"COSO",Gdx.graphics.getWidth()*0.5f-layout.width*0.5f,Gdx.graphics.getHeight()*0.9f);
        game.batch.end();

        if (scoreButton.getBoundingRectangle()){
            game.setScreen(new PlayScreen(game));
            dispose();
        }
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
