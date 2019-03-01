package com.gamemasters.magiccat;

import android.graphics.Point;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;

public class GameHome extends AppCompatActivity {

    // Game Engine Variable
    private GameEngine gameEngine;

    // Define Variables for Screen
    Display display;
    Point size;
    int screenHeight;
    int screenWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        display = getWindowManager().getDefaultDisplay();
        size = new Point();

        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;

        gameEngine = new GameEngine(this, screenWidth, screenHeight);
        setContentView(gameEngine);
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
    public boolean onTouchEvent(MotionEvent event) {

        int action = MotionEventCompat.getActionMasked(event);

        String DEBUG_TAG = "HELLO DEBUG";
        switch (action) {
            case (MotionEvent.ACTION_DOWN):
                Log.d(DEBUG_TAG, "Action was DOWN");
                for(int i=0;i<gameEngine.enemies.size();i++){
                    gameEngine.enemies.remove(i);
                }
                return true;
            case (MotionEvent.ACTION_UP):
                Log.d(DEBUG_TAG, "Action was UP");
                return true;
            case (MotionEvent.ACTION_CANCEL):
                Log.d(DEBUG_TAG, "Action was CANCEL");
                return true;
            case (MotionEvent.ACTION_OUTSIDE):
                Log.d(DEBUG_TAG, "Movement occurred outside bounds " +
                        "of current screen element");
                return true;
            default:
                return super.onTouchEvent(event);
        }

    }
}

// Start Rebasing Project to it's OOP Structure