package com.mygdx.game;

import com.badlogic.gdx.math.MathUtils;

public class Mosquito {
    private float x, y;
    float width, height;
    private float vx, vy;
    int phase;
    private int nPhases = 10;
    boolean isAlive;

    Mosquito() {
        x = MathUtils.random(0, MyGame.SCR_WIDTH);
        y = MathUtils.random(0, MyGame.SCR_HEIGHT);
        width = height = MathUtils.random(80, 200);
        vx = MathUtils.random(-5f, 5);
        vy = MathUtils.random(-5f, 5);
        isAlive = true;
    }

    void fly(){
        x += vx;
        y += vy;
        if(isAlive) {
            outBounds();
            changePhase();
        }
    }

    private void changePhase(){
        if(++phase == nPhases) phase = 0;
        //phase = ++phase%nPhases;
    }

    float scrX(){
        return x-width/2;
    }

    float scrY(){
        return y-height/2;
    }

    boolean flip(){
       if(vx>0) return true;
       return false;
    }

    private void outBounds(){
        if(x<0-width/2) x = MyGame.SCR_WIDTH;
        if(x>MyGame.SCR_WIDTH+width/2) x = 0;
        if(y<0-height/2) y = MyGame.SCR_HEIGHT;
        if(y>MyGame.SCR_HEIGHT+height/2) y = 0;
    }

    boolean hit(float tx, float ty){
        return x-width/2<tx && tx<x+width/2 && y-height/2<ty && ty<y+height/2;
    }

    void kill(){
        vx = 0;
        vy = -6;
        phase = 10;
        isAlive = false;
    }
}
