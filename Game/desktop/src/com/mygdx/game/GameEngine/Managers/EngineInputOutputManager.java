package com.mygdx.game.GameEngine.Managers;

import com.mygdx.game.GameMaster;
import com.mygdx.game.Game.InputOutput.InputKeyboard;
import com.mygdx.game.Game.InputOutput.InputMouse;

public abstract class EngineInputOutputManager extends BaseManager{
    public EngineInputOutputManager(GameMaster game){
        super(game);
    }
    public abstract InputKeyboard getInputKeyboard(int key);
    public abstract InputMouse getInputMouse(int button);

}
