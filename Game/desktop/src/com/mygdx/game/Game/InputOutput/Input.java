package com.mygdx.game.Game.InputOutput;

public class Input { //Superclass Input to allow for easier implementation of input devices in the future (i.e joystick)

    private int input; //Declare int input
    private boolean inputReceived; //Declare boolean inputReceived
    
    //Default constructor (no input)
    public Input(){
        this.input = -2; 
        this.inputReceived = false;
    }

    //Constructor if there is an input
    public Input(int input){
        this.input = input; //set to new input
        this.inputReceived = true; //set inputReceive to true
    }
    //Getter and setters
    public int getInput(){
        return this.input;
    }

    public void setInput(int new_input){
        this.input = new_input;
    }
    public boolean getInputReceived(){
        return this.inputReceived;
    }
    public void setInputReceived(boolean new_inputReceived){
        this.inputReceived = new_inputReceived;
    }
}
