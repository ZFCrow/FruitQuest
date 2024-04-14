package com.mygdx.game.Game.AIControl;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Game.Entities.AI;
import com.mygdx.game.GameEngine.Entities.Entity;

public class Movement extends AIController{
    
    //default constructor
    public Movement(){
        super();
    }
    //Constructor with AI
    public Movement(AI entityAI){
        super(entityAI);
    }
    //Method to move entityAI left
    public void movementLeft(AI entityAI){
        float forceX = 100f * entityAI.getSpeed(); //force changes according to entityAI's speed
        entityAI.getBody().applyForceToCenter(new Vector2(-forceX, 0), true); //push the entityAI to the left
        entityAI.getBody().setLinearVelocity(new Vector2(0,0)); //ensure entityAI velocity remain 0 so it does not exceed platform position
    }
    //Method to move entityAI right
    public void movementRight(AI entityAI){
        float forceX = 100f * entityAI.getSpeed(); //force changes according to entityAI's speed
        entityAI.getBody().applyForceToCenter(new Vector2(forceX, 0), true); //push the entityAI to the right
        entityAI.getBody().setLinearVelocity(new Vector2(0,0)); //ensure entityAI velocity remain 0 so it does not exceed platform position
    }
    //Method to randomize movement direction of entityAI
    public void movementBox2D(Entity AI){
        AI entityAI = (AI) AI;
        if (entityAI.getMovementState() == 0){ // if movementState is not stated
            entityAI.setMovementState((int) (Math.random() * 2) + 1); //set it between 1 or 2
        }
        if(entityAI.getMovementState() == 1){ //if movementState is 1
            movementRight(entityAI); //move the AI entity to the right
            if(entityAI.getBody().getPosition().x >= entityAI.getMaxPosX()){ //Once it reaches the maximum right position
                System.out.println("Change direction from right to left"); 
                entityAI.setMovementState(2); //Change movementState to 2
            }
        }else if (entityAI.getMovementState() == 2){ //if movementState is 2
            movementLeft(entityAI); //move the AI entity to the left
            if(entityAI.getBody().getPosition().x <= entityAI.getMinPosX()){ //Once it reaches the maximum left position
                System.out.println("Change direction from left to right");
                entityAI.setMovementState(1); //Change movementState to 1
            }
        }
    }
}
