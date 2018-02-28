package com.avarma.gameobjects;

/**
 * Created by abhishek on 9/3/2016.
 */
public class PowerProgress {
    private int width;
    private float dx;
    private float runTime=0;
    public PowerProgress()
    {
        width=0;
        dx=140/20;
    }

    public void update(float delta)
    {
        runTime+=delta;
        if (runTime<20)
        {
            width=(int)(dx*runTime);
        }
        if (runTime>=20)
        {
            width=140;
        }
    }

    public void reset()
    {
        width=0;
        runTime=0;
    }

    public int getWidth()
    {
        return width;
    }
}
