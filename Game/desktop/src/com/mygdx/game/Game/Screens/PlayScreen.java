package com.mygdx.game.Game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.Game.Entities.AI;
import com.mygdx.game.Game.Entities.Player;
import com.mygdx.game.Game.Managers.*;
import com.mygdx.game.GameEngine.Entities.Entity;
import com.mygdx.game.GameEngine.Managers.EngineEntityManager;
import com.mygdx.game.GameMaster;

import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class PlayScreen extends BaseScreen {
    private Label screenLabel;
    private Label livesLabel;
    private Label scoreLabel;
    private Label speedLabel;
    private Label jumpHeightLabel;

    public PlayScreen(GameMaster game) {
        super(game);


        game.getManager(SimulationLifeCycleManager.class).setupLevel();
        initUI();
        System.out.println("Number of entities: " + game.getManager(EntityManager.class).getEntities().size());

        //print out my entitiy in the list
        for (int i = 0; i < game.getManager(EntityManager.class).getEntities().size(); i++) {
            System.out.println(game.getManager(EntityManager.class).getEntities().get(i));
            System.out.println(game.getManager(EntityManager.class).getEntities().get(i).getSpeed());
        }
    }

    @Override
    public void initUI() {
        Player player = (Player) game.getManager(EntityManager.class).getEntity(Player.class);

        livesLabel = new Label("Lives: " + player.getNumberofLives(), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel = new Label("Score: " + player.getPlayerScore(), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        speedLabel = new Label("Speed: " + player.getSpeed(), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        jumpHeightLabel = new Label("JumpHeight: " + player.getJumpHeight(), new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        Table table = new Table();
        table.setFillParent(true);
        table.top();


        float padValue = 10;

        table.add(livesLabel).padRight(padValue);
        table.add(scoreLabel).padRight(padValue);
        table.add(speedLabel).padRight(padValue);
        table.add(jumpHeightLabel);

        stage.addActor(table);
    }

    @Override
    public void show() {
        super.show();
        game.getManager(SoundManager.class).setVolume(1.0f);
        game.getManager(SoundManager.class).getMusic().play(); //play the music

    }
    @Override
    public void render(float delta) {
        //clear color to blue
        Gdx.gl.glClearColor(0, 0, 1, 1); //this clear the screen to blue
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);




        game.getManager(MapManager.class).render((OrthographicCamera) game.getCamera());
        game.getManager(PhysicsManager.class).update(delta);




        game.getBatch().begin();
        game.getBatch().setProjectionMatrix(game.getCamera().combined);



        displayStats();

        //while the batch is running, the IOManager will listen for left and right mouse button being clicked
        if(game.getManager(InputOutputManager.class).getInputMouse(Buttons.LEFT).getInputReceived()||game.getManager(InputOutputManager.class).getInputMouse(Buttons.RIGHT).getInputReceived()){
            //if LMB was clicked, it will notify on the terminal. The ifButtonPressed(Buttons.RIGHT) will return true if the right mouse button (RMB) is clicked, therefore it will enter the if statement if RMB is pressed
            if(game.getManager(InputOutputManager.class).getInputMouse(Buttons.LEFT).ifButtonPressed(Buttons.LEFT)) {
                System.out.println("LMB was pressed");
            }

            //if LMB was clicked, it will notify on the terminal. The ifButtonPressed(Buttons.RIGHT) will return true if the right mouse button (RMB) is clicked, therefore it will enter the if statement if RMB is pressed
            if(game.getManager(InputOutputManager.class).getInputMouse(Buttons.RIGHT).ifButtonPressed(Buttons.RIGHT)) {
                System.out.println("RMB was pressed");
            }
        }


        //loop through the entities and draw them
        for (int i = 0; i < game.getManager(EntityManager.class).getEntities().size(); i++) {
            Entity entity = game.getManager(EntityManager.class).getEntities().get(i);
            //game.getManager(EntityManager.class).getEntities().get(i).draw(game.getBatch());
            entity.draw(game.getBatch());


            playerMovements(entity);
        }
        //for engine testing
        for (int i = 0; i < game.getManager(EngineEntityManager.class).getEntities().size(); i++) {
            Entity entity = game.getManager(EngineEntityManager.class).getEntities().get(i);
            entity.draw(game.getBatch());
        }
        AiMovements();
        pauseScreen();


        game.getBatch().end();


        //run the debug renderer
       // game.getManager(PhysicsManager.class).renderDebug(game.getCamera());
        super.stage.act(delta);
        super.stage.draw();

        game.getManager(SimulationLifeCycleManager.class).checkPlayerStatus();
        ((Player) game.getManager(EntityManager.class).getEntity(Player.class)).capStats();
        }


        public void pauseScreen() {
            if (game.getManager(InputOutputManager.class).getInputKeyboard(Keys.ESCAPE).ifKeyPressed(Keys.ESCAPE)) { //If the IOManager detects the escape button pressed
                game.getManager(SceneManager.class).setScreen(game.getManager(SceneManager.class).getScreen(PauseScreen.class));
                game.getManager(SoundManager.class).getMusic().stop();
            }
        }

        public void playerMovements(Entity entity){

            if (entity instanceof Player && game.getManager(InputOutputManager.class).getInputKeyboard(Keys.ANY_KEY).getInputReceived()==true) { //if ... and a keyboard input is detected using keyPressed() from InputKeyboard
                Player player = (Player) entity;
                if (game.getManager(InputOutputManager.class).getInputKeyboard(Keys.RIGHT).ifKeyPressed(Keys.RIGHT)==true) { //if the right arrow key button was pressed using a keyboard, ifKeyPressed() from InputKeyboard will return true
                    game.getManager(PlayerControlManager.class).run(player, Keys.RIGHT);
                }
                else if (game.getManager(InputOutputManager.class).getInputKeyboard(Keys.LEFT).ifKeyPressed(Keys.LEFT)==true) { //else if the left arrow key button was pressed using a keyboard, ifKeyPressed() from InputKeyboard will return true
                    game.getManager(PlayerControlManager.class).run(player, Keys.LEFT);
                }
                else if (game.getManager(InputOutputManager.class).getInputKeyboard(Keys.UP).ifKeyPressed(Keys.UP)==true) { //else if the up arrow key button was pressed using a keyboard, ifKeyPressed() from InputKeyboard will return true
                    game.getManager(PlayerControlManager.class).jump(player);
                }
            }
        }

        public void AiMovements(){
            //check the size of the list of entitiesAI, move left or right
            if(game.getManager(EntityManager.class).getEntities(AI.class).size()>0)game.getManager(AIControlManager.class).getAIController().getMovement().movementBox2D(game.getManager(EntityManager.class).getEntities(AI.class).get(0));
            if(game.getManager(EntityManager.class).getEntities(AI.class).size()>1)game.getManager(AIControlManager.class).getAIController().getMovement().movementBox2D(game.getManager(EntityManager.class).getEntities(AI.class).get(1));
        }

    public void displayStats() {
        Player player = (Player) game.getManager(EntityManager.class).getEntity(Player.class);
        livesLabel.setText("Lives: " + player.getNumberofLives());
        scoreLabel.setText("Score: " + player.getPlayerScore());
        speedLabel.setText("Speed: " + player.getSpeed());
        jumpHeightLabel.setText("JumpHeight: " + player.getJumpHeight());


        if (player.getNumberofLives() == 1) {
            livesLabel.getStyle().fontColor = Color.RED;
        } else {
            livesLabel.getStyle().fontColor = Color.WHITE;
        }

        if (player.getPlayerScore() >= game.getManager(PhysicsManager.class).getScoreToUnlock()) {
            scoreLabel.getStyle().fontColor = Color.GREEN;
        } else {
            scoreLabel.getStyle().fontColor = Color.WHITE;
        }
    }


    @Override
        public void resize(int width, int height) {
            super.resize(width, height);

        }

    }

