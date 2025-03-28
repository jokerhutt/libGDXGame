package camera;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import constants.Constants;
import jokerhut.main.FarmScreen;

public class MainCamera {

    FarmScreen screen;

    public OrthographicCamera camera;
    private Vector2 cameraTarget = new Vector2();
    private Vector2 cameraPosition = new Vector2();
    private float cameraLerp = 5f;

    private float mapWidthPixels;
    private float mapHeightPixels;

    public MainCamera (FarmScreen screen) {

        this.screen = screen;

        int mapWidth = screen.map.getProperties().get("width", Integer.class);
        int mapHeight = screen.map.getProperties().get("height", Integer.class);
        int tileWidth = screen.map.getProperties().get("tilewidth", Integer.class);
        int tileHeight = screen.map.getProperties().get("tileheight", Integer.class);

        mapWidthPixels = mapWidth * tileWidth * Constants.SCALE;
        mapHeightPixels = mapHeight * tileHeight * Constants.SCALE;

        camera = new OrthographicCamera();

        camera.setToOrtho(false, 60, 60);
        camera.zoom = 0.6f;

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

        float clampedX = MathUtils.clamp(cameraPosition.x, halfViewWidth, mapWidthPixels - halfViewWidth);
        float clampedY = MathUtils.clamp(cameraPosition.y, halfViewHeight, mapHeightPixels - halfViewHeight);

        clampedX = Math.round(clampedX * 100f) / 100f;
        clampedY = Math.round(clampedY * 100f) / 100f;

        camera.position.set(clampedX, clampedY, 0);
        camera.update();
    }




}
