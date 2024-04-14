package com.mygdx.game.Game.Entities;

import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.GameEngine.Entities.Entity;

public class AI extends Entity implements NonPlayable{

    private int awarenessRange;
    private String enemyType;

    private int movementState;
    private double minPosX;
    private double maxPosX;
    public AI() {
        //coordinates are randomly generated within the game screen
        super("wizard2", (int)(Math.random() * 800), (int)(Math.random() * 600));
        this.awarenessRange = 100;
        this.enemyType = "wizard2"; //default enemy type
        this.minPosX = 0;
        this.maxPosX = 0;
    }

    public AI(String enemyType, int awarenessRange) {
        super(enemyType, (int)(Math.random() * 800), (int)(Math.random() * 600));
        this.awarenessRange = awarenessRange;
        this.enemyType = enemyType;

        System.out.println("Created Enemy type: " + enemyType);
        System.out.print("Awareness range: " + awarenessRange);
        System.out.println(" X: " + this.getPosX() + " Y: " + this.getPosY());
    }

    public AI(String enemyType, int awarenessRange, int speed) {
        super(enemyType, (int)(Math.random() * 800), (int)(Math.random() * 600), speed); //this will call the constructor with the speed parameter
        this.awarenessRange = awarenessRange;
        this.enemyType = enemyType;
    }

    public AI(String enemyType, float x, float y,int speed) {
        super(enemyType, x, y, speed); //this will call the constructor with the speed parameter
        this.awarenessRange = 100;
        this.enemyType = enemyType;
        this.minPosX = x/32-1.2;
        this.maxPosX = x/32+1.2;
    }

    public double getMinPosX(){
        return this.minPosX;
    }

    public double getMaxPosX(){
        return this.maxPosX;
    }

    public int getAwarenessRange() {
        return awarenessRange;
    }

    public void setAwarenessRange(int awarenessRange) {
        this.awarenessRange = awarenessRange;
    }

    @Override
    public String getSubType() {
        return enemyType;
    }

    @Override
    public void setSubType(String subType) {
        this.enemyType = subType;
    }


    public int getMovementState() {
        return movementState;
    }
    public void setMovementState(int movementState) {
        this.movementState = movementState;
    }


    @Override
    public void configureBody(Body body) {
        body.setGravityScale(0);
        body.getFixtureList().get(0).setDensity(10f);
        body.getFixtureList().get(0).setSensor(true);
        body.setUserData(this);
        body.setLinearDamping(1.5f);
        setBody(body);
    }
}
