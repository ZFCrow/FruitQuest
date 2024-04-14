package com.mygdx.game.GameEngine.Utility;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.GdxRuntimeException;

import java.util.HashMap;
import java.util.Map;

public class TextureLoader {
    private static final Map<String, Texture> textures = new HashMap<>();
    private static final String[] directories = {
            "",
            "Entities/Items/",
            "Entities/Player/",
            "Entities/AI/"

    };

    //Private Constructor to prevent instantiation
    private TextureLoader(){}

    public static Texture loadTexture(String textureName){
        if (textures.containsKey(textureName)) {
            return textures.get(textureName);
        }

        Texture texture = null;

        for (String dir : directories) {
            try {
                texture = new Texture(dir + textureName + ".png");
                break; // If texture is successfully loaded, exit the loop
            } catch (GdxRuntimeException e) {
                // proceed silently
            }
        }

        if (texture == null) {
            // Load a default texture if none of the specified textures could be loaded
            texture = new Texture("default.png");
        }

        textures.put(textureName,texture);
        return texture;

    }


    public static void dispose() {
        for (Texture tex : textures.values()) {
            tex.dispose();
        }
        textures.clear();
        System.out.println("Textures has been cleared");
    }

}
