package com.mygdx.game.Game.Managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.mygdx.game.GameEngine.Managers.EngineSoundManager;
import com.mygdx.game.GameMaster;

import java.util.HashMap;

public class SoundManager extends EngineSoundManager {
    private Music music;
    private HashMap<String, Music> musicMap; // using a hashmap to map the sounds to a string

    private HashMap<String, Sound> soundMap;
    private float volume = 0.5f; // dafault volume will be at 50%
    private boolean isMuted = false; //check muted state
    private static final String directory = "audio/";
    public SoundManager(GameMaster game){
        super(game);
        init();
    }

    @Override
    public void init() {
        musicMap = new HashMap<>();
        soundMap = new HashMap<>();

        musicMap.put("game", Gdx.audio.newMusic(Gdx.files.internal(directory + "music.mp3")));
        musicMap.put("menu", Gdx.audio.newMusic(Gdx.files.internal(directory + "loadingMusic.mp3")));
        musicMap.put("collide", Gdx.audio.newMusic(Gdx.files.internal(directory + "uh.mp3"))); // sound effects for collision
        musicMap.put("munch", Gdx.audio.newMusic(Gdx.files.internal(directory + "munch.mp3")));
        musicMap.put("wall", Gdx.audio.newMusic(Gdx.files.internal(directory + "wall.mp3")));
        soundMap.put("win", Gdx.audio.newSound(Gdx.files.internal(directory + "win.mp3")));
        soundMap.put("lose", Gdx.audio.newSound(Gdx.files.internal(directory + "lose.mp3")));
        soundMap.put("hoverSound", Gdx.audio.newSound(Gdx.files.internal(directory + "Pop.mp3")));

    }
    @Override
    public Music getMusic(){
        return musicMap.get("game"); // get the game music
    }
    @Override
    public Music getMusic(String key){
        return musicMap.get(key); // get the music based on the key
    }
    @Override
    public void increaseVolume() {
        volume = Math.min(1.0f, volume + 0.1f); // increase by 10%, can change up to preference(?)
        updateVolume();
    }
    @Override
    public void decreaseVolume() {
        volume = Math.max(0.0f, volume - 0.1f); // decrease by 10%, can change up to preference(?)
        updateVolume();
    }
    @Override
    public void updateVolume() {
        float currentVolume = isMuted ? 0.0f : volume;
        for (Music music : musicMap.values()) {// this updates to all sounds in the game
            music.setVolume(currentVolume);
        }
    }
    @Override
    public void setVolume(float volume) {
        this.volume = Math.max(0.0f, Math.min(1.0f, volume)); //this setting default sound volume
        updateVolume();
    }
    @Override
    public void toggleMute() {
        if (isMuted) {
            // if currently muted, unmute by restoring volume
            isMuted = false;
            updateVolume();
        } else {
            // if currently unmuted, mute by setting volume to 0
            isMuted = true;
            muteVolume();
        }
    }
    @Override
    public void muteVolume() {
        for (Music music : musicMap.values()) {
            music.setVolume(0.0f);
        }
    }
    @Override
    public void playSound(String key) {
        if (!isMuted && soundMap.containsKey(key)) {
            Sound sound = soundMap.get(key);
            sound.play(volume);
        }
    }


}
