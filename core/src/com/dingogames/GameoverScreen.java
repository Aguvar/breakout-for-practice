package com.dingogames;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Dingo on 09-Jan-17.
 */
public class GameoverScreen implements Screen {

    private final BreakoutGame game;
    private final int score;

    private OrthographicCamera camera;
    private Viewport viewport;

    private BitmapFont overFont;
    private GlyphLayout layout;

    public GameoverScreen(BreakoutGame game, int score) {
        this.game = game;
        this.score = score;

        camera = new OrthographicCamera();
        viewport = new FitViewport(360,640, camera);

        overFont = new BitmapFont(Gdx.files.internal("overFont.fnt"));
        layout = new GlyphLayout(overFont,"GAME\nOVER");
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.8f, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        overFont.draw(game.batch,"GAME\nOVER",180 - layout.width*0.5f,640*0.8f);
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

    }
}
