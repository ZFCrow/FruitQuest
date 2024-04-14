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
import com.mygdx.game.Game.Managers.SimulationLifeCycleManager;
import com.mygdx.game.GameMaster;
import com.mygdx.game.Game.Managers.SceneManager;
import com.mygdx.game.Game.Managers.SoundManager;

public class WinLoseScreen extends BaseScreen {
    private boolean win;
    private Sprite backgroundSprite;
    private ScreenTextureObject nextLevelButtonObject, quitButtonObject, mainMenuButtonObject, resultTitleObject;
    private Image nextLevelButton, quitButton, mainMenuButton, resultTitle;

    private Texture winBackgroundTexture, loseBackgroundTexture;

    public WinLoseScreen(GameMaster game, boolean win) {
        super(game);
        this.win = win;
        winBackgroundTexture = new Texture(Gdx.files.internal("winhappy.jpg"));
        loseBackgroundTexture = new Texture(Gdx.files.internal("losesad.jpg"));
        initUI();

        if (win) {
            game.getManager(SoundManager.class).playSound("win");
        } else {
            game.getManager(SoundManager.class).playSound("lose");
        }
    }

    @Override
    protected void initUI() {
        Texture backgroundTexture = win ? winBackgroundTexture : loseBackgroundTexture; // Select background based on win/lose
        backgroundSprite = new Sprite(backgroundTexture);
        backgroundSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        nextLevelButtonObject = new ScreenTextureObject("Buttons/NextLevel.png", 0, 0, 2, 3);
        quitButtonObject = new ScreenTextureObject("Buttons/QuitButton.png", 0, 0, 2, 3);
        mainMenuButtonObject = new ScreenTextureObject("Buttons/MainMenu.png", 0, 0, 2, 3);
        resultTitleObject = new ScreenTextureObject(win ? "Win.png" : "Lose.png", 0, 0, 1, 1);

        nextLevelButton = nextLevelButtonObject.getImage();
        quitButton = quitButtonObject.getImage();
        mainMenuButton = mainMenuButtonObject.getImage();
        resultTitle = resultTitleObject.getImage();

        centerActorsOnScreen();
        setUpButtons();
        addActorsToStage();
    }

    private void centerActorsOnScreen() {
        float screenCenterX = Gdx.graphics.getWidth() / 2f;
        float screenCenterY = Gdx.graphics.getHeight() / 2f;
        float buttonSpacing = 10f;

        // Title position (corrected)
        resultTitle.setPosition(screenCenterX - resultTitle.getWidth() / 2, screenCenterY + buttonSpacing); // Adjust offset as needed

        // Button positions (vertically stacked)
        nextLevelButton.setPosition(screenCenterX - nextLevelButton.getWidth() / 2,
                screenCenterY - nextLevelButton.getHeight() / 2);
        mainMenuButton.setPosition(screenCenterX - mainMenuButton.getWidth() / 2,
                nextLevelButton.getY() - mainMenuButton.getHeight() - buttonSpacing);
        quitButton.setPosition(screenCenterX - quitButton.getWidth() / 2,
                mainMenuButton.getY() - quitButton.getHeight() - buttonSpacing);
    }

    private void setUpButtons() {
        if (win) {
            setUpButton(nextLevelButton, "Buttons/NextLevelHover.png", new ClickListener() {
                @Override
                public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                    game.getManager(SimulationLifeCycleManager.class).incrementLevel();
                    //dispose this screen by removing it from the screen manager
                    //DISPLAY LIST OF screens
                    game.getManager(SceneManager.class).removeScreen(WinLoseScreen.class);

                    if (game.getManager(SimulationLifeCycleManager.class).getLevel() > 2) {
                        game.getManager(SceneManager.class).setScreen(game.getManager(SceneManager.class).getScreen(MainScreen.class));
                    } else {
                        game.getManager(SceneManager.class).setScreen(game.getManager(SceneManager.class).getScreen(PlayScreen.class));
                    }
                }

            });
        }

        setUpButton(mainMenuButton, "Buttons/MainMenuHover.png", new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.getManager(SceneManager.class).setScreen(game.getManager(SceneManager.class).getScreen(MainScreen.class));
                //dispose this screen by removing it from the screen manager
                game.getManager(SceneManager.class).removeScreen(WinLoseScreen.class);
            }
        });

        setUpButton(quitButton, "Buttons/QuitButtonHover.png", new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
    }

    private void addActorsToStage() {
        if (win) stage.addActor(nextLevelButton);
        stage.addActor(mainMenuButton);
        stage.addActor(quitButton);
        stage.addActor(resultTitle);
    }

    private void setUpButton(final Image button, String hoverImagePath, ClickListener listener) {
        final Texture hoverTexture = new Texture(Gdx.files.internal(hoverImagePath));
        final Texture originalTexture = ((TextureRegionDrawable) button.getDrawable()).getRegion().getTexture();

        button.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                button.setDrawable(new TextureRegionDrawable(new Texture(Gdx.files.internal(hoverImagePath))));
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
        stage.getViewport().update(width, height, true);
        centerActorsOnScreen();
    }

    @Override
    public void dispose() {
        backgroundSprite.getTexture().dispose();
        nextLevelButtonObject.dispose();
        quitButtonObject.dispose();
        mainMenuButtonObject.dispose();
        resultTitleObject.dispose();
        stage.dispose();
    }
}
