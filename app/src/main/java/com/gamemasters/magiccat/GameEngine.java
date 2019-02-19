package com.gamemasters.magiccat;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class GameEngine extends SurfaceView implements Runnable {

    // Game Thread Variables
    private Thread gameThread = null;
    private volatile boolean gameIsRunning;

    // Screen Drawing Variables
    private Canvas canvas;
    private Paint paintbrush;
    private SurfaceHolder holder;

    // Screen Resolution Variables
    private int screenWidth;
    private int screenHeight;

    // Player Variable
    Cat player;

    // Enemy Variables
    List<Enemy> enemies = new ArrayList<Enemy>();

    // Timer Variable (Interval between addition of new enemies)

    private Timer timer;


    // Background Image
    Bitmap background;

    // Misc

    final int SIZE = 100;


    @Override
    public void run() {
        while (gameIsRunning == true) {
            updateGame();
            drawGame();
            controlFPS();
        }
    }

    public void controlFPS() {
        try {
            gameThread.sleep(17);
        }
        catch (InterruptedException e) {

        }
    }

    public GameEngine(Context context, int screenWidth, int screenHeight) {
        super(context);

        // Initialize the Drawing Variables
        this.holder = this.getHolder();
        this.paintbrush = new Paint();

        // Set Screen Height and Width
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        background = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg);
        // resize the image to match the phone
        background = Bitmap.createScaledBitmap(background,this.screenWidth, this.screenHeight, false);

    }

    public void pauseGame() {
        gameIsRunning = false;
        try {
            gameThread.join();
        }
        catch (InterruptedException e) {

        }
    }
    public void  resumeGame() {
        gameIsRunning = true;
        gameThread = new Thread(this);
        gameThread.start();
    }


    public void drawGame() {
        if (holder.getSurface().isValid()) {

            // initialize the canvas
            canvas = holder.lockCanvas();
            // --------------------------------
            // @TODO: put your drawing code in this section

            // set the game's background color
            canvas.drawColor(Color.argb(255,255,255,255));

            // @TODO: Draw the background
            canvas.drawBitmap(this.background, 0,0, paintbrush);

            // @TODO:  Draw a stationary object on the screen (Player)
            paintbrush.setStyle(Paint.Style.STROKE);
            paintbrush.setStrokeWidth(10);
            paintbrush.setColor(Color.WHITE);
            //canvas.drawRect(player.xPosition, player.yPosition,player.xPosition+SIZE, player.yPosition+SIZE, paintbrush);

            // --------------------------------
            holder.unlockCanvasAndPost(canvas);
        }

    }

    public void updateGame() {

    }


}
