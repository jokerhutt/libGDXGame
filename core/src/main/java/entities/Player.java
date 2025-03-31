package entities;

import com.badlogic.gdx.Game;
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
import constants.Constants;
import jokerhut.main.AnimationHandler;
import jokerhut.main.CollisionChecker;
import jokerhut.main.FarmScreen;
import jokerhut.main.KeyHandler;
import movementUtils.DirectionUtils;
import objects.GameObject;
import objects.OBJ_Axe;
import objects.OBJ_Hoe;

public class Player extends Entity {

    public TextureRegion idleDown, idleUp, idleLeft, idleRight;
    public Animation<TextureRegion> walkDown, walkUp, walkLeft, walkRight;
    public Animation<TextureRegion> flowerLeft, flowerRight, flowerUp, flowerDown;
    public Animation<TextureRegion> actionDown, actionUp, actionLeft, actionRight;



    float shrinkX = 0.5f;
    float shrinkY = 0.2f;
    public float animationTimer = 0f;
    public int actionDurationCounter = 0;


    public Array<Rectangle> collisionRects;
    public KeyHandler playerKeyHandler;
    AnimationHandler playerAnimationHandler;
    boolean isColliding;

    private int selectedItem;
    private int selectedItemIndex;
    public int cursorCounter = 0;

    public GameObject equippedItem;

    float hitboxHeight = 0.5f; // 1 unit tall
    float hitboxWidth = 1.0f;  // or whatever feels right


    FarmScreen screen;

    public Vector2 lastDirection = new Vector2(0, -1);
    private final Vector2 futurePosition = new Vector2();

    public boolean moving;
    public boolean performingAction;

    float rangeSize;
    float rangeX;
    float rangeY;

    public Rectangle farmingRange;

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

        this.shrinkX = 2f;
        this.shrinkY = 1f;
        this.dialogueCollisionZone = new Rectangle();
        setupPlayerAnimation();
        this.performingAction = false;
        this.screen = screen;
        playerKeyHandler = new KeyHandler(this, screen);
        playerAnimationHandler = new AnimationHandler(this);
        //INITIALISE PLAYER SPRITE TO IDLE DOWN
        sprite = new Sprite(idleDown);
        sprite.setSize(5, 5); // Match map units
        setupInventory();
        this.selectedItem = 0;
        this.selectedItemIndex = 0;
        equipSelectedItem();
        setActionAnimation();
        rangeSize = 4;
        rangeX = getCenterX() - 4;
        rangeY = getCenterY() - 4;
        this.farmingRange = new Rectangle(rangeX, rangeY, rangeSize, rangeSize);

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


    public void equipSelectedItem () {

        for (int i = 0; i < this.inventory.length; i++) {

            if (inventory[i] != null && inventory[i].name != null && i == selectedItemIndex) {
                this.equippedItem = inventory[i];
                return;
            } else {
                this.equippedItem = null;
            }

        }

    }

    public void setActionAnimation () {
        if (this.equippedItem != null && this.equippedItem.name != null) {

            switch (this.equippedItem.name) {
                case "floweringPot" :
                    this.actionUp = this.flowerUp;
                    this.actionDown = this.flowerDown;
                    this.actionLeft = this.flowerLeft;
                    this.actionRight = this.flowerRight;
                    break;
            }

        }
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
            collisionRect.set(
                futurePosition.x + (sprite.getWidth() - hitboxWidth) / 2f,
                futurePosition.y + 1.8f, // align at feet
                hitboxWidth,
                hitboxHeight
            );

            //If the predicted position doesnt overlap on x, move the player in the x axis (regardless of y axis)
            if (!screen.collisionChecker.checkGameObjectCollision(screen.treeObjects, this)
                && !screen.collisionChecker.checkEntityCollision(screen.animals, this)
                && !screen.collisionChecker.checkStaticObjectCollision(screen.wallCollisionRects, this)
                && !screen.collisionChecker.checkEntityCollision(screen.npcs, this))
            {
                position.x = futurePosition.x;
            }

            // --- Then move Y ---
            futurePosition.set(position.x, position.y + velocity.y * delta);
            collisionRect.set(
                futurePosition.x + (sprite.getWidth() - hitboxWidth) / 2f,
                futurePosition.y  + 1.8f, // align at feet
                hitboxWidth,
                hitboxHeight
            );

            //If the predicted position doesnt overlap on y, move the player in the x axis (regardless of x axis)
            if (!screen.collisionChecker.checkGameObjectCollision(screen.treeObjects, this)
                && !screen.collisionChecker.checkEntityCollision(screen.animals, this)
                && !screen.collisionChecker.checkStaticObjectCollision(screen.wallCollisionRects, this)
                && !screen.collisionChecker.checkEntityCollision(screen.npcs, this))
            {
                position.y = futurePosition.y;
            }

            // --- Set final sprite position ---
            sprite.setPosition(position.x, position.y);
            updateDialogueCollisionZone();
            screen.collisionChecker.checkEntityDialogueCollision(screen.npcs, this);
            playerKeyHandler.enterDialogue();

    }

    public void updateDialogueCollisionZone () {
        float offset = 1.2f;
        float boxSize = 0.8f;

        float dx = 0;
        float dy = 0;

        if (lastDirection.x > 0) dx = offset;
        if (lastDirection.x < 0) dx = -offset;
        if (lastDirection.y > 0) dy = offset;
        if (lastDirection.y < 0) dy = -offset;

        dialogueCollisionZone.set(
            getCenterX() + dx - boxSize / 2f,
            getCenterY() + dy - boxSize / 2f,
            boxSize,
            boxSize
        );
    }

    public void setupPlayerAnimation () {
        Texture sheet = new Texture("Basic Charakter Spritesheet.png");
        TextureRegion[][] split = TextureRegion.split(sheet, 48, 48);
        idleDown  = split[0][0];
        walkDown  = new Animation<>(0.2f, split[0][2], split[0][3]);
        idleUp    = split[1][0];
        walkUp    = new Animation<>(0.2f, split[1][2], split[1][3]);
        idleLeft  = split[2][0];
        walkLeft  = new Animation<>(0.2f, split[2][2], split[2][3]);
        idleRight = split[3][0];
        walkRight = new Animation<>(0.2f, split[3][2], split[3][3]);

        Texture actionsSheet = new Texture("Basic Charakter Actions.png");
        TextureRegion[][] splitActionsSheet = TextureRegion.split(actionsSheet, 48, 48);

        flowerDown = new Animation<>(0.2f, splitActionsSheet [8][0], splitActionsSheet[8][1]);
        flowerUp = new Animation<>(0.2f, splitActionsSheet [9][0], splitActionsSheet[9][1]);
        flowerLeft = new Animation<>(0.2f, splitActionsSheet [10][0], splitActionsSheet[10][1]);
        flowerRight = new Animation<>(0.2f, splitActionsSheet [11][0], splitActionsSheet[11][1]);

    }

    @Override
    public void update(float delta) {


        playerKeyHandler.changeSelectedInventoryItem();
        equipSelectedItem();
        setActionAnimation();
        if (!playerKeyHandler.performAction(delta) && !this.performingAction) {
            handleMovement(delta);
        }
        updateFarmingRange();

    }

    public void updateFarmingRange () {
        rangeSize = 4;
        rangeX = getCenterX() - 2;
        rangeY = getCenterY() - 2;
        farmingRange.set(rangeX, rangeY, rangeSize, rangeSize);
    }

    @Override
    public void render(SpriteBatch batch) {
        sprite.draw(batch);
    }
}
