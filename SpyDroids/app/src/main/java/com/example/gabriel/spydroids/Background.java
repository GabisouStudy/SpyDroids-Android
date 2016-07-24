package com.example.gabriel.spydroids;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by Gabriel.Souza on 08/07/2016.
 */
public class Background {

    private int color;
    private int x, y, dx;

    private int opacity;

    public Background(int c, int a){
        color = c;
        opacity = a;
        //dx = GamePanel.MOVESPEED;
    }
    public void update(){

    }
    public void draw(Canvas canvas){
        //canvas.drawBitmap(image, x, y, null);
        Rect r = new Rect(x, y, x + 1920, y + 1080);
        Paint p = new Paint();
        // smooths
        p.setAntiAlias(true);
        p.setColor(color);
        p.setStyle(Paint.Style.FILL);
        p.setAlpha(opacity);
        canvas.drawRect(r, p);
    }
}
