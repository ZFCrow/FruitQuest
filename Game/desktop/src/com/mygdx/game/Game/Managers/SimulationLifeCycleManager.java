package com.mygdx.game.Game.Managers;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector4;
import com.mygdx.game.Game.Entities.AI;
import com.mygdx.game.Game.Entities.Item;
import com.mygdx.game.Game.Entities.Player;
import com.mygdx.game.Game.Screens.BaseScreen;
import com.mygdx.game.Game.Screens.PlayScreen;
import com.mygdx.game.Game.Screens.WinLoseScreen;
import com.mygdx.game.GameEngine.Entities.Entity;
import com.mygdx.game.GameEngine.Managers.EngineEntityManager;
import com.mygdx.game.GameEngine.Managers.EngineSimulationLifeCycleManager;
import com.mygdx.game.GameMaster;
import com.mygdx.game.Game.LevelConfigs.LevelConfig;
import com.mygdx.game.Game.LevelConfigs.LevelLoader;
//import com.sun.java.swing.plaf.windows.resources.windows;

import java.util.List;


public class SimulationLifeCycleManager extends EngineSimulationLifeCycleManager {
    //private GameMaster game;
    private int currentLevel;
    private Vector2 spawnPoint;
    private List<Vector2> aiSpawnPoints;
    private List<Vector4> groundCoordinates;
    private List<Vector4> deathPoints;
    public SimulationLifeCycleManager(GameMaster game){
        super(game); 
        init(); 
    }
    
    @Override
    public void init() {
        this.currentLevel = 1;
    }
    

 
    
    //for my understanding it loops and remove all the entities, but what if only need remove one entity?
    @Override
    public void disposeEntities(List<Entity> entities){
        for(Entity entity: entities){
            entity.dispose();
            System.out.println("Entity has been disposed");
        }

        entities.clear(); //clear the entities list
        //check if the entities list is empty
        if(entities.isEmpty()){
            System.out.println("Entities list is empty");
        } else {
            System.out.println("Entities list is not empty");
        }
    }

    public void disposeEntities(List<Entity> entities, Entity entityToDispose) {
        if (entities.contains(entityToDispose)) {
            entityToDispose.dispose();
            entities.remove(entityToDispose);
            System.out.println("Entity has been disposed");
        }
    }

