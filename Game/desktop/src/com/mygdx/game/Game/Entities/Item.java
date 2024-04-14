package com.mygdx.game.Game.Entities;

import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.Game.Entities.itemEffects.itemEffectMapper;
import com.mygdx.game.GameEngine.Entities.Entity;

public class Item extends Entity implements NonPlayable {
    private String itemType;


    public Item() {
        super("burger", (int)(Math.random() * 800), 600);
        this.itemType = "burger";
    }

    public Item(String itemType) {

        super(itemType, (int)(Math.random() * 800), 600);
        this.itemType = itemType;
    }


    public Item(String itemType, float posX, float posY) {
        super(itemType, posX, posY);
        this.itemType = itemType;
    }

    @Override
    public String getSubType() {
        return itemType;
    }

    @Override
    public void setSubType(String subType) {
        this.itemType = subType;
    }

    @Override
    public void configureBody(Body body) {
        body.setGravityScale(0.5f);
        body.setUserData(this);
        body.setLinearDamping(1.5f);
        setBody(body);

    }

    public void onCollision(Player player){

        System.out.println("Item collided with player");
        //using the itemEffectMapper class to apply the effect of the item on the player  (dependency relationship ?)
        itemEffectMapper.applyEffect(this.itemType, player);

    }

}
