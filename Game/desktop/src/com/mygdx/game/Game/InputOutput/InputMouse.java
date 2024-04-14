package com.mygdx.game.Game.InputOutput;

import com.badlogic.gdx.Gdx;
public class InputMouse extends Input {
    //default constructor (no input)
    public InputMouse(){
        super();
    }
    //constructor with input
    public InputMouse(int button){
        super(button);
    }

    //ifMousePressed() function will return true if specific mouse buttons are pressed, current way of getting which button was pressed since inputProcessor is not implemented
    public boolean ifButtonPressed(int button){
        if(button == getInput()) return Gdx.input.isButtonJustPressed(button);
        else return false;
    }

}
