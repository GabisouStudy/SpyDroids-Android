package com.example.gabriel.spydroids;

import android.graphics.Rect;

/**
 * Created by Gabriel on 13/04/2016.
 */
public class GameObject {
    protected float x;
    protected float y;
    protected int dy;
    protected int dx;
    protected int width;
    protected int height;


    public void setX(int x){
        this.x = x;
    }
    public float getX(){
        return x;
    }

    public void setY(int y){
        this.y = y;
    }
    public float getY(){
        return y;
    }


    public int getHeight(){
        return height;
    }
    public int getWidth(){
        return width;
    }

    public Rect getRectangle(){
        return new Rect((int)x, (int)y, ((int)x)+width, ((int)y)+height);
    }

    public boolean collision(GameObject rect1, GameObject rect2){
        if (rect1.x-(rect1.getWidth()/2) < rect2.x-(rect2.getWidth()/2) + rect2.width && rect1.x-(rect1.getWidth()/2) + rect1.width > rect2.x-(rect2.getWidth()/2) && rect1.y-(rect1.getHeight()/2) < rect2.y-(rect2.getHeight()/2) + rect2.height && rect1.height + rect1.y-(rect1.getHeight()/2) > rect2.y-(rect2.getHeight()/2)) {
            return true;
        }else{
            return false;
        }
    }
}
