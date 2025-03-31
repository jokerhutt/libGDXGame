package entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import jokerhut.main.FarmScreen;

public class Frog extends Animal{

    public Vector2 lastDirection;

    public Frog(float x, float y, FarmScreen screen) {
        super(x, y, screen);
        setupAnimation();
        sprite = new Sprite(idle);
        sprite.setSize(2, 2);
        sprite.setPosition(x, y);
        this.lastDirection = new Vector2(0, -1);
        this.speed = 4f;
        this.shrinkX = 0.5f;
        this.shrinkY = 0.2f;

    }

    @Override
    public void setupAnimation() {
        this.lastDirection = new Vector2(0, -1);

        Texture sheet = new Texture("FrogSprites.png");
        TextureRegion[][] split = TextureRegion.split(sheet, 16, 16);
        idle = split[0][0];
        sleeping = split[0][0];
        walk = new Animation<>(0.2f, split[0][0], split[0][1]);
    }

    @Override
    public void createCollisionRect() {

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
