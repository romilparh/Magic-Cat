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

    public Cat(Context c, int xPosition, int yPosition, int width, int height, int movement) {

        this.context = c;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.width = width;
        this.height = height;
        this.movement = movement;

        this.hitBox = new Rect(this.xPosition,this.yPosition,this.xPosition+this.width, this.xPosition+yPosition);
    }

    public void updateHitbox() {
        hitBox.left = this.xPosition;
        hitBox.top = this.yPosition;
        hitBox.right = this.xPosition + this.width;
        hitBox.bottom = this.yPosition + this.height;
    }

    public Rect getHitbox() {
        return this.hitBox;
    }
}
