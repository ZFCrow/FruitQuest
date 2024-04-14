package com.mygdx.game.Game.CollisionHandlers;

import com.mygdx.game.Game.Entities.AI;
import com.mygdx.game.Game.Entities.Player;
import com.mygdx.game.GameMaster;
import com.mygdx.game.Game.Managers.SoundManager;

public class PlayerAICollisionHandler implements CollisionHandler{
    private GameMaster game;
    public PlayerAICollisionHandler(GameMaster game) {
        this.game = game;
    }
    @Override
    public void handleCollision(Object objectA, Object objectB) {
        System.out.println("Collision detected between " + objectA + " and " + objectB);
        Player player = (Player) (objectA instanceof Player ? objectA : objectB);
        AI ai = (AI) (objectA instanceof AI ? objectA : objectB);
        game.getManager(SoundManager.class).getMusic("collide").play();
        player.decreaseLives();
    }
}
