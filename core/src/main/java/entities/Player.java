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
import objects.GameObject;
import objects.OBJ_Axe;
import objects.OBJ_Hoe;

public class Player extends Entity {

    public Sprite sprite;
    public TextureRegion idleDown, idleUp, idleLeft, idleRight;
    public Animation<TextureRegion> walkDown, walkUp, walkLeft, walkRight;
    public Animation<TextureRegion> flowerLeft, flowerRight;

    float shrinkX = 0.5f;
    float shrinkY = 0.2f;
    public float animationTimer = 0f;


    public Array<Rectangle> collisionRects;
    KeyHandler playerKeyHandler;
    AnimationHandler playerAnimationHandler;
    boolean isColliding;

    private int selectedItem;
    private int selectedItemIndex;
    public int cursorCounter = 0;

    FarmScreen screen;

    public Vector2 lastDirection = new Vector2(0, -1);
    private final Vector2 futurePosition = new Vector2();

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

    public GameObject[] inventory = new GameObject[8];

    public Player (float x, float y, FarmScreen screen) {

        super(x, y);

        setupPlayerAnimation();
        this.screen = screen;
        playerKeyHandler = new KeyHandler(this, screen);
        playerAnimationHandler = new AnimationHandler(this);
        //INITIALISE PLAYER SPRITE TO IDLE DOWN
        sprite = new Sprite(idleDown);
        sprite.setSize(2, 2); // Match map units
        setupInventory();

        this.selectedItem = 0;
        this.selectedItemIndex = 0;

    }

    public void setSelectedItemIndex (int selectedItemIndex) {
        this.selectedItemIndex = selectedItemIndex;
    }

    public int getSelectedItemIndex () {
        return this.selectedItemIndex;
    }

    public void setSelectedItem (int selectedItem) {
        this.selectedItem = selectedItem;
    }

    public int getSelectedItem () {
        return this.selectedItem;
    }



    public float getCenterX() {
        return this.position.x + this.sprite.getWidth() / 2f;
    }
    public float getCenterY () {
        return this.position.y + this.sprite.getHeight() / 2f;
    }

    public void setupInventory () {

        this.inventory[0] = new OBJ_Hoe(0, 0, 0, 0, false);
        this.inventory[1] = new OBJ_Axe(0, 0, 0, 0, false);

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

        handleCollisionAndUpdatePlayer(delta);

    }

    public void handleCollisionAndUpdatePlayer (float delta) {

        //First move X
        //Move the Hypothetical player in the X direction
        futurePosition.set(position.x + velocity.x * delta, position.y);
        //Move the players collision box in the X direction
        this.collisionRect.set(futurePosition.x + shrinkX, futurePosition.y + shrinkY,
            sprite.getWidth() - 2 * shrinkX, sprite.getHeight() - 6 * shrinkY);

        //If the predicted position doesnt overlap on x, move the player in the x axis (regardless of y axis)
        if (!checkGameObjectCollision(screen.treeObjects) && !checkEntityCollision(screen.animals) && !checkStaticObjectCollision(screen.wallCollisionRects)) {
            position.x = futurePosition.x;
        }

        // --- Then move Y ---
        futurePosition.set(position.x, position.y + velocity.y * delta);
        this.collisionRect.set(futurePosition.x + shrinkX, futurePosition.y + shrinkY,
            sprite.getWidth() - 2 * shrinkX, sprite.getHeight() - 6 * shrinkY);

        //If the predicted position doesnt overlap on y, move the player in the x axis (regardless of x axis)
        if (!checkGameObjectCollision(screen.treeObjects) && !checkEntityCollision(screen.animals) && !checkStaticObjectCollision(screen.wallCollisionRects)) {
            position.y = futurePosition.y;
        }

        // --- Set final sprite position ---
        sprite.setPosition(position.x, position.y);
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

        Texture floweringSheet = new Texture("bunnyflowering.png");
        TextureRegion[][] splitFloweringSheet = TextureRegion.split(floweringSheet, 16, 16);
        flowerLeft = new Animation<>(0.2f, splitFloweringSheet [1][2], splitFloweringSheet[1][5]);
        flowerRight = new Animation<>(0.2f, splitFloweringSheet [1][0], splitFloweringSheet[1][1]);

    }

    public boolean checkStaticObjectCollision (Array<Rectangle> collisionRs) {

        boolean doesCollide = false;
        for (Rectangle rect : collisionRs) {
            if (rect.overlaps(this.collisionRect)) {
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
            if (gameObject.collisionRect.overlaps(this.collisionRect)) {
                doesCollide = true;
                System.out.println("collision");
                break;
            }
        }
        return doesCollide;

    }

    public boolean checkEntityCollision (Entity[] entities) {
        boolean doesCollide = false;
        for (int i = 0; i < entities.length; i++) {
            if (entities[i] != null) {
                if (entities[i].collisionRect.overlaps(this.collisionRect)) {
                    doesCollide = true;
                    System.out.println("collision");
                    break;
                }
            }
        }
        return doesCollide;
    }

    @Override
    public void update(float delta) {

        handleMovement(delta);
        playerKeyHandler.changeSelectedInventoryItem();


    }

    @Override
    public void render(SpriteBatch batch) {
        sprite.draw(batch);
    }
}
