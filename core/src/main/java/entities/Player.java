package entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import jokerhut.main.AnimationHandler;
import jokerhut.main.FarmScreen;
import jokerhut.main.KeyHandler;
import movementUtils.DirectionUtils;

public class Player extends Entity {

    public Sprite sprite;
    public TextureRegion idleDown, idleUp, idleLeft, idleRight;
    public Animation<TextureRegion> walkDown, walkUp, walkLeft, walkRight;
    public float animationTimer = 0f;
    public Vector2 lastDirection = new Vector2(0, -1);
    public Array<Rectangle> collisionRects;
    KeyHandler playerKeyHandler;
    AnimationHandler playerAnimationHandler;
    boolean isColliding;

    FarmScreen screen;

    private final Vector2 futurePosition = new Vector2();
    private final Rectangle playerRect = new Rectangle();

    private boolean moving;

    public float getAnimationTimer() {
        return animationTimer;
    }

    public void setAnimationTimer(float animationTimer) {
        this.animationTimer = animationTimer;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public Rectangle getCollisionRect() {
        return playerRect;
    }


    public Player (float x, float y, FarmScreen screen) {

        super(x, y);

        setupPlayerAnimation();
        this.screen = screen;
        playerKeyHandler = new KeyHandler(this);
        playerAnimationHandler = new AnimationHandler(this);
        //INITIALISE PLAYER SPRITE TO IDLE DOWN
        sprite = new Sprite(idleDown);
        sprite.setSize(2, 2); // Match map units

    }

    public void handleMovement (float delta) {

        animationTimer += delta;
        velocity.set(0, 0);
        moving = false;

        if (!playerKeyHandler.handleDiagonalMovement()) {
            playerKeyHandler.handlePlayerCardinalMovement();
        }


        if (!moving) {
            animationTimer = 0f;
            playerAnimationHandler.handleIdleAnimation();
        }

        futurePosition.set(position).add(velocity.x * delta, velocity.y * delta);
        playerRect.set(futurePosition.x + 0.5f, futurePosition.y + 0.2f, sprite.getWidth() - 2 * 0.5f, sprite.getHeight() - 6 * 0.2f);

        if (this.checkWallCollision(screen.treeCollisionRects)) {
            isColliding = true;
        }

        if (this.checkWallCollision(screen.wallCollisionRects)) {
            isColliding = true;
        }
        else {
            isColliding = false;
        }

        if (!isColliding) {
            position.set(futurePosition);
            sprite.setPosition(position.x, position.y);
        }

    }

    public void setupPlayerAnimation () {
        Texture sheet = new Texture("Basic Charakter Spritesheet.png");
        TextureRegion[][] split = TextureRegion.split(sheet, 16, 16);
        idleDown  = split[1][1];
        walkDown  = new Animation<>(0.2f, split[1][7], split[1][10]);
        idleUp    = split[4][1];
        walkUp    = new Animation<>(0.2f, split[4][7], split[4][10]);
        idleLeft  = split[7][1];
        walkLeft  = new Animation<>(0.2f, split[7][7], split[7][10]);
        idleRight = split[10][1];
        walkRight = new Animation<>(0.2f, split[10][7], split[10][10]);
    }

    public boolean checkWallCollision (Array<Rectangle> collisionRs) {

        boolean doesCollide = false;
        for (Rectangle rect : collisionRs) {
            if (rect.overlaps(playerRect)) {
                doesCollide = true;
                break;
            }
        }
        return doesCollide;

    }

    @Override
    public void update(float delta) {

        handleMovement(delta);


    }

    @Override
    public void render(SpriteBatch batch) {
        sprite.draw(batch);
    }
}
