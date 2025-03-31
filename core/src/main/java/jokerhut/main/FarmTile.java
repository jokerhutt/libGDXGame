package jokerhut.main;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;


public class FarmTile {


    public float x, y, width, height;
    public Rectangle bounds;

    public int stage = 0; //1 is tilled, 2 is watered, 3 is seeds, 4 is growing, 5 is harvestable
    public boolean watered = false;
    public Sprite sprite;
    public Texture wateredTexture;


    public FarmTile (float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.bounds = new Rectangle(x, y, width, height);
        wateredTexture = new Texture("seedDirt.png");
    }

    public void update () {
    }

    public void setStageLogic () {
        switch (stage) {
            case 0 :

                break;
            case 1 :
                this.sprite = new Sprite();
                this.sprite.setRegion(wateredTexture);
                sprite.setSize(2, 2);
                sprite.setPosition(bounds.x, bounds.y);
                break;
        }
    }

    public void render (SpriteBatch batch) {
        if (this.sprite != null) {
            sprite.draw(batch);
        }
    }

    public void increaseStage (String actionName) {

        if (this.stage == 0 && actionName == "floweringPot") {
            this.stage = 1;
        }

        setStageLogic();


    }




}
