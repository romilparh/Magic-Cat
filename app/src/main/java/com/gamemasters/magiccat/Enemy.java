package com.gamemasters.magiccat;

import android.content.Context;
import android.graphics.Rect;

public class Enemy {

    private Context context;

    protected int xPosition;
    protected int yPosition;

    private int width;
    private int height;

    private Rect hitBox;

    public Enemy(Context c, int xPosition, int yPosition, int width, int height) {

        this.context = c;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.width = width;
        this.height = height;

        this.hitBox = new Rect(this.xPosition,this.yPosition,this.xPosition+this.width, this.xPosition+yPosition);
    }

    public void updateEnemyPosition() {
        // override this in the child class
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
