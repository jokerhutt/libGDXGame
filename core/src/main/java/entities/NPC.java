package entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import jokerhut.main.DialogueHandler;
import jokerhut.main.FarmScreen;

public class NPC extends Entity{

    FarmScreen screen;
    public float lastDirectionX = 1;
    public DialogueHandler dialogueHandler;
    public Texture portrait;
    public String name;

    public NPC (float x, float y, FarmScreen screen) {
        super(x, y);
        this.screen = screen;
    }



    @Override
    public void update(float delta) {

    }

    @Override
    public void render(SpriteBatch batch) {

    }
}
