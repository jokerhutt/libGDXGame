package entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import jokerhut.main.FarmScreen;
import objects.GameObject;

public abstract class Animal extends NPC{

    public TextureRegion idle;
    public TextureRegion sleeping;
    public Animation<TextureRegion> walk;

    protected int actionLockCounter = 0;
    float actionTimer = 0;
    float actionDuration = 0;
    boolean isMoving = false;


    public Animal(float x, float y, FarmScreen screen) {
        super(x, y, screen);
    }

    public abstract void setupAnimation ();
    public abstract void createCollisionRect ();
    public void setAction(float delta) {
        actionTimer += delta;

        if (actionTimer >= actionDuration) {
            actionTimer = 0;
            isMoving = !isMoving;

            if (isMoving) {
                chooseRandomDirection();
                actionDuration = MathUtils.random(1f, 4f);
            } else {
                if (velocity.x < 0) {
                    sprite.setFlip(true, false);
                } else if (velocity.x > 0) {
                    sprite.setFlip(false, false);
                }
                velocity.set(0, 0); // stop
                actionDuration = MathUtils.random(1f, 10f);
            }
        }

        if (isMoving) {
            if (!screen.collisionChecker.checkPlayerCollision(screen.player, this) &&
                !screen.collisionChecker.checkGameObjectCollision(screen.treeObjects, this) &&
                !screen.collisionChecker.checkStaticObjectCollision(screen.wallCollisionRects, this) &&
                !screen.collisionChecker.checkEntityCollision(screen.npcs, this))
            {
                position.add(velocity.x * speed * delta, velocity.y * speed * delta);
                this.collisionRect.set(this.position.x + this.shrinkX, this.position.y + this.shrinkY, this.sprite.getWidth() - 2 * this.shrinkX, this.sprite.getHeight() - 6 * this.shrinkY);
                sprite.setRegion(walk.getKeyFrame(actionTimer, true));
            }
        } else {
            sprite.setRegion(idle);
        }

        sprite.setPosition(position.x, position.y);
        if (velocity.x != 0) {
            lastDirectionX = velocity.x;
        }

        if (lastDirectionX < 0) {
            sprite.setFlip(true, false);
        } else {
            sprite.setFlip(false, false);
        }
    }

    public void chooseRandomDirection() {
        int i = MathUtils.random(0, 3);
        switch (i) {
            case 0:
                velocity.set(1, 0);
                break;
            case 1:
                velocity.set(-1, 0);
                break;
            case 2:
                velocity.set(0, 1);
                break;
            case 3:
                velocity.set(0, -1);
                break;
        }
    }


}
