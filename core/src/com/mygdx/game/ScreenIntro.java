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
        btnPlay = new TextButton("Play", fontLarge, SCR_WIDTH/2, 500);
        btnSettings = new TextButton("Settings", fontLarge, SCR_WIDTH/2, 400);
        btnAbout = new TextButton("About", fontLarge, SCR_WIDTH/2, 300);
        btnExit = new TextButton("Exit", fontLarge, SCR_WIDTH/2, 200);
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
            if(btnPlay.hit(touch.x, touch.y)) {
                mg.setScreen(mg.screenGame);
            }
            if(btnSettings.hit(touch.x, touch.y)) {
                mg.setScreen(mg.screenSettings);
            }
            if(btnAbout.hit(touch.x, touch.y)) {
                mg.setScreen(mg.screenAbout);
            }
            if(btnExit.hit(touch.x, touch.y)) {
                Gdx.app.exit();
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
        btnPlay.font.draw(batch, btnPlay.text, btnPlay.scrX(), btnPlay.scrY());
        btnSettings.font.draw(batch, btnSettings.text, btnSettings.scrX(), btnSettings.scrY());
        btnAbout.font.draw(batch, btnAbout.text, btnAbout.scrX(), btnAbout.scrY());
        btnExit.font.draw(batch, btnExit.text, btnExit.scrX(), btnExit.scrY());
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
}
