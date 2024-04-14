package com.mygdx.game.GameEngine.Utility;

import com.mygdx.game.GameEngine.Factory.BaseFactory;
import com.mygdx.game.GameMaster;

import java.lang.reflect.Constructor;

public class FactoryProducer {

    //create a factory by passing the type of factory
    public static <T extends BaseFactory> T createFactory(Class<T> factoryClass, GameMaster game, Object... args) {
        try {
            T factory;
//            factory = factoryClass.getConstructor(GameMaster.class).newInstance(game);
//            return factory;
            Class<?>[] argTypes = new Class[args.length + 1]; //creates a new array of classes with the length of the arguments + 1 for GameMaster
            argTypes[0] = GameMaster.class;

            for (int i = 0; i < args.length; i++) {
                argTypes[i + 1] = args[i].getClass();
                }
            Constructor<T> constructor = factoryClass.getConstructor(argTypes);
            Object[] constructorArgs = new Object[args.length + 1];
            constructorArgs[0] = game;
            System.arraycopy(args, 0, constructorArgs, 1, args.length);
            factory = constructor.newInstance(constructorArgs);
            return factory;


        } catch (Exception e) {
            e.printStackTrace();

    }
        return null;
    }
}
