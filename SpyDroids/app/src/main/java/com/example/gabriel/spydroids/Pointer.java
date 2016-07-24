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
    private Bitmap sprite = null;
    private int color;
    private int opacity;
    public Pointer(Bitmap res){
        sprite = res;
        width = sprite.getWidth();
        height= sprite.getHeight();
    }
    public Pointer(int w, int h, int c, int a){
        width = w;
        height = h;
        color = c;
        opacity = a;
    }
    public void setPosition(int pX, int pY)
    {
        x = (pX);
        y = (pY);
    }
    public void draw(Canvas canvas){
        if(sprite != null) {
            canvas.drawBitmap(sprite, (((int) x) - width / 2), (((int) y) - height / 2), null);
        }else {
            Rect r = new Rect(((int)x)-(width/2), ((int)y)-(height/2), (((int)x) + width/2), (((int)y) + height/2));
            Paint p = new Paint();
            // smooths
            p.setAntiAlias(true);
            p.setColor(color);
            p.setStyle(Paint.Style.FILL);
            p.setAlpha(opacity);
            canvas.drawRect(r, p);
        }
    }
}
