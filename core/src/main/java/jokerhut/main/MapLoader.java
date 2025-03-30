package jokerhut.main;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import constants.Constants;
import objects.GameObject;
import objects.OBJ_Tree;

public class MapLoader {

    FarmScreen screen;


    public MapLoader (FarmScreen screen) {
        this.screen = screen;
    }

    public Array<Rectangle> createStaticCollisionRects (String layerName) {

        Array<Rectangle> newCollisionRects = new Array<>();
        MapLayer collisionLayer = screen.map.getLayers().get(layerName);

        if (collisionLayer != null) {
            MapObjects objects = collisionLayer.getObjects();
            for (RectangleMapObject obj : objects.getByType(RectangleMapObject.class)) {

                Rectangle r = obj.getRectangle();

//                System.out.println(r.width);
//                System.out.println(r.height);
                //Copy original
                Rectangle scaled = new Rectangle(
                    r.x * Constants.SCALE,
                    r.y * Constants.SCALE,
                    r.width * Constants.SCALE,
                    r.height * Constants.SCALE
                );

                newCollisionRects.add(scaled);
            }
        }
        return newCollisionRects;

    }

    public Array<GameObject> loadObjectsFromLayer(String layerName) {
        Array<GameObject> objects = new Array<>();
        MapLayer layer = screen.map.getLayers().get(layerName);

        if (layer != null) {
            MapObjects mapObjects = layer.getObjects();
            for (RectangleMapObject obj : mapObjects.getByType(RectangleMapObject.class)) {

                Rectangle r = obj.getRectangle();

                float x = r.x * Constants.SCALE;
                float y = r.y * Constants.SCALE;
                float width = r.width * Constants.SCALE;
                float height = r.height * Constants.SCALE;
                GameObject newObject;



                switch (layerName) {
                    case "Tree":
                        newObject = new OBJ_Tree(x, y, width, height, true);
                        objects.add(newObject);
                        break;
                }
            }
        }
        return objects;
    }



}
