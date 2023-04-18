package com.mygdx.game;

import static com.mygdx.game.MyGame.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;


public class ScreenIntro implements Screen {
    // ссылка на главный класс игры
    MyGame mg;

    // ресуры (изображения и звуки)
    Texture imgBG;

    // кнопки
    TextButton btnPlay;
    TextButton btnSettings;
    TextButton btnAbout;
    TextButton btnExit;

    public ScreenIntro(MyGame myGame) {
        mg = myGame;
        imgBG = new Texture("landscape02.jpg");
        btnPlay = new TextButton("Play", mg.fontLarge, SCR_WIDTH/2, 500);
        btnSettings = new TextButton("Settings", mg.fontLarge, SCR_WIDTH/2, 400);
        btnAbout = new TextButton("About", mg.fontLarge, SCR_WIDTH/2, 300);
        btnExit = new TextButton("Exit", mg.fontLarge, SCR_WIDTH/2, 200);
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
            if(btnPlay.hit(mg.touch.x, mg.touch.y)) {
                mg.setScreen(mg.screenGame);
            }
            if(btnSettings.hit(mg.touch.x, mg.touch.y)) {
                mg.setScreen(mg.screenSettings);
            }
            if(btnAbout.hit(mg.touch.x, mg.touch.y)) {
                mg.setScreen(mg.screenAbout);
            }
            if(btnExit.hit(mg.touch.x, mg.touch.y)) {
                Gdx.app.exit();
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
        btnPlay.font.draw(mg.batch, btnPlay.text, btnPlay.scrX(), btnPlay.scrY());
        btnSettings.font.draw(mg.batch, btnSettings.text, btnSettings.scrX(), btnSettings.scrY());
        btnAbout.font.draw(mg.batch, btnAbout.text, btnAbout.scrX(), btnAbout.scrY());
        btnExit.font.draw(mg.batch, btnExit.text, btnExit.scrX(), btnExit.scrY());
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
}
