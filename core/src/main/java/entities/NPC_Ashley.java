package entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import jokerhut.main.DialogueHandler;
import jokerhut.main.FarmScreen;

public class NPC_Ashley extends NPC{

    protected int actionLockCounter = 0;
    float actionTimer = 0;
    float actionDuration = 0;
    boolean isMoving = false;
    float lastDirectionY = 0;

    public TextureRegion idleDown, idleUp, idleLeft, idleRight;
    public Animation<TextureRegion> walkDown, walkUp, walkLeft, walkRight;

    public NPC_Ashley (float x, float y, FarmScreen screen) {
        super(x, y, screen);
        this.dialogueHandler = new DialogueHandler(this, screen);
        this.shrinkX = 1.4f;
        this.shrinkY = 0.8f;
        setupAnimation();
        sprite = new Sprite(idleDown);
        sprite.setSize(4, 4);
        sprite.setPosition(x, y);
        this.speed = 1f;
        this.portrait = new Texture("Portrait.png");
        this.name = "Ashley";
    }

    public void drawSpeechBubble (SpriteBatch batch) {
        if (isInteracting) {
            float bubbleWidth = 2f;
            float bubbleHeight = 2f;

            float bubbleX = sprite.getX() + sprite.getWidth() / 2.2f - bubbleWidth / 2f;
            float bubbleY = sprite.getY() + (sprite.getHeight() / 1.3f);

            batch.draw(this.speechBubble, bubbleX, bubbleY, bubbleWidth, bubbleHeight);
        }
    }

    public void setupAnimation () {
        Texture sheet = new Texture("npconeidle.png");
        Texture animationSheet = new Texture("npconewalking.png");
        TextureRegion[][] splitAnimation = TextureRegion.split(animationSheet, 32, 32);
        TextureRegion[][] split = TextureRegion.split(sheet, 32, 32);
        idleDown  = split[0][0];
        walkDown  = new Animation<>(0.2f, splitAnimation[0][0], splitAnimation[0][1], splitAnimation[0][2], splitAnimation[0][3], splitAnimation[0][4], splitAnimation[0][5]);
        idleUp    = split[1][0];
        walkUp    = new Animation<>(0.2f, splitAnimation[1][0], splitAnimation[1][1], splitAnimation[1][2], splitAnimation[1][3], splitAnimation[1][4], splitAnimation[1][5]);
        idleRight = split[2][0];
        walkRight = new Animation<>(0.2f, splitAnimation[2][0], splitAnimation[2][1], splitAnimation[2][2], splitAnimation[2][3], splitAnimation[2][4], splitAnimation[2][5]);
        idleLeft  = split[2][0];
        walkLeft  = new Animation<>(0.2f, splitAnimation[2][0], splitAnimation[2][1], splitAnimation[2][2], splitAnimation[2][3], splitAnimation[2][4], splitAnimation[2][5]);

    }

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
                !screen.collisionChecker.checkEntityCollision(screen.animals, this))
            {

                if (velocity.y > 0) {
                    this.sprite.setRegion(walkUp.getKeyFrame(actionTimer, true));
                } else if (velocity.y < 0) {
                    this.sprite.setRegion(walkDown.getKeyFrame(actionTimer, true));
                } else if (velocity.x > 0) {
                    this.sprite.setRegion(walkRight.getKeyFrame(actionTimer, true));
                } else if (velocity.x < 0) {
                    this.sprite.setRegion(walkLeft.getKeyFrame(actionTimer, true));
                }

                position.add(velocity.x * speed * delta, velocity.y * speed * delta);
                this.collisionRect.set(this.position.x + this.shrinkX, this.position.y + this.shrinkY + 0.5f, this.sprite.getWidth() - 2 * this.shrinkX, this.sprite.getHeight() -4 * this.shrinkY);

            }
        } else {
            if (lastDirectionX > 0) {
                sprite.setRegion(idleUp);
            } else if (lastDirectionX < 0) {
                sprite.setRegion(idleDown);
            } else if (lastDirectionY > 0) {
                sprite.setRegion(idleRight);
            } else if (lastDirectionY < 0) {
                sprite.setRegion(idleLeft);
            }
        }

        sprite.setPosition(position.x, position.y);
        if (velocity.x != 0) {
            lastDirectionX = velocity.x;
        }
        if (velocity.y != 0) {
            lastDirectionY = velocity.y;
        }

        if (lastDirectionX < 0) {
            sprite.setFlip(true, false);
        } else {
            sprite.setFlip(false, false);
        }
    }

    public void update(float delta) {
        setAction(delta);
    }

    public void render(SpriteBatch batch) {
        drawSpeechBubble(batch);
        sprite.draw(batch);
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
