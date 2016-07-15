package com.example.gabriel.spydroids;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Gabriel.Souza on 15/07/2016.
 */
public class Button extends GameObject {
    private Bitmap sprite;

    public Button(Bitmap res){
        sprite = res;
    }

    public void setPosition(int pX, int pY)
    {
        x = (pX - width/2);
        y = (pY - height/2);
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(sprite, (x), (y), null);
    }
}
