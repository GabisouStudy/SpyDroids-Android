package com.example.gabriel.spydroids;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Gabriel.Souza on 22/07/2016.
 */
public class Briefcase extends GameObject{

    private Bitmap sprite;

    private boolean active = false;

    private boolean locked = true;
    private boolean clicked = false;

    public Briefcase(Bitmap res){
        sprite = res;
        width = sprite.getWidth();
        height= sprite.getHeight();
        setPosition(960,420);
    }

    public void setPosition(int pX, int pY)
    {
        x = pX;
        y = pY;
    }

    public void start(){
        active = false;
        locked = true;
        clicked = false;
    }

    public void update(GameObject pointer, boolean t){
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
        canvas.drawBitmap(sprite, (((int) x) - width / 2), (((int) y) - height / 2), null);
    }
}
