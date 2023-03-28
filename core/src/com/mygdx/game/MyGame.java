package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;

public class MyGame extends Game {
	// задаём константы ширину и высоту экрана
	public static final float SCR_WIDTH = 1280, SCR_HEIGHT = 720;

	// ссылки на системные объекты
	static SpriteBatch batch;
	static OrthographicCamera camera;
	static Vector3 touch;
	static BitmapFont font, fontLarge;
	static InputKeyboard keyboard;

	// ссылки на экраны
	ScreenIntro screenIntro;
	ScreenSettings screenSettings;
	ScreenGame screenGame;

	@Override
	public void create () {
		// создаём системные объекты
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, SCR_WIDTH, SCR_HEIGHT);
		touch = new Vector3();
		generateFonts();
		keyboard = new InputKeyboard(SCR_WIDTH, SCR_HEIGHT, 10);

		// создаём экраны
		screenIntro = new ScreenIntro(this);
		screenSettings = new ScreenSettings(this);
		screenGame = new ScreenGame(this);
		setScreen(screenIntro);
	}
	
	@Override
	public void dispose () {
		// очищаем память
		batch.dispose();
		font.dispose();
		keyboard.dispose();
	}

	void generateFonts(){
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("windsor.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 60;
		parameter.color = Color.valueOf("ebdf73ff");
		parameter.borderWidth = 2;
		parameter.borderColor = Color.BLACK;
		parameter.characters = "абвгдеёжзийклмнопрстуфхцчшщъыьэюяabcdefghijklmnopqrstuvwxyzАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;:,{}\"´`'<>";;
		font = generator.generateFont(parameter);
		parameter.size = 100;
		fontLarge = generator.generateFont(parameter);
		generator.dispose();
	}
}
