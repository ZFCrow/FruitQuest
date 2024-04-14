package com.mygdx.game.Game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class ScreenTextureObject {
    private Image image;

    public ScreenTextureObject(String texturePath, float x, float y, float widthScale, float heightScale) {
        Texture texture = new Texture(Gdx.files.internal(texturePath));
        this.image = new Image(texture);
        this.image.setPosition(x, y);
        this.image.setSize(texture.getWidth() * widthScale, texture.getHeight() * heightScale);
        this.image.setUserObject(texture);
    }

    public Image getImage() {
        return image;
    }

    public void dispose() {
        ((Texture) image.getUserObject()).dispose();
    }
}
