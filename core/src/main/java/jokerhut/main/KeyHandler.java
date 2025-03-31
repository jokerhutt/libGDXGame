package jokerhut.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import entities.Entity;
import entities.NPC;
import entities.Player;
import hud.HUD;
import movementUtils.DirectionUtils;

public class KeyHandler {

    Player player;
    FarmScreen screen;

    public KeyHandler (Player player, FarmScreen screen) {
        this.player = player;
        this.screen = screen;
    }

    public void enterDialogue () {
        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            if (!screen.isInDialogue) {
                for (int i = 0; i < screen.npcs.length; i++) {
                    if (screen.npcs[i] != null && player.dialogueCollisionZone.overlaps(screen.npcs[i].collisionRect)) {
                        screen.isInDialogue = true;
                        screen.currentNPC = screen.npcs[i];
                        screen.currentNPC.dialogueHandler.startDialogue();
                        break;
                    }
                }
            }
        }
    }

    public void checkUpdateDialogue () {
        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            System.out.println("Advancing");
            if (!screen.currentNPC.dialogueHandler.advanceLine()) {
                screen.isInDialogue = false;
                screen.currentNPC.dialogueHandler.currentLine = -1;
                screen.currentNPC = null;
            }
        }
    }

    public boolean farmTileWithClick (Array<FarmTile> farmTiles) {

        boolean hasFarmed = false;
        if (Gdx.input.isTouched()) {
            int screenX = Gdx.input.getX();
            int screenY = Gdx.input.getY();
            screen.worldClick.set(screenX, screenY, 0);
            screen.mainCamera.camera.unproject(screen.worldClick);

            float dx = screen.worldClick.x - player.getCenterX();
            float dy = screen.worldClick.y - player.getCenterY();

            setDirectionBasedOnAction(dx, dy);

            player.performingAction = true;

            for (FarmTile farmTile : farmTiles) {
                if (
                    farmTile.bounds.contains(screen.worldClick.x, screen.worldClick.y) &&
                        player.farmingRange.overlaps(farmTile.bounds)
                ) {
                    System.out.println("Tilled tile at: " + farmTile.x + ", " + farmTile.y);
                    hasFarmed = true;

                    farmTile.increaseStage(player.equippedItem.name);

                    break;
                }
            }
        }
        return hasFarmed;
    }

    public void setDirectionBasedOnAction (float dx, float dy) {
        if (Math.abs(dy) > Math.abs(dx)) {
            if (dy > 0) {
                player.sprite.setRegion(player.actionUp.getKeyFrame(player.animationTimer, true));
                player.lastDirection.set(0, 1);
            } else {
                player.sprite.setRegion(player.actionDown.getKeyFrame(player.animationTimer, true));
                player.lastDirection.set(0, -1);
            }
        } else {
            if (dx > 0) {
                player.sprite.setRegion(player.actionRight.getKeyFrame(player.animationTimer, true));
                player.lastDirection.set(1, 0);
            } else {
                player.sprite.setRegion(player.actionLeft.getKeyFrame(player.animationTimer, true));
                player.lastDirection.set(-1, 0);
            }
        }
    }

    public boolean performAction (float delta) {

        if (player.performingAction) {

            player.actionDurationCounter++;
            player.animationTimer += delta;

            if (player.lastDirection.x > 0) {
                player.sprite.setRegion(player.actionRight.getKeyFrame(player.animationTimer, true));
            } else if (player.lastDirection.x < 0) {
                player.sprite.setRegion(player.actionLeft.getKeyFrame(player.animationTimer, true));
            } else if (player.lastDirection.y > 0) {
                player.sprite.setRegion(player.actionUp.getKeyFrame(player.animationTimer, true));
            } else if (player.lastDirection.y < 0) {
                player.sprite.setRegion(player.actionDown.getKeyFrame(player.animationTimer, true));
            }

        }

        if (player.performingAction && player.actionDurationCounter >= 60) {
            player.performingAction = false;
            player.animationTimer = 0;
            player.actionDurationCounter = 0;
        }
        if (!player.performingAction && player.equippedItem != null && player.equippedItem.name != null) {
            if (farmTileWithClick(screen.farmableTiles)) {
                player.animationTimer = 0f;
                player.moving = false;
                player.performingAction = true;
            }
            else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                player.animationTimer = 0f;
                player.moving = false;
                player.performingAction = true;
                player.sprite.setRegion(player.actionRight.getKeyFrame(player.animationTimer, true));
                player.lastDirection.set(1, 0);
                return true;
            } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                player.animationTimer = 0f;
                player.moving = false;
                player.performingAction = true;
                player.sprite.setRegion(player.actionLeft.getKeyFrame(player.animationTimer, true));
                player.lastDirection.set(-1, 0);
                return true;
            } else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                player.animationTimer = 0f;
                player.moving = false;
                player.performingAction = true;
                player.sprite.setRegion(player.actionUp.getKeyFrame(player.animationTimer, true));
                player.lastDirection.set(0, 1);
                return true;
            } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                player.animationTimer = 0f;
                player.moving = false;
                player.performingAction = true;
                player.sprite.setRegion(player.actionDown.getKeyFrame(player.animationTimer, true));
                player.lastDirection.set(0, -1);
                return true;
            }
            return  false;
        }
        return false;


    }

    public void changeSelectedInventoryItem () {


        int sItemIndex = player.getSelectedItemIndex();

        if (Gdx.input.isKeyJustPressed(Input.Keys.G)) {
            if (sItemIndex > 0) {
                player.setSelectedItemIndex(sItemIndex - 1);
                System.out.println(player.getSelectedItemIndex());
                screen.hud.refreshInventory();
            }
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.H)) {
            if (sItemIndex < 7) {
                player.setSelectedItemIndex(sItemIndex + 1);
                System.out.println(player.getSelectedItemIndex());
                screen.hud.refreshInventory();
            }
        }

    }

    public void handlePlayerCardinalMovement () {

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            player.setVelocityY(player.getSpeed());;
            player.sprite.setRegion(player.walkUp.getKeyFrame(player.animationTimer, true));
            player.setMoving(true);
            player.lastDirection.set(0, 1);
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            player.setVelocityX(player.getSpeed() * -1);;
            player.sprite.setRegion(player.walkLeft.getKeyFrame(player.animationTimer, true));
            player.setMoving(true);
            player.lastDirection.set(-1, 0);
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            player.setVelocityY(player.getSpeed() * -1);
            player.sprite.setRegion(player.walkDown.getKeyFrame(player.animationTimer, true));
            player.setMoving(true);
            player.lastDirection.set(0, -1);
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.setVelocityX(player.getSpeed());;
            player.sprite.setRegion(player.walkRight.getKeyFrame(player.animationTimer, true));
            player.setMoving(true);
            player.lastDirection.set(1, 0);
        }

    }

    public boolean handleDiagonalMovement () {

        if (Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.A)) {
            player.setVelocity(DirectionUtils.calculateDiagonalVector(-1, 1, player.getSpeed()));
            player.sprite.setRegion(player.walkLeft.getKeyFrame(player.animationTimer, true));
            player.setMoving(true);
            player.lastDirection.set(-1, 0);
            return true;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.setVelocity(DirectionUtils.calculateDiagonalVector(1, 1, player.getSpeed()));
            player.sprite.setRegion(player.walkRight.getKeyFrame(player.animationTimer, true));
            player.setMoving(true);
            player.lastDirection.set(1, 0);
            return true;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.A)) {
            player.setVelocity(DirectionUtils.calculateDiagonalVector(-1, -1, player.getSpeed()));
            player.sprite.setRegion(player.walkLeft.getKeyFrame(player.animationTimer, true));
            player.setMoving(true);
            player.lastDirection.set(-1, 0);
            return true;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.setVelocity(DirectionUtils.calculateDiagonalVector(1, -1, player.getSpeed()));
            player.sprite.setRegion(player.walkRight.getKeyFrame(player.animationTimer, true));
            player.setMoving(true);
            player.lastDirection.set(1, 0);
            return true;
        }

        return false;

    }

}
