package com.mygdx.game.Game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.GameMaster;
import com.mygdx.game.Game.Managers.SceneManager;
import com.mygdx.game.Game.Managers.SoundManager;

public class PauseScreen extends BaseScreen {
    private Sprite backgroundSprite;
    private ScreenTextureObject resumeButtonObject, mainMenuButtonObject, exitButtonObject, muteButtonObject, increaseVolumeButtonObject, decreaseVolumeButtonObject;
    private Image resumeButton, mainMenuButton, exitButton, muteButton,increaseVolumeButton, decreaseVolumeButton;
    private boolean isMuted = false;

    public PauseScreen(GameMaster game) {
        super(game);
        backgroundSprite = new Sprite(new Texture(Gdx.files.internal("menubackground.jpg")));
        backgroundSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        initUI();
    }

    @Override
    protected void initUI() {
        resumeButtonObject = new ScreenTextureObject("Buttons/Resume.png", 0, 0, 2, 3);
        mainMenuButtonObject = new ScreenTextureObject("Buttons/MainMenu.png", 0, 0, 2, 3);
        exitButtonObject = new ScreenTextureObject("Buttons/Exit.png", 0, 0, 2, 3);

        muteButtonObject = new ScreenTextureObject(isMuted ? "Buttons/Mute.png" : "Buttons/Unmute.png", 0, 0, 2, 3);
        increaseVolumeButtonObject = new ScreenTextureObject("Buttons/IncreaseVolume.png", 0, 0, 2, 3);
        decreaseVolumeButtonObject = new ScreenTextureObject("Buttons/DecreaseVolume.png", 0, 0, 2, 3);

        resumeButton = resumeButtonObject.getImage();
        mainMenuButton = mainMenuButtonObject.getImage();
        exitButton = exitButtonObject.getImage();

        muteButton = muteButtonObject.getImage();
        increaseVolumeButton = increaseVolumeButtonObject.getImage();
        decreaseVolumeButton = decreaseVolumeButtonObject.getImage();

        centerActorsOnScreen();

        setUpButton(increaseVolumeButton, "Buttons/IncreaseVolumeHover.png", () -> {
            game.getManager(SoundManager.class).increaseVolume();
        });

        setUpButton(decreaseVolumeButton, "Buttons/DecreaseVolumeHover.png", () -> {
            game.getManager(SoundManager.class).decreaseVolume();
        });

        setUpButton(resumeButton, "Buttons/ResumeHover.png", () -> {
            game.getManager(SceneManager.class).setScreen(game.getManager(SceneManager.class).getScreen(PlayScreen.class));
            game.getManager(SoundManager.class).getMusic("menu").stop();
        });

        setUpButton(mainMenuButton, "Buttons/MainMenuHover.png", () -> {
            game.getManager(SceneManager.class).setScreen(game.getManager(SceneManager.class).getScreen(MainScreen.class));
        });

        setUpButton(exitButton, "Buttons/ExitHover.png", () -> Gdx.app.exit());

        setUpMuteButton();

        stage.addActor(resumeButton);
        stage.addActor(mainMenuButton);
        stage.addActor(exitButton);

        stage.addActor(muteButton);
        stage.addActor(increaseVolumeButton);
        stage.addActor(decreaseVolumeButton);
    }

    private void setUpMuteButton() {
        muteButton.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                String hoverTexturePath = isMuted ? "Buttons/MuteHover.png" : "Buttons/UnmuteHover.png";
                muteButton.setDrawable(new TextureRegionDrawable(new Texture(Gdx.files.internal(hoverTexturePath))));
                game.getManager(SoundManager.class).playSound("hoverSound");
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                String texturePath = isMuted ? "Buttons/Mute.png" : "Buttons/Unmute.png";
                muteButton.setDrawable(new TextureRegionDrawable(new Texture(Gdx.files.internal(texturePath))));
            }

            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.getManager(SoundManager.class).toggleMute();
                isMuted = !isMuted; // Update the local isMuted state
                // Update the muteButtonObject with the new texture based on the isMuted state
                muteButtonObject.dispose(); // Dispose of the previous texture
                String texturePath = isMuted ? "Buttons/Mute.png" : "Buttons/Unmute.png";
                muteButtonObject = new ScreenTextureObject(texturePath, muteButton.getX(), muteButton.getY(), 2, 3);
                muteButton.setDrawable(new TextureRegionDrawable(new Texture(Gdx.files.internal(texturePath))));
            }
        });
    }


    private void setUpButton(Image button, String hoverImagePath, Runnable onClickAction) {
        button.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                button.setDrawable(new TextureRegionDrawable(new Texture(hoverImagePath)));
                game.getManager(SoundManager.class).playSound("hoverSound");
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                button.setDrawable(new TextureRegionDrawable(((Texture)button.getUserObject())));
            }

            @Override
            public void clicked(InputEvent event, float x, float y) {
                onClickAction.run();
            }
        });
    }

    private void centerActorsOnScreen() {
        float screenCenterX = Gdx.graphics.getWidth() / 2f;
        float screenCenterY = Gdx.graphics.getHeight() / 2f;


        float buttonHeight = resumeButton.getHeight();
        float verticalButtonSpacing = 10; // vertical space between buttons
        float horizontalButtonSpacing = 10; // horizontal space between mute, increase, and decrease buttons

        resumeButton.setPosition(screenCenterX - resumeButton.getWidth() / 2, screenCenterY - buttonHeight / 2);

        muteButton.setPosition(screenCenterX - muteButton.getWidth() / 2, resumeButton.getY() + buttonHeight + verticalButtonSpacing);

        increaseVolumeButton.setPosition(muteButton.getX() + muteButton.getWidth() + horizontalButtonSpacing, muteButton.getY());

        decreaseVolumeButton.setPosition(muteButton.getX() - decreaseVolumeButton.getWidth() - horizontalButtonSpacing, muteButton.getY());

        mainMenuButton.setPosition(screenCenterX - mainMenuButton.getWidth() / 2, resumeButton.getY() - buttonHeight - verticalButtonSpacing);
        exitButton.setPosition(screenCenterX - exitButton.getWidth() / 2, mainMenuButton.getY() - buttonHeight - verticalButtonSpacing);
    }



    @Override
    public void render(float delta) {
        super.render(delta);
        SpriteBatch batch = game.getBatch();
        batch.begin();
        backgroundSprite.draw(batch);
        batch.end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void dispose() {
        backgroundSprite.getTexture().dispose();
        resumeButtonObject.dispose();
        mainMenuButtonObject.dispose();
        exitButtonObject.dispose();
        muteButtonObject.dispose();
        super.dispose();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        //backgroundSprite.setSize(width, height);
        stage.getViewport().update(width, height, true);
        centerActorsOnScreen();
    }

    @Override
    public void show() {
        super.show();
        game.getManager(SoundManager.class).setVolume(0.5f);
        game.getManager(SoundManager.class).getMusic("menu").play();
    }
}


