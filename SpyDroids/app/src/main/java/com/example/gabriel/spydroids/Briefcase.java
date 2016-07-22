package com.example.gabriel.spydroids;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Gabriel.Souza on 22/07/2016.
 */
public class Briefcase extends GameObject{

    private Bitmap sprite;

    public Briefcase(Bitmap res){
        sprite = res;
        width = sprite.getWidth();
        height= sprite.getHeight();
        setPosition(960,540);
    }

    public void setPosition(int pX, int pY)
    {
        x = (pX - width/2);
        y = (pY - height/2);
    }

    public void update(){

    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(sprite, (x), (y), null);
    }
}
