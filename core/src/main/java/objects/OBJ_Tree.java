package objects;

import com.badlogic.gdx.math.Rectangle;

public class OBJ_Tree extends GameObject{

    public OBJ_Tree(float x, float y, float width, float height, boolean isCollideable) {
        super(x, y, width, height, isCollideable);
        System.out.println("Created brah");
        System.out.println(this.collisionRect.width );
    }

}
