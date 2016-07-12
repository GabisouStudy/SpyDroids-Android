package com.example.gabriel.spydroids;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Gabriel.Souza on 08/07/2016.
 */
public class Background {
    private Bitmap image;
    private int x, y, dx;

    public Background(Bitmap res){
        image = res;
        dx = GamePanel.MOVESPEED;
    }
    public void update(){

    }
    public void draw(Canvas canvas){
        canvas.drawBitmap(image, x, y, null);
    }
}
