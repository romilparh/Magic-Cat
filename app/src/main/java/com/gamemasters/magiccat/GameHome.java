package com.gamemasters.magiccat;

import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class GameHome extends AppCompatActivity implements View.OnClickListener {

    // Game Engine Variable
    private GameEngine gameEngine;

    // Define Variables for Screen
    Display display;
    Point size;
    int screenHeight;
    int screenWidth;

    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    private GestureDetector gestureDetector;
    View.OnTouchListener gestureListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.display = getWindowManager().getDefaultDisplay();
        this.size = new Point();

        this.display.getSize(size);
        this.screenWidth = size.x;
        this.screenHeight = size.y;

        // Gesture detection
        this.gestureDetector = new GestureDetector(new MyGestureDetector());
        this.gestureListener = new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        };

        this.gameEngine = new GameEngine(this, screenWidth, screenHeight);
        this.gameEngine.setOnClickListener(GameHome.this);
        this.gameEngine.setOnTouchListener(gestureListener);
        this.setContentView(gameEngine);
    }

    class MyGestureDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            try {
                // downward swipe
                if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY){
                    Toast.makeText(GameHome.this, "Downward Swipe"+e2.getY()+" "+e1.getY(), Toast.LENGTH_SHORT).show();
                    if(gameEngine.cat.isAllowedToPlay()){
                        for(int i = 0;i<gameEngine.enemies.size();i++){
                            if(gameEngine.enemies.get(i).getSignBoolean()==true){
                                gameEngine.enemies.remove(i);
                            }
                        }
                        gameEngine.spawnEnemy();
                    } else{
                        gameEngine.resetGame();
                    }
                }


                else if (Math.abs(e2.getY() - e1.getY()) > SWIPE_MAX_OFF_PATH && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY){
                    Toast.makeText(GameHome.this, "Upward Swipe"+e2.getY()+e1.getY(), Toast.LENGTH_SHORT).show();
                    if(gameEngine.cat.isAllowedToPlay()){
                        for(int i = 0;i<gameEngine.enemies.size();i++){
                            if(gameEngine.enemies.get(i).getSignBoolean()==true){
                                gameEngine.enemies.remove(i);
                            }
                        }
                        gameEngine.spawnEnemy();
                    } else{
                        gameEngine.resetGame();
                    }
                }

                    // right to left swipe

                else if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    Toast.makeText(GameHome.this, "Left Swipe", Toast.LENGTH_SHORT).show();
                    if(gameEngine.cat.isAllowedToPlay()){
                        for(int i = 0;i<gameEngine.enemies.size();i++){
                            if(gameEngine.enemies.get(i).getSignBoolean()==false){
                                gameEngine.enemies.remove(i);
                            }
                        }
                        gameEngine.spawnEnemy();
                    } else{
                        gameEngine.resetGame();
                    }
                }
                else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    Toast.makeText(GameHome.this, "Right Swipe", Toast.LENGTH_SHORT).show();
                    if(gameEngine.cat.isAllowedToPlay()){
                        for(int i = 0;i<gameEngine.enemies.size();i++){
                            if(gameEngine.enemies.get(i).getSignBoolean()==false){
                                gameEngine.enemies.remove(i);
                            }
                        }
                        gameEngine.spawnEnemy();
                    } else{
                        gameEngine.resetGame();
                    }
                }
            } catch (Exception e) {
                // nothing
            }
            return false;
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        gameEngine.pauseGame();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameEngine.resumeGame();
    }



    @Override
    public void onClick(View v) {
    }
}

// Start Rebasing Project to it's OOP Structure

// Left Outs: Image of Ghost and Cat