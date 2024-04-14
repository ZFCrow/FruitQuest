package com.mygdx.game.GameEngine.Managers;

import com.badlogic.gdx.audio.Music;
import com.mygdx.game.GameMaster;

public abstract class EngineSoundManager extends BaseManager {
    public EngineSoundManager(GameMaster game) {
        super(game);
    }
    public abstract Music getMusic();
    public abstract Music getMusic(String key);
    public abstract void increaseVolume();
    public abstract void decreaseVolume();
    public abstract void muteVolume();
    public abstract void updateVolume();
    public abstract void setVolume(float volume);

    public abstract void toggleMute();
    public abstract void playSound(String key);

}
