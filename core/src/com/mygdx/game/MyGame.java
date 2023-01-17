package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class MyGame extends ApplicationAdapter {
	public static final float SCR_WIDTH = 1280, SCR_HEIGHT = 720;

	SpriteBatch batch;
	Texture imgBG;
	Texture imgMosquito;

	Mosquito[] mosq = new Mosquito[10];
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		imgBG = new Texture("landscape.jpg");
		imgMosquito = new Texture("mosquito.png");
		for (int i = 0; i < mosq.length; i++) {
			mosq[i] = new Mosquito();
		}
	}

	@Override
	public void render () {
		// отрисовка всех изображений
		batch.begin();
		// фон
		batch.draw(imgBG, 0, 0, SCR_WIDTH, SCR_HEIGHT);
		// комары
		for (int i = 0; i < mosq.length; i++) {
			batch.draw(imgMosquito, mosq[i].x, mosq[i].y, mosq[i].width, mosq[i].height);
		}
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		imgBG.dispose();
		imgMosquito.dispose();
	}
}
