package entities;

import jokerhut.main.MainScreen;

public class NPC_Gilbert extends NPC {

    public NPC_Gilbert (float x, float y, MainScreen screen) {
        super(x, y, screen);
        this.name = "gilbert";
        this.type = "merchant";
        this.idlePath = "gilbertIdle.png";
        this.walkingPath = "gilbertWalk.png";
        setupAnimation(idlePath, walkingPath);
        setupSprite(idleDown);
        this.lastDirectionY = -1f;
        this.movesOnItsOwn = false;
    }


}
