package com.mygdx.game.Game.InputOutput;
import com.badlogic.gdx.Gdx;

public class InputKeyboard extends Input {
    //default constructor (no input)
    public InputKeyboard(){
        super();
    }
    //constructor with input
    public InputKeyboard(int key){
        super(key);
    }

    //ifkeyPressed(int key) function will return true if specific keyboard buttons are pressed via the key parse, current way of getting which key was pressed since inputProcessor is not implemented
    public boolean ifKeyPressed(int key){
        if(key == getInput()) return Gdx.input.isKeyPressed(key);
        else return false;
    }
    
}