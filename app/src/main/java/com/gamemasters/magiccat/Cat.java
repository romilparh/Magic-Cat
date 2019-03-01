package com.gamemasters.magiccat;

import android.content.Context;
import android.graphics.Rect;

public class Cat {
    private Context context;

    protected int xPosition;
    protected int yPosition;

    private int width;
    private int height;



    private Rect hitBox;

    private int movement;
    private int lives;

    public Cat(Context c, int screenHeight, int screenWidth) {

        this.context = c;
        this.xPosition = (screenWidth/2)-50;
        this.yPosition = (int)(0.7*screenHeight);
        this.width = 100;
        this.height = 100;
        this.movement = 0;

        this.lives = 5;

        this.hitBox = new Rect(this.xPosition,this.yPosition,this.xPosition+this.width, this.xPosition+yPosition);
    }

    public Rect getHitbox() {
        return this.hitBox;
    }

    public int getxPosition() {
        return xPosition;
    }

    public int getyPosition() {
        return yPosition;
    }

    public void reduceLives(){
        this.lives--;
    }

    public void updateMovement(int movement){
        // 1- Horizontal
        // 2- Vertical
        // other- invalid

        this.movement = movement;
    }

    public boolean movementKill(){
        if(this.movement == 1){
            return false;
        } else {
            return true;
        }
    }

    public boolean isAllowedToPlay(){
        if(this.lives<=0){
            return false;
        } else{
            return true;
        }
    }
}
