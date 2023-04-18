package com.mygdx.game;

import static com.mygdx.game.MyGame.SCR_HEIGHT;
import static com.mygdx.game.MyGame.SCR_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;


public class ScreenAbout implements Screen {
    // ссылка на главный класс игры
    MyGame mg;

    // ресуры (изображения и звуки)
    Texture imgBG;

    // кнопки
    TextButton btnBack;

    // текст
    String textAbout =  "Это клёвая игрушка в комариков. Комары каким-то образом\n" +
                        "оказались зимой в лесу. Это злые комары-мутанты.\n" +
                        "Надо их посбивать, а то всем кранты! Спасайте мир!";

    public ScreenAbout(MyGame myGame) {
        mg = myGame;
        imgBG = new Texture("landscape03.jpg");

        btnBack = new TextButton("Back", mg.fontLarge, SCR_WIDTH/2, 200);
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

            if(btnBack.hit(mg.touch.x, mg.touch.y)) {
                mg.setScreen(mg.screenIntro);
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
        mg.font.draw(mg.batch, textAbout, 50, SCR_HEIGHT-50);
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

}
