package com.mygdx.game;

import static com.mygdx.game.MyGame.SCR_HEIGHT;
import static com.mygdx.game.MyGame.SCR_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;


public class ScreenSettings implements Screen {
    // ссылка на главный класс игры
    MyGame mg;

    // ресуры (изображения и звуки)
    Texture imgBG;

    // кнопки
    TextButton btnSound;
    TextButton btnMusic;
    TextButton btnClearRecords;
    TextButton btnBack;

    Music music;

    public ScreenSettings(MyGame myGame) {
        mg = myGame;
        imgBG = new Texture("landscape01.jpg");
        music = Gdx.audio.newMusic(Gdx.files.internal("sound/mrsandman.mp3"));

        music.setLooping(true);

        btnSound = new TextButton("Sound On", mg.fontLarge, SCR_WIDTH/2, 550);
        btnMusic = new TextButton("Music On", mg.fontLarge, SCR_WIDTH/2, 450);
        btnClearRecords = new TextButton("Clear Records", mg.fontLarge, SCR_WIDTH/2, 350);

        btnBack = new TextButton("Back", mg.fontLarge, SCR_WIDTH/2, 100);
        loadSettings();
        updateButtons();
        if(mg.isMusicOn) {
            music.play();
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        // ----------- обработка касаний (или кликов) ----------------------
        if(Gdx.input.justTouched()){
            mg.touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            mg.camera.unproject(mg.touch);
            if(btnSound.hit(mg.touch.x, mg.touch.y)) {
                mg.isSoundOn = !mg.isSoundOn;
                updateButtons();
            }
            if(btnMusic.hit(mg.touch.x, mg.touch.y)) {
                mg.isMusicOn = !mg.isMusicOn;
                if(mg.isMusicOn) {
                    music.play();
                } else {
                    music.stop();
                }
                updateButtons();
            }
            if(btnClearRecords.hit(mg.touch.x, mg.touch.y)) {
                btnClearRecords.updateText("Records cleared");
                mg.screenGame.clearTableOfRecords();
            }
            if(btnBack.hit(mg.touch.x, mg.touch.y)) {
                btnClearRecords.updateText("Clear Records");
                mg.setScreen(mg.screenIntro);
                saveSettings();
            }
        }
        //------------------------------------------------------------------

        // ---------------------- игровые события --------------------------
        //-------
        //------------------------------------------------------------------

        // ------------ отрисовка всех изображений -------------------------
        mg.camera.update();
        mg.batch.setProjectionMatrix(mg.camera.combined);
        mg.batch.begin();
        mg.batch.draw(imgBG, 0, 0, SCR_WIDTH, SCR_HEIGHT); // фон
        btnSound.font.draw(mg.batch, btnSound.text, btnSound.scrX(), btnSound.scrY());
        btnMusic.font.draw(mg.batch, btnMusic.text, btnMusic.scrX(), btnMusic.scrY());
        btnClearRecords.font.draw(mg.batch, btnClearRecords.text, btnClearRecords.scrX(), btnClearRecords.scrY());
        btnBack.font.draw(mg.batch, btnBack.text, btnBack.scrX(), btnBack.scrY());
        mg.batch.end();
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
            btnSound.updateText("Sound On");
        } else {
            btnSound.updateText("Sound Off");
        }
        if(mg.isMusicOn) {
            btnMusic.updateText("Music On");
        } else {
            btnMusic.updateText("Music Off");
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
