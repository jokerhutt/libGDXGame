package entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import jokerhut.main.FarmScreen;

public class Cow extends Animal{

    public Sprite sprite;
    public TextureRegion idle;
    public TextureRegion sleeping;
    public Animation<TextureRegion> walk;
    protected int actionLockCounter = 0;
    float actionTimer = 0;
    float actionDuration = 0;
    boolean isMoving = false;

    public Vector2 lastDirection;

    public Cow(float x, float y, FarmScreen screen) {
        super(x, y, screen);
        setupAnimation();
        sprite = new Sprite(idle);
        sprite.setSize(4, 4);
        sprite.setPosition(x, y);
        this.lastDirection = new Vector2(0, -1);
        this.speed = 1f;

    }

    @Override
    public void setupAnimation() {
        this.lastDirection = new Vector2(0, -1);

        Texture sheet = new Texture("Free Cow Sprites.png");
        TextureRegion[][] split = TextureRegion.split(sheet, 32, 32);
        idle = split[0][0];
        sleeping = split[0][1];
        walk = new Animation<>(0.2f, split[1][0], split[1][1]);
    }

    @Override
    public void createCollisionRect() {

    }

    @Override
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
            if (!checkPlayerCollision() && !checkGameObjectCollision(screen.treeObjects) && !checkStaticObjectCollision(screen.wallCollisionRects)) {
                position.add(velocity.x * speed * delta, velocity.y * speed * delta);
                this.collisionRect.set(this.position.x + 0.5f, this.position.y + 0.2f, this.sprite.getWidth() - 2 * 0.5f, this.sprite.getHeight() - 6 * 0.2f);
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

    @Override
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

    @Override
    public void update(float delta) {
        setAction(delta);
    }

    @Override
    public void render(SpriteBatch batch) {
        sprite.draw(batch);
    }
}
