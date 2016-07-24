package com.example.gabriel.spydroids;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;

import java.util.Random;

/**
 * Created by Gabriel.Souza on 22/07/2016.
 */
public class Spy extends GameObject {

    private Bitmap[][] sprite = new Bitmap[2][2];

    private int spawn;

    private boolean noShy;
    //  | 0 disable | - | 1 enable | - | 2 escape |
    private int state = 0;

    private int speed;

    // | 0 = black | - | 1 = white |
    private int color;

    public Spy(Bitmap res0, Bitmap res1, int s){
        sprite[0][0] = res0;
        sprite[0][1] = flip(res0);
        sprite[1][0] = res1;
        sprite[1][1] = flip(res1);

        width = 140;
        height = 230;

        spawn = s;

        switch (spawn){
            case 0:
                x = 0 - 300;
                y = 0 - 300;
                break;

            case 1:
                x = 960;
                y = 0 - 300;
                break;

            case 2:
                x = 1920 + 300;
                y = 0 - 300;
                break;

            case 3:
                x = 0 - 300;
                y = 540+68;
                break;

            case 4:
                x = 1920 + 300;
                y = 540+68;
                break;

            case 5:
                x = 0 - 300;
                y = 1080 + 300;
                break;

            case 6:
                x = 960;
                y = 1080 + 300;
                break;

            case 7:
                x = 1920 + 300;
                y = 1080 + 300;
                break;
        }
    }

    public void setPosition(int pX, int pY)
    {
        x = (pX);
        y = (pY);
    }

    public void setState(int s, int v){
        state = s;
        speed = v;
    }

    public void update(float d){
        switch (state){
            case 0:
                Random cR = new Random();
                color = cR.nextInt(2);

                switch (spawn){
                    case 0:
                        x = 0 - 300;
                        y = 0 - 300;
                        break;

                    case 1:
                        x = 960;
                        y = 0 - 300;
                        break;

                    case 2:
                        x = 1920 + 300;
                        y = 0 - 300;
                        break;

                    case 3:
                        x = 0 - 300;
                        y = 540+68;
                        break;

                    case 4:
                        x = 1920 + 300;
                        y = 540+68;
                        break;

                    case 5:
                        x = 0 - 300;
                        y = 1080 + 300;
                        break;

                    case 6:
                        x = 960;
                        y = 1080 + 300;
                        break;

                    case 7:
                        x = 1920 + 300;
                        y = 1080 + 300;
                        break;
                }
                /*if(spawn == 1 || spawn == 2 || spawn == 4 || spawn == 7) {
                    spriteF = flip(sprite);
                }*/
                break;

            case 1:
                double nSpeed = 0;
                if(spawn == 1 || spawn == 6){
                    nSpeed = speed * 0.6;
                }else {
                    nSpeed = speed;
                }
                if(this.x - 10 > 960){
                    x -= nSpeed*d;
                }else if(this.x + 10 < 960){
                    x += nSpeed*d;
                }

                if(this.y - 10 > 540+68){
                    y -= nSpeed*d;
                }else if(this.y + 10 < 540+68){
                    y+= nSpeed*d;
                }
                break;

            case 2:

                break;
        }
    }

    Bitmap flip(Bitmap d)
    {
        Matrix m = new Matrix();
        m.preScale(-1, 1);
        Bitmap src = d;
        Bitmap dst = Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), m, false);
        dst.setDensity(DisplayMetrics.DENSITY_DEFAULT);
        return dst;
    }

    public void draw(Canvas canvas){
        /*Matrix flipHorizontalMatrix = new Matrix();
        flipHorizontalMatrix.setScale(-1, 1);
        flipHorizontalMatrix.postTranslate(sprite.getWidth(), 0);

        canvas.drawBitmap(sprite, flipHorizontalMatrix, null);*/
        if(spawn == 1 || spawn == 2 || spawn == 4 || spawn == 7) {
            canvas.drawBitmap(sprite[color][1], (((int) x) - width / 2), (((int) y) - height / 2), null);
        }else {
            canvas.drawBitmap(sprite[color][0], (((int) x) - width / 2), (((int) y) - height / 2), null);
        }
    }
}
