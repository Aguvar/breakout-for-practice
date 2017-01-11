package com.dingogames;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
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
public class PlayScreen implements Screen, InputProcessor, ContactListener {
    private final BreakoutGame game;
    private OrthographicCamera camera;
    private Viewport viewport;
    private World world;
    private Box2DDebugRenderer renderer;
    private Body ball;
    private Sprite ballSprite;
    private Body palette;
    private Sprite paletteSprite;
    private Array<Array<Body>> blocks;
    private Sprite blockSprite;
    private Array<Body> walls;

    //World to simulation conversion
    private final float WtoS = 1/20f;
    private final float StoW = 20f;
    //Parameters (In world units)
    private final float paletteSpeed = 180;
    private final int blocksX = 6;
    private final int blocksY = 4;

    private Vector2 flag = new Vector2(-1,-1);

    public PlayScreen(BreakoutGame game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.position.x = Gdx.graphics.getWidth()*0.5f;
        camera.position.y = Gdx.graphics.getHeight()*0.5f;
        camera.update();

        viewport = new FitViewport(360,640,camera);

        ballSprite = new Sprite(new Texture(Gdx.files.local("breakoutBall.png")));
        ballSprite.setScale(0.5f);

        paletteSprite = new Sprite(new Texture(Gdx.files.local("breakoutPalette.png")));

        blockSprite = new Sprite(new Texture(Gdx.files.local("breakoutBlock.png")));

        //Create matrix
        blocks = new Array<Array<Body>>(blocksY);
        for (int i = 0; i < blocksY; i++) {
            blocks.add(new Array<Body>(blocksX));
//            blocks.items[i] = new Array<Body>(blocksX);
//            blocks.set(i,new Array<Body>(blocksX));
        }

        walls = new Array<Body>(4);

        generatePhysics();

        world.setContactListener(this);
        Gdx.input.setInputProcessor(this);

    }

    private void generatePhysics() {
        world = new World(new Vector2(0,0), true);

        //Generate ball
        BodyDef ballDef = new BodyDef();
        ballDef.type = BodyDef.BodyType.DynamicBody;
        ballDef.position.set(Gdx.graphics.getWidth()*0.5f*WtoS,Gdx.graphics.getHeight()*0.5f*WtoS);

        CircleShape ballShape = new CircleShape();
        ballShape.setRadius(ballSprite.getHeight()*0.5f*ballSprite.getScaleX()*WtoS);

        FixtureDef circleFix = new FixtureDef();
        circleFix.shape = ballShape;
        circleFix.density = 0.5f;
        circleFix.friction = 0;
        circleFix.restitution = 1.1f;

        ball = world.createBody(ballDef);
        ball.createFixture(circleFix);
        ball.setUserData(ballSprite);

        ballShape.dispose();

        //Generate Palette
        BodyDef paletteDef = new BodyDef();
        paletteDef.type = BodyDef.BodyType.KinematicBody;
        paletteDef.position.set(Gdx.graphics.getWidth()*0.5f*WtoS,30*WtoS);

        PolygonShape paletteShape = new PolygonShape();
        paletteShape.setAsBox(paletteSprite.getWidth()*WtoS*0.5f,paletteSprite.getHeight()*WtoS*0.5f);

        FixtureDef rectangleFix = new FixtureDef();
        rectangleFix.shape = paletteShape;
        rectangleFix.density = 1;
        rectangleFix.friction = 0;
        rectangleFix.restitution = 1;

        palette = world.createBody(paletteDef);
        palette.createFixture(rectangleFix);
        palette.setUserData(paletteSprite);

        paletteShape.dispose();

        //Generate blocks
        BodyDef blockDef = new BodyDef();
        blockDef.type = BodyDef.BodyType.StaticBody;
        //Ver la posicion despues

        PolygonShape blockShape = new PolygonShape();
        blockShape.setAsBox(blockSprite.getWidth()*0.5f*blockSprite.getScaleX()*WtoS,blockSprite.getHeight()*0.5f*blockSprite.getScaleY()*WtoS);

        FixtureDef blockFix = new FixtureDef();
        blockFix.shape = blockShape;
        blockFix.density = 1;
        blockFix.friction = 0;
        blockFix.restitution = 0.9f;

        for (int y = 0; y < blocksY; y++) {
            for (int x = 0; x < blocksX; x++) {
                blockDef.position.set(Gdx.graphics.getWidth()*0.3f*WtoS + (blockSprite.getWidth() + 5)*x*WtoS , Gdx.graphics.getHeight()*0.8f*WtoS + (blockSprite.getHeight() + 5)*y*WtoS);
                blocks.get(y).add(world.createBody(blockDef));
                blocks.get(y).get(x).createFixture(blockFix);
                blocks.get(y).get(x).setUserData(new Vector2(x,y));
            }
        }

        blockShape.dispose();

        //Generar paredes
        BodyDef wallDef = new BodyDef();
        wallDef.type = BodyDef.BodyType.StaticBody;


        EdgeShape wallShape = new EdgeShape();
        wallShape.set(0,0,0,Gdx.graphics.getHeight()*WtoS);

        FixtureDef wallFix = new FixtureDef();
        wallFix.shape = wallShape;
        wallFix.friction = 0;
        wallFix.restitution = 1;

        walls.add(world.createBody(wallDef));
        walls.get(0).createFixture(wallFix);

        wallShape.set(0,Gdx.graphics.getHeight()*WtoS,Gdx.graphics.getWidth()*WtoS,Gdx.graphics.getHeight()*WtoS);
        wallFix.shape = wallShape;

        walls.add(world.createBody(wallDef));
        walls.get(1).createFixture(wallFix);

        wallShape.set(Gdx.graphics.getWidth()*WtoS,Gdx.graphics.getHeight()*WtoS,Gdx.graphics.getWidth()*WtoS,0);
        wallFix.shape = wallShape;

        walls.add(world.createBody(wallDef));
        walls.get(2).createFixture(wallFix);

        wallShape.set(Gdx.graphics.getWidth()*WtoS,0,0,0);
        wallFix.shape = wallShape;

        walls.add(world.createBody(wallDef));
        walls.get(3).createFixture(wallFix);

        wallShape.dispose();

        ball.setLinearVelocity(10,15);

        renderer = new Box2DDebugRenderer(true,false,false,true,false,false);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        limitPalette();
        world.step(1/60f,6,2);
        eliminarBloques();

//        updateSpritePositions();

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        drawBodies();
        game.batch.end();

        renderer.render(world,camera.combined);
    }

