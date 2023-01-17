package com.mygdx.game;

import com.badlogic.gdx.math.MathUtils;

public class Mosquito {
    float x, y;
    float width, height;
    float vx, vy;

    Mosquito() {
        x = MathUtils.random(0, MyGame.SCR_WIDTH);
        y = MathUtils.random(0, MyGame.SCR_HEIGHT);
        width = height = MathUtils.random(100, 200);
        vx = MathUtils.random(-10f, 10);
        vy = MathUtils.random(-10f, 10);
    }

    void fly(){
        x += vx;
        y += vy;
        outBounds();
    }

    void outBounds(){
        if(x<0) x = MyGame.SCR_WIDTH;
        if(x>MyGame.SCR_WIDTH) x = 0;
        if(y<0) y = MyGame.SCR_HEIGHT;
        if(y>MyGame.SCR_HEIGHT) y = 0;
    }
}
