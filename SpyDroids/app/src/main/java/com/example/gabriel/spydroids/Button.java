package com.example.gabriel.spydroids;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by Gabriel.Souza on 15/07/2016.
 */
public class Button extends GameObject {
    private Bitmap sprite = null;
    private Bitmap sprite2 = null;

    private boolean active = false;

    private boolean locked = true;
    private boolean clicked = false;

    private int color = -1;
    private int color1 = -1;
    private int color2 = -1;

    public Button(Bitmap res){
        sprite = res;
        width = sprite.getWidth();
        height= sprite.getHeight();
    }

    public Button(Bitmap res, Bitmap res2){
        sprite = res;
        sprite2 = res2;
        width = sprite.getWidth();
        height= sprite.getHeight();
    }
    public Button(int w, int h, int c1, int c2){
        width = w;
        height = h;
        color = color1 = c1;
        color2 = c2;
    }

    public void setPosition(int pX, int pY)
    {
        x = (pX);
        y = (pY);
    }

    public boolean isActive(){
        return active;
    }

    public void start(){
        active = false;
        locked = true;
        clicked = false;
    }

    public void update(GameObject pointer, boolean t){

        if(color2 != -1){
            if(clicked){
                color = color2;
            }else {
                color = color1;
            }
        }

        if(!t){
            locked = false;
            if(clicked){
                active = true;
            }
        }else {
            if(collision(this, pointer) && !locked){
                clicked = true;
            }else {
                clicked = false;
                locked = true;
            }
        }
    }

    public void draw(Canvas canvas){
        if(sprite == null){
            Rect r = new Rect(((int)x)-(width/2), ((int)y)-(height/2), (((int)x) + width/2), (((int)y) + height/2));
            Paint p = new Paint();
            // smooths
            p.setAntiAlias(true);
            p.setColor(color);
            p.setStyle(Paint.Style.FILL);
            //p.setAlpha(255);
            //canvas.drawRect(r, p);
            canvas.drawCircle(((int)x), ((int)y), width/2, p);
        }else if(sprite2 != null){
            if(clicked){
                canvas.drawBitmap(sprite2, (((int)x) - width/2), (((int)y) - height/2), null);
            }else {
                canvas.drawBitmap(sprite, (((int)x) - width/2), (((int)y) - height/2), null);
            }
        }else {
            canvas.drawBitmap(sprite, (((int)x) - width/2), (((int)y) - height/2), null);
        }
    }
}
