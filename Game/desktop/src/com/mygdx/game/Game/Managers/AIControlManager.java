package com.mygdx.game.Game.Managers;
import com.mygdx.game.Game.AIControl.AIController;
import com.mygdx.game.Game.Entities.AI;
import com.mygdx.game.GameEngine.Managers.EngineAIControlManager;
import com.mygdx.game.GameMaster;


public class AIControlManager extends EngineAIControlManager {
    private AI entityAI; //Declare AI entityAI
    private AIController AIController; //Declare AIController AIController
    public AIControlManager(GameMaster game){
        super(game);
        init();
    }
    //init
    @Override
    public void init() {
        this.AIController = new AIController(entityAI);
    }
    
    //method to get AIController
    public AIController getAIController(){
        return this.AIController;
    }
    

}

