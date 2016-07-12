package com.example.gabriel.spydroids;

import android.graphics.Rect;

/**
 * Created by Gabriel on 13/04/2016.
 */
public class GameObject {
    protected int x;
    protected int y;
    protected int dy;
    protected int dx;
    protected int width;
    protected int height;


    public void setX(int x){
        this.x = x;
    }
    public int getX(){
        return x;
    }

    public void setY(int y){
        this.y = y;
    }
    public int getY(){
        return y;
    }


    public int getHeight(){
        return height;
    }
    public int getWidth(){
        return width;
    }

    public Rect getRectangle(){
        return new Rect(x, y, x+width, y+height);
    }
}
