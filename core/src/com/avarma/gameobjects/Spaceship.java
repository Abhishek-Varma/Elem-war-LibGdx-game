package com.avarma.gameobjects;

import java.util.ArrayList;

/**
 * Created by abhishek on 8/14/2016.
 */
public class Spaceship {
    private float position_x,position_y;
    private float velocity_x,velocity_y;

    private int width, height;

    private int[] stackTile=new int[6];
    private int stackSize=0;
    private int loopIncr;
    private int tile_array[][];
    private ArrayList<PowerBall> powerBall = new ArrayList<PowerBall>();

    public Spaceship(float x, float y, int width, int height, int[][] tile_array)
    {
        this.width = width;
        this.height = height;
        position_x=x;
        position_y=y;
        velocity_x=-1;
        velocity_y=0;
        loopIncr=60;
        this.tile_array=tile_array;
    }

    public void update(float delta)
    {
        //nothing to do here onclick function takes care of it
    }

    public float getX() {
        return position_x;
    }

    public float getY() {
        return position_y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public void onclick(int touchX)
    {
        int col=touchX/60;
        col*=60;
        velocity_x=col;
        if(velocity_x>=0 && velocity_x<=540)
        {
            if(velocity_x<position_x)
                loopIncr=-60;

            int top_y;
            if(stackSize!=0)// check collision with the topmost stackTile
            {
                top_y=(480-((stackSize*32)+64))/32;// y down coordinate
            }
            else // check collision with spaceship only
            {
                top_y=13;
            }
            int top_x=(int)(position_x);// x right coordinate

            for (;top_x!=velocity_x;top_x+=loopIncr)
            {
                if (tile_array[top_y][top_x/60]!=0)
                {
                    top_x-=loopIncr;
                    break;
                }
            }

            if(top_x==velocity_x)
            {
                if(tile_array[top_y][(int)velocity_x/60]!=0)
                    top_x-=loopIncr;
            }

            position_x=top_x;
        }
        loopIncr=60;
        velocity_x=-1;
    }

    public int[] getStackTile()
    {
        return stackTile;
    }


    public int getStackSize() {
        return stackSize;
    }

    public void setStackSize(int stackSize) {
        this.stackSize = stackSize;
    }

    public void onRestart() {

        position_x=240;
        stackSize=0;
        resetPowerBall();
    }

    public void resetPowerBall()
    {
        powerBall.clear();
    }
    public void shoot() {

        PowerBall p=new PowerBall(position_x);
        powerBall.add(p);
    }

    public ArrayList<PowerBall> getPowerBall()
    {
        return powerBall;
    }
}
