package com.avarma.gameworld;

import com.avarma.ewhelpers.AssetLoader;
import com.avarma.gameobjects.Background;
import com.avarma.gameobjects.LvlProgress;
import com.avarma.gameobjects.PowerBall;
import com.avarma.gameobjects.PowerProgress;
import com.avarma.gameobjects.Spaceship;
import com.avarma.gameobjects.TileArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import java.util.ArrayList;

/**
 * Created by abhishek on 8/13/2016.
 */
public class GameRenderer {
    private GameWorld myWorld;
    private OrthographicCamera cam;
    //private ShapeRenderer shapeRenderer;

    private Spaceship mySpaceship;
    private int[] myStackTile;
    private Background mybg1,mybg2;
    private TileArray mytileArrayOB;
    private LvlProgress mylvlProgress;
    private PowerProgress mypowerProgress;
    private ArrayList<PowerBall> myPowerBall;
    private int[][] mytile_array;
    private int temp,x,y;
    private TextureRegion water_sp, background, fire_tile, water_tile, air_tile, earth_tile,fire_tile_glow, water_tile_glow, air_tile_glow, earth_tile_glow;
    private TextureRegion progressFront, sp_glass, eWar;
    private TextureRegion waterBall1, waterBall2, waterBall3, waterBall4, waterBall5;
    private Animation alienAnimation, waterBallAnimation;
    public static TextureRegion cur_pause, cur_play, cur_restart;
    public static TextureRegion cur_PowerMode;
    private SpriteBatch batcher;
    private ShapeRenderer shapeRenderer;
    public GameRenderer(GameWorld world) {

        myWorld=world;
        cam=new OrthographicCamera();
        cam.setToOrtho(true,800,480);
        batcher=new SpriteBatch();
        batcher.setProjectionMatrix(cam.combined);
        shapeRenderer=new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(cam.combined);

        initGameObjects();
        initAssets();
    }

    private void initGameObjects()
    {
        mySpaceship=myWorld.getSpaceship();
        mybg1=myWorld.getBg1();
        mybg2 = myWorld.getBg2();
        mytileArrayOB=myWorld.getTileArrayOB();
        mytile_array=mytileArrayOB.getTile_array();
        myStackTile=mySpaceship.getStackTile();
        mylvlProgress=myWorld.getLvlProgress();
        mypowerProgress=myWorld.getPowerProgress();
        myPowerBall=myWorld.getPowerBall();
    }

    private void initAssets()
    {
        water_sp= AssetLoader.water_sp;
        background=AssetLoader.background;
        fire_tile=AssetLoader.fire_tile;
        water_tile=AssetLoader.water_tile;
        air_tile=AssetLoader.air_tile;
        earth_tile=AssetLoader.earth_tile;
        fire_tile_glow=AssetLoader.fire_tile_glow;
        water_tile_glow=AssetLoader.water_tile_glow;
        air_tile_glow=AssetLoader.air_tile_glow;
        earth_tile_glow=AssetLoader.earth_tile_glow;
        progressFront=AssetLoader.progressFront;
        cur_pause=AssetLoader.pause;
        cur_play=AssetLoader.play;
        cur_restart=AssetLoader.restart;
        sp_glass=AssetLoader.sp_glass;
        alienAnimation=AssetLoader.alienAnimation;
        eWar=AssetLoader.eWar;
        waterBall1=AssetLoader.waterBall1;
        waterBall2=AssetLoader.waterBall2;
        waterBall3=AssetLoader.waterBall3;
        waterBall4=AssetLoader.waterBall4;
        waterBall5=AssetLoader.waterBall5;
        waterBallAnimation=AssetLoader.waterBallAnimation;
        cur_PowerMode=AssetLoader.cooldownPower;
    }

