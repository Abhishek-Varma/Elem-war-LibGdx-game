package com.avarma.gameobjects;

/**
 * Created by abhishek on 9/3/2016.
 */
public class PowerBall {

    private float position_x, position_y;
    private boolean vanish=false;

    PowerBall(float sp_x)
    {
        position_x=sp_x+15;
        position_y=384;
    }

    public void update(float delta)
    {
        position_y-=5;
        if (position_y<=-32)//condition to make the power ball vanish
        {
            vanish=true;
        }
    }

    public boolean isVanish()
    {
        return vanish;
    }

    public float getPosition_x()
    {
        return position_x;
    }

    public float getPosition_y()
    {
        return position_y;
    }

    public void setVanish(boolean b) {
        vanish=true;
    }
}
