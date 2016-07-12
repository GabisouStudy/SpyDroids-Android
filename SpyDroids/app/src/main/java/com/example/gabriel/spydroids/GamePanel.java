package com.example.gabriel.spydroids;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.text.style.LineHeightSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Gabriel on 04/04/2016.
 */

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback
{
    public static final int WIDTH = 1920;
    public static final int HEIGHT = 1080;
    public static final int MOVESPEED = -5;

    private int screenX;
    private int screenY;

    private int gameState;

    private boolean touch = false;

    private MainThread thread;

    private Background bg;
    private FullScreen screen;
    //private Player player;

    //private Item[] items = new Item[5];

    //private Requester[] requesters = new Requester[5];


    private Bitmap[] itemsSprites = new Bitmap[5];

    private Bitmap[] requestsSprites = new Bitmap[5];

    private Bitmap[] screens = new Bitmap[3];

    public GamePanel(Context context, int x, int y)
    {
        super(context);

        //add the callback to the surfaceholder to intercept events
        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);

        //make gamePanel focusable so it can handle events
        setFocusable(true);

        screenX = getResources().getDisplayMetrics().widthPixels;
        screenY = getResources().getDisplayMetrics().heightPixels;

        Log.d("RESOLUTION: ", screenX + " " + screenY);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        boolean retry = true;
        while(retry)
        {
            try{thread.setRunning(false);
                thread.join();

            }catch(InterruptedException e){e.printStackTrace();}
            retry = false;
        }

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){
        //we can safely start the game loop
        thread.setRunning(true);
        thread.start();

        gameState = 0;

        screen = new FullScreen(screens, gameState);

        bg = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.bg_menu_main));
        /*bg.setVector(-5);

        player = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.hand_player), 40, 60, (WIDTH - 200), itemsSprites);

        /*itemsSprites[0] = BitmapFactory.decodeResource(getResources(), R.drawable.item0);
        itemsSprites[1] = BitmapFactory.decodeResource(getResources(), R.drawable.item1);
        itemsSprites[2] = BitmapFactory.decodeResource(getResources(), R.drawable.item2);
        itemsSprites[3] = BitmapFactory.decodeResource(getResources(), R.drawable.item3);
        itemsSprites[4] = BitmapFactory.decodeResource(getResources(), R.drawable.item4);

        requestsSprites[0] = BitmapFactory.decodeResource(getResources(), R.drawable.request0);
        requestsSprites[1] = BitmapFactory.decodeResource(getResources(), R.drawable.request1);
        requestsSprites[2] = BitmapFactory.decodeResource(getResources(), R.drawable.request2);
        requestsSprites[3] = BitmapFactory.decodeResource(getResources(), R.drawable.request3);
        requestsSprites[4] = BitmapFactory.decodeResource(getResources(), R.drawable.request4);

        screens[0] = BitmapFactory.decodeResource(getResources(), R.drawable.menu);
        screens[1] = BitmapFactory.decodeResource(getResources(), R.drawable.gameplay);
        screens[2] = BitmapFactory.decodeResource(getResources(), R.drawable.gameover);*/

    }

    public void startGame(){
        /*player = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.hand_player), 40, 60, (WIDTH - 200), itemsSprites);

        for(int i = 0; i <= 4; i++){
            items[i] = new Item(itemsSprites[i], i, (HEIGHT - 120));
        }

        for(int i = 0; i <= 4; i++){
            requesters[i] = new Requester(requestsSprites, (HEIGHT - 240), i);
        }*/
    }



    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        float x = event.getX();
        float y = event.getY();

        //player.setPosition(((int) (x * scaleIndexX)), ((int) (y * scaleIndexY)));

        if (event.getAction() == android.view.MotionEvent.ACTION_DOWN) {
            touch = true;
        } else if (event.getAction() == android.view.MotionEvent.ACTION_UP) {
            touch = false;
        }

        return  true;
    }

    public boolean collision(GameObject rect1, GameObject rect2){
        if (rect1.x < rect2.x + rect2.width && rect1.x + rect1.width > rect2.x && rect1.y < rect2.y + rect2.height && rect1.height + rect1.y > rect2.y) {
            return true;
        }else{
            return false;
        }
    }

    public void update()
    {
        switch (gameState){
            case 0:
                if(touch){
                    startGame();
                    gameState = 1;
                    screen.setId(1);
                }
                //bg.update();
                break;

            case 1:
                //game
                break;

            case 2:
                //gameover
                break;
        }


    }


    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        final float scaleFactorX = screenX / (WIDTH * 1.f);
        final float scaleFactorY = screenY / (HEIGHT * 1.f);


        if (canvas != null) {
            final int savedState = canvas.save();
            canvas.scale(scaleFactorX*2, scaleFactorY*2);
            bg.draw(canvas);

            screen.draw(canvas);

            /*if(gameState == 1) {
                for (int i = 0; i <= 4; i++) {
                    items[i].draw(canvas);
                    requesters[i].draw(canvas);
                }
            }

            if(gameState != 0) {
                player.draw(canvas);
            }*/

            canvas.restoreToCount(savedState);
        }
    }
}