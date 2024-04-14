package com.mygdx.game.GameEngine.Managers;

import com.mygdx.game.GameMaster;

public abstract class BaseManager {
    private GameMaster game;


    public BaseManager(GameMaster game){
        this.game = game;
    }

    public GameMaster getGame(){
        return this.game;
    }

    public abstract void init();

}