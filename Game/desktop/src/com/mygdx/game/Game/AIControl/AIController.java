package com.mygdx.game.Game.AIControl;

import com.mygdx.game.Game.Entities.AI;

public class AIController {
    private AI entityAI; //Declare AI entityAI
    public AIController(){ //default constructor
        this.entityAI = null;
    }
    public AIController(AI entityAI){ //constructor with input
        this.entityAI = entityAI;
    }
    public AI getEntityAI(){ //getter to get current entityAI
        return this.getEntityAI(); 
    }

    public Movement getMovement(){ //method to return movement (empty)
        return new Movement(this.entityAI);
    }
}
