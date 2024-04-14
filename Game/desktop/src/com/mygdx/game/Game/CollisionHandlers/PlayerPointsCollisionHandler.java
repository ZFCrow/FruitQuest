package com.mygdx.game.Game.CollisionHandlers;

import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.Game.Managers.SoundManager;
import com.mygdx.game.GameEngine.Entities.Entity;
import com.mygdx.game.Game.Entities.Player;
import com.mygdx.game.GameMaster;
import com.mygdx.game.Game.Managers.PhysicsManager;
import com.mygdx.game.Game.Managers.PlayerControlManager;
import com.mygdx.game.Game.Managers.SimulationLifeCycleManager;
import com.mygdx.game.Game.Screens.WinLoseScreen;

public class PlayerPointsCollisionHandler implements CollisionHandler{
    private GameMaster game;
    public PlayerPointsCollisionHandler(GameMaster game) {
        this.game = game;
    }
    @Override
    public void handleCollision(Object objectA, Object objectB) {
        System.out.println("Collision detected between " + objectA + " and " + objectB);
        String entityA = (String) (objectA instanceof String ? objectA : objectB);
        Entity entityB = (Entity) (objectA instanceof Entity ? objectA : objectB);

        if (entityA.equals("exit") && entityB instanceof Player && ((Player) entityB).getPlayerScore() >= game.getManager(PhysicsManager.class).getScoreToUnlock()) {
            Body body = game.getManager(PhysicsManager.class).getBody("exit");
            body.getFixtureList().get(0).setSensor(true);
            handlePlayerEscape();
        }

        else if (entityA.equals("exit") && entityB instanceof Player && ((Player) entityB).getPlayerScore() < game.getManager(PhysicsManager.class).getScoreToUnlock()){
            game.getManager(SoundManager.class).getMusic("wall").play();
        }

        else if (entityA.equals("deathpoint") && entityB instanceof Player) {
                // getGame().getManager(SimulationLifeCycleManager.class).transitionToScreen(WinLoseScreen.class, false);
                ((Player) entityB).setNumberofLives(0);
            }

        else if (entityA.equals("ground") && entityB instanceof Player) {
                //reset jumpcount to 2
                System.out.println("Player has landed");
                game.getManager(PlayerControlManager.class).resetJumpCount();
            }
        }


    public void handlePlayerEscape(){
        System.out.println("Player has escaped");
        game.getManager(SimulationLifeCycleManager.class).transitionToScreen(WinLoseScreen.class, true);
    }
}
