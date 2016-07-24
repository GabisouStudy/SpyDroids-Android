package com.example.gabriel.spydroids;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.os.Handler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Gabriel on 04/04/2016.
 */

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback, Runnable
{
    public static boolean isDead, isPaused, isUpdating;

    public static final int WIDTH = 1920;
    public static final int HEIGHT = 1080;

    private int screenX;
    private int screenY;
    private float scaleFactorX;
    private float scaleFactorY;

    private int gameState;

    private boolean inInterface;

    private MainThread thread;


    private Background bg_game;
    private Background front_pause;
    private Background front_On;
    private Background front_Off;

    private FullScreen screen;

    private Pointer flashlight;
    private Pointer collPointer;

    private Pointer upTab;

    private Button playButton;
    private Button pauseButton;

    private Button pause;

    private Button gameOver;
    private Button title;
    private Button logo;
    private Button spyWhite;
    private Button spyBlack;

    private Button letterA;

    private Button bitButton;

    private Bar powerBar;

    private boolean activateA = false;
    private boolean gotA = false;

    private float xT;
    private float yT;
    private boolean touch = false;

    private long start;
    private float deltaTime;

    //
    private float timer = 0;

    private double spawnTime;
    private double spawnTimeIndex;

    private int speed;

    private int droids;

    private boolean changeSpawn = false;

    private float lightTime;
    private float power;

    private boolean lockPower;

    private boolean flash;

    //
    private Button briefcase;

    private Spy spies[][] = new Spy[8][1];

    private int spyGroup = 0;
    private int spyIndex = 0;

    private float fScore = 0;
    private int iScore = 0;

    private boolean lightOn = true;

    List<Integer> spawnPoints = new ArrayList<Integer>();

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
        scaleFactorX = screenX / (WIDTH * 1.f);
        scaleFactorY = screenY / (HEIGHT * 1.f);

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
        start = System.nanoTime();

        screen = new FullScreen(screens, gameState);

        flashlight = new Pointer(BitmapFactory.decodeResource(getResources(), R.drawable.flashlight));

        collPointer = new Pointer(80, 80, Color.BLUE, 120);

        upTab = new Pointer(1920, 135, Color.GRAY, 255);

        bg_game = new Background(Color.parseColor("#333333"),255);
        front_On = new Background(Color.parseColor("#ffffff"), ((int)(255 * 0.25)));
        front_Off = new Background(Color.parseColor("#000000"),((int)(255 * 0.5)));

        front_pause = new Background(Color.parseColor("#004d40"),((int)(255 * 0.5)));

        playButton = new Button(BitmapFactory.decodeResource(getResources(), R.drawable.button_play_0), BitmapFactory.decodeResource(getResources(), R.drawable.button_play_1));

        pauseButton = new Button(BitmapFactory.decodeResource(getResources(), R.drawable.button_pause0), BitmapFactory.decodeResource(getResources(), R.drawable.button_pause1));

        gameOver = new Button(BitmapFactory.decodeResource(getResources(), R.drawable.gameover0));
        title = new Button(BitmapFactory.decodeResource(getResources(), R.drawable.title));
        logo = new Button(BitmapFactory.decodeResource(getResources(), R.drawable.logo));
        spyWhite = new Button(BitmapFactory.decodeResource(getResources(), R.drawable.spy_white_big));
        spyBlack = new Button(BitmapFactory.decodeResource(getResources(), R.drawable.spy_black_big));

        pause = new Button(BitmapFactory.decodeResource(getResources(), R.drawable.pause));

        letterA = new Button(BitmapFactory.decodeResource(getResources(), R.drawable.letter));

        briefcase = new Button(BitmapFactory.decodeResource(getResources(), R.drawable.briefcase));

        bitButton = new Button(110,110, Color.parseColor("#ffc107"), Color.parseColor("#ffeb3b"));

        powerBar = new Bar(640,90);

        for(int i = 0; i < spies.length; i++){
            for(int j = 0; j < spies[i].length; j++){
                spies[i][j] = new Spy(BitmapFactory.decodeResource(getResources(), R.drawable.spy_white_side_0), BitmapFactory.decodeResource(getResources(), R.drawable.spy_black_side_0),i);
            }
        }

        startGame();
    }

    public void startGame(){
        isDead = isPaused = false;
        isUpdating = true;

        timer = 0;

        lockPower = true;

        spawnTime = spawnTimeIndex = 5;

        lightTime = 3;

        power = 3;

        spyGroup = 0;
        spyIndex = 0;

        speed = 200;

        fScore = 0;
        iScore = 0;
        droids = 0;

        Collections.shuffle(spawnPoints);

        playButton.setPosition(960, 540);
        pauseButton.setPosition(1850, 65);

        briefcase.setPosition(960, 770);

        gameOver.setPosition(960, 300);
        title.setPosition(960, 250);
        spyWhite.setPosition(296, 720);
        spyBlack.setPosition(1664, 720);
        logo.setPosition(960, 960);

        pause.setPosition(960, 540);

        playButton.start();

        letterA.setPosition(960, 800);

        upTab.setPosition((upTab.getWidth()/2), (upTab.getHeight()/2));
        bitButton.setPosition(100, (upTab.getHeight()/2));

        powerBar.setPosition(460, 20);

        spawnPoints.add(0);
        spawnPoints.add(1);
        spawnPoints.add(2);
        spawnPoints.add(3);
        spawnPoints.add(4);
        spawnPoints.add(5);
        spawnPoints.add(6);
        spawnPoints.add(7);

        for(int i = 0; i < spies.length; i++){
            for(int j = 0; j < spies[i].length; j++){
                spies[i][j].setState(0, speed);
            }
        }

        gameState = 0;
    }



    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if(isDead) startGame();
        else if(isPaused) isPaused = false;


        xT = event.getX();
        yT = event.getY();

        if (event.getAction() == android.view.MotionEvent.ACTION_DOWN) {
            touch = true;
        } else if (event.getAction() == android.view.MotionEvent.ACTION_UP) {
            touch = false;
        }

        return  true;
    }

    public boolean collision(GameObject rect1, GameObject rect2){
        if (rect1.x-(rect1.getWidth()/2) < rect2.x-(rect2.getWidth()/2) + rect2.width && rect1.x-(rect1.getWidth()/2) + rect1.width > rect2.x-(rect2.getWidth()/2) && rect1.y-(rect1.getHeight()/2) < rect2.y-(rect2.getHeight()/2) + rect2.height && rect1.height + rect1.y-(rect1.getHeight()/2) > rect2.y-(rect2.getHeight()/2)) {
            return true;
        }else{
            return false;
        }
    }

    public void run(){
        if(isUpdating){
            //Add a Runnable in the main thread message queue to be executed after a delayed time (ms).
            //handler.postDelayed(this, 30);
            update();
        }
    }

    public void update()
    {
        collPointer.setPosition((int)(xT / scaleFactorX), (int)(yT / scaleFactorY));

        long time = System.nanoTime();
        deltaTime = (float)((time - start)/1000000);//1000.
        start = time;


        Random r = new Random();

        switch (gameState){
            case 0:
                playButton.update(collPointer, touch);

                if(playButton.isActive()){
                    gameState = 1;
                    letterA.start();
                    briefcase.setPosition(960,608);
                }

                briefcase.update(collPointer,touch);


                if(briefcase.isActive()){
                    briefcase.start();
                    briefcase.setPosition(1440, 1000);
                    gameState = 3;
                }

                break;

            case 1:
                //game
                if(!isPaused && !isDead) {
                    pauseButton.update(collPointer, touch);
                    if(pauseButton.isActive()) {
                        pauseButton.start();
                        isPaused = true;
                    }
                    flashlight.setPosition((int) (xT / scaleFactorX), (int) (yT / scaleFactorY));
                    //briefcase.update();

                    if(collision(collPointer, upTab)){
                        inInterface = true;
                    }else {
                        inInterface = false;
                    }


                    if(power < 6) {
                        power += 1 * (deltaTime / 1000);
                        if(power > 4){
                            lockPower = false;
                        }
                    }else {
                        power = 6;
                    }

                    if(!lockPower){
                        if(power > 0){
                            if(!inInterface && touch){
                                flash = true;
                                power -= 2.5 * (deltaTime/1000);
                            }else {
                                flash = false;
                            }

                            if(bitButton.isActive() /*&& ((power-0.5) > 0)*/){
                                fScore += 1;
                                power -=0.5;
                                bitButton.start();
                            }
                        }else {
                            lockPower = true;
                        }
                    }else if(power < 0){
                        power = 0;
                    }else {
                        flash = false;
                    }
                    bitButton.update(collPointer, touch);
                    /*
                    bitButton.update(collPointer, touch);
                    if(bitButton.isActive() && power > 0 && !lockPower){
                        fScore += 1;
                        power -= 0.5;
                        bitButton.start();
                    }

                    if(!inInterface && touch && !lockPower){
                        if(power >= 0){
                            flash = true;
                            power -= 2.5 * (deltaTime/1000);
                        }else {
                            power = 0;
                            lockPower = true;
                        }
                    }else {
                        flash = false;
                        if(power < 6) {
                            power += 1 * (deltaTime / 1000);
                            if(power > 4){
                                lockPower = false;
                            }
                        }else {
                            power = 6;
                        }
                    }*/

                    for (int i = 0; i <= spies.length-1; i++) {
                        for (int j = 0; j <= spies[i].length-1; j++) {
                            spies[i][j].update(deltaTime / 1000);
                            if (collision(spies[i][j], collPointer) && !inInterface && flash) {
                                spies[i][j].setState(0, speed);
                                fScore += 3;
                                droids++;
                            }
                            if (collision(spies[i][j], briefcase)) {
                                gameState = 2;
                                playButton.setPosition(960,750);
                                playButton.start();
                            }
                        }
                    }
                    timer += 1 * (deltaTime / 1000);
                    fScore += 1 * (deltaTime / 1000);
                    iScore = ((int)fScore);

                    spawnTime -= 1 * (deltaTime / 1000);

                    lightTime -= 1 * (deltaTime / 1000);

                    if(activateA){
                        letterA.update(collPointer, touch);
                        if(letterA.isActive()){
                            gotA = true;
                            activateA = false;
                            Intent sendIntent = new Intent();
                            sendIntent.setAction("JAMV");
                            sendIntent.putExtra("letter", "A7");
                            isPaused = true;
                            getContext().startActivity(sendIntent);
                        }
                    }

                    if (lightTime <= 0) {
                        if (r.nextInt(3) > 0) {
                            if (lightOn) {
                                lightOn = false;
                            } else {
                                lightOn = true;
                            }
                        }
                        lightTime = r.nextInt(2) + 3;
                    }

                    if (spawnTime <= 0) {
                        if (spyIndex <= spies[0].length-1) {
                            if (spyGroup <= spies.length-1) {
                                int s;
                                s = r.nextInt(100);
                                if(speed <= 600){
                                    spies[spawnPoints.get(spyGroup)][spyIndex].setState(1, speed+s);
                                }else {
                                    spies[spawnPoints.get(spyGroup)][spyIndex].setState(1, speed-s);
                                }

                                spyGroup++;
                            } else {
                                spyIndex++;
                                spyGroup = 0;
                                Collections.shuffle(spawnPoints);
                                if(!gotA){
                                    activateA = true;
                                }
                            }
                        } else {
                            if(changeSpawn){
                                spawnTimeIndex -= 0.125;
                                //spawnTime = spawnTimeIndex;
                                changeSpawn = false;
                            }else {
                                if(speed < 1200) {
                                    speed += 100;
                                }
                                //speed = speed;
                                if(spawnTimeIndex > 3) {
                                    changeSpawn = true;
                                }
                            }
                            for (int i = 0; i <= spies.length-1; i++) {
                                for (int j = 0; j <= spies[i].length-1; j++) {
                                    spies[i][j].setState(0, speed);
                                }
                            }
                            spyIndex = 0;
                        }
                        int t;
                        t = r.nextInt(3);
                        spawnTime = spawnTimeIndex-t;
                    }
                }
                break;

            case 2:
                playButton.update(collPointer, touch);

                for(int i = 0; i < spies.length; i++){
                    for(int j = 0; j < spies[i].length; j++){
                        spies[i][j].setState(0, speed);
                    }
                }

                if(playButton.isActive()){
                    startGame();
                }

                //gameover
                break;

            case 3:
                //info
                briefcase.update(collPointer, touch);

                if(briefcase.isActive()){
                    startGame();
                    briefcase.start();
                }
                break;
        }
    }


    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        if (canvas != null) {
            final int savedState = canvas.save();
            canvas.scale(scaleFactorX, scaleFactorY);

            Paint paint = new Paint();

            bg_game.draw(canvas);

            switch (gameState) {
                case 0:
                    briefcase.draw(canvas);

                    //

                    playButton.draw(canvas);

                    title.draw(canvas);
                    logo.draw(canvas);
                    spyWhite.draw(canvas);
                    spyBlack.draw(canvas);

                    break;

                case 1:
                    //game
                    briefcase.draw(canvas);

                    int cs = ((int)((power/6)*powerBar.getWidth()));
                    if(cs < 0){
                        cs = 0;
                    }
                    powerBar.update(cs);

                    if(activateA){
                        letterA.draw(canvas);
                    }
                    for(int i = 0; i < spies.length; i++){
                        for(int j = 0; j < spies[i].length; j++){
                            spies[i][j].draw(canvas);
                        }
                    }

                    if(lightOn){
                        front_On.draw(canvas);
                    }else {
                        front_Off.draw(canvas);
                    }

                    if(flash) {
                        flashlight.draw(canvas);
                    }

                    if(isPaused){
                        front_pause.draw(canvas);
                        pause.draw(canvas);
                    }

                    upTab.draw(canvas);

                    powerBar.draw(canvas);

                    bitButton.draw(canvas);

                    paint.setColor(Color.parseColor("#e65100"));
                    paint.setTextSize(100);

                    canvas.drawText(("฿"), 75, 95, paint);

                    paint.setColor(Color.BLACK);
                    paint.setTextSize(70);
                    canvas.drawText(("฿: " + iScore), 1160, 80, paint);
                    canvas.drawText(("Power: " ), 230, 80, paint);
                    //canvas.drawText(("Score: " + iScore + " || Speed: " + speed + " || Spawn: " + spawnTimeIndex + " || Power: " + power), 30, 80, paint);
                    //canvas.drawText(("Score: " + iScore + " || Speed: " + speed + " || Spawn: " + spawnTimeIndex + " || Power: " + power), 30, 80, paint);

                    pauseButton.draw(canvas);

                    break;

                case 2:
                    //gameover
                    gameOver.draw(canvas);
                    playButton.draw(canvas);
                    spyWhite.draw(canvas);
                    spyBlack.draw(canvas);
                    paint = new Paint();

                    paint.setColor(Color.GRAY);
                    paint.setTextSize(200);

                    canvas.drawText(("฿: " + iScore ), 500, 600, paint);
                    break;
                case 3:
                    //info
                    paint.setColor(Color.WHITE);
                    paint.setTextSize(70);
                    canvas.drawText(("Instructions: "), 40, 80, paint);
                    canvas.drawText(("The case in the center will generate 1 Bitcoin every second."), 40, 160, paint);
                    canvas.drawText(("If the spies catch your case, you will not win more Bitcoins."), 40, 240, paint);
                    canvas.drawText(("And you lose!"), 40, 320, paint);
                    canvas.drawText(("Click in the spy for the light to destroy him,"), 40, 400, paint);
                    canvas.drawText(("but it takes energy!"), 40, 480, paint);
                    canvas.drawText(("Click on the currency ($) to generate 1 Bitcoin instantly, "), 40, 560, paint);
                    canvas.drawText(("but spends energy."), 40, 640, paint);
                    canvas.drawText(("Do not let your energy runs out, or you will be undefended!!!"), 40, 720, paint);
                    canvas.drawText(("A game made by: Gabriel de Souza, Nicole Alves,"), 40, 880, paint);
                    canvas.drawText(("Patrick Pissurno, Talita Canadinhas."), 40, 960, paint);
                    paint.setTextSize(40);
                    canvas.drawText(("Click in the briefcase to back."), 40, 1040, paint);

                    briefcase.draw(canvas);
                    break;
            }

            //screen.draw(canvas);

            //  0   1   2
            //  3  [=]  4
            //  5   6   7

            canvas.restoreToCount(savedState);
        }
    }
}