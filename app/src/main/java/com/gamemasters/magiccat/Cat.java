package com.gamemasters.magiccat;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class Cat {
    private Context context;

    protected int xPosition;
    protected int yPosition;

    private int width;
    private int lifeGap;



    private Rect hitBox;

    private int movement;
    private int lives;

    Bitmap catImage,heart;

    public Cat(Context c, int screenHeight, int screenWidth) {

        this.context = c;
        this.catImage = BitmapFactory.decodeResource(context.getResources(),R.drawable.cat);
        this.heart = BitmapFactory.decodeResource(context.getResources(),R.drawable.heart);
        this.xPosition = (screenWidth/2)-50;
        this.yPosition = (int)(0.7*screenHeight);
        this.width = 100;
        this.lifeGap = 0;
        this.movement = 0;

        this.lives = 5;

        this.hitBox = new Rect(this.xPosition, this.yPosition, this.xPosition + this.width, this.xPosition + this.yPosition);
    }

    public Rect getHitbox() {
        return this.hitBox;
    }

    public int getxPosition() {
        return this.xPosition;
    }

    public void updateHitbox() {

        // update the position of the hit-box
        this.hitBox.top = this.yPosition;
        this.hitBox.left = this.xPosition;
        this.hitBox.right = this.xPosition + 100;
        this.hitBox.bottom = this.yPosition + 100;
    }

    public int getyPosition() {
        return this.yPosition;
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

    public void resetLives(){
        this.lives = 5;
    }

    public int getLives(){
        return this.lives;
    }

    public int getLifeGap(){
        return this.lifeGap;
    }

    public void updateLifeGap(){
        lifeGap+=75;
    }

    public void setLifeGap(int lifeGap){
        this.lifeGap = lifeGap;
    }
}
