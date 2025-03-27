package entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract class Entity {

    protected Vector2 position;
    protected Vector2 velocity;
    protected float speed = 4f;

    public Entity (float x, float y) {

        this.position = new Vector2(x, y);
        this.velocity = new Vector2(0, 0);

    }

    public void setVelocityY (float velocityY) {
        this.velocity.y = velocityY;
    }

    public float getVelocityY () {
        return this.velocity.y;
    }

    public void setVelocityX (float velocityX) {
        this.velocity.x = velocityX;
    }

    public float getVelocityX () {
        return this.velocity.x;
    }

    public float getSpeed () {
        return this.speed;
    }

    public void setSpeed (float speed) {
        this.speed = speed;
    }

    public abstract void update (float delta);
    public abstract void render (SpriteBatch batch);

    public Vector2 getPosition () {
        return this.position;
    }

}
