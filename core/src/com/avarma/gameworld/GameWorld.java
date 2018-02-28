package com.avarma.gameworld;

import com.avarma.ewhelpers.AssetLoader;
import com.avarma.gameobjects.Background;
import com.avarma.gameobjects.LvlProgress;
import com.avarma.gameobjects.PowerBall;
import com.avarma.gameobjects.PowerProgress;
import com.avarma.gameobjects.Spaceship;
import com.avarma.gameobjects.TileArray;

import java.util.ArrayList;

/**
 * Created by abhishek on 8/13/2016.
 */
public class GameWorld {

    private Spaceship spaceship;
    private Background bg1, bg2;
    private TileArray tileArrayOB;
    private LvlProgress lvlProgress;
    private PowerProgress powerProgress;
    private ArrayList<PowerBall> powerBall;
    private int tileCount=0, score=0, combo=1;
    private float loopTime_newLine=0, powerTime=0;
    private boolean gameOver;



    public enum GameState
    {
        READY, MENU, RUNNING, POWERMODE, PAUSED, GAMEOVER
    }

    private GameState currentState;
    public GameWorld(){

        bg1=new Background(0,0,800,480);
        bg2=new Background(0,480,800,480);
        tileArrayOB=new TileArray();
        spaceship=new Spaceship(240,416,60,64, tileArrayOB.getTile_array());
        lvlProgress=new LvlProgress();
        powerProgress=new PowerProgress();
        powerBall=spaceship.getPowerBall();

        gameOver=false;
        currentState=GameState.MENU;
    }

    public void update(float delta)
    {
        switch (currentState)
        {
            case READY:
                updateReady(delta);
                break;
            case MENU:
                updateMenu(delta);
                break;
            case RUNNING:
                updateRunning(delta);
                break;
            case POWERMODE:
                updatePowerMode(delta);
            case GAMEOVER:
                updateGameOver(delta);
        }
    }

    private void updatePowerMode(float delta) {
        powerTime+=delta;

        bg1.update(delta);
        bg2.update(delta);
        spaceship.update(delta);
        lvlProgress.update(delta);
        powerProgress.update(delta);
        for(int i=0;i<powerBall.size();i++)
        {
            powerBall.get(i).update(delta);
            if(powerBall.get(i).isVanish())
                powerBall.remove(i);
            else
            {//check for collision with tile array
                tileArrayOB.checkCollision(powerBall.get(i), this);
            }
        }
        if (powerTime>=10)
        {
            powerProgress.reset();
            powerTime=0;
            spaceship.resetPowerBall();
            currentState=GameState.RUNNING;
            GameRenderer.cur_PowerMode=AssetLoader.cooldownPower;
        }
    }

    private void updateGameOver(float delta) {
    }

    private void updateReady(float delta) {
    }

    private void updateMenu(float delta)
    {
        bg1.update(delta);
        bg2.update(delta);

    }
    private void updateRunning(float delta) {

        loopTime_newLine+=delta;
        bg1.update(delta);
        bg2.update(delta);
        spaceship.update(delta);
        lvlProgress.update(delta);
        powerProgress.update(delta);
        if(loopTime_newLine>=6)
        {
            boolean returned_val=tileArrayOB.newLine();
            gameOver=tileArrayOB.checkCollision(spaceship);
            if (gameOver || returned_val)
            {
                AssetLoader.run_music.stop();
                AssetLoader.game_over.play();
                currentState=GameState.GAMEOVER;
            }
            loopTime_newLine=0;
        }
    }

    public Spaceship getSpaceship()
    {
        return spaceship;
    }

    public Background getBg1()
    {
        return bg1;
    }

    public Background getBg2()
    {
        return bg2;
    }

    public LvlProgress getLvlProgress()
    {
        return lvlProgress;
    }

    public PowerProgress getPowerProgress()
    {
        return powerProgress;
    }

    public void setTileCount(int tC, int combo)
    {
        tileCount+=tC;
        lvlProgress.setMove(tC);
        this.combo=combo;
        setScore(tC);
    }

    private void setScore(int tC) {
        int sc;
        if(tC>=4)
            sc=combo*(60 + ((tC-4)*30));
        else
            sc=10;
        score+=sc;
    }

    public TileArray getTileArrayOB()
    {
        return tileArrayOB;
    }
    public ArrayList<PowerBall> getPowerBall() {
        return powerBall;
    }

    public boolean isGameOver()
    {
        return currentState==GameState.GAMEOVER;
    }
    public boolean isGamePaused()
    {
        return currentState==GameState.PAUSED;
    }
    public boolean isGameRunning()
    {
        return currentState==GameState.RUNNING;
    }
    public boolean isGameMenu(){return currentState==GameState.MENU;}
    public boolean isGamePowerMode(){return currentState==GameState.POWERMODE;}

    public void setCurrentState(GameState state)
    {
        currentState=state;
    }
    public void setCombo(){combo=1;}

    public int getScore(){return score;}
    public int getCombo(){
        return combo;
    }

    public void restart()
    {
        bg1.setY(0);
        bg2.setY(-480);
        spaceship.onRestart();
        tileArrayOB.onRestart();
        lvlProgress.onRestart();
        currentState=GameState.RUNNING;
        score=0;
        combo=1;
        powerTime=0;
        powerProgress.reset();
    }
}
