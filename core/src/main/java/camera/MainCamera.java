package camera;

import Constants.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import jokerhut.main.MainScreen;
import sound.MusicHandler;

import static Constants.Constants.screenHeightInTiles;

public class MainCamera {

    MainScreen screen;

    public OrthographicCamera camera;
    private Vector2 cameraTarget = new Vector2();
    private Vector2 cameraPosition = new Vector2();
    private float cameraLerp = 5f;

    private float mapWidthPixels;
    private float mapHeightPixels;

    int mapWidth;
    int mapHeight;

    public MainCamera (MainScreen screen) {

        this.screen = screen;

        int tileWidth = screen.map.getProperties().get("tilewidth", Integer.class);
        int tileHeight = screen.map.getProperties().get("tileheight", Integer.class);

        this.mapHeight = screen.map.getProperties().get("height", Integer.class);
        this.mapWidth = screen.map.getProperties().get("width", Integer.class);

        mapWidthPixels = mapWidth * Constants.TILESIZE;
        mapHeightPixels = mapHeight * Constants.TILESIZE;

        camera = new OrthographicCamera();

        camera.setToOrtho(false, Constants.screenWidthInTiles, Constants.screenHeightInTiles);
        camera.zoom = 0.5f;

    }

    public void updateCamera (float delta) {
        Vector2 playerPos = screen.player.getPosition();

        //CENTER POSITIONS OF PLAYERS
        float centerX = screen.player.getCenterX();
        float centerY = screen.player.getCenterY();

        // Update camera target
        cameraTarget.set(centerX, centerY);

        //LERP for smooth render
        if (cameraPosition.dst(cameraTarget) > 0.01f) {
            cameraPosition.lerp(cameraTarget, cameraLerp * delta);
        }

        // Clamp to map bounds
        float halfViewWidth = camera.viewportWidth * 0.5f * camera.zoom;
        float halfViewHeight = camera.viewportHeight * 0.5f * camera.zoom;

        float clampedX = MathUtils.clamp(cameraPosition.x, halfViewWidth, mapWidth - halfViewWidth);
        float clampedY = MathUtils.clamp(cameraPosition.y, halfViewHeight, mapHeight - halfViewHeight);

        clampedX = Math.round(clampedX * 100f) / 100f;
        clampedY = Math.round(clampedY * 100f) / 100f;

        camera.position.set(clampedX, clampedY, 0);
        camera.update();
    }

}
