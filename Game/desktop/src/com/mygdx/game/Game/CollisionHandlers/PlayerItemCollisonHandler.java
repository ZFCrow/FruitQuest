package com.mygdx.game.Game.CollisionHandlers;

import com.mygdx.game.Game.Entities.Item;
import com.mygdx.game.Game.Entities.Player;
import com.mygdx.game.GameMaster;
import com.mygdx.game.Game.Managers.EntityManager;
import com.mygdx.game.Game.Managers.PhysicsManager;
import com.mygdx.game.Game.Managers.PlayerControlManager;
import com.mygdx.game.Game.Managers.SoundManager;

public class PlayerItemCollisonHandler implements CollisionHandler {
    private GameMaster game;
    public PlayerItemCollisonHandler(GameMaster game) {
        this.game = game;
    }
    @Override
    public void handleCollision(Object objectA, Object objectB) {
        System.out.println("Collision detected between " + objectA + " and " + objectB);
        Item item = (Item) (objectA instanceof Item ? objectA : objectB);
        Player player = (Player) (objectA instanceof Player ? objectA : objectB);

        //play the sound
        game.getManager(SoundManager.class).getMusic("munch").play();
        //increase jumpCount
        System.out.println("jump Count before collision with item: " + game.getManager(PlayerControlManager.class).getJumpCount());
        game.getManager(PlayerControlManager.class).increaseJumpCount();
        System.out.println("Increasing jump count by 1 after collision with item");
        System.out.println("jump Count after collision with item: " + game.getManager(PlayerControlManager.class).getJumpCount());
        //removes the item

        game.getManager(EntityManager.class).removeEntity(item);

        //calls the item collision method;
        item.onCollision(player);

        //add a entity to create in physicsmanager
        game.getManager(PhysicsManager.class).increaseEntitiesToCreate();

    }
}
