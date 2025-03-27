package jokerhut.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import entities.Entity;
import entities.Player;
import movementUtils.DirectionUtils;

public class KeyHandler {

    Player player;

    public KeyHandler (Player player) {
        this.player = player;
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
            player.setVelocityY(DirectionUtils.calculateScaledMagnitude(0, 1, player.getSpeed()));
            player.setVelocityX(DirectionUtils.calculateScaledMagnitude(-1, 0, player.getSpeed() * -1));
            player.sprite.setRegion(player.walkLeft.getKeyFrame(player.animationTimer, true));
            player.setMoving(true);
            player.lastDirection.set(-1, 0);
            return true;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.setVelocityY(DirectionUtils.calculateScaledMagnitude(0, 1, player.getSpeed()));
            player.setVelocityX(DirectionUtils.calculateScaledMagnitude(1, 0, player.getSpeed()));
            player.sprite.setRegion(player.walkRight.getKeyFrame(player.animationTimer, true));
            player.setMoving(true);
            player.lastDirection.set(1, 0);
            return true;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.A)) {
            player.setVelocityY(DirectionUtils.calculateScaledMagnitude(0, -1, player.getSpeed() * -1));
            player.setVelocityX(DirectionUtils.calculateScaledMagnitude(-1, 0, player.getSpeed() * -1));
            player.sprite.setRegion(player.walkLeft.getKeyFrame(player.animationTimer, true));
            player.setMoving(true);
            player.lastDirection.set(-1, 0);
            return true;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.setVelocityY(DirectionUtils.calculateScaledMagnitude(0, -1, player.getSpeed() * -1));
            player.setVelocityX(DirectionUtils.calculateScaledMagnitude(1, 0, player.getSpeed()));
            player.sprite.setRegion(player.walkRight.getKeyFrame(player.animationTimer, true));
            player.setMoving(true);
            player.lastDirection.set(-1, 0);
            return true;
        }

        return false;

    }

}
