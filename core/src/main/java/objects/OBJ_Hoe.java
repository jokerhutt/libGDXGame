package objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class OBJ_Hoe extends GameObject{

    private Texture image;

    public OBJ_Hoe (float x, float y, float width, float height, boolean isCollideable) {
        super(x, y, width, height, isCollideable);
        this.image = new Texture("floweringpot.png");
        setImage();
        this.type = "type_tool";
    }

    public void setImage () {
        this.sprite = new Sprite(image);
        sprite.setSize(1, 1);
    }




}
