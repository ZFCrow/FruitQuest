package com.mygdx.game.Game.Factory;

import com.badlogic.gdx.Screen;
import com.mygdx.game.GameEngine.Factory.BaseFactory;
import com.mygdx.game.GameMaster;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ScreenFactory extends BaseFactory {
    //GameMaster game;
    public ScreenFactory(GameMaster game) {
        super(game);
        //this.game = game;
    }
    public Screen createScreen(Class<? extends Screen> screenClass, Object... args) {
        try {
            Class<?>[] argTypes = new Class[args.length + 1];
            argTypes[0] = GameMaster.class;
            for (int i = 0; i < args.length; i++) {
                if (args[i] instanceof Boolean) {
                    argTypes[i + 1] = boolean.class;
                } else {
                    argTypes[i + 1] = args[i].getClass();
                }
            }
            Constructor<? extends Screen> constructor = screenClass.getConstructor(argTypes);
            Object[] constructorArgs = new Object[args.length + 1];
            constructorArgs[0] = super.getGame();
            System.arraycopy(args, 0, constructorArgs, 1, args.length);
            Screen screen = constructor.newInstance(constructorArgs);
            System.out.println("Screen created in ScreenFactory");
            return screen;
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException |
                 InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }
}
