package com.example.gabriel.spydroids;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by Gabriel on 24/07/2016.
 */
public class Bar extends GameObject{
    private Bitmap sprite = null;
    private int color;
    //private int opacity;

    private int wC;

    public Bar (int w, int h){
        width = w;
        height = h;
    }
    public void setPosition(int pX, int pY)
    {
        x = (pX);
        y = (pY);
    }

    public void update(int wN){
        wC = wN;
        if(wC > (width/3)*2){
            color = Color.parseColor("#8bc34a");
        }else if(wC > (width/3)){
            color = Color.parseColor("#ffeb3b");
        }else {
            color = Color.parseColor("#f44336");
        }
    }

    public void draw(Canvas canvas){
        Rect r = new Rect(((int)x), ((int)y), (((int)x) + width), (((int)y) + height));
        Paint p = new Paint();
        // smooths
        p.setAntiAlias(true);
        p.setColor(Color.WHITE);
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(10);
        //p.setAlpha(255);
        canvas.drawRect(r, p);


        r = new Rect(((int)x), ((int)y), (((int)x) + wC), (((int)y) + height));
        p.setColor(color);
        p.setStyle(Paint.Style.FILL);
        //p.setAlpha(255);
        canvas.drawRect(r, p);
    }
}
