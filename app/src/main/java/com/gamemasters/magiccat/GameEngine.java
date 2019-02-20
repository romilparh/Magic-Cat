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
            updateEnemyPosition();
            drawGame();
            controlFPS();
            Log.d("Game Updating","Game Running"+enemies.get(0).xPosition);
        }
    }

    public void controlFPS() {
        try {
            gameThread.sleep(60);
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

        this.spawnCat();
        this.spawnEnemy();

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
            canvas.drawRect(player.xPosition, player.yPosition,player.xPosition+SIZE, player.yPosition+SIZE, paintbrush);

            for(int i = 0; i<enemies.size();i++){
                canvas.drawRect(enemies.get(i).xPosition, enemies.get(i).yPosition,enemies.get(i).xPosition+SIZE, enemies.get(i).yPosition+SIZE, paintbrush);
            }
            // --------------------------------
            holder.unlockCanvasAndPost(canvas);
        }

    }

    public void updateEnemyPosition() {

        // Move The Enemy

        double xDist = this.player.xPosition - this.enemies.get(0).xPosition;
        double yDist = this.player.yPosition - this.enemies.get(0).yPosition;

        double distance = Math.sqrt((xDist * xDist) + (yDist * yDist));

        double xSpeed = xDist/distance;
        double ySpeed = yDist/distance;

        this.enemies.get(0).xPosition = enemies.get(0).xPosition+(int)(xSpeed*10);
        this.enemies.get(0).yPosition = enemies.get(0).yPosition+(int)(ySpeed*10);

        Log.d("Updated Game","Updated"+enemies.get(0).xPosition);


    }


    public void updateGame(){

        if (player.getHitbox().intersect(enemies.get(0).hitBox)) {
            enemies.remove(0);
            Log.d("Pop","Popped");
        } else {
            Log.d("NotPop","NotPopped");
        }

    }

    private void spawnCat() {
        // put player in middle of screen --> you may have to adjust the Y position
        // depending on your device / emulator
        this.player = new Cat(this.getContext(), (this.screenWidth/2)-50, (int)(0.7*this.screenHeight),100,100,0);
    }

    private void spawnEnemy(){
        ArrayList<Integer> sign = new ArrayList<>();
        sign.add(10);
        enemies.add(new Enemy(this.getContext(),100,100,100,100,sign));
    }

    public void resetGame() {
        // spawn finger back in starting position
        try {
            Thread.sleep(1000);
            this.spawnEnemy();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
