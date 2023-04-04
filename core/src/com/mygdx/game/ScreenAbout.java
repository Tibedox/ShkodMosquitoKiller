package com.mygdx.game;

import static com.mygdx.game.MyGame.SCR_HEIGHT;
import static com.mygdx.game.MyGame.SCR_WIDTH;
import static com.mygdx.game.MyGame.batch;
import static com.mygdx.game.MyGame.camera;
import static com.mygdx.game.MyGame.fontLarge;
import static com.mygdx.game.MyGame.touch;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;


public class ScreenAbout implements Screen {
    // ссылка на главный класс игры
    MyGame mg;

    // ресуры (изображения и звуки)
    Texture imgBG;

    // кнопки
    TextButton btnSound;
    TextButton btnMusic;
    TextButton btnBack;

    public ScreenAbout(MyGame myGame) {
        mg = myGame;
        imgBG = new Texture("landscape01.jpg");
        btnSound = new TextButton("Sound On", fontLarge, SCR_WIDTH/2, 500);
        btnMusic = new TextButton("Music On", fontLarge, SCR_WIDTH/2, 400);

        btnBack = new TextButton("Back", fontLarge, SCR_WIDTH/2, 200);
        loadSettings();
        updateButtons();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        // ----------- обработка касаний (или кликов) ----------------------
        if(Gdx.input.justTouched()){
            touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touch);
            if(btnSound.hit(touch.x, touch.y)) {
                mg.isSoundOn = !mg.isSoundOn;
                updateButtons();
            }
            if(btnMusic.hit(touch.x, touch.y)) {
                mg.isMusicOn = !mg.isMusicOn;
                updateButtons();
            }
            if(btnBack.hit(touch.x, touch.y)) {
                mg.setScreen(mg.screenIntro);
                saveSettings();
            }
        }
        //------------------------------------------------------------------

        // ---------------------- игровые события --------------------------
        //-------
        //------------------------------------------------------------------

        // ------------ отрисовка всех изображений -------------------------
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(imgBG, 0, 0, SCR_WIDTH, SCR_HEIGHT); // фон
        btnSound.font.draw(batch, btnSound.text, btnSound.scrX(), btnSound.scrY());
        btnMusic.font.draw(batch, btnMusic.text, btnMusic.scrX(), btnMusic.scrY());
        btnBack.font.draw(batch, btnBack.text, btnBack.scrX(), btnBack.scrY());
        batch.end();
        //------------------------------------------------------------------
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        imgBG.dispose();
    }

    void updateButtons() {
        if(mg.isSoundOn) {
            btnSound.text = "Sound On";
        } else {
            btnSound.text = "Sound Off";
        }
        if(mg.isMusicOn) {
            btnMusic.text = "Music On";
        } else {
            btnMusic.text = "Music Off";
        }
    }

    void saveSettings() {
        Preferences prefs = Gdx.app.getPreferences("ShkodSettings");
        prefs.putBoolean("sound", mg.isSoundOn);
        prefs.putBoolean("music", mg.isMusicOn);
        prefs.flush();
    }

    void loadSettings() {
        Preferences prefs = Gdx.app.getPreferences("ShkodSettings");
        if(prefs.contains("sound")) mg.isSoundOn = prefs.getBoolean("sound");
        if(prefs.contains("music")) mg.isMusicOn = prefs.getBoolean("music");
    }
}
