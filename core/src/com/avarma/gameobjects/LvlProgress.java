package com.avarma.gameobjects;

/**
 * Created by abhishek on 8/23/2016.
 */
public class LvlProgress {

    private int pos_y, final_pos;
    private float dy, speed_y;

    public LvlProgress()
    {
        pos_y=470;
        final_pos=-1;
        dy=460/50;
        speed_y=0;
    }

    public void update(float delta)
    {
        if(final_pos!=-1 && pos_y!=10) {
            pos_y -= (speed_y * delta);
            if (pos_y <= final_pos) {
                pos_y = final_pos;
                final_pos = -1;
            }
        }
    }

    public void setMove(int num_tiles)
    {
        final_pos=pos_y-(int)(dy*num_tiles);
        if (final_pos<42)
            final_pos=42;
        speed_y=(float) ((pos_y-final_pos)/1.5);
    }

    public int getPos_y()
    {
        return pos_y;
    }

    public void onRestart() {
        pos_y=470;
        final_pos=-1;
        speed_y=0;
    }
}
