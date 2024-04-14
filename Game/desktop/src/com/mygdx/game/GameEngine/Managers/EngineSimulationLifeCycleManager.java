package com.mygdx.game.GameEngine.Managers;

import com.mygdx.game.GameEngine.Entities.Entity;
import com.mygdx.game.GameMaster;
import com.mygdx.game.Game.Screens.BaseScreen;

import java.util.List;

public abstract class EngineSimulationLifeCycleManager extends BaseManager {
    public EngineSimulationLifeCycleManager(GameMaster game) {
        super(game);
    }

    public abstract void setupLevel();

    public abstract void incrementLevel();

    public abstract int getLevel();

    public abstract void transitionToScreen(Class<? extends BaseScreen> screenClass, Object... args);

    public abstract void checkPlayerStatus();

    public abstract void disposeEntities(List<Entity> entities);
}