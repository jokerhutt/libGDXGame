package jokerhut.main;

import Constants.Constants;
import camera.MainCamera;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import entities.Player;

/** First screen of the application. Displayed after the application is created. */
public class MainScreen implements Screen {


    public TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    public Array<Rectangle> wallCollisionRects;
    MapLoader mapLoader;
    SpriteBatch batch;
    MainCamera mainCamera;
    FitViewport viewport;

    public Player player;

    @Override
    public void show() {
        // Prepare your screen here.

        map = new TmxMapLoader().load("ninjatilesmap.tmx");
        mapLoader = new MapLoader(this);

        mainCamera = new MainCamera(this);
        viewport = new FitViewport(60, 60, mainCamera.camera);
        renderer = new OrthogonalTiledMapRenderer(map, 3f);

        wallCollisionRects = new Array<>();
        wallCollisionRects = mapLoader.createStaticCollisionRects("Collision");

        batch = new SpriteBatch();

        player = new Player(5, 15, this);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        mainCamera.updateCamera(delta);
        renderer.setView(mainCamera.camera);
        batch.setProjectionMatrix(mainCamera.camera.combined); // sync batch with camera
        renderer.render();
        batch.begin();
        player.render(batch);
        batch.end();
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
}
