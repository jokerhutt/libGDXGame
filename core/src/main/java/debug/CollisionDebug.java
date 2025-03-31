package debug;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import entities.Animal;
import entities.Entity;
import jokerhut.main.FarmScreen;
import jokerhut.main.FarmTile;
import objects.GameObject;

public class CollisionDebug {

    public static boolean SHOWPLAYERCOLLISION = true;
    public static boolean SHOWGAMEOBJECTCOLLISION = true;
    public static boolean SHOWSTATICOBJECTCOLLISION = true;
    public static boolean SHOWANIMALCOLLISION = true;
    public static boolean SHOWNPCCOLLISION = true;
    public static boolean SHOWDIALOGUECOLLISION = true;
    public static boolean SHOWFARMABLEDEBUG = true;
    public static boolean SHOWFARMINGRANGEDEBUG = true;



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

    public void playerFarmingRangeDebug () {

        shapeRenderer.setProjectionMatrix(screen.mainCamera.camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.PINK);

        shapeRenderer.rect(
            screen.player.farmingRange.x,
            screen.player.farmingRange.y,
            screen.player.farmingRange.width,
            screen.player.farmingRange.height
        );

        shapeRenderer.end();
    }

    public void playerDialogueCollisionDebug () {

        shapeRenderer.setProjectionMatrix(screen.mainCamera.camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.PINK);

        shapeRenderer.rect(
            screen.player.dialogueCollisionZone.x,
            screen.player.dialogueCollisionZone.y,
            screen.player.dialogueCollisionZone.width,
            screen.player.dialogueCollisionZone.height
        );

        shapeRenderer.end();
    }

    public void showFarmableTilesDebug (Array<FarmTile> farmTiles) {

        shapeRenderer.setProjectionMatrix(screen.mainCamera.camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.ORANGE);

        for (FarmTile farmTile : farmTiles) {
            if (farmTile != null) {
                shapeRenderer.rect(
                    farmTile.bounds.x,
                    farmTile.bounds.y,
                    farmTile.bounds.width,
                    farmTile.bounds.height
                );
            }
        }
        shapeRenderer.end();
    }

    public void entityArrayCollisionDebug (Entity[] entities) {

        shapeRenderer.setProjectionMatrix(screen.mainCamera.camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.GREEN);

        for (int i = 0; i < entities.length; i++) {
            if (entities[i] != null) {
                shapeRenderer.rect(
                    entities[i].getCollisionRect().x,
                    entities[i].getCollisionRect().y,
                    entities[i].getCollisionRect().width,
                    entities[i].getCollisionRect().height
                );
            }

        }
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
