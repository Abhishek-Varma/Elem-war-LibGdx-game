package com.avarma.ewhelpers;

import com.avarma.gameobjects.Spaceship;
import com.avarma.gameobjects.TileArray;
import com.avarma.gameworld.GameRenderer;
import com.avarma.gameworld.GameWorld;
import com.badlogic.gdx.InputProcessor;

/**
 * Created by abhishek on 8/14/2016.
 */
public class InputHandler implements InputProcessor {
    private GameWorld myWorld;
    private Spaceship myspaceship;
    private TileArray mytileArrayOB;
    private int x_last, y_last;
    private boolean canSwipe, swipeUP;
    private Thread shootBack_thread;

    public InputHandler(final GameWorld myWorld)
    {
        this.myWorld=myWorld;
        myspaceship=myWorld.getSpaceship();
        mytileArrayOB=myWorld.getTileArrayOB();
        canSwipe=true;
        swipeUP=false;
        shootBack_thread=new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                 if(!canSwipe && swipeUP) {
                     if (myspaceship.getStackSize()>0)
                        AssetLoader.shoot.play();
                     mytileArrayOB.shootBack(myspaceship, myWorld);
                     canSwipe=true;
                     swipeUP=false;
                 }
                }
            }
        });
        shootBack_thread.start();
    }
    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(myWorld.isGameRunning())
        {
            if(screenX>=0 && screenX<=599) {
                y_last = screenY;
                myspaceship.onclick(screenX);
                return true;
            }
            else if (screenX>=759 && screenX<=799 && screenY>=0 && screenY<=40)
            {
                GameRenderer.cur_pause=AssetLoader.pause_shadow;
                AssetLoader.click.play();
                x_last=screenX;
                y_last=screenY;
                return true;
            }
            else if (screenX>=655 && screenX<=795 && screenY>=210 && screenY<=330 && myspaceship.getStackSize()==0)
            {
                myWorld.setCurrentState(GameWorld.GameState.POWERMODE);
                GameRenderer.cur_PowerMode=AssetLoader.superPower;
                return true;
            }
        }
        else if(myWorld.isGameOver())
        {
            myWorld.restart();
            AssetLoader.font.getData().setScale(1f, -1f);
            AssetLoader.run_music.play();
            return true;
        }
        else if (myWorld.isGameMenu())
        {
            if(screenX>=340 && screenX<=440 && screenY>=300 && screenY<=400) {
                x_last=screenX;
                y_last=screenY;
                GameRenderer.cur_play=AssetLoader.play_shadow;
                AssetLoader.click.play();
                return true;
            }
        }
        else if (myWorld.isGamePaused())
        {
            if (screenX>=200 && screenX<=400 && screenY>=120 && screenY<=320)
            {
                GameRenderer.cur_play=AssetLoader.play_shadow;

                AssetLoader.click.play();
            }
            else if (screenX>=440 && screenX<=640 && screenY>=120 && screenY<=320)
            {
                GameRenderer.cur_restart=AssetLoader.restart_shadow;

                AssetLoader.click.play();
            }
            x_last=screenX;
            y_last=screenY;
            return true;
        }
        else if (myWorld.isGamePowerMode())
        {
            if(screenX>=0 && screenX<=599) {
                myspaceship.onclick(screenX);
                myspaceship.shoot();
                return true;
            }
            else if (screenX>=759 && screenX<=799 && screenY>=0 && screenY<=40)
            {
                GameRenderer.cur_pause=AssetLoader.pause_shadow;
                AssetLoader.click.play();
                x_last=screenX;
                y_last=screenY;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        if (myWorld.isGameRunning())
        {
           if(screenX>=0 && screenX<=599)
           {
               if(screenY-y_last>32 && canSwipe)
               {
                   //suck in

                   AssetLoader.suck.play();
                   mytileArrayOB.suckIn(myspaceship);
               }
               else if(y_last-screenY>32 && canSwipe)
               {
                   canSwipe=false;
                   swipeUP=true;
               }
           }
           else if (screenX>=759 && screenX<=799 && screenY>=0 && screenY<=40)
           {
               if (x_last>=759 && x_last<=799 && y_last>=0 && y_last<=40)
               {
                   AssetLoader.run_music.pause();
                   myWorld.setCurrentState(GameWorld.GameState.PAUSED);
               }
           }
            GameRenderer.cur_pause=AssetLoader.pause;
        }
        else if (myWorld.isGamePaused())
        {
            if (screenX>=200 && screenX<=400 && screenY>=120 && screenY<=320)
            {
                if (x_last>=200 && x_last<=400 && y_last>=120 && y_last<=320) {

                    myWorld.setCurrentState(GameWorld.GameState.RUNNING);
                    AssetLoader.run_music.play();
                }
            }
            else if (screenX>=440 && screenX<=640 && screenY>=120 && screenY<=320)
            {
                if (x_last>=440 && x_last<=640 && y_last>=120 && y_last<=320) {

                    myWorld.restart();
                    AssetLoader.run_music.stop();
                    AssetLoader.run_music.play();
                }
            }
            GameRenderer.cur_play = AssetLoader.play;
            GameRenderer.cur_restart = AssetLoader.restart;
        }
        else if (myWorld.isGameMenu())
        {
            if(screenX>=340 && screenX<=440 && screenY>=300 && screenY<=400) {
                if (x_last>=340 && x_last<=440 && y_last>=300 && y_last<=400) {
                    myWorld.getBg1().setWidth(600);
                    myWorld.getBg2().setWidth(600);
                    mytileArrayOB.firstTime();
                    myWorld.setCurrentState(GameWorld.GameState.RUNNING);
                    AssetLoader.run_music.play();
                }
            }
            GameRenderer.cur_play=AssetLoader.play;
        }
        else if (myWorld.isGamePowerMode())
        {
            if (screenX>=759 && screenX<=799 && screenY>=0 && screenY<=40)
            {
                if (x_last>=759 && x_last<=799 && y_last>=0 && y_last<=40)
                {
                    AssetLoader.run_music.pause();
                    myWorld.setCurrentState(GameWorld.GameState.PAUSED);
                }
            }
            GameRenderer.cur_pause=AssetLoader.pause;
        }
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
