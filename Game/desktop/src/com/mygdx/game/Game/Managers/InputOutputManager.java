package com.mygdx.game.Game.Managers;
import com.mygdx.game.GameEngine.Managers.EngineInputOutputManager;
import com.mygdx.game.GameMaster;
import com.mygdx.game.Game.InputOutput.InputKeyboard;
import com.mygdx.game.Game.InputOutput.InputMouse;

public class InputOutputManager extends EngineInputOutputManager {
    private InputKeyboard inputKeyboard; //Declare object InputKeyboard
    private InputMouse inputMouse; //Declare object InputMouse
    
    //Default constructor
    public InputOutputManager(GameMaster game){
        super(game);
        init();
    }

    @Override
    public void init() {
        this.inputKeyboard = new InputKeyboard(); //initialise object InputKeyBoard
        this.inputMouse = new InputMouse(); //initialise object InputMouse
    }


    //Return the initialized inputKeyboard to use their functions        
    public InputKeyboard getInputKeyboard(){
        return this.inputKeyboard;
    }//Return the initialized inputKeyboard to use their functions (with input key)
    public InputKeyboard getInputKeyboard(int key){
        return this.inputKeyboard = new InputKeyboard(key);
    }

    //Return the initialized inputMouse to use their functions    
    public InputMouse getInputMouse(){
        return this.inputMouse;
    }
    //Return the initialized inputMouse to use their functions (with input button)
    public InputMouse getInputMouse(int button){
        return this.inputMouse = new InputMouse(button);
    }
}
