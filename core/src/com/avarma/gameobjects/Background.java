package com.avarma.gameobjects;

/**
 * Created by abhishek on 8/14/2016.
 */
public class Background {

    private int position_x,position_y;
    private int velocity_y;

    private int width, height;

    public Background(float x, float y, int width, int height)
    {
        this.width = width;
        this.height = height;
        position_x=(int)x;
        position_y=(int)y;
        velocity_y =4;
    }

    public void update(float delta)
    {
        position_y+=velocity_y;
        if(position_y>=480)
            position_y-=960;
    }

    public float getX() {
        return position_x;
    }

    public float getY() {
        return position_y;
    }

    public void setX(int x) {
        position_x=x;
    }

    public void setY(int y) {
        position_y=y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public void setWidth(int width) {
        this.width=width;
    }

    public void setHeight(int height) {
        this.height=height;
    }
}
