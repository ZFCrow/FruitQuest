package com.mygdx.game.GameEngine.Managers;

import com.mygdx.game.Game.Entities.Player;
import com.mygdx.game.GameMaster;

public abstract class EnginePlayerControlManager extends BaseManager{
    public EnginePlayerControlManager(GameMaster game){
        super(game);
    }
    public abstract void run(Player player, int key);
    public abstract void jump(Player player);
    public abstract Boolean shouldMediateJump(Player player);

}
