package com.mygdx.game.Game.LevelConfigs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;

public class LevelLoader {
    public LevelConfig loadLevel(int level) {
        Json json = new Json();
        LevelConfig config = json.fromJson(LevelConfig.class, Gdx.files.internal("Levels/Level" + level + ".json"));
        return config;
    }
}
