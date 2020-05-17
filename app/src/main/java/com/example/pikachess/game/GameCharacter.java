package com.example.pikachess.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.example.pikachess.R;

public class GameCharacter {

    private boolean isMoving;
    //private Bitmap coreSpriteSheet;
    private CharacterSpritesheet spritesheet;
    private int canvasWidth;

    public GameCharacter(Context context, int canvasWidth) {
        this.canvasWidth = canvasWidth;
        isMoving = true;
        spritesheet = new CharacterSpritesheet(context, canvasWidth);
        //coreSpriteSheet = BitmapFactory.decodeResource(context.getResources(), R.drawable.peppapigsprites);
    }

    public boolean getIsMoving() {
        return this.isMoving;
    }

    public void setIsMoving(boolean isMoving) {
        this.isMoving = isMoving;
    }

    public void draw(Canvas canvas) {
        //canvas.drawBitmap(coreSpriteSheet, 0, 0, null);
        spritesheet.draw(canvas);
    }
}