    // resetting entities instead of disposing them
    public void resetEntities(List<Entity> entities){
        entities.clear(); //clear the entities list
    }
    @Override
    public void setupLevel(){
        //instantiating objects based on the level
        if (this.currentLevel > 2){
            this.currentLevel = 1;
        }
        System.out.println("Setting up level " + this.currentLevel);
        resetEntities(super.getGame().getManager(EntityManager.class).getEntities()); //a mechanism to reset the entities
        super.getGame().getManager(PhysicsManager.class).clearAllBodies(); //clear all bodies

        //read the level config file
        // instantiate the mapmanager based off the config file mapPath
        // look for ground coordinates
        // look for death points
        // look for ai spawn points
        // look for player spawn point
        //set up those static

        LevelLoader levelLoader = new LevelLoader();
        LevelConfig levelConfig = levelLoader.loadLevel(this.currentLevel);
        //loads the map
        super.getGame().getManager(MapManager.class, levelConfig.getMapPath());

        //get the player spawn
        spawnPoint = super.getGame().getManager(MapManager.class).getSpawnPointCoordinates("spawnpoint");
        //get the ai spawn points
        aiSpawnPoints = super.getGame().getManager(MapManager.class).getAISpawnPointCoordinates();
        //get the ground coordinates
        groundCoordinates = super.getGame().getManager(MapManager.class).getGroundCoordinates();
        //get the death point coordinates
        deathPoints = super.getGame().getManager(MapManager.class).getDeathPointCoordinates();
        //create the player
        super.getGame().getManager(EntityManager.class).createEntity(Player.class, spawnPoint.x , spawnPoint.y + 300, 2);

        //todo to cater for more than 1 player
        //!super.getGame().getManager(EntityManager.class).createEntity(Player.class, (float)400,(float) 300, 2);
        
        //create the ai
        for (Vector2 aiSpawnPoint : aiSpawnPoints) {
            super.getGame().getManager(EntityManager.class).createEntity(AI.class, "ninjagirl", aiSpawnPoint.x, aiSpawnPoint.y, 1);
        }
        //create the ground
        super.getGame().getManager(PhysicsManager.class).createStaticPoints(groundCoordinates, "ground", false);
        //create the deathpoints
        super.getGame().getManager(PhysicsManager.class).createStaticPoints(deathPoints, "deathpoint", true);
        //?=================================================================================
        //Static var that i am creating from the config, items, random items, walls, score to unlock
        //?=================================================================================
        //create the wall (look for the wall in the config file)
        for (LevelConfig.Wall wall : levelConfig.getWalls()) { //therer should only be 2 walls in the list leftblock and exit
            System.out.println("Wall found in the config file: " + wall.getType());
            System.out.println("Wall x: " + wall.getX());
            System.out.println("World width:" + super.getGame().getViewport().getWorldWidth());

            super.getGame().getManager(PhysicsManager.class).createStaticPoints(wall.getX(), wall.getY(), wall.getWidth(), wall.getHeight(), wall.getType(), false);
        }
        //item
        for (LevelConfig.Item item : levelConfig.getItems()) {
            super.getGame().getManager(EntityManager.class).createEntities(Item.class, item.getQuantity(), item.getType());
        }
        //random items
        super.getGame().getManager(EntityManager.class).createRandomEntities(Item.class, levelConfig.getRandomItems().getQuantity());

        //score to unlock
        super.getGame().getManager(PhysicsManager.class).setScoreToUnlock(levelConfig.getScoreToUnlock());



        // if you want to create more entities through code
        //super.getGame().getManager(EntityManager.class).createEntities(Item.class, 3,"burger");



        //System.out.println("creating a ENGINE BURGER");
       //super.getGame().getManager(EngineEntityManager.class).createEntity(Entity.class,"burger",300f,400f);

    }

    @Override
    public void incrementLevel(){
        this.currentLevel++;
    }
    @Override
    public int getLevel(){
        return this.currentLevel;
    }



    //Screen transition logic
    @Override
    public void transitionToScreen(Class<? extends BaseScreen> screenClass, Object... args) {
        //if its the winlosescreen, i will definitely have additional arguments to see if the player won or lost
        if (screenClass.equals(WinLoseScreen.class)) {
            if (args.length > 0) {
                boolean win = (boolean) args[0];
                super.getGame().getManager(SceneManager.class).setScreen(super.getGame().getManager(SceneManager.class).createScreen(screenClass, win));  //creating a winlose screen then set it
                resetEntities(super.getGame().getManager(EntityManager.class).getEntities());
                super.getGame().getManager(SceneManager.class).removeScreen(PlayScreen.class);
                super.getGame().getManager(SoundManager.class).getMusic().stop();
            }
            else {
                //something is wrong so i will auto pass lost
                super.getGame().getManager(SceneManager.class).setScreen(super.getGame().getManager(SceneManager.class).createScreen(screenClass, false));
                resetEntities(super.getGame().getManager(EntityManager.class).getEntities());
                super.getGame().getManager(SceneManager.class).removeScreen(PlayScreen.class);
                super.getGame().getManager(SoundManager.class).getMusic().stop();

            }
        }
    }

    //checks for players live, speed, jumpheight , if any is zero , it will transition to winlosescreen
    @Override
    public void checkPlayerStatus(){
        Player player = (Player) super.getGame().getManager(EntityManager.class).getEntity(Player.class);
        if (player.getNumberofLives() <= 0){
            transitionToScreen(WinLoseScreen.class, false);
        }
        else if (player.getSpeed() <= 0){
            transitionToScreen(WinLoseScreen.class, false);
        }
        else if (player.getHeight() <= 0){
            transitionToScreen(WinLoseScreen.class, false);
        }
    }


    

}
