package com.mygdx.game.Game.Factory;
import com.mygdx.game.Game.CollisionHandlers.*;
import com.mygdx.game.Game.Entities.AI;
import com.mygdx.game.Game.Entities.Item;
import com.mygdx.game.Game.Entities.Player;
import com.mygdx.game.GameEngine.Factory.BaseFactory;
import com.mygdx.game.GameMaster;

import java.util.HashMap;
import java.util.Map;

public class CollisionHandlerFactory extends BaseFactory {
   // private GameMaster game;

    //list of existing collision handlers
    private Map<String, CollisionHandler> handlerMap;

    public CollisionHandlerFactory(GameMaster game) {
        super(game);
        //this.game = game;
        this.handlerMap = new HashMap<>();
    }

    public CollisionHandler getHandler(Object objectA, Object objectB) {
        // Generate a key based on the classes of objectA and objectB
        String key = generateKey(objectA.getClass(), objectB.getClass());

        // Check if a handler for this key already exists in the cache
        if (handlerMap.containsKey(key)) {
            return handlerMap.get(key);
        }

        // Instantiate a new handler based on the collision types and cache it
        CollisionHandler handler = createHandler(objectA, objectB);
        if (handler != null) {
            System.out.println("Handler created for " + key);
            handlerMap.put(key, handler);
        }

        return handler;
    }

    private String generateKey(Class<?> class1, Class<?> class2) {
        // Ensure consistent ordering for the key
        if (class1.getName().compareTo(class2.getName()) > 0) {
            return class1.getName() + ":" + class2.getName();
        } else {
            return class2.getName() + ":" + class1.getName();
        }
    }

    private CollisionHandler createHandler(Object objectA, Object objectB) {
        // Logic to instantiate the appropriate handler
        if (objectA instanceof Player && objectB instanceof AI || objectA instanceof AI && objectB instanceof Player) {
            System.out.println("Creating handler for Player-AI collision");
            return new PlayerAICollisionHandler(super.getGame());
        }
        else if (objectA instanceof Player && objectB instanceof Item || objectA instanceof Item && objectB instanceof Player) {
            System.out.println("Creating handler for Player-Item collision");
            return new PlayerItemCollisonHandler(super.getGame());
        }
        else if (objectA instanceof Player && objectB instanceof String || objectA instanceof String && objectB instanceof Player) {
            System.out.println("Creating handler for Player-Points collision");
            return new PlayerPointsCollisionHandler(super.getGame());
        }
        else{
            return new DefaultCollisionHandler(super.getGame());
        }
    }




}
