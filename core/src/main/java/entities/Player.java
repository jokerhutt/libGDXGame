package entities;

import Constants.Constants;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import jokerhut.main.AnimationHandler;
import jokerhut.main.KeyHandler;
import jokerhut.main.MainScreen;

public class Player extends Entity{

    public TextureRegion idleDown, idleUp, idleLeft, idleRight;
    public Animation<TextureRegion> walkDown, walkUp, walkLeft, walkRight;
    AnimationHandler playerAnimationHandler;
    public float animationTimer = 0f;
    public boolean moving;

    public KeyHandler playerKeyHandler;
    MainScreen screen;

    public Player (float x, float y, MainScreen screen) {
        super(x, y);
        this.screen = screen;
        setupPlayerAnimation();
        playerAnimationHandler = new AnimationHandler(this);
        sprite = new Sprite(idleDown);
        sprite.setSize(Constants.TILESIZE, Constants.TILESIZE);
        sprite.setPosition(this.position.x, this.position.y);
        sprite.setRegion(idleDown);
        this.moving = false;
        this.playerKeyHandler = new KeyHandler(this, screen);

    }

    public float getCenterX() {
        return this.position.x + this.sprite.getWidth() / 2f;
    }
    public float getCenterY () {
        return this.position.y + this.sprite.getHeight() / 2f;
    }

    public void setMoving (boolean moving) {
        this.moving = moving;
    }

    public void setVelocity (Vector2 velocity) {
        this.velocity = velocity;
    }

    public void setVelocityY (float velocityY) {
        this.velocity.y = velocityY;
    }

    public void setVelocityX (float velocityX) {
        this.velocity.x = velocityX;
    }

    public float getSpeed () {
        return this.speed;
    }

    public void setupPlayerAnimation () {
        Texture sheet = new Texture("Idle.png");
        TextureRegion[][] split = TextureRegion.split(sheet, 16, 16);

        Texture walkingSheet = new Texture("Walk.png");
        TextureRegion[][] splitWalkingSheet = TextureRegion.split(walkingSheet, 16, 16);

        idleDown  = split[0][0];
        walkDown  = new Animation<>(0.2f, splitWalkingSheet[0][0], splitWalkingSheet[1][0], splitWalkingSheet[2][0], splitWalkingSheet[3][0]);
        idleUp    = split[0][1];
        walkUp    = new Animation<>(0.2f, splitWalkingSheet[0][1], splitWalkingSheet[1][1], splitWalkingSheet[2][1], splitWalkingSheet[3][1]);
        idleLeft  = split[0][2];
        walkLeft  = new Animation<>(0.2f, splitWalkingSheet[0][2], splitWalkingSheet[1][2], splitWalkingSheet[2][2], splitWalkingSheet[3][2]);
        idleRight = split[0][3];
        walkRight = new Animation<>(0.2f, splitWalkingSheet[0][3], splitWalkingSheet[1][3], splitWalkingSheet[2][3], splitWalkingSheet[3][3] );

    }

    public void update(float delta) {

    }

    public void render(SpriteBatch batch) {
        sprite.draw(batch);
    }


}
