package com.example.gabriel.spydroids;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Gabriel.Souza on 22/07/2016.
 */
public class Spy extends GameObject {

    private Bitmap sprite;

    private int spawn;

    private boolean noShy;


    public Spy(Bitmap res, int s){
        sprite = res;
        width = sprite.getWidth();
        height = sprite.getHeight();
        spawn = s;
        noShy = true;
    }

    public void setPosition(int pX, int pY)
    {
        x = (pX - width/2);
        y = (pY - height/2);
    }

    public void update(){
        //if(noShy){

        //}
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(sprite, (x), (y), null);
    }
}
