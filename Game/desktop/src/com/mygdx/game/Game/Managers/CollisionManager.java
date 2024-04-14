package com.mygdx.game.Game.Managers;

import com.mygdx.game.Game.Factory.CollisionHandlerFactory;
import com.mygdx.game.Game.CollisionHandlers.CollisionHandler;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.GameEngine.Managers.EngineCollisionManager;
import com.mygdx.game.GameMaster;

public class CollisionManager extends EngineCollisionManager implements ContactListener {


    public CollisionManager(GameMaster game)
    {
        super(game);
        init();
    }

    @Override
    public void init() {
    }


    @Override
    public void beginContact(Contact contact) {
        Object objectA = contact.getFixtureA().getBody().getUserData();
        Object objectB = contact.getFixtureB().getBody().getUserData();

        CollisionHandler handler = getGame().getFactory(CollisionHandlerFactory.class).getHandler(objectA, objectB);
        handler.handleCollision(objectA, objectB);

    }


    @Override
    public void endContact(Contact contact) {
        // Handle the end of a collision event
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

        }



    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        // Handle collision logic after the physics calculation
    }





}
