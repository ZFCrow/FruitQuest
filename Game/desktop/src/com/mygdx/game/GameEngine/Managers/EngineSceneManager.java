package com.mygdx.game.GameEngine.Managers;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.mygdx.game.GameMaster;

public abstract class EngineSceneManager extends BaseManager{
    public EngineSceneManager(GameMaster game){
        super(game);
    }

    public abstract void setScreen(Screen screen);
    public abstract void removeScreen(Class<? extends Screen> screenClass);

    public abstract Screen createScreen(Class<? extends Screen> screenClass, Object... args);

}
