package debug;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import jokerhut.main.FarmScreen;

public class CollisionDebug {

    public static boolean SHOWPLAYERCOLLISION = true;
    public static boolean SHOWTREECOLLISION = true;

    FarmScreen screen;
    ShapeRenderer shapeRenderer;

    public CollisionDebug (FarmScreen screen) {
        this.screen = screen;
        shapeRenderer = new ShapeRenderer();
    }


    public void playerCollisionDebug () {

            shapeRenderer.setProjectionMatrix(screen.camera.combined);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(Color.RED);

            shapeRenderer.rect(
                screen.player.getCollisionRect().x,
                screen.player.getCollisionRect().y,
                screen.player.getCollisionRect().width,
                screen.player.getCollisionRect().height
            );

            shapeRenderer.end();
        }

    public void treeCollisionDebug (Array<Rectangle> treeRects) {
//        System.out.println("Tree rect count: " + treeRects.size);

        shapeRenderer.setProjectionMatrix(screen.camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.BLUE);
        for (Rectangle treeRect: treeRects) {
//            System.out.println(treeRect.x);
//            System.out.println(treeRect.y);
//            System.out.println(treeRect.width);
//            System.out.println(treeRect.height);

            shapeRenderer.rect(
                treeRect.x,
                treeRect.y,
                treeRect.width,
                treeRect.height
            );

        }

        shapeRenderer.end();
    }




}
