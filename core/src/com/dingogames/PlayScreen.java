package com.dingogames;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Dingo on 09-Jan-17.
 */
public class PlayScreen implements Screen{
    private final BreakoutGame game;
    private OrthographicCamera camera;
    private Viewport viewport;
    private World world;
    private Box2DDebugRenderer renderer;
    private Body ball;
    private Sprite ballSprite;

    //World to simulation conversion
    private final float WtoS = 1/40f;
    private final float StoW = 40f;

    public PlayScreen(BreakoutGame game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.position.x = Gdx.graphics.getWidth()*0.5f;
        camera.position.y = Gdx.graphics.getHeight()*0.5f;
        camera.update();

        viewport = new FitViewport(360,640,camera);

        ballSprite = new Sprite(new Texture(Gdx.files.local("breakoutBall.png")));
        ballSprite.setScale(0.5f);

        generatePhysics();

    }

    private void generatePhysics() {
        world = new World(new Vector2(0,0), true);

        //Generate ball
        BodyDef ballDef = new BodyDef();
        ballDef.type = BodyDef.BodyType.DynamicBody;
        ballDef.position.set(Gdx.graphics.getWidth()*0.5f*WtoS,Gdx.graphics.getHeight()*0.5f*WtoS);

        CircleShape ballShape = new CircleShape();
        ballShape.setRadius(0.5f);

        FixtureDef circleFix = new FixtureDef();
        circleFix.shape = ballShape;
        circleFix.density = 0.5f;
        circleFix.friction = 0;
        circleFix.restitution = 1;

        ball = world.createBody(ballDef);
        ball.createFixture(circleFix);
        ball.setUserData(ballSprite);

        //Generate Pallette


        renderer = new Box2DDebugRenderer(true,false,false,true,false,false);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        world.step(1/60f,6,2);

//        updateSpritePositions();

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        drawBodies();
        game.batch.end();

        renderer.render(world,camera.combined);
    }

    private void drawBodies() {
        Array<Body> holder = new Array<Body>(200);
        world.getBodies(holder);
        for (Body body : holder) {
            if (body.getUserData() instanceof Sprite){
                ((Sprite) (body.getUserData())).setPosition(body.getPosition().x*StoW - ((Sprite) (body.getUserData())).getWidth()*0.5f,body.getPosition().y*StoW - ((Sprite) (body.getUserData())).getHeight()*0.5f);
                ((Sprite) (body.getUserData())).draw(game.batch);
            }
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
