package jokerhut.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Array;
import constants.Constants;
import debug.CollisionDebug;
import entities.Player;
import com.badlogic.gdx.math.Rectangle;
import sound.MusicHandler;


/** First screen of the application. Displayed after the application is created. */
public class FarmScreen implements Screen {

    public TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    public OrthographicCamera camera;
    public Player player;
    private SpriteBatch batch;

    public Array<Rectangle> treeCollisionRects;
    public Array<Rectangle> wallCollisionRects;

    MapLoader mapLoader;
    MusicHandler musicHandler;
    CollisionDebug collisionDebugger;



    @Override
    public void show() {
        // Prepare your screen here.

        map = new TmxMapLoader().load("sproutynewnew.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, Constants.SCALE);
        camera = new OrthographicCamera();

        musicHandler = new MusicHandler();
        musicHandler.playRainyDayMusic();

        mapLoader = new MapLoader(this);
        collisionDebugger = new CollisionDebug(this);

        wallCollisionRects = new Array<>();
        wallCollisionRects = mapLoader.createCollisionRects("Wall");

        treeCollisionRects = new Array<>();
        treeCollisionRects = mapLoader.createCollisionRects("Tree");

        player = new Player(15, 15, this);



        camera.setToOrtho(false, 60, 60);

        batch = new SpriteBatch();

    }

    @Override
    public void render(float delta) {
        // Draw your screen here. "delta" is the time since last render in seconds.
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        renderer.setView(camera);
        renderer.render();

        player.update(delta);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        player.render(batch);

        batch.end();

        runScreenDebugMethods();

    }

    @Override
    public void resize(int width, int height) {
        // Resize your screen here. The parameters represent the new window size.
    }

    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void hide() {
        // This method is called when another screen replaces this one.
    }

    @Override
    public void dispose() {
        // Destroy screen's assets here.
    }

    public void runScreenDebugMethods () {

        if (CollisionDebug.SHOWPLAYERCOLLISION) {
            collisionDebugger.playerCollisionDebug();
        }
        if (CollisionDebug.SHOWTREECOLLISION) {
            collisionDebugger.staticObjectCollisionDebug(treeCollisionRects);
        }
        if (CollisionDebug.SHOWHOUSECOLLISION) {
            collisionDebugger.staticObjectCollisionDebug(wallCollisionRects);
        }


    }

}
