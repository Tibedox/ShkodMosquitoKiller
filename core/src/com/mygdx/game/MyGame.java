package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class MyGame extends ApplicationAdapter {
	public static final float SCR_WIDTH = 1280, SCR_HEIGHT = 720;

	SpriteBatch batch;
	Texture imgBG;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		imgBG = new Texture("landscape.jpg");
	}

	@Override
	public void render () {
		batch.begin();
		batch.draw(imgBG, 0, 0, SCR_WIDTH, SCR_HEIGHT);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		imgBG.dispose();
	}
}
