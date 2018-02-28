package com.avarma.gameobjects;

import com.avarma.ewhelpers.AssetLoader;
import com.avarma.gameworld.GameWorld;
import com.badlogic.gdx.Gdx;

import java.util.Random;

/**
 * Created by abhishek on 8/14/2016.
 */
public class TileArray {
    private int tile_array[][]=new int[15][10];
    private int tile_a_visited[][]=new int[15][10];
    private int[][] cdStack, tileAdjacent;
    private int cdStackSize=0,tileAdjacentSize=0;
    private boolean canPrint;
    private Random random;

    public TileArray()
    {
        cdStack=new int[20][2];
        tileAdjacent=new int[20][2];
        random=new Random();
        canPrint=true;
    }

    public int[][] getTile_array()
    {
        return tile_array;
    }

    public synchronized void firstTime()
    {
        for (int i=0;i<6;i++)// initial Tile Array will contain 6 rows filled
        {
            newLine();
        }
    }

    public synchronized boolean newLine()
    {
            int i,j,x,y;
            int pt,nt;
            for (i = 0; i < 10; i++) //checking if any tile has reached the end of screen
                if (tile_array[14][i] !=0) {
                    return true;
                }

            canPrint=false;
            for (i = 0; i < 10; i++) { //shifting every tile down by a row
                pt = tile_array[0][i];
                tile_array[0][i] = 0;
                nt = 0;
                for (j = 0; j < 14; j++) {
                    nt = tile_array[j + 1][i];
                    tile_array[j + 1][i] = pt;
                    pt = nt;
                    if (pt == 0)
                        break;
                }
            }

        //Creating a new row
        //Making sure that no 4 chained blocks is made
        for (i = 0; i < 10; i++) {
            tile_array[0][i] = random.nextInt(4)+1;
            int val = tile_array[0][i];
            while (true) {
                tileAdjacentSize=0;
                checkAdjacent(0, i, val);
                for (int k = 0; k < tileAdjacentSize; k++) {
                    x=tileAdjacent[k][0];
                    y=tileAdjacent[k][1];
                    tile_a_visited[x][y]=0;
                }
                // System.out.print("---");
                if (tileAdjacentSize >= 4) {
                    val = (val + 1) % 4 + 1;
                    tile_array[0][i]=val;
                } else
                    break;
            }

            // System.out.println();
            tile_array[0][i]=val;
        }
        canPrint=true;
        tileAdjacentSize=0;

        return false;
    }

    public boolean checkCollision(Spaceship spaceship) {

        int stackSize=spaceship.getStackSize();
        int x=(int)spaceship.getX();
        int y=(int)spaceship.getY();
        if (stackSize != 0) {
            int del=32*stackSize;
            y-=del;
            if (tile_array[y/32][x/60]!=0)
                return true;
            else
                return false;
        }
        if (tile_array[13][x / 60] != 0)
            return true;
        return false;
    }

    private synchronized void checkAdjacent(int row, int col, int val) {

        if (row >= 0 && row < 15 && col >= 0 && col < 10) {
            if (tile_array[row][col] != 0
                    && tile_array[row][col] == val
                    && tile_a_visited[row][col] == 0) {
                tile_a_visited[row][col]=1;

                tileAdjacent[tileAdjacentSize][0]=row;
                tileAdjacent[tileAdjacentSize][1]=col;
                tileAdjacentSize++;
                checkAdjacent(row - 1, col, val);
                checkAdjacent(row, col - 1, val);
                checkAdjacent(row, col + 1, val);
                checkAdjacent(row + 1, col, val);
            }
        }
    }

    //Sucking in the tiles
    //Maximum allowed tiles is 6
    //Called on swipe down
    public synchronized void suckIn(Spaceship sp) {

        Gdx.app.log("TileArray", "shoot back");
        canPrint=false;
        int col=(int)sp.getX()/60;
        int[] stackTile = sp.getStackTile();
        int stackSize=sp.getStackSize();
        for (int i = 14; i >= 0; i--)
            if (tile_array[i][col] != 0) {
                if (stackSize == 0) {
                    stackTile[stackSize++]=tile_array[i][col];
                    tile_array[i][col] = 0;
                    i--;
                }
                int value = stackTile[0];
                while (i >= 0 && tile_array[i][col] == value
                        && stackSize < 6) {
                    /*tile_array[i][col]
                            .setY(416 - (32 * (stackTile.size + 1)));*/
                    stackTile[stackSize++]=tile_array[i][col];
                    tile_array[i][col] = 0;
                    i--;
                }
                break;
            }
       sp.setStackSize(stackSize);
        canPrint=true;
    }

