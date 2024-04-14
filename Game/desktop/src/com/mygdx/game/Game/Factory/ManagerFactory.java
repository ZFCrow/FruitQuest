package com.mygdx.game.Game.Factory;

import com.mygdx.game.GameEngine.Factory.BaseFactory;
import com.mygdx.game.GameEngine.Managers.BaseManager;
import com.mygdx.game.GameMaster;

import java.lang.reflect.Constructor;

public class ManagerFactory extends BaseFactory {
    //private GameMaster game;

    public ManagerFactory(GameMaster game) {
        super(game);
        //this.game = game;
    }

    public <T extends BaseManager>T createManager(Class<T> managerClass, Object... args) {
        try {
            T manager;
            Class<?>[] argClasses = new Class<?>[args.length + 1]; // +1 for GameMaster
            argClasses[0] = GameMaster.class; // First argument is always GameMaster
            for (int i = 0; i < args.length; i++) {
                argClasses[i + 1] = args[i].getClass();
            }
            Constructor<T> constructor = managerClass.getConstructor(argClasses);
            Object[] initargs = new Object[args.length + 1];
            initargs[0] = super.getGame(); // The GameMaster instance
            System.arraycopy(args, 0, initargs, 1, args.length); // Copy additional arguments
            manager = constructor.newInstance(initargs);
            return manager;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
