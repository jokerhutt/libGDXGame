package entities;

import jokerhut.main.MainScreen;

public class NPC_OldMan extends NPC{

    public NPC_OldMan (float x, float y, MainScreen screen) {
        super(x, y, screen);
        this.name = "oldMan";
        this.idlePath = "oldManIdle.png";
        this.walkingPath = "oldManWalk.png";
        setupAnimation(idlePath, walkingPath);
        setupSprite(idleDown);
        this.lastDirectionY = -1f;
        this.movesOnItsOwn = true;
    }

}
