package com.mygdx.game.Game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.GameMaster;
import com.mygdx.game.Game.Managers.SceneManager;
import com.mygdx.game.Game.Managers.SoundManager;

public class MainScreen extends BaseScreen {
    private Sprite backgroundSprite;
    private ScreenTextureObject newGameButtonObject;
    private ScreenTextureObject quitButtonObject;
    private ScreenTextureObject mainMenuTitleObject;
    private Image newGameButton;
    private Image quitButton;
    private Image mainMenuTitle;


    public MainScreen(GameMaster game) {
        super(game);
        Texture backgroundTexture = new Texture(Gdx.files.internal("menubackground.jpg"));
        backgroundSprite = new Sprite(backgroundTexture);
        backgroundSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        initUI();
    }

    @Override
    public void show() {
        super.show(); // this is to set the stage as the input processor
        game.getManager(SoundManager.class).setVolume(0.5f); // set mainscreen volume to 50%
        game.getManager(SoundManager.class).getMusic("menu").play(); // play the music
    }

    @Override
    protected void initUI() {
        newGameButtonObject = new ScreenTextureObject("Buttons/PlayButton.png", 0, 0, 2, 3);
        quitButtonObject = new ScreenTextureObject("Buttons/QuitButton.png", 0, 0, 2, 3);
        mainMenuTitleObject = new ScreenTextureObject("MainMenuTitle.png", 0, 0, 1, 1);

        newGameButton = newGameButtonObject.getImage();
        quitButton = quitButtonObject.getImage();
        mainMenuTitle = mainMenuTitleObject.getImage();

        centerActorsOnScreen();

        setUpButton(newGameButton, "Buttons/PlayButtonHover.png", new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.getManager(SceneManager.class).setScreen(game.getManager(SceneManager.class).getScreen(PlayScreen.class));
                game.getManager(SoundManager.class).getMusic("menu").stop();
            }
        });

        setUpButton(quitButton, "Buttons/QuitButtonHover.png", new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        stage.addActor(newGameButton);
        stage.addActor(quitButton);
        stage.addActor(mainMenuTitle);
    }

    private void centerActorsOnScreen() {
        float screenCenterX = Gdx.graphics.getWidth() / 2f;
        float screenCenterY = Gdx.graphics.getHeight() / 2f;
        float buttonSpacing = 10f;

        mainMenuTitle.setPosition(screenCenterX - mainMenuTitle.getWidth() / 2, screenCenterY + buttonSpacing);
        newGameButton.setPosition(screenCenterX - newGameButton.getWidth() / 2, screenCenterY - newGameButton.getHeight() / 2);
        quitButton.setPosition(screenCenterX - quitButton.getWidth() / 2, newGameButton.getY() - quitButton.getHeight() - buttonSpacing);
    }


    private void setUpButton(final Image button, String hoverImagePath, ClickListener listener) {
        final Texture hoverTexture = new Texture(Gdx.files.internal(hoverImagePath));
        final Texture originalTexture = (Texture) button.getUserObject();

        button.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                button.setDrawable(new TextureRegionDrawable(hoverTexture));
                game.getManager(SoundManager.class).playSound("hoverSound");
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                button.setDrawable(new TextureRegionDrawable(originalTexture));
            }

            @Override
            public void clicked(InputEvent event, float x, float y) {
                listener.clicked(event, x, y);
            }
        });
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
    public void resize(int width, int height) {
        super.resize(width, height);
        backgroundSprite.setSize(width, height);
        stage.getViewport().update(width, height, true);
        centerActorsOnScreen();
    }

    @Override
    public void dispose() {
        backgroundSprite.getTexture().dispose();
        newGameButtonObject.dispose();
        quitButtonObject.dispose();
        mainMenuTitleObject.dispose();
        stage.dispose();
    }
}
