package com.gamemasters.magiccat;

import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;

public class GameHome extends AppCompatActivity {

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

        setContentView(R.layout.activity_game_home);
    }
}
