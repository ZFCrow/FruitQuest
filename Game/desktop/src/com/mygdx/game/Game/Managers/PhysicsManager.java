package com.mygdx.game.Game.Managers;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector4;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.GameEngine.Entities.Entity;
import com.mygdx.game.Game.Entities.Item;
import com.mygdx.game.Game.Factory.BodyFactory;
import com.mygdx.game.GameEngine.Managers.EnginePhysicsManager;
import com.mygdx.game.GameMaster;
//import jdk.incubator.vector.VectorOperators;

import java.util.List;


public class PhysicsManager extends EnginePhysicsManager {
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private static final float PPM = 32.0f; // pixels per meter

    //a list of bodies to remove
    private Array<Body> bodiesToRemove = new Array<Body>();

    //need to store a list of entities, then send it to the entityManager to create here so i can create after the world step
    private int noOfEntitiesToCreate;

    private int scoreToUnlock;

    //private BodyFactory bodyFactory;

    public PhysicsManager(GameMaster game) {
        super(game);
        init();
    }


    @Override
    public void init() {
        world = new World(new Vector2(0, -9.8f), true);
        debugRenderer = new Box2DDebugRenderer();
        world.setContactListener(getGame().getManager(CollisionManager.class));
        //bodyFactory = new BodyFactory(getGame(), world);

    }

    @Override
    public FixtureDef createFixtureDef(float width, float height, boolean isSensor) {
        PolygonShape shape = new PolygonShape();
        FixtureDef fixtureDef = new FixtureDef();
        System.out.println("Creating fixture for entity with width " + width + " and height " + height);
        shape.setAsBox(width  / PPM, height  / PPM); // Set the shape as a box
        fixtureDef.isSensor = isSensor;
        fixtureDef.shape = shape;

        return fixtureDef;
    }

    // Method to create a physics body for an entity
    public Body createBody(Entity entity, BodyDef.BodyType bodyType, float width, float height, boolean isSensor) {
        //Body body = bodyFactory.createBody(entity.getPosX(), entity.getPosY(), PPM, bodyType);
        Body body = getGame().getFactory(BodyFactory.class,world).createBody(entity.getPosX(), entity.getPosY(), PPM, bodyType);
        FixtureDef fixtureDef = createFixtureDef( width/4, height/4, isSensor);

        body.createFixture(fixtureDef);

        System.out.println("Fixture created for entity. DENSITY: " + body.getFixtureList().get(0).getDensity());
        fixtureDef.shape.dispose();

        entity.configureBody(body);

        System.out.println("Body created for entity" + entity.getID()+ " at position " + entity.getPosX() + ", " + entity.getPosY() + " with width " + width + " and height " + height + " and isSensor " + isSensor);
        return body;
    }



    //createGround,deathpoints, leftblock
    @Override
    public void createStaticPoints(float x,float y, float z, float w,String point, boolean isSensor){ //z is width, w is height

       //Body body = bodyFactory.createBody((x + z / 2), ((y + w )/ 2), PPM, BodyDef.BodyType.StaticBody);
        Body body = getGame().getFactory(BodyFactory.class,world).createBody((x + z / 2), ((y + w )/ 2), PPM, BodyDef.BodyType.StaticBody);
        float halfWidth = z / 2;
        float halfHeight = w / 2;
        if (point.equals("deathpoint")) { //my width should last the entire screen 
            halfWidth = getGame().getCamera().viewportWidth / 2;
        }

        FixtureDef fixtureDef = createFixtureDef(halfWidth, halfHeight, isSensor);

        body.createFixture(fixtureDef);
        body.setUserData(point);
        System.out.println( point + " created at position " + x + ", " + y + " with width " + z);

        fixtureDef.shape.dispose();

    }


    @Override
    public void createStaticPoints(List<Vector4> groundCoordinates, String point , boolean isSensor){

        for(Vector4 vector: groundCoordinates){
            System.out.println("From parent function Creating ground at position " + vector.w + vector.x + vector.z + vector.w);
            createStaticPoints(vector.x, vector.y, vector.z,vector.w, point, isSensor);
        }
    }



    //removeAllExistingBodies
    public void clearAllBodies(){
        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);
        for(Body body: bodies){

                System.out.println("Removing body with userData " + body.getUserData()); // Remove the ground from the previous level
                world.destroyBody(body);

        }
    }

    @Override
    public World getWorld() {
        return world;
    }

    @Override
    public void update(float delta) {
        world.step(delta, 6, 2); // Advance the physics simulation
        //remove the bodies from the array of bodies to remove
        removeBodies();

        getGame().getManager(EntityManager.class).createRandomEntities(Item.class, noOfEntitiesToCreate);
        noOfEntitiesToCreate = 0; //reset the number of entities to create

        for (Entity entity : getGame().getManager(EntityManager.class).getEntities()) {
            Body body = entity.getBody();
            Vector2 position = body.getPosition();
            entity.setPosX((position.x * PPM));
            entity.setPosY((position.y * PPM));
        }
    }
    public void dispose() {
        world.dispose();
        debugRenderer.dispose();
    }

    public Array<Body> getBodiesToRemove(){
        return bodiesToRemove;
    }

    public void removeBodies(){
        for(Body body: bodiesToRemove){
            world.destroyBody(body);
        }
        bodiesToRemove.clear();
    }
    @Override
    public void addBodyToRemove(Body body){
        bodiesToRemove.add(body);
    }
    @Override
    public void increaseEntitiesToCreate(){
        noOfEntitiesToCreate++; //increase the number of entities to create
    }
    public void decreaseEntitiesToCreate(){
        noOfEntitiesToCreate--; //decrease the number of entities to create
    }

    // Debug rendering
    public void renderDebug(Camera camera) {
        debugRenderer.render(world, camera.combined);
    }

    //get body for specific userdata
    @Override
    public Body getBody(String userData){
        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);
        for(Body body: bodies){
            if(body.getUserData().equals(userData)){
                return body;
            }
        }
        return null;
    }

    public void setScoreToUnlock(int scoreToUnlock){
        this.scoreToUnlock = scoreToUnlock;
    }

    public int getScoreToUnlock(){
        return scoreToUnlock;
    }

    public static float getPPM(){
        return PPM;
    }
}
