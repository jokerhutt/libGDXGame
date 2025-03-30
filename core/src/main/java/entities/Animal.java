package entities;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import jokerhut.main.FarmScreen;
import objects.GameObject;

public abstract class Animal extends Entity{

    float lastDirectionX = 1;
    FarmScreen screen;


    public Animal(float x, float y, FarmScreen screen) {
        super(x, y);
        this.screen = screen;
    }

    public abstract void setupAnimation ();
    public abstract void createCollisionRect ();
    public abstract void setAction (float delta);
    public abstract void chooseRandomDirection ();

    public boolean checkStaticObjectCollision (Array<Rectangle> collisionRs) {

        boolean doesCollide = false;
        for (Rectangle rect : collisionRs) {
            if (rect.overlaps(collisionRect)) {
                doesCollide = true;
                System.out.println("collision");
                break;
            }
        }
        return doesCollide;

    }


    public boolean checkGameObjectCollision (Array<GameObject> gameObjects) {

        boolean doesCollide = false;
        for (GameObject gameObject : gameObjects) {
            if (gameObject.collisionRect.overlaps(collisionRect)) {
                doesCollide = true;
                System.out.println("collision");
                break;
            }
        }
        return doesCollide;

    }

    public boolean checkPlayerCollision () {

        boolean doesCollide = false;
            if (screen.player.collisionRect.overlaps(this.collisionRect)) {
                doesCollide = true;
                System.out.println("collision");
            }
        return doesCollide;

    }


}
