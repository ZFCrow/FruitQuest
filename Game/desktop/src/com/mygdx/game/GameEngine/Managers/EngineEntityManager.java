package com.mygdx.game.GameEngine.Managers;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.mygdx.game.Game.Factory.EntityFactory;
import com.mygdx.game.Game.Managers.PhysicsManager;
import com.mygdx.game.GameEngine.Entities.Entity;
import com.mygdx.game.GameMaster;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

public class EngineEntityManager extends BaseManager {
    private List<Entity> entities;

    public EngineEntityManager(GameMaster game) {
        super(game);
        System.out.println("EngineEntityManager constructor");
        this.entities = new ArrayList<>();
        System.out.println("Entity list init in engineEntityManager");
    }

    public Entity createEntity(Class<? extends Entity> entityClass, Object... args) {
        Entity entity;
        //create entity based on entityType
        try {

            if (args.length > 0){
                //get the classes of the arguments, so it can figure out which constructor to use
                Class<?>[] argClasses = new Class<?>[args.length];
                for (int i = 0; i < args.length; i++){
                    // System.out.println("args[i]: " + args[i]);
                    argClasses[i] = args[i].getClass();

                    if (args[i] instanceof Integer){
                        argClasses[i] = int.class;
                    }
                    else if (args[i] instanceof Float){
                        argClasses[i] = float.class;
                    }

                }
                Constructor<? extends Entity> constructor = entityClass.getConstructor(argClasses);
                entity = constructor.newInstance(args);

            }else{
                //uses the default constructor
                entity = entityClass.newInstance();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        addEntity(entity);
        return entity;
    }

    public void init() {
        System.out.println("EngineEntityManager init");
    }

    public List<Entity> getEntities() {
        return entities;
    }
    public void addEntity(Entity entity) {
        entities.add(entity);
    }
    public void removeEntity(Entity entity) {
        entities.remove(entity);
    }


}
