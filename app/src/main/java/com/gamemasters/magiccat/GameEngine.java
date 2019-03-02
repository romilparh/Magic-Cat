package com.gamemasters.magiccat;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import android.media.MediaPlayer;

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
    int score = 0;
    int winScore;

    int gameOverCount;
    // Player Variable
    Cat cat;

    List<LifeGiver> lifeGivers = new ArrayList<LifeGiver>();
    // Enemy Variables
    List<Enemy> enemies = new ArrayList<Enemy>();

    // Background Image
    Bitmap background;

    public MediaPlayer scream;
    public MediaPlayer backgroundSound;
    public MediaPlayer killYou;
    public MediaPlayer tada;
    public MediaPlayer gameOver;
    public MediaPlayer lifeUp;

    @Override
    public void run() {
        while (gameIsRunning == true) {
            this.updateGame();
            this.drawGame();
            this.controlFPS();
            Log.d("Game Updating", "Game Running");
        }
    }

    public void controlFPS() {
        try {
            this.gameThread.sleep(60);
        } catch (InterruptedException e) {

        }
    }

    public GameEngine(Context context, int screenWidth, int screenHeight) {
        super(context);

        // Initialize the Drawing Variables
        this.holder = this.getHolder();
        this.paintbrush = new Paint();
        this.winScore = 30;
        this.gameOverCount = 0;
        // Set Screen Height and Width
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.scream = MediaPlayer.create(context, R.raw.scary_scream);
        this.scream.setLooping(false);
        this.tada = MediaPlayer.create(context, R.raw.ta_da);
        this.tada.setLooping(false);
        this.killYou = MediaPlayer.create(context, R.raw.kill_you);
        this.killYou.setLooping(false);
        this.gameOver = MediaPlayer.create(context, R.raw.gameover);
        this.gameOver.setLooping(false);
        this.lifeUp = MediaPlayer.create(context, R.raw.lifeup);
        this.lifeUp.setLooping(false);
        this.backgroundSound = MediaPlayer.create(context, R.raw.creepy);
        this.backgroundSound.setLooping(true);

        this.background = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg);
        // resize the image to match the phone
        this.background = Bitmap.createScaledBitmap(background, this.screenWidth, this.screenHeight, false);

        this.spawnCat();
        this.spawnEnemy();
        this.spawnLifeGiver();

    }

    public void pauseGame() {
        this.gameIsRunning = false;
        try {
            this.stopSounds();
            this.gameThread.join();


        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resumeGame() {
        this.gameIsRunning = true;
        this.gameThread = new Thread(this);
        this.gameThread.start();
    }


    public void drawGame() {
        if (this.holder.getSurface().isValid()) {

            // initialize the canvas
            this.canvas = this.holder.lockCanvas();
            // --------------------------------
            // @TODO: put your drawing code in this section

            // set the game's background color
            this.canvas.drawColor(Color.argb(255, 255, 255, 255));

            // @TODO: Draw the background
            this.canvas.drawBitmap(this.background, 0, 0, this.paintbrush);

            if (this.cat.isAllowedToPlay() && this.enemies.size()>0) {
                // @TODO:  Draw a stationary object on the screen (Player)
                this.paintbrush.setStyle(Paint.Style.STROKE);
                this.paintbrush.setStrokeWidth(10);
                this.paintbrush.setColor(Color.WHITE);

                try{
                    this.canvas.drawBitmap(resizeBitmapMatrix(this.cat.catImage),this.cat.getxPosition(),this.cat.getyPosition(),this.paintbrush);
                    //this.canvas.drawRect(this.cat.getHitbox(), this.paintbrush);

                    for(int i=0;i<cat.getLives();i++){
                        this.canvas.drawBitmap(resizeLifeBitmapMatrix(this.cat.heart),10+this.cat.getLifeGap(),10,this.paintbrush);
                        this.paintbrush.setStrokeWidth(1);
                        this.paintbrush.setTextSize(50);
                        this.canvas.drawText("Score: "+this.score, 10, 100, paintbrush);
                        this.cat.updateLifeGap();
                    }

                    this.cat.setLifeGap(10);

                    for (int i = 0; i < this.lifeGivers.size(); i++) {
                        this.canvas.drawBitmap(resizeBitmapMatrix(this.lifeGivers.get(i).heartImage),this.lifeGivers.get(i).getxPosition(),this.lifeGivers.get(i).getyPosition(),this.paintbrush);
                        //this.canvas.drawRect(this.enemies.get(i).getHitbox(), this.paintbrush);
                    }

                    for (int i = 0; i < this.enemies.size(); i++) {
                        this.canvas.drawBitmap(resizeBitmapMatrix(this.enemies.get(i).monsterImage),this.enemies.get(i).getxPosition(),this.enemies.get(i).getyPosition(),this.paintbrush);
                        //this.canvas.drawRect(this.enemies.get(i).getHitbox(), this.paintbrush);
                    }
                } catch (Exception e){this.drawGame();
                }

            } else if(this.cat.isAllowedToPlay() && this.enemies.size()<=0){
                this.paintbrush.setStyle(Paint.Style.STROKE);
                this.paintbrush.setStrokeWidth(10);
                this.paintbrush.setColor(Color.WHITE);

                try{
                    this.canvas.drawBitmap(resizeBitmapMatrix(this.cat.catImage),this.cat.getxPosition(),this.cat.getyPosition(),this.paintbrush);
                    //this.canvas.drawRect(this.cat.getHitbox(), this.paintbrush);

                    for(int i=0;i<cat.getLives();i++){
                        this.canvas.drawBitmap(resizeLifeBitmapMatrix(this.cat.heart),10+this.cat.getLifeGap(),10,this.paintbrush);
                        this.paintbrush.setStrokeWidth(1);
                        this.paintbrush.setTextSize(50);
                        this.canvas.drawText("Score: "+this.score, 10, 100, paintbrush);
                        this.cat.updateLifeGap();
                    }

                    this.cat.setLifeGap(10);

                    for (int i = 0; i < this.lifeGivers.size(); i++) {
                        this.canvas.drawBitmap(resizeBitmapMatrix(this.lifeGivers.get(i).heartImage),this.lifeGivers.get(i).getxPosition(),this.lifeGivers.get(i).getyPosition(),this.paintbrush);
                        //this.canvas.drawRect(this.enemies.get(i).getHitbox(), this.paintbrush);
                    }

                } catch (Exception e){this.drawGame();
                }
            } else {
                // Game Over Draw
                for (int i = 0; i < this.enemies.size(); i++) {
                    this.enemies.remove(i);
                }
                if(this.score<this.winScore){
                    this.paintbrush.setColor(Color.WHITE);
                    this.paintbrush.setTextSize(100);
                    this.paintbrush.setStrokeWidth(10);
                    this.canvas.drawText("GAME OVER", screenWidth / 2, screenHeight / 2, paintbrush);
                    this.paintbrush.setStrokeWidth(1);
                    this.paintbrush.setTextSize(50);
                    this.canvas.drawText("Swipe to Restart", screenWidth / 2 , screenHeight / 2+150, paintbrush);
                    this.paintbrush.setTextSize(100);
                    this.paintbrush.setStrokeWidth(10);
                    if(this.gameOverCount == 0){
                        this.playGameOver();
                        this.gameOverCount++;
                    }
                } else{
                    this.paintbrush.setColor(Color.WHITE);
                    this.paintbrush.setTextSize(100);
                    this.paintbrush.setStrokeWidth(10);
                    this.canvas.drawText("You Win", screenWidth / 2, screenHeight / 2, paintbrush);
                    this.paintbrush.setStrokeWidth(1);
                    this.paintbrush.setTextSize(50);
                    this.canvas.drawText("Swipe to Restart", screenWidth / 2 , screenHeight / 2+150, paintbrush);
                    this.paintbrush.setTextSize(100);
                    this.paintbrush.setStrokeWidth(10);
                    if(this.gameOverCount == 0){
                        this.playTaDa();
                        this.gameOverCount++;
                    }
                }

            }

            this.holder.unlockCanvasAndPost(this.canvas);
        }

    }

    public void playBackgroundMusic(){
        this.backgroundSound.start();
    }

    public void playScreamMusic(){
        this.scream.start();
    }

    public void playKillYou(){
        this.killYou.start();
    }

    public void playTaDa(){
        this.tada.start();
    }

    public void playGameOver(){
        this.gameOver.start();
    }

    public void playLifeUp(){
        this.lifeUp.start();
    }

    public void stopSounds(){
        this.lifeUp.pause();
        this.gameOver.pause();
        this.backgroundSound.pause();
        this.tada.pause();
        this.killYou.pause();
        this.scream.pause();
    }

    public Bitmap resizeBitmapMatrix(Bitmap bm){
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) 100) / width;
        float scaleHeight = ((float) 100) / height;
        // create a matrix for the manipulation
        Matrix matrix = new Matrix();
        // resize the bit map
        matrix.postScale(scaleWidth, scaleHeight);

        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
        return resizedBitmap;
    }

    public Bitmap resizeLifeBitmapMatrix(Bitmap bm){
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) 50) / width;
        float scaleHeight = ((float) 50) / height;
        // create a matrix for the manipulation
        Matrix matrix = new Matrix();
        // resize the bit map
        matrix.postScale(scaleWidth, scaleHeight);

        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
        return resizedBitmap;
    }

    // Function to either detect collision and delete the enemy object from list, or update the x and y coordinates of enemy
    public void updateGame() {
        if(this.cat.isAllowedToPlay()){
            for (int i = 0; i < this.enemies.size(); i++) {
                if (this.cat.getHitbox().intersect(this.enemies.get(i).getHitbox())) {
                    this.cat.reduceLives();
                    this.enemies.remove(i);
                    this.playScreamMusic();
                }else {
                    this.enemies.get(i).updateEnemyPosition(this.cat.getxPosition(), this.cat.getyPosition());
                    Log.d("Update Enemy Position", "Update Enemy Position");
                }
            }
            for(int i=0;i<this.lifeGivers.size();i++){
                if(this.cat.getHitbox().intersect(this.lifeGivers.get(i).getHitbox())){
                    this.cat.increaseLives();
                    this.lifeGivers.remove(i);
                    this.playLifeUp();
                } else{
                    this.lifeGivers.get(i).updateHeartPosition(this.cat.getxPosition(), this.cat.getyPosition());
                }
            }

            if(this.enemies.size()==0){
                this.spawnEnemy();
            }
            this.cat.updateHitbox();
        } else {
            // NOTHING
        }

    }

    public void spawnCat() {
        // put player in middle of screen --> you may have to adjust the Y position
        // depending on your device / emulator
        this.cat = new Cat(this.getContext(), this.screenHeight, this.screenWidth);
    }

    public void spawnEnemy() {
        Random rand = new Random();
        for (int i = 0; i <= rand.nextInt(3); i++) {
            this.enemies.add(new Enemy(this.getContext(), this.screenHeight, this.screenWidth));
        }
    }

    public void spawnLifeGiver() {
        Random rand = new Random();
        for (int i = 0; i <= rand.nextInt(3); i++) {
            this.lifeGivers.add(new LifeGiver(this.getContext(), this.screenHeight, this.screenWidth));
        }
    }

    public void resetGame() {
        try {
            Thread.sleep(1000);
            this.score = 0;
            this.gameOverCount = 0;
            this.cat.resetLives();
            this.spawnEnemy();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
