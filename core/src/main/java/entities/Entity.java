package entities;

import Constants.Constants;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Entity {

    public Vector2 lastDirection;
    public Vector2 position;
    public Vector2 velocity;
    public float speed;
    public Sprite sprite;

    public Entity (float x, float y) {
        this.lastDirection = new Vector2(-1, 0);
        this.velocity = new Vector2(0, 0);
        this.position = new Vector2(x * Constants.TILESIZE, y * Constants.TILESIZE);
        this.speed = 2f * Constants.TILESIZE;
    }

    public Vector2 getPosition () {
        return this.position;
    }

}
