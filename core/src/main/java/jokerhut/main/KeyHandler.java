package jokerhut.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import entities.Entity;
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
