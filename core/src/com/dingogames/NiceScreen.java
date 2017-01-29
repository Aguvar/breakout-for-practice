package com.dingogames;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Dingo on 28-Jan-17.
 */
public class NiceScreen implements Screen {

    private final BreakoutGame game;

    private OrthographicCamera camera;
    private Viewport viewport;
    private final int score;
    private int nextLevel;
    private int currentLives;
    private GlyphLayout layout;
    private Sound niceSound;

    public NiceScreen(final BreakoutGame game, final int score, int currentLevel, final int currentLives) {
        this.game = game;
        this.score = score;
        this.nextLevel = currentLevel + 1;
        this.currentLives = currentLives;

        camera = new OrthographicCamera();
        viewport = new FitViewport(360,640, camera);
        camera.position.set(viewport.getWorldWidth()*0.5f,viewport.getWorldHeight()*0.5f,0);

        layout = new GlyphLayout(game.gameFont,"NICE!");

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                game.setScreen(new PlayScreen(game,nextLevel,score,currentLives));
            }
        },2);

        niceSound = Gdx.audio.newSound(Gdx.files.local("Nice.wav"));
        niceSound.play();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.gameFont.draw(game.batch,layout,viewport.getWorldWidth()*0.5f-layout.width*0.5f,viewport.getWorldHeight()*0.5f);
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
