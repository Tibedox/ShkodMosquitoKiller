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
    }
}
