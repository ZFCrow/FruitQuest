package com.mygdx.game.GameEngine.Managers;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector4;
import com.mygdx.game.GameMaster;

import java.util.List;

public abstract class EngineMapManager extends BaseManager {
    public EngineMapManager(GameMaster game) {
        super(game);
    }
    public abstract Vector2 getSpawnPointCoordinates(String spawnPointName);
    public abstract List<Vector2> getAISpawnPointCoordinates();
    public abstract List<Vector4> getGroundCoordinates();
    public abstract List<Vector4> getDeathPointCoordinates();
    public abstract void render (OrthographicCamera camera);
    public abstract void dispose();
    public abstract void setMapPath(String mapPath);

}
