package com.avarma.ewhelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by abhishek on 8/14/2016.
 */
public class AssetLoader {

    public static Texture texture;
    public static TextureRegion eWar, water_sp, sp_glass, background, progressFront, pause, pause_shadow, play, play_shadow, restart, restart_shadow;
    public static TextureRegion fire_tile, water_tile, air_tile, earth_tile,fire_tile_glow, water_tile_glow, air_tile_glow, earth_tile_glow;
    public static TextureRegion water_alien1,water_alien2,water_alien3,water_alien4;
    public static TextureRegion waterBall1, waterBall2, waterBall3, waterBall4, waterBall5;
    public static TextureRegion superPower, cooldownPower, activatePower;
    public static Animation alienAnimation, waterBallAnimation;
    public static BitmapFont font,shadow;
    public static Sound click, explode, suck, shoot, collide, game_over;
    public static Music run_music;
    public static void load()
    {
        texture=new Texture(Gdx.files.internal("data/elem_war_TEXT.png"));
        texture.setFilter(TextureFilter.Nearest,TextureFilter.Nearest);

        eWar=new TextureRegion(texture,0,0,656,86);
        eWar.flip(false,true);

        texture=new Texture(Gdx.files.internal("data/water-min.png"));
        texture.setFilter(TextureFilter.Nearest,TextureFilter.Nearest);

        water_sp=new TextureRegion(texture,0,0,220,218);
        water_sp.flip(false,true);

        texture=new Texture(Gdx.files.internal("data/texture.png"));
        texture.setFilter(TextureFilter.Nearest,TextureFilter.Nearest);

        background=new TextureRegion(texture,0,240,599,479);
        background.flip(false,true);


        fire_tile=new TextureRegion(texture, 0,0,209,59);
        fire_tile.flip(false,true);

        air_tile=new TextureRegion(texture, 210,0,209,59);
        air_tile.flip(false,true);

        water_tile=new TextureRegion(texture, 0,60,209,59);
        water_tile.flip(false,true);

        earth_tile=new TextureRegion(texture, 210,60,209,59);
        earth_tile.flip(false,true);


        fire_tile_glow=new TextureRegion(texture, 0,120,209,59);
        fire_tile_glow.flip(false,true);

        air_tile_glow=new TextureRegion(texture, 210,120,209,59);
        air_tile_glow.flip(false,true);

        water_tile_glow=new TextureRegion(texture, 0,180,209,59);
        water_tile_glow.flip(false,true);

        earth_tile_glow=new TextureRegion(texture, 210,180,209,59);
        earth_tile_glow.flip(false,true);

        texture=new Texture(Gdx.files.internal("data/lvl_body-min.png"));
        texture.setFilter(TextureFilter.Nearest,TextureFilter.Nearest);
        progressFront=new TextureRegion(texture,0,0,20,920);

        texture=new Texture(Gdx.files.internal("data/buttons.png"));
        texture.setFilter(TextureFilter.Nearest,TextureFilter.Nearest);
        pause=new TextureRegion(texture,0,0,240,235);
        pause.flip(false,true);
        pause_shadow=new TextureRegion(texture,0,235,240,235);
        pause_shadow.flip(false,true);
        play=new TextureRegion(texture,476,0,234,230);
        play.flip(false,true);
        play_shadow=new TextureRegion(texture,473,230,235,240);
        play_shadow.flip(false,true);
        restart=new TextureRegion(texture,240,0,236,235);
        restart.flip(false,true);
        restart_shadow=new TextureRegion(texture,238,239,238,230);
        restart_shadow.flip(false,true);

        texture=new Texture(Gdx.files.internal("data/alien_water1-min.png"));
        texture.setFilter(TextureFilter.Nearest,TextureFilter.Nearest);
        water_alien1=new TextureRegion(texture,0,0,205,312);
        water_alien1.flip(false,true);
        texture=new Texture(Gdx.files.internal("data/alien_water2-min.png"));
        texture.setFilter(TextureFilter.Nearest,TextureFilter.Nearest);
        water_alien2=new TextureRegion(texture,0,0,205,312);
        water_alien2.flip(false,true);
        texture=new Texture(Gdx.files.internal("data/alien_water3-min.png"));
        texture.setFilter(TextureFilter.Nearest,TextureFilter.Nearest);
        water_alien3=new TextureRegion(texture,0,0,205,312);
        water_alien3.flip(false,true);
        texture=new Texture(Gdx.files.internal("data/alien_water4-min.png"));
        texture.setFilter(TextureFilter.Nearest,TextureFilter.Nearest);
        water_alien4=new TextureRegion(texture,0,0,205,312);
        water_alien4.flip(false,true);
        TextureRegion[] alien={water_alien1,water_alien1, water_alien1, water_alien1,water_alien1, water_alien1,water_alien2,water_alien3, water_alien4};
        alienAnimation=new Animation(0.2f, alien);
        alienAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        texture=new Texture(Gdx.files.internal("data/spaceship_glass-min.png"));
        texture.setFilter(TextureFilter.Nearest,TextureFilter.Nearest);
        sp_glass=new TextureRegion(texture,0,0,246,186);
        sp_glass.flip(false,true);

        click=Gdx.audio.newSound(Gdx.files.internal("data/click.wav"));
        explode=Gdx.audio.newSound(Gdx.files.internal("data/explode.wav"));
        shoot=Gdx.audio.newSound(Gdx.files.internal("data/shoot up.wav"));
        suck=Gdx.audio.newSound(Gdx.files.internal("data/suck in.wav"));
        game_over=Gdx.audio.newSound(Gdx.files.internal("data/game over.wav"));
        run_music=Gdx.audio.newMusic(Gdx.files.internal("data/Suddenly_(game runs).wav"));

        run_music.setLooping(true);

        texture=new Texture(Gdx.files.internal("data/waterball1-min.png"));
        texture.setFilter(TextureFilter.Nearest,TextureFilter.Nearest);
        waterBall1=new TextureRegion(texture,0,0,55,108);
        waterBall1.flip(false,true);
        texture=new Texture(Gdx.files.internal("data/waterball2-min.png"));
        texture.setFilter(TextureFilter.Nearest,TextureFilter.Nearest);
        waterBall2=new TextureRegion(texture,0,0,55,108);
        waterBall2.flip(false,true);
        texture=new Texture(Gdx.files.internal("data/waterball3-min.png"));
        texture.setFilter(TextureFilter.Nearest,TextureFilter.Nearest);
        waterBall3=new TextureRegion(texture,0,0,55,108);
        waterBall3.flip(false,true);
        texture=new Texture(Gdx.files.internal("data/waterball4-min.png"));
        texture.setFilter(TextureFilter.Nearest,TextureFilter.Nearest);
        waterBall4=new TextureRegion(texture,0,0,55,108);
        waterBall4.flip(false,true);
        texture=new Texture(Gdx.files.internal("data/waterball5-min.png"));
        texture.setFilter(TextureFilter.Nearest,TextureFilter.Nearest);
        waterBall5=new TextureRegion(texture,0,0,55,108);
        waterBall5.flip(false,true);

        texture=new Texture(Gdx.files.internal("data/Power_mode_combined.png"));
        texture.setFilter(TextureFilter.Nearest,TextureFilter.Nearest);
        activatePower=new TextureRegion(texture,0,0,140,120);
        activatePower.flip(false,true);
        superPower=new TextureRegion(texture,0,120,140,120);
        superPower.flip(false,true);
        cooldownPower=new TextureRegion(texture,0,240,140,120);
        cooldownPower.flip(false,true);

        TextureRegion[] waterB={waterBall1, waterBall2, water_alien3,water_alien4,waterBall5};
        waterBallAnimation=new Animation(0.05f, waterB);
        waterBallAnimation.setPlayMode(Animation.PlayMode.LOOP);

        font = new BitmapFont(Gdx.files.internal("data/text.fnt"));
        font.getData().setScale(1f, -1f);
        //shadow = new BitmapFont(Gdx.files.internal("data/shadow.fnt"));
        //shadow.getData().setScale(1f, -1f);


    }

    public static void dispose()
    {
        texture.dispose();
        font.dispose();
        //shadow.dispose();
        run_music.dispose();
        click.dispose();
        explode.dispose();
        shoot.dispose();
        suck.dispose();
        game_over.dispose();
    }
}