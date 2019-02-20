package com.gamemasters.magiccat;

import android.content.Context;
import android.graphics.Rect;

import java.util.ArrayList;

public class Enemy {

    private Context context;

    protected int xPosition;
    protected int yPosition;

    private int width;
    private int height;

    // Sign is for the type of enemy, 0 and 1 for vertical and horizontal: the only two types of enemies there.
    private ArrayList <Integer> sign;

    Rect hitBox;

    public Enemy(Context c, int xPosition, int yPosition, int width, int height, ArrayList <Integer> sign) {

        this.sign =  new ArrayList();

        this.context = c;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.width = width;
        this.height = height;
        this.sign = sign;

        this.hitBox = new Rect(this.xPosition,this.yPosition,this.xPosition+this.width, this.xPosition+yPosition);
    }

    public void updateEnemyPosition() {
        // override this in the child class
    }

    public void setXPosition(int xPosition){
        this.xPosition = xPosition;
    }

    public void setYPosition(int yPosition){
        this.yPosition = yPosition;
    }

    public Rect getHitbox() {
        return this.hitBox;
    }
}
