package com.mygdx.game.Game.Factory;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.GameEngine.Factory.BaseFactory;
import com.mygdx.game.GameMaster;

public class BodyFactory extends BaseFactory {
    private World world;
   // private GameMaster game;


    public BodyFactory(GameMaster game, World world){
       // this.game = game;
        super(game);
        this.world = world;
    }


    public Body createBody(float posX, float posY, float PPM, BodyDef.BodyType bodyType){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = bodyType;
        bodyDef.position.set(posX/PPM, posY/PPM);
        return world.createBody(bodyDef);
    }
}
