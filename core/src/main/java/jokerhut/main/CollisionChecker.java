package jokerhut.main;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import entities.Entity;
import entities.Player;
import objects.GameObject;

public class CollisionChecker {

    public boolean checkGameObjectCollision (Array<GameObject> gameObjects, Entity callingEntity) {

        boolean doesCollide = false;
        for (GameObject gameObject : gameObjects) {
            if (gameObject.collisionRect.overlaps(callingEntity.collisionRect)) {
                doesCollide = true;
                break;
            }
        }
        return doesCollide;

    }

    public boolean checkFarmTileCollision (Player player, FarmTile farmTile) {
        boolean doesCollide = false;
            if (farmTile != null) {
                if (player.farmingRange.overlaps(farmTile.bounds));
                doesCollide = true;
            }
        return doesCollide;
    }

    public boolean checkEntityCollision (Entity[] entities, Entity callingEntity) {
        boolean doesCollide = false;
        for (int i = 0; i < entities.length; i++) {
            if (entities[i] != null) {
                if (entities[i].collisionRect.overlaps(callingEntity.collisionRect)) {
                    doesCollide = true;
                    break;
                }
            }
        }
        return doesCollide;
    }


    public void checkEntityDialogueCollision (Entity[] entities, Entity callingEntity) {
        for (int i = 0; i < entities.length; i++) {
            if (entities[i] != null) {
                if (entities[i].collisionRect.overlaps(callingEntity.dialogueCollisionZone)) {
                    entities[i].isInteracting = true;
                    break;
                } else {
                    entities[i].isInteracting = false;
                }
            }
        }
    }



    public boolean checkStaticObjectCollision (Array<Rectangle> collisionRs, Entity callingEntity) {

        boolean doesCollide = false;
        for (Rectangle rect : collisionRs) {
            if (rect.overlaps(callingEntity.collisionRect)) {
                doesCollide = true;
                break;
            }
        }
        return doesCollide;

    }

    public boolean checkPlayerCollision (Entity player, Entity callingEntity) {

        boolean doesCollide = false;
        if (player.collisionRect.overlaps(callingEntity.collisionRect)) {
            doesCollide = true;
        }
        return doesCollide;

    }

}
