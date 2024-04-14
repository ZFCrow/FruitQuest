package com.mygdx.game.Game.CollisionHandlers;

import com.mygdx.game.GameMaster;

public class DefaultCollisionHandler implements CollisionHandler {
    private GameMaster game;
    public DefaultCollisionHandler(GameMaster game) {
        this.game = game;

    }

    @Override
    public void handleCollision(Object objectA, Object objectB) {
        System.out.println("Default Collision detected between " + objectA + " and " + objectB);
    }
}
