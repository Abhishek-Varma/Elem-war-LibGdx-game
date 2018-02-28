package com.avarma.screens;

/**
 * Created by abhishek on 8/13/2016.
 */

import com.avarma.ewhelpers.InputHandler;
import com.avarma.gameworld.GameRenderer;
import com.avarma.gameworld.GameWorld;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

public class GameScreen implements Screen {

    private GameWorld world;
    private GameRenderer renderer;
    private float runTime=0;

    public GameScreen()
    {
        world=new GameWorld();
        renderer=new GameRenderer(world);
        Gdx.input.setInputProcessor(new InputHandler(world));
    }
    @Override
    public void show() {

        Gdx.app.log("GameScreen", "show called");
    }

    @Override
    public void render(float delta) {//Game Loop

        runTime+=delta;
        world.update(delta);
        renderer.render(runTime);
    }

    @Override
    public void resize(int width, int height) {

        Gdx.app.log("GameScreen", "resizing");
    }

    @Override
    public void pause() {

        Gdx.app.log("GameScreen", "pause called");
    }

    @Override
    public void resume() {

        Gdx.app.log("GameScreen", "resume called");
    }

    @Override
    public void hide() {

        Gdx.app.log("GameScreen", "hide called");
    }

    @Override
    public void dispose() {

    }
}
