package com.mygdx.game.GameEngine.Factory;

import com.badlogic.gdx.Game;
import com.mygdx.game.GameMaster;

public class BaseFactory {
    private GameMaster game;

    public BaseFactory(GameMaster game){
        this.game = game;
    }

    public GameMaster getGame(){
        return this.game;
    }
}
