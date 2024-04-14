package com.mygdx.game.GameEngine.Entities;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.GameEngine.Utility.TextureLoader;

import java.util.HashMap;
import java.util.Map;

//entity abstract class
public class Entity{
    private Texture entityImage;
    private float posX;
    private float posY;

    private Sprite entitySprite;
    private static int entityID = 0 ;
    private int id;

    private int speed;
    protected static final Map< String, Texture> textures = new HashMap<>();
    private Body body;
    private int width;
    private int height;


    public Entity(String imageName, float posX, float posY, int speed) {
        this.entityImage = loadTexture(imageName);
        this.posX = posX;
        this.posY = posY;
        this.speed = speed;
        this.width = 40;
        this.height = 40;
        this.entitySprite = new Sprite(entityImage);
        this.entitySprite.setPosition(posX, posY);
        this.entitySprite.setSize(this.width, this.height);

        this.id = entityID++;

    }

    public Entity(String imageName, float posX, float posY) {
        this.entityImage = loadTexture(imageName);
        this.posX = posX;
        this.posY = posY;
        this.speed = 1;
        this.width = 40;
        this.height = 40;
        this.entitySprite = new Sprite(entityImage);
        this.entitySprite.setPosition(posX, posY);
        this.entitySprite.setSize(this.width, this.height);

        this.id = entityID++;

    }

    //might be overriden by the subclasses
    public void draw(SpriteBatch batch) {
        this.entitySprite.setPosition(posX, posY); // this needs to happen to update the position of the sprite as when i am drawing using rhe sprite , the default set position is used
        this.entitySprite.draw(batch);
    }

    public Texture getEntityImage() {
        return entityImage;
    }

    public void setEntityImage(Texture entityImage) {
        this.entityImage = entityImage;
    }

    public float getPosX() {
        return posX;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

    public int getSpeed(){
        return this.speed;
    }

    public void setSpeed(int speed){
        this.speed = speed;
    }


    public void dispose() {
        entityImage.dispose();
    }

    public int getID(){
        return this.id;
    }

    public static Texture loadTexture(String textureName) {
        return TextureLoader.loadTexture(textureName);
    }

    public void configureBody(Body body){
        body.setUserData(this);
        body.setLinearDamping(1.5f);
        setBody(body);
    };

    public void setBody(Body body){
        this.body = body;
    }
    public Body getBody(){
        return this.body;
    }

    public int getWidth(){
        return this.width;
    }
    public int getHeight(){
        return this.height;
    }


    //stats manipulation functions

    public void decreaseSpeed(){
        this.speed--;

    }
    public void increaseSpeed(){
        this.speed++;
    }

    public void decreaseSpeed(int amount){
        this.speed -= amount;
    }
    public void increaseSpeed(int amount){
        this.speed += amount;
    }
    //==================================================
}


