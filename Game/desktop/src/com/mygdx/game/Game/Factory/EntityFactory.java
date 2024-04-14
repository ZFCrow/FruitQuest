package com.mygdx.game.Game.Factory;

import com.mygdx.game.GameEngine.Entities.Entity;
import com.mygdx.game.GameEngine.Factory.BaseFactory;
import com.mygdx.game.GameMaster;

import java.lang.reflect.Constructor;

public class EntityFactory extends BaseFactory {
    //private GameMaster game;

    public EntityFactory(GameMaster game) {
        super(game);
        //this.game = game;
    }


    public Entity createEntity(Class<? extends Entity> entityClass, Object... args) {
        //create entity based on entityType
        try {
            Entity entity;
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
            return entity;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

}
