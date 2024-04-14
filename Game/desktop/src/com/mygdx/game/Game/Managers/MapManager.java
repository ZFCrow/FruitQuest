package com.mygdx.game.Game.Managers;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.Vector4;
import com.mygdx.game.GameEngine.Managers.EngineMapManager;
import com.mygdx.game.GameMaster;

import java.util.ArrayList;
import java.util.List;

public class MapManager extends EngineMapManager {
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    private String mapPath;

    public MapManager(GameMaster game, String mapPath) {
        super(game);
        this.mapPath = mapPath;
        init();
    }


    @Override
    public void init() {
        map = new TmxMapLoader().load(mapPath);
        mapRenderer = new OrthogonalTiledMapRenderer(map);
    }

    @Override
    public Vector2 getSpawnPointCoordinates(String spawnPointName) {
        // Implementation remains the same as before
        MapLayer layer = map.getLayers().get("Object Layer 1");
        for (MapObject object : layer.getObjects()) {
            MapProperties properties = object.getProperties();
            if (properties.containsKey(spawnPointName)) {
                float x = (float) properties.get("x");
                float y = (float) properties.get("y");
                return new Vector2(x, y);
            }
        }
        return null;
    }

    //get spawnpoint for AIs, in a arraylist
    @Override
    public List<Vector2> getAISpawnPointCoordinates() {
        List<Vector2> aiSpawnPoints = new ArrayList<>();
        MapLayer layer = map.getLayers().get("Object Layer 1");
        for (MapObject object : layer.getObjects()) {
            MapProperties properties = object.getProperties();
            if (properties.containsKey("AISpawnpoint1")) {
                float x = (float) properties.get("x");
                float y = (float) properties.get("y");
                aiSpawnPoints.add(new Vector2(x, y));
            }
        }
        return aiSpawnPoints;
    }
    @Override
    public List<Vector4> getGroundCoordinates() {
        List<Vector4> groundCoordinates = new ArrayList<>();
        MapLayer layer = map.getLayers().get("Object Layer 1");
        for (MapObject object : layer.getObjects()) {
            MapProperties properties = object.getProperties();
            if (properties.containsKey("ground")) {
                float x = (float) properties.get("x");
                float y = (float) properties.get("y");
                float width = (float) properties.get("width"); // Get the width of the ground object
                float height = (float) properties.get("height"); // Get the height of the ground object
                groundCoordinates.add(new Vector4(x,y,width,height));
            }
        }
        return groundCoordinates;
    }

    //get deathpoint for player, which are gameOver points in the map
    @Override
    public List<Vector4> getDeathPointCoordinates() {
        List<Vector4> deathPoints = new ArrayList<>();
        MapLayer layer = map.getLayers().get("Object Layer 1");
        for (MapObject object : layer.getObjects()) {
            MapProperties properties = object.getProperties();
            if (properties.containsKey("gameOver")) {
                float x = (float) properties.get("x");
                float y = (float) properties.get("y");
                float width = (float) properties.get("width"); // Get the width of the deathpoint object
                float height = (float) properties.get("height"); // Get the height of the deathpoint object
                deathPoints.add(new Vector4(x, y,width,height ));
            }
        }
        return deathPoints;
    }

    @Override
    public void render(OrthographicCamera camera) {
        mapRenderer.setView(camera);
        mapRenderer.render();
    }
    @Override
    public void dispose() {
        if (map != null) {
            map.dispose();
        }
        if (mapRenderer != null) {
            mapRenderer.dispose();
        }
    }
    @Override
    public void setMapPath(String mapPath) {
        this.mapPath = mapPath;
        System.out.println("Map path set to " + mapPath); // Print the map path
    }

}
