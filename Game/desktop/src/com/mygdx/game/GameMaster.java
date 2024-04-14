package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Game.Factory.ManagerFactory;
import com.mygdx.game.Game.Managers.EntityManager;
import com.mygdx.game.Game.Managers.MapManager;
import com.mygdx.game.Game.Managers.SceneManager;
import com.mygdx.game.Game.Managers.SimulationLifeCycleManager;
import com.mygdx.game.GameEngine.Factory.BaseFactory;
import com.mygdx.game.GameEngine.Managers.*;
import com.mygdx.game.Game.Screens.MainScreen;
import com.mygdx.game.GameEngine.Utility.FactoryProducer;
import com.mygdx.game.GameEngine.Utility.TextureLoader;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

//so what i need to here is extends game instead of application adapter
//so i am allowed to use the screen class
//i will also define my managers here so i can use them in the screen class

public class GameMaster extends Game {
    private SpriteBatch batch;
    private BitmapFont font;
    //creates the instances of my managers

    private List<BaseManager> managers; // i will use this to store all my managers
    private List<BaseFactory> factories; // i will use this to store all my factories
    private OrthographicCamera camera;
    private Viewport viewport;

    @Override
    public void create() {
        //managerFactory = new ManagerFactory(this);
        //800,600
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 600);

        viewport = new FitViewport(800, 600, camera); //this is to create a new instance of the FitViewport object

        batch = new SpriteBatch();
        font = new BitmapFont(); //this is to create a new instance of the BitmapFont object
        managers = new ArrayList<>();
        factories = new ArrayList<>();
        getManager(SceneManager.class).setScreen(new MainScreen(this));
    }

    @Override
    public void render() {

        ScreenUtils.clear(0, 0, 0.2f, 1);
        super.render(); //this is to delegate the render method to the current screen by calling the render method of the current screen
    }

    @Override
    public void dispose() {
        System.out.println("GameMaster disposing of resources");
        font.dispose();
        batch.dispose();
        getManager(SimulationLifeCycleManager.class).disposeEntities(getManager(EntityManager.class).getEntities());
        TextureLoader.dispose();
    }

    //getFont method
    public BitmapFont getFont() {
        return font;
    }

    //i will create managers with this getter as
    // i will only have 1 instance of each manager at any point in time
    // thus a singleton pattern make sense where i
    // create a new instance of the manager if it doesnt exist
    // and return the existing instance if it does
   public <T extends BaseManager> T getManager(Class<T> managerClass, Object... args) {
       // System.out.println("Getting manager from GameMaster");
        for (BaseManager manager : managers) {
            if (manager.getClass().equals(managerClass)) {

                //*==========================================================================
                //?if i have additional arguments that is a String and the maanagerClass is instance of MAP
                //?then i will set the map path and init the map
                //?this is a special case for the MapManager
                //*==========================================================================
                if(args.length > 0 && args[0] instanceof String && MapManager.class.isAssignableFrom(managerClass)){
                    ((MapManager) manager).setMapPath(args[0].toString());
                    ((MapManager) manager).init();
                    return managerClass.cast(manager);
                }
                //*==========================================================================
                //*==========================================================================
                return managerClass.cast(manager);
            }
        }

        T manager = getFactory(ManagerFactory.class).createManager(managerClass, args);

        if (manager != null){
            addManager(manager);
            System.out.println("Manager created and added to the pool of managers in GameMaster");
            return manager;
        }
        System.out.println("Manager is null");
        return null;
    }

    //addManager method
    public void addManager(BaseManager manager) {
        managers.add(manager);
    }

    //same thing with factories as with managers

    public <T extends BaseFactory> T getFactory(Class<T> factoryClass, Object... args) {
        for (BaseFactory factory : factories) {
            if (factory.getClass().equals(factoryClass)) {
                return factoryClass.cast(factory);
            }
        }

        T factory = FactoryProducer.createFactory(factoryClass, this, args);
        if (factory != null){
            addFactory(factory);
            System.out.println("Factory created and added to the pool of factories in GameMaster");
            return factory;
        }
        return null;
    }
    public void addFactory(BaseFactory factory) {
        //convert it to a T type extending BaseFactory

        factories.add(factory);
    }

    //==========================================================================
    //getBatch method
    public SpriteBatch getBatch() {
        return batch;
    }

    //getCamera method
    public OrthographicCamera getCamera() {
        return camera;
    }

    public Viewport getViewport() {
        return viewport;
    }

    @Override
    public void resize(int width, int height) {
        //System.out.println("Resizing from gameMaster");
        super.resize(width, height);
    }


}
