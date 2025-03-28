package debug;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import jokerhut.main.FarmScreen;
import objects.GameObject;

public class CollisionDebug {

    public static boolean SHOWPLAYERCOLLISION = true;
    public static boolean SHOWGAMEOBJECTCOLLISION = true;
    public static boolean SHOWSTATICOBJECTCOLLISION = true;

    FarmScreen screen;
    ShapeRenderer shapeRenderer;

    public CollisionDebug (FarmScreen screen) {
        this.screen = screen;
        shapeRenderer = new ShapeRenderer();
    }


    public void playerCollisionDebug () {

            shapeRenderer.setProjectionMatrix(screen.mainCamera.camera.combined);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(Color.RED);

            shapeRenderer.rect(
                screen.player.getCollisionRect().x,
                screen.player.getCollisionRect().y,
                screen.player.getCollisionRect().width,
                screen.player.getCollisionRect().height
            );

            shapeRenderer.end();
        }

    public void staticObjectCollisionDebug (Array<Rectangle> rects) {
        shapeRenderer.setProjectionMatrix(screen.mainCamera.camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.BLUE);
        for (Rectangle rect: rects) {
            shapeRenderer.rect(
                rect.x,
                rect.y,
                rect.width,
                rect.height
            );

        }
        shapeRenderer.end();
    }

    public void gameObjectCollisionDebug (Array<GameObject> gameObjects) {
        shapeRenderer.setProjectionMatrix(screen.mainCamera.camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.BLUE);
        for (GameObject gameObject: gameObjects) {
            shapeRenderer.rect(
                gameObject.collisionRect.x,
                gameObject.collisionRect.y,
                gameObject.collisionRect.width,
                gameObject.collisionRect.height
            );
        }
        shapeRenderer.end();
    }








}
