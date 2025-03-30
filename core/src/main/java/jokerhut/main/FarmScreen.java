package jokerhut.main;

import camera.MainCamera;
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
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import constants.Constants;
import debug.CollisionDebug;
import entities.Animal;
import entities.Chicken;
import entities.Cow;
import entities.Player;
import com.badlogic.gdx.math.Rectangle;
import hud.HUD;
import objects.GameObject;
import sound.MusicHandler;


/** First screen of the application. Displayed after the application is created. */
public class FarmScreen implements Screen {

    public TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    public Player player;
    private SpriteBatch batch;

    public Array<GameObject> treeObjects;
    public Array<Rectangle> wallCollisionRects;
    public Array<GameObject> canPickUpObjects;

    public Animal[] animals;


    MapLoader mapLoader;
    MusicHandler musicHandler;
    CollisionDebug collisionDebugger;
    FitViewport viewport;
    public HUD hud;

    public MainCamera mainCamera;


    @Override
    public void show() {

        map = new TmxMapLoader().load("sproutynewnew.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, Constants.SCALE);

        musicHandler = new MusicHandler();
        musicHandler.playRainyDayMusic();

        mapLoader = new MapLoader(this);
        mainCamera = new MainCamera(this);
        viewport = new FitViewport(60, 60, mainCamera.camera);
        collisionDebugger = new CollisionDebug(this);

        animals = new Animal[8];

        wallCollisionRects = new Array<>();
        wallCollisionRects = mapLoader.createStaticCollisionRects("Wall");

        treeObjects = new Array<>();
        treeObjects = mapLoader.loadObjectsFromLayer("Tree");

        player = new Player(15, 15, this);
        addAnimals();

        batch = new SpriteBatch();
        hud = new HUD(new ScreenViewport(), batch, this);


    }

    public void addAnimals () {
        int i = 0;
        animals[i] = new Chicken(20f, 20f, this);
        i++;
        animals[i] = new Chicken(18f, 18f, this);
        i++;
        animals[i] = new Cow(30f, 18f, this);
        i++;
        animals[i] = new Cow(25f, 18f, this);
        i++;
    }

    public void renderAnimals () {
        for (int i = 0; i < animals.length; i++) {
            if (animals[i] != null) {
                animals[i].render(batch);
            }
        }
    }

    public void updateAnimals (float delta) {
        for (int i = 0; i < animals.length; i++){
            if (animals[i] != null) {
                Animal selectedAnimal = animals[i];
                selectedAnimal.update(delta);
            }
        }
    }

    @Override
    public void render(float delta) {
        // Draw your screen here. "delta" is the time since last render in seconds.
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        mainCamera.updateCamera(delta);
        renderer.setView(mainCamera.camera);
        renderer.render();
        player.update(delta);
        updateAnimals(delta);
        batch.setProjectionMatrix(mainCamera.camera.combined);
        batch.begin();
        player.render(batch);
        renderAnimals();
        batch.end();
        runScreenDebugMethods();
        hud.render(delta);
    }


    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        hud.resize(width, height); // now safe
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
        if (CollisionDebug.SHOWGAMEOBJECTCOLLISION) {
            collisionDebugger.gameObjectCollisionDebug(treeObjects);
        }
        if (CollisionDebug.SHOWSTATICOBJECTCOLLISION) {
            collisionDebugger.staticObjectCollisionDebug(wallCollisionRects);
        }
        if (CollisionDebug.SHOWANIMALCOLLISION) {
            collisionDebugger.entityArrayCollisionDebug();
        }

    }

}
