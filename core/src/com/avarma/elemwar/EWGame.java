package com.avarma.elemwar;

import com.avarma.ewhelpers.AssetLoader;
import com.avarma.screens.GameScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class EWGame extends Game {
	@Override
	public void create() {
		Gdx.app.log("EWGame", "created");
		AssetLoader.load();
		setScreen(new GameScreen());
	}
	@Override
	public void dispose()
	{
		super.dispose();
		AssetLoader.dispose();
	}
}
