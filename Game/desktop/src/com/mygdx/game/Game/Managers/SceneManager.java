package com.mygdx.game.Game.Managers;

import com.mygdx.game.Game.Factory.ScreenFactory;
import com.mygdx.game.Game.Screens.MainScreen;
import com.mygdx.game.GameEngine.Managers.EngineSceneManager;
import com.mygdx.game.GameMaster;
import com.badlogic.gdx.Screen;

import java.util.ArrayList;
import java.util.List;

//manages what screens to load,
// instances of screens should not be created instantly but instead be created when needed
// so i need to have a method that creates a new instance of a screen when needed
public class SceneManager extends EngineSceneManager {
    private List<Screen> screens;
    //private ScreenFactory screenFactory;

    public SceneManager(GameMaster game){
        super(game);
        init();
    }

    @Override
    public void init() {
        this.screens = new ArrayList<>();
       // this.screenFactory = new ScreenFactory(super.getGame());
    }


    public void setScreen(){
        //check if screenlist is empty
        if (screens.isEmpty()) {
            screens.add(createScreen(MainScreen.class));
        }
        super.getGame().setScreen(screens.get(screens.size() - 1)); //set the last screen in the list

    }
    @Override
    public void setScreen(Screen screen) {
        super.getGame().setScreen(screen);
    }

    public <T extends Screen> T getScreen(Class<T> type) {
        for (Screen screen : screens) {
            if (screen.getClass().equals(type)) {
                return (T) screen;
            }
        }
        try {
            T screen = type.cast(createScreen(type));
            return screen; // Return the newly created screen
        } catch (Exception e) {
            throw new RuntimeException("Failed to create screen: " + type.getName(), e);
        }
    }

    //removeScreen method
    @Override
    public void removeScreen(Class<? extends Screen> type) {
        for (Screen screen : screens) {
            if (screen.getClass().equals(type)) {
                screens.remove(screen);
                screen.dispose();
                System.out.println("THE SCREEN DISPOSED");
                return;
            }
        }
    }

    @Override
    public Screen createScreen(Class<? extends Screen> screenClass, Object... args) {
        //Screen screen = screenFactory.createScreen(screenClass, args);
        Screen screen = getGame().getFactory(ScreenFactory.class).createScreen(screenClass, args);
        screens.add(screen);
        return screen;
    }



}
