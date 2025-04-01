package jokerhut.main;

import entities.Player;

public class AnimationHandler {
    Player player;

    public AnimationHandler (Player player) {

        this.player = player;

    }

    public void handleIdleAnimation () {

        if (player.lastDirection.y == 1) {
            player.sprite.setRegion(player.idleUp);
        }
        else if (player.lastDirection.x == -1) {
            player.sprite.setRegion(player.idleLeft);
        }
        else if (player.lastDirection.y == -1) {
            player.sprite.setRegion(player.idleDown);
        }
        else if (player.lastDirection.x == 1) {
            player.sprite.setRegion(player.idleRight);
        }

    }

}
