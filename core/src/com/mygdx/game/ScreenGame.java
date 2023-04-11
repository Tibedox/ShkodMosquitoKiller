package com.mygdx.game;

import static com.mygdx.game.MyGame.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;

public class ScreenGame implements Screen {
    // ссылка на главный класс игры
    MyGame mg;

    // ресуры (изображения и звуки)
    Texture imgBG;
    Texture[] imgMosq = new Texture[11];
    Texture imgPause, imgPlay;
    Sound[] sndMosq = new Sound[25];

    // наши переменные и объекты
    Mosquito[] mosq = new Mosquito[5];
    Player[] players = new Player[6];
    int frags;
    long time, timeStartGame, timePause;

    // кнопки
    TextButton btnBack;
    ImageButton btnPause;

    // состояния игры
    public static final int PLAY_GAME = 0, ENTER_NAME = 1, SHOW_RECORDS = 2;
    int stateOfGame = PLAY_GAME;
    boolean pause;

    public ScreenGame(MyGame myGame) {
        mg = myGame;
        imgBG = new Texture("landscape.jpg");
        imgPause = new Texture("pause.png");
        imgPlay = new Texture("play.png");
        for (int i = 0; i < imgMosq.length; i++) {
            imgMosq[i] = new Texture("mosq"+i+".png");
        }
        for (int i = 0; i < sndMosq.length; i++) {
            sndMosq[i] = Gdx.audio.newSound(Gdx.files.internal("sound/mosq"+i+".mp3"));
        }

        for (int i = 0; i < players.length; i++) {
            players[i] = new Player("Noname", 0);
        }

        btnPause = new ImageButton(imgPause, SCR_WIDTH/2, SCR_HEIGHT-5-70f/2, 70, 70);
        btnBack = new TextButton("Back", font, SCR_WIDTH-80, 50);

        loadTableOfRecords();
    }

    @Override
    public void show() {
        for (int i = 0; i < mosq.length; i++) {
            mosq[i] = new Mosquito();
        }
        time = 0;
        frags = 0;
        timeStartGame = TimeUtils.millis();
        stateOfGame = PLAY_GAME;
    }

    @Override
    public void render(float delta) {
// ----------- обработка касаний (или кликов) ----------------------
        if(Gdx.input.justTouched()){
            touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touch);
            if(stateOfGame == PLAY_GAME) {
                if(!pause) {
                    for (int i = mosq.length - 1; i >= 0; i--) {
                        if (mosq[i].hit(touch.x, touch.y) && mosq[i].isAlive) {
                            mosq[i].kill();
                            if(mg.isSoundOn) {
                                sndMosq[MathUtils.random(0, 4)].play();
                            }
                            frags++;
                            // если сбиты все комары, состояние игры переключается на ввод имени
                            if (frags == mosq.length) {
                                stateOfGame = ENTER_NAME;
                            }
                            break;
                        }
                    }
                }
                if(btnBack.hit(touch.x, touch.y)) {
                    mg.setScreen(mg.screenIntro);
                }
                if(btnPause.hit(touch.x, touch.y)){
                    pause = !pause;

					/*if(pause) btnPause.img = imgPlay;
					else btnPause.img = imgPause;*/
                    btnPause.img = pause ? imgPlay : imgPause;
                }
            } else if(stateOfGame == ENTER_NAME) {
                // если завершён ввод имени, состояние игры переключается на показ таблицы рекордов
                if(keyboard.endOfEdit(touch.x, touch.y)){
                    players[players.length-1].name = keyboard.getText();
                    players[players.length-1].time = time;
                    sortTableOfRecords();
                    saveTableOfRecords();
                    stateOfGame = SHOW_RECORDS;
                }
            } else if(stateOfGame == SHOW_RECORDS) {
                mg.setScreen(mg.screenIntro);
            }
        }
        //------------------------------------------------------------------

        // ---------------------- игровые события --------------------------
        if(!pause) {
            if (stateOfGame == PLAY_GAME) {
                time = TimeUtils.millis() - timeStartGame - timePause;
            }
            for (int i = 0; i < mosq.length; i++) {
                mosq[i].fly();
            }
        } else {
            if (stateOfGame == PLAY_GAME) {
                timePause = TimeUtils.millis() - timeStartGame - time;
            }
        }
        //------------------------------------------------------------------

        // ------------ отрисовка всех изображений -------------------------
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        // фон
        batch.draw(imgBG, 0, 0, SCR_WIDTH, SCR_HEIGHT);
        // комары
        for (int i = 0; i < mosq.length; i++) {
            batch.draw(imgMosq[mosq[i].phase], mosq[i].scrX(), mosq[i].scrY(), mosq[i].width, mosq[i].height, 0, 0, 500, 500, mosq[i].flip(), false);
        }
        // тексты
        font.draw(batch, "FRAGS: "+frags, 10, SCR_HEIGHT-10);
        font.draw(batch, "TIME: "+timeToString(time), SCR_WIDTH-300, SCR_HEIGHT-10);
        batch.draw(btnPause.img, btnPause.scrX(), btnPause.scrY(), btnPause.width, btnPause.height);
        btnBack.font.draw(batch, btnBack.text, btnBack.scrX(), btnBack.scrY());
        if(stateOfGame == ENTER_NAME){
            keyboard.draw(batch);
        }
        if(stateOfGame == SHOW_RECORDS){
            // выводим таблицу рекордов
            for (int i = 0; i < players.length-1; i++) {
                font.draw(batch, players[i].name+" .... "+timeToString(players[i].time), 500, 600-80*i);
            }
        }
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
        imgPause.dispose();
        imgPlay.dispose();
        for (int i = 0; i < imgMosq.length; i++) {
            imgMosq[i].dispose();
        }
        for (int i = 0; i < sndMosq.length; i++) {
            sndMosq[i].dispose();
        }
    }

    String timeToString(long time){
        long s = time/1000;
        long m = s/60;
        long h = m/60;
        m = m%60;
        s = s%60%60;
        return h+":"+m/10+m%10+":"+s/10+s%10;
    }

    void sortTableOfRecords(){
        for (int i = 0; i < players.length; i++) if(players[i].time == 0) players[i].time = 1000000;

        for(int j = 0; j < players.length; j++) {
            for (int i = 0; i < players.length - 1; i++) {
                if (players[i].time > players[i + 1].time) {
                    Player z = players[i];
                    players[i] = players[i + 1];
                    players[i + 1] = z;
                }
            }
        }

        for (int i = 0; i < players.length; i++) if(players[i].time == 1000000) players[i].time = 0;
    }

    void saveTableOfRecords(){
        Preferences prefs = Gdx.app.getPreferences("ShkodTableOfRecords");
        for (int i = 0; i < players.length; i++) {
            prefs.putString("name"+i, players[i].name);
            prefs.putLong("time"+i, players[i].time);
        }
        prefs.flush();
    }

    void loadTableOfRecords(){
        Preferences prefs = Gdx.app.getPreferences("ShkodTableOfRecords");
        for (int i = 0; i < players.length; i++) {
            if(prefs.contains("name"+i)) players[i].name = prefs.getString("name"+i);
            if(prefs.contains("time"+i)) players[i].time = prefs.getLong("time"+i);
        }
    }

    void clearTableOfRecords(){
        for (int i = 0; i < players.length; i++) {
            players[i].name = "Noname";
            players[i].time = 0;
        }
    }
}
