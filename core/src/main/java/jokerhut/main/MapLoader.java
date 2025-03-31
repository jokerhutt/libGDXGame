package jokerhut.main;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
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

    public Array<Rectangle> createStaticCollisionRects (String layerName, String layerName2) {

        Array<Rectangle> newCollisionRects = new Array<>();
        MapLayer collisionLayer = screen.map.getLayers().get(layerName);
        addRectsToArray(collisionLayer, newCollisionRects);

        collisionLayer = screen.map.getLayers().get(layerName2);
        addRectsToArray(collisionLayer, newCollisionRects);

        return newCollisionRects;

    }

    public void addRectsToArray (MapLayer collisionLayer, Array<Rectangle> newCollisionRects) {
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
    }

    public Array<FarmTile> addFarmTiles () {
        Array<FarmTile> farmTiles = new Array<>();
        TiledMapTileLayer farmLayer = (TiledMapTileLayer) screen.map.getLayers().get("Farmable");

        if (farmLayer != null) {
            int width = farmLayer.getWidth();
            int height = farmLayer.getHeight();
            int tileSize = farmLayer.getTileWidth(); // usually 16

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    TiledMapTileLayer.Cell cell = farmLayer.getCell(x, y);
                    if (cell != null && cell.getTile() != null) {
                        float worldX = x * tileSize * Constants.SCALE;
                        float worldY = y * tileSize * Constants.SCALE;
                        float size = tileSize * Constants.SCALE;

                        FarmTile tile = new FarmTile(worldX, worldY, size, size);
                        farmTiles.add(tile);
                    }
                }
            }
        }

        return farmTiles;
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
