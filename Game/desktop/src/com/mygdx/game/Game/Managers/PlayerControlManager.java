package com.mygdx.game.Game.Managers;
import com.badlogic.gdx.Input;
import com.mygdx.game.Game.Entities.Player;
import com.mygdx.game.GameEngine.Managers.EnginePlayerControlManager;
import com.mygdx.game.GameMaster;

public class PlayerControlManager extends EnginePlayerControlManager {
    // Initialize variables
    private long lastJumpTime = 0;
    private final long jumpCooldown = 200; // 1 second cooldown for jump
    private int jumpCount; // Number of jumps allowed

    // Constructor
    public PlayerControlManager(GameMaster game){
        super(game);
        init();
    }

    // Init
    public void init() {
        this.jumpCount = 2;
    }


    @Override
    // Run Function
    public void run(Player player,int key) {
        try{
            player.run(key);
        } catch (NullPointerException e){
            System.out.println("NullExceptionPointer error. Player entity is not present");
        } catch (Exception e){
            System.out.println("Unexpected exception in run method" + e.getMessage());
        }
    }

    @Override
    // Jump function
    public void jump(Player player){
        long currentTime = System.currentTimeMillis();
        if (jumpCount> 0 && (currentTime - lastJumpTime) >= jumpCooldown){
            //check if mediate jump is needed
            Boolean mediateJump = shouldMediateJump(player);
            player.jump(mediateJump);
            lastJumpTime = currentTime;
            decreaseJumpCount();
        }
    }

    @Override
    // shouldMediateJump function
    public Boolean shouldMediateJump(Player player){
        if (player.getBody() != null){
            float playerPosY = player.getPosY() * getGame().getManager(PhysicsManager.class).getPPM();
            float screenTopinWorldUnits = (getGame().getCamera().position.y + getGame().getCamera().viewportHeight/2 * getGame().getManager(PhysicsManager.class).getPPM());
            if(playerPosY + player.getJumpHeight() > screenTopinWorldUnits){
                //the jump will cause the player to leave the screen
                return true;
            }
        }
        return false;
    }

    // resetJumpCount Function
    public void resetJumpCount(){
        this.jumpCount = 2;
    }

    // decreaseJumpCount Function
    public void decreaseJumpCount(){
        if(this.jumpCount>0){
            this.jumpCount--;
        }
    }

    // increaseJumpCount function
    public void increaseJumpCount(){
        this.jumpCount++;
        if (this.jumpCount > 3){
            this.jumpCount = 3;
        }
    }

    // getJumpCount Function
    public int getJumpCount(){
        return this.jumpCount;
    }
}