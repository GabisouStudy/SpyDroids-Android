package com.example.gabriel.spydroids;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Gabriel on 22/04/2016.
 */
public class FullScreen {
    private Bitmap[] image;
    private int x, y, id;
    public void setId(int id){
        this.id = id;
    }

    public FullScreen(Bitmap[] res, int ID){
        image = res;
        id = ID;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image[id], 0, 0, null);
    }
}
