package objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class GameObject {

    private float x;
    private float y;
    private float width;
    private float height;
    public boolean isCollideable;
    protected String type;
    public Sprite sprite;
    public Rectangle collisionRect;
    public String name;

    public GameObject(float x, float y, float width, float height, boolean isCollideable) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.isCollideable = isCollideable;
        createCollisionRect();
    }

    public String getType () {
        return this.type;
    }

    public void setType (String type) {
        this.type = type;
    }

    public void createCollisionRect () {

        Rectangle cRect = new Rectangle();
        cRect.x = this.x;
        cRect.y = this.y;
        cRect.width = this.width;
        cRect.height = this.height;

        this.collisionRect = cRect;

    }

}
