package com.mygdx.game.Game.Entities;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GameEngine.Entities.Entity;

public class Player extends Entity {
    private int playerID;
    private int playerScore;
    private int numberofLives;

    private int jumpHeight;



    public Player() {
        super("player2",150f , 400f);

        playerScore = 0;
        numberofLives = 2;
        jumpHeight = 10;
    }

    public Player (float x , float y , int speed){
        super("player", x, y, speed);
        this.playerScore = 0;
        this.numberofLives = 3;
        this.jumpHeight = 10;
    }


    public int getPlayerScore() {
        return playerScore;
    }

    public void setPlayerScore(int playerScore) {
        this.playerScore = playerScore;
    }

    public int getNumberofLives() {
        return numberofLives;
    }

    public void setNumberofLives(int numberofLives) {
        this.numberofLives = numberofLives;
    }



    public int getJumpHeight(){
        return jumpHeight;
    }

    //actions
    public void jump(boolean mediateJump){
        if (this.getBody()!= null){
            if (mediateJump){
                this.getBody().applyLinearImpulse(new Vector2(0,0.5f), this.getBody().getWorldCenter(), true);
            }else{
                this.getBody().applyLinearImpulse(new Vector2(0,jumpHeight), this.getBody().getWorldCenter(), true);
            }
        }
    }
    public void run(int key) {
        //move the physics body
        try {
            if (key == Input.Keys.RIGHT) {
                this.getBody().setLinearVelocity(this.getSpeed(), this.getBody().getLinearVelocity().y);
            } else if (key == Input.Keys.LEFT) {
                this.getBody().setLinearVelocity(-(this.getSpeed()), this.getBody().getLinearVelocity().y);
            }
        } catch (NullPointerException e) {
            System.out.println("NullExceptionPointer error. Player entity is not present");
        } catch (Exception e) {
            System.out.println("Unexpected exception in run method" + e.getMessage());
        }
    }



    //stats manipulation functions

    public void increaseLives(){
        this.numberofLives++;
    }

    public void increaseLives(int lives){
        this.numberofLives += lives;
    }

    public void decreaseLives(){
        this.numberofLives--;
    }

    public void decreaseLives(int lives){
        this.numberofLives -= lives;
    }

    public void increaseJumpHeight(){
        this.jumpHeight += 2;

    }
    public void increaseJumpHeight(int jumpHeight){
        this.jumpHeight += 1;

    }
    public void decreaseJumpHeight(){
        this.jumpHeight -= 2;
    }
    public void decreaseJumpHeight(int jumpHeight){
        this.jumpHeight -= 1;
    }

    public void increaseScore(){
        this.playerScore++;
    }
    public void increaseScore(int score){
        this.playerScore += score;
    }

    public void decreaseScore(){
        if (this.playerScore > 0) {
            this.playerScore--;
        }
    }
    public void decreaseScore(int score){
        this.playerScore -= score;
        if (this.playerScore < 0){
            this.playerScore = 0;
        }
    }
    //================================================================================

    //check players stat to cap it at a certain value
    public void capStats(){
        if (this.getSpeed() > 5){
            this.setSpeed(5);
        }
        if (this.jumpHeight > 15){
            this.jumpHeight = 15;
        }

    }
}
