package objects;

import com.badlogic.gdx.math.Rectangle;

public class GameObject {

    private float x;
    private float y;
    private float width;
    private float height;
    public Rectangle collisionRect;

    public GameObject(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        createCollisionRect();
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
