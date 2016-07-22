package com.example.gabriel.spydroids;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

/**
 * Created by Gabriel.Souza on 22/07/2016.
 */
public class Pointer extends GameObject {
    private Bitmap sprite;
    public Pointer(Bitmap res){
        sprite = res;
        width = sprite.getWidth();
        height= sprite.getHeight();
    }
    public void setPosition(int pX, int pY)
    {
        x = (pX - width/2);
        y = (pY - height/2);
    }
    public void draw(Canvas canvas){
        /*Rect r = new Rect(x, y, x + width, y + height);
        Paint p = new Paint();
        // smooths
        p.setAntiAlias(true);
        p.setColor(Color.BLACK);
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(4.5f);
        canvas.drawRect(r, p);*/
        canvas.drawBitmap(sprite, (x), (y), null);
    }
}
