package com.example.pikachess.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.example.pikachess.R;

public class GameCharacter {

    //private boolean isMoving;
    //private Bitmap coreSpriteSheet;
    private CharacterSpritesheet spritesheet;
    private CharacterState characterState;
    private int x, y;
    private int xVel, yVel;
    private int canvasWidth;

    public GameCharacter(Context context, int canvasWidth) {
        this.canvasWidth = canvasWidth;
        //isMoving = false;
        spritesheet = new CharacterSpritesheet(context, canvasWidth);
        characterState = CharacterState.Stationary;
        x = 0;
        y = 0;
        //coreSpriteSheet = BitmapFactory.decodeResource(context.getResources(), R.drawable.peppapigsprites);
    }

//    public boolean getIsMoving() {
//        return this.isMoving;
//    }

//    public void setIsMoving(boolean isMoving) {
//        this.isMoving = isMoving;
//    }

    public void setCharacterState(CharacterState characterState) {
        this.characterState = characterState;
    }

    public void draw(Canvas canvas) {
        //canvas.drawBitmap(coreSpriteSheet, 0, 0, null);
        spritesheet.draw(canvas, characterState);
    }

    public void update() {
        updateCharacterMotion();
    }

    private void updateCharacterMotion() {
        if (characterState == CharacterState.MovingLeft) {
            xVel = -5;
            yVel = 0;
            x--;
        }
        else if (characterState == CharacterState.MovingUp){
            xVel = 0;
            yVel = -5;
            y--;
        }
        else if (characterState == CharacterState.MovingRight) {
            xVel = 5;
            yVel = 0;
            x++;
        }
        else if (characterState == CharacterState.MovingDown){
            xVel = 0;
            yVel = 5;
            y++;
        }
        else {
            xVel = 0;
            yVel = 0;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getXVel() {
        return xVel;
    }

    public int getYVel() {
        return yVel;
    }
}
