package com.mygdx.game.GameEngine.Managers;

import com.mygdx.game.GameMaster;
import com.mygdx.game.Game.AIControl.AIController;

public abstract class EngineAIControlManager extends BaseManager{
    public EngineAIControlManager(GameMaster game){
        super(game);
    }
    public abstract AIController getAIController();

}