    //Shoots back the sucked in tiles
    //Called on swipe up
    public synchronized void shootBack(Spaceship sp, GameWorld gameWorld) {


        Gdx.app.log("TileArray", "shoot back");
        int[] stackTile = sp.getStackTile();
        int stackSize=sp.getStackSize();
        if(stackSize!=0) {
            int tileCount=0;
            int combo=1;
            canPrint=false;
            int col=(int)sp.getX()/60, flag=0;
            for (int i = 0; i < 15; i++) {
                if (tile_array[i][col] == 0) {
                    cdStack[cdStackSize][0]=i;
                    cdStack[cdStackSize][1]=col;
                    cdStackSize++;
                    for (int j = stackSize - 1; j >= 0 && i < 15; j--) {
                        tile_array[i][col] = stackTile[j];
                        i++;
                    }
                    break;
                }
            }

            sp.setStackSize(0);
            canPrint=true;
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            while (cdStackSize != 0) {
                int x, y, size = cdStackSize;
                int count = 0;
                Gdx.app.log("TileArray", "cdStack = "+cdStackSize);
                while (size != 0) {
                    size--;
                    Gdx.app.log("TileArray", "size = "+size);
                    tileAdjacentSize=0;
                    count++;
                    x = cdStack[cdStackSize-1][0];
                    y = cdStack[cdStackSize-1][1];
                    cdStackSize--;
                    if (tile_array[x][y] == 0)
                        continue;
                    checkAdjacent(x, y, tile_array[x][y]);

                    if (tileAdjacentSize >= 4) {
                        canPrint=false;
                        //Gdx.app.log("TileArray", ""+tileAdjacentSize);
                        for (int i = 0; i < tileAdjacentSize; i++) {
                            x = tileAdjacent[i][0];
                            y = tileAdjacent[i][1];
                            int val = tile_array[x][y];
                            tile_array[x][y]=val + 4;
                            tile_a_visited[x][y]=0;
                            tileCount++;
                        }

                        canPrint=true;
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        canPrint=false;
                        AssetLoader.explode.play();
                        vanishTile();
                        canPrint=true;
                        gameWorld.setTileCount(tileCount, combo);
                        combo++;
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        canPrint=false;
                        adjustTArray();
                        canPrint=true;
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        for (int j = 0; j < tileAdjacentSize; j++) {
                            x = tileAdjacent[j][0];
                            y = tileAdjacent[j][1];
                            tile_a_visited[x][y]=0;
                        }
                    }
                }
            }
            gameWorld.setCombo();// sets combo to 0
        }
    }

    private synchronized void vanishTile() {
        int x,y;
        //Gdx.app.log("TileArray", ""+tileAdjacentSize);
        for (int i = 0; i < tileAdjacentSize; i++) {
            x = tileAdjacent[i][0];
            y = tileAdjacent[i][1];
            tile_array[x][y] = 0;
            //Gdx.app.log("TileArray", ""+x+" "+y);
        }
    }

    private synchronized void adjustTArray() {

        int flag=0,col,k,i,j;
        for (i = 0; i < 10; i++) {
            col = i;
            k = 0;
            for (j = 0; j < 15; j++) {
                if (tile_array[j][col] != 0) {
                    tile_array[k][col] = tile_array[j][col];
                    //tile_array[k][col].setY(k * 32);
                    if (k != j) {
                        cdStack[cdStackSize][0] = k;
                        cdStack[cdStackSize][1] = col;
                        cdStackSize++;
                        tile_array[j][col] = 0;
                    }
                    k++;
                }
            }
        }
    }

    public boolean isCanPrint()
    {
        return canPrint;
    }

    public void onRestart() {

        for (int i=0;i<15;i++)
            for (int j=0;j<10;j++)
                tile_array[i][j]=0;

        firstTime();
    }

    public void checkCollision(PowerBall powerBall,  GameWorld gameWorld) {

        int x=(int)powerBall.getPosition_x()/60;
        int y=(int)powerBall.getPosition_y()/32;
        if (tile_array[y][x]!=0)
        {
            tile_array[y][x]=0;
            gameWorld.setTileCount(1, 1);
            powerBall.setVanish(true);
        }
    }
}
