package com.mygdx.game.GameEngine.Managers;

import com.badlogic.gdx.math.Vector4;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.GameEngine.Entities.Entity;
import com.mygdx.game.GameMaster;

import java.util.List;

public abstract class EnginePhysicsManager extends BaseManager{
    public EnginePhysicsManager(GameMaster game){
        super(game);
    }

    public abstract FixtureDef createFixtureDef(float width, float height, boolean isSensor);
    public abstract Body createBody(Entity entity, BodyDef.BodyType bodyType, float width, float height, boolean isSensor);

    public abstract void createStaticPoints(float x,float y, float z, float w,String point, boolean isSensor);

    public abstract void createStaticPoints(List<Vector4> groundCoordinates, String point , boolean isSensor);
    public abstract void clearAllBodies();
    public abstract void update(float delta);
    public abstract World getWorld();
    public abstract void removeBodies();
    public abstract void addBodyToRemove(Body body);
    public abstract void increaseEntitiesToCreate();
    public abstract Body getBody(String userData);
    public abstract void setScoreToUnlock(int scoreToUnlock);

    public abstract int getScoreToUnlock();

}
