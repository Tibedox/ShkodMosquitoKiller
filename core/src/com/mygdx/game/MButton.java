package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

public class MButton {
    float x, y;
    float width, height;
    Texture img;

    public MButton(Texture img, float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.img = img;
    }

    float scrX(){
        return x-width/2;
    }

    float scrY(){
        return y-height/2;
    }

    boolean hit(float tx, float ty){
        return x-width/2<tx && tx<x+width/2 && y-height/2<ty && ty<y+height/2;
    }
}
