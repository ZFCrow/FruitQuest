package com.mygdx.game.Game.Managers;

import com.mygdx.game.Game.Entities.AI;
import com.mygdx.game.Game.Entities.NonPlayable;
import com.mygdx.game.GameEngine.Entities.Entity;
import com.mygdx.game.Game.Factory.EntityFactory;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.mygdx.game.GameEngine.Managers.EngineEntityManager;
import com.mygdx.game.GameMaster;

import java.util.ArrayList;
import java.util.List;


public class EntityManager extends EngineEntityManager {

    // i can have a entity arraylist here, everytime i create a new entity i add it to the arraylist
    // then i can loop through the arraylist and update and render each entity
    //*private List<Entity> entities;

    // a list of String that contains all creatable entities
    private List<String> creatableItems;

    public EntityManager(GameMaster game ) {

        super(game);
        init();

    }

    @Override
    public void init() {
        //this.entities = new ArrayList<>();
        //list all the item files in the items folder in entities through AssetManager
        creatableItems = new ArrayList<>();
        FileHandle dirHandle = Gdx.files.internal("assets/Entities/Items");

        if (!dirHandle.isDirectory()) {
            System.err.println("Items directory not found");
            //if this is the case, i will set up 3 items by default
            creatableItems.add("burger");
            creatableItems.add("carrot");
            creatableItems.add("coke");

        }else{
            for (FileHandle entry : dirHandle.list()) {
                creatableItems.add(entry.nameWithoutExtension());
            }
            System.out.println("Creatable entities: " + creatableItems);
        }

    }

    @Override
    public Entity createEntity(Class<? extends Entity> entityClass, Object... args) {
        Entity entity = getGame().getFactory(EntityFactory.class).createEntity(entityClass, args);
        getGame().getManager(PhysicsManager.class).createBody(entity, BodyDef.BodyType.DynamicBody, entity.getWidth(), entity.getHeight(), false);
        addEntity(entity);
        return entity;
    }

    public void createRandomEntities(Class<? extends Entity> entityClass,int numEntities){
        for (int i = 0; i < numEntities; i++) {
            int random = (int) (Math.random() * creatableItems.size());
            String randomEntity = creatableItems.get(random);
            createEntity(entityClass, randomEntity);
        }
    }

    public List<Entity> createEntities(Class<? extends Entity> entityClass, int numEntities, Object... args) {
        List<Entity> createdEntities = new ArrayList<>();
        for (int i = 0; i < numEntities; i++) {
            Entity entity = createEntity(entityClass, args);
            createdEntities.add(entity);
        }
        return createdEntities;
    }

    @Override //override the removeEntity method to remove the body from the physics world
    public void removeEntity(Entity entity) {

        Body body = entity.getBody();
        if (body != null) {
            getGame().getManager(PhysicsManager.class).addBodyToRemove(body);
        }
        super.removeEntity(entity);
    }


    //A method to get the entity from the list, if it doesnt exist, create and add it to the list
    //this always returns the first entity of the type, if there are multiple entities of the same type,
    // it will not return the correct one
    //* Useful if i want to get the instance of a class without knowing its ID

    public Entity getEntity(Class<? extends Entity> entityClass, Object... args) {
        //loop through my current entities, if the entity exists, return it
        for (Entity entity : super.getEntities()) {
            if (entity.getClass().equals(entityClass)) {
                if (entity instanceof AI && args.length > 0){
                    AI enemy = (AI) entity; //cast the entity to an AI so i can get the enemy type
                    if (enemy.getSubType().equals(args[0])){
                        return entity;
                    }

                }else{
                    //this is not the AI, so i know its definitely the entity i want since theres only 1 of each entity
                    //either that or its an AI with no declared args, so i return it anyways
                    return entity;
                }
            }
        }
        //if the entity does not exist, create it and add it to the list
          return createEntity(entityClass, args);
    }


    //i need 2 other methods to getEntity, one through subtype, another through class, i create an array of entiity for those that fulfill the condition and return it

    public List<Entity> getEntities(Class<? extends Entity> entityClass){
        List<Entity> entityList = new ArrayList<>();
        for (Entity entity : super.getEntities()) {
            if (entity.getClass().equals(entityClass)){
                entityList.add(entity);
            }
        }
        return entityList;
    }

    public List<Entity> getEntities(String subType){
        List<Entity> entityList = new ArrayList<>();
        for (Entity entity : super.getEntities()) {
            if (entity instanceof NonPlayable) {
                NonPlayable nonPlayableEntity = (NonPlayable) entity;
                if (nonPlayableEntity.getSubType().equals(subType)){
                    entityList.add(entity);
                }
            }
        }
        return entityList;
    }



}