    private void eliminarBloques() {
        if (flag.x != -1){
            world.destroyBody(blocks.get((int)flag.y).get((int)flag.x));
            flag = new Vector2(-1,-1);
        }
    }

    private void limitPalette() {
        if ((paletteSprite.getX() < 5 && palette.getLinearVelocity().x < 0) || (paletteSprite.getX() > Gdx.graphics.getWidth() - paletteSprite.getWidth() - 5 && palette.getLinearVelocity().x > 0 )){
            palette.setLinearVelocity(0,0);
        }
    }

    private void drawBodies() {
        Array<Body> holder = new Array<Body>(200);
        world.getBodies(holder);
        for (Body body : holder) {
            if (body.getUserData() instanceof Sprite){
                ((Sprite) (body.getUserData())).setPosition(body.getPosition().x*StoW - ((Sprite) (body.getUserData())).getWidth()*0.5f,body.getPosition().y*StoW - ((Sprite) (body.getUserData())).getHeight()*0.5f);
                ((Sprite) (body.getUserData())).draw(game.batch);
            }
            else if (body.getUserData() instanceof Vector2){
//                blockDef.position.set(Gdx.graphics.getWidth()*0.3f*WtoS + (blockSprite.getWidth() + 5)*x*WtoS , Gdx.graphics.getHeight()*0.8f*WtoS + (blockSprite.getHeight() + 5)*y*WtoS);
                float x = ((Vector2) body.getUserData()).x;
                float y = ((Vector2) body.getUserData()).y;
                blockSprite.setPosition(blocks.get((int)y).get((int)x).getPosition().x*StoW - blockSprite.getWidth()*0.5f,blocks.get((int)y).get((int)x).getPosition().y*StoW - blockSprite.getHeight()*0.5f);
                blockSprite.draw(game.batch);
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
        world.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.RIGHT){
            palette.setLinearVelocity(paletteSpeed*WtoS,0);
        }
        if (keycode == Input.Keys.LEFT){
            palette.setLinearVelocity(-paletteSpeed*WtoS,0);
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.RIGHT && palette.getLinearVelocity().x > 0){
            palette.setLinearVelocity(0,0);
        }
        if (keycode == Input.Keys.LEFT && palette.getLinearVelocity().x < 0){
            palette.setLinearVelocity(0,0);
        }
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
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

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        Body bodyA = fixA.getBody();
        Body bodyB = fixB.getBody();

        if (walls.contains(bodyA,false) || walls.contains(bodyB,false)){
            //Sonido
        }
        if (bodyA.getUserData() instanceof Vector2){
            flagEliminar((Vector2)bodyA.getUserData());
        }
        if (bodyB.getUserData() instanceof Vector2){
            flagEliminar((Vector2)bodyB.getUserData());
        }
        if (bodyA.equals(palette) || bodyB.equals(palette)){
            //Otro sonido
        }

    }

    private void flagEliminar(Vector2 blockLocation) {
        //Soniditos y efectos
        flag = blockLocation;
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
