package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

public class MyGame extends ApplicationAdapter {
	public static final float SCR_WIDTH = 1280, SCR_HEIGHT = 720;

	SpriteBatch batch;
	OrthographicCamera camera;
	Vector3 touch;

	Texture imgBG;
	Texture imgMosquito;
	Texture[] imgMosq = new Texture[11];

	Mosquito[] mosq = new Mosquito[110];
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, SCR_WIDTH, SCR_HEIGHT);
		touch = new Vector3();

		imgBG = new Texture("landscape.jpg");
		imgMosquito = new Texture("mosquito.png");
		for (int i = 0; i < imgMosq.length; i++) {
			imgMosq[i] = new Texture("mosq"+i+".png");
		}

		for (int i = 0; i < mosq.length; i++) {
			mosq[i] = new Mosquito();
		}
	}

	@Override
	public void render () {
		// обработка касаний (или кликов)
		if(Gdx.input.justTouched()){
			touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touch);
			for (int i = 0; i < mosq.length; i++) {
				if(mosq[i].hit(touch.x, touch.y)) {
					mosq[i].kill();
					break;
				}
			}
		}

		// игровые события
		for (int i = 0; i < mosq.length; i++) {
			mosq[i].fly();
		}

		// отрисовка всех изображений
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		// фон
		batch.draw(imgBG, 0, 0, SCR_WIDTH, SCR_HEIGHT);
		// комары
		for (int i = 0; i < mosq.length; i++) {
			batch.draw(imgMosq[mosq[i].phase], mosq[i].scrX(), mosq[i].scrY(), mosq[i].width, mosq[i].height, 0, 0, 500, 500, mosq[i].flip(), false);
		}
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		imgBG.dispose();
		imgMosquito.dispose();
		for (int i = 0; i < imgMosq.length; i++) {
			imgMosq[i].dispose();
		}
	}
}