    public void render(float runTime) {

        Gdx.app.log("GameRenderer", "render");
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (myWorld.isGameRunning()||myWorld.isGamePaused()||myWorld.isGamePowerMode()){

            shapeRenderer.begin(ShapeType.Filled);
            shapeRenderer.setColor(255 / 255.0f, 0 / 255.0f, 0 / 255.0f, 1);//background
            //shapeRenderer.rect(610,10,30,460);
            shapeRenderer.rect(605,5,40,5);
            shapeRenderer.rect(605,470,40,5);
            shapeRenderer.setColor(3 / 255.0f, 251 / 255.0f, 18 / 255.0f, 1);//foreground
            shapeRenderer.rect(610,mylvlProgress.getPos_y(),30,470-mylvlProgress.getPos_y());
            shapeRenderer.end();

            batcher.begin();
            batcher.disableBlending();
            batcher.draw(background, mybg1.getX(), mybg1.getY(), mybg1.getWidth(), mybg1.getHeight());
            batcher.draw(background, mybg2.getX(), mybg2.getY(), mybg2.getWidth(), mybg2.getHeight());
            batcher.enableBlending();
            x = (int) mySpaceship.getX();
            y = (int) mySpaceship.getY();
            batcher.draw(water_sp, x, y, mySpaceship.getWidth(), mySpaceship.getHeight());
            batcher.draw(progressFront, 620,10,10,460);
            batcher.draw(water_sp, 610,mylvlProgress.getPos_y()-32, 30,32);
            if (mytileArrayOB.isCanPrint())
                drawTileArray();

            x = (int) mySpaceship.getX();
            y = (int) mySpaceship.getY();

            drawStackTile();

            batcher.draw(background, 650,0,150,480);
            batcher.draw(cur_pause, 759,0,40,40);
            /*shapeRenderer.begin(ShapeType.Filled);
            shapeRenderer.setColor(204 / 255.0f, 163 / 255.0f, 0 / 255.0f, 1);
            shapeRenderer.rect(655,60,140,50);
            shapeRenderer.setColor(255 / 255.0f, 204 / 255.0f, 0 / 255.0f, 1);
            shapeRenderer.rect(655,110,140,50);*/
            //AssetLoader.shadow.draw(batcher, "SCORE", 700, 65);
            AssetLoader.font.draw(batcher, "SCORE:", 650, 64);
            AssetLoader.font.draw(batcher, "COMBO:", 650, 135);
            AssetLoader.font.getData().setScale(0.4f,-0.4f);
            AssetLoader.font.draw(batcher,""+myWorld.getScore(), 650,100);
            AssetLoader.font.draw(batcher,myWorld.getCombo()+"X", 650, 171);
            AssetLoader.font.getData().setScale(0.5f,-0.5f);
            batcher.draw(sp_glass, 650, 352, 150, 128);
            batcher.draw(alienAnimation.getKeyFrame(runTime), 680,370,90,110);
            if (myWorld.isGamePowerMode())
            {
                for (int i=0;i<myPowerBall.size();i++)
                    batcher.draw(waterBallAnimation.getKeyFrame(runTime),myPowerBall.get(i).getPosition_x(), myPowerBall.get(i).getPosition_y(),30,32);
            }
            batcher.end();

            shapeRenderer.begin(ShapeType.Filled);
            shapeRenderer.setColor(66 / 255.0f, 68 / 255.0f, 66 / 255.0f, 1);
            shapeRenderer.rect(655,210,140, 120);

            if (!myWorld.isGamePowerMode()) {
                if (mypowerProgress.getWidth() < 140) {//Power mode is in cooldown period
                    shapeRenderer.setColor(146 / 255.0f, 15 / 255.0f, 15 / 255.0f, 1);
                    shapeRenderer.rect(655, 210, mypowerProgress.getWidth(), 120);
                } else {//Power mode can be activated
                    shapeRenderer.setColor(53 / 255.0f, 176 / 255.0f, 36 / 255.0f, 1);
                    shapeRenderer.rect(655, 210, 140, 120);
                    cur_PowerMode=AssetLoader.activatePower;
                }
            }
            shapeRenderer.end();
            batcher.begin();
            batcher.draw(cur_PowerMode,655,210,140,120);
            batcher.end();

            if (myWorld.isGamePaused())
                drawPausedUI();

        }
        else
        if (myWorld.isGameOver()) {
            batcher.begin();
            AssetLoader.font.getData().setScale(1f,-1f);
            AssetLoader.font.draw(batcher, "Game Over", 224, 95);
            AssetLoader.font.draw(batcher, "Try again?", 224, 155);
            batcher.end();
        }
        else if (myWorld.isGameMenu())
        {
            batcher.begin();
            batcher.draw(background, mybg1.getX(), mybg1.getY(), mybg1.getWidth(), mybg1.getHeight());
            batcher.draw(background, mybg2.getX(), mybg2.getY(), mybg2.getWidth(), mybg2.getHeight());
            batcher.draw(eWar, 30,50,740,90);
            batcher.draw(cur_play,340, 300, 100, 100);
            batcher.end();
        }
    }

    private void drawPausedUI() {

        shapeRenderer.begin(ShapeType.Filled);
        shapeRenderer.setColor(0,0,0,0.5f);
        shapeRenderer.rect(0,0,800,480);
        shapeRenderer.end();

        batcher.begin();
        batcher.draw(cur_play, 200, 120,200,200);
        batcher.draw(cur_restart,440,120, 200,200);
        batcher.end();
    }

    private void drawTileArray()
    {
        for (int i = 0; i < 15 ; i++) {
            for (int j = 0; j < 10; j++) {
                if (mytile_array[i][j] == 0)
                    continue;
                temp = mytile_array[i][j];
                x = j * 60;
                y = i * 32;
                switch (temp) {
                    case 1:
                        batcher.draw(air_tile, x, y, 60, 32);
                        break;
                    case 2:
                        batcher.draw(water_tile, x, y, 60, 32);
                        break;
                    case 3:
                        batcher.draw(fire_tile, x, y, 60, 32);
                        break;
                    case 4:
                        batcher.draw(earth_tile, x, y, 60, 32);
                        break;
                    case 5:
                        batcher.draw(air_tile_glow, x, y, 60, 32);
                        break;
                    case 6:
                        batcher.draw(water_tile_glow, x, y, 60, 32);
                        break;
                    case 7:
                        batcher.draw(fire_tile_glow, x, y, 60, 32);
                        break;
                    case 8:
                        batcher.draw(earth_tile_glow, x, y, 60, 32);
                        break;
                }
            }
        }
    }

    private void drawStackTile()
    {
        for (int i = 0; i < mySpaceship.getStackSize(); i++) {
            y -= 32;
            switch (myStackTile[i]) {
                case 1:
                    batcher.draw(air_tile, x, y, 60, 32);
                    break;
                case 2:
                    batcher.draw(water_tile, x, y, 60, 32);
                    break;
                case 3:
                    batcher.draw(fire_tile, x, y, 60, 32);
                    break;
                case 4:
                    batcher.draw(earth_tile, x, y, 60, 32);
                    break;
            }
        }
    }
}
