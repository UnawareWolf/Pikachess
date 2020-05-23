package com.example.pikachess.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.pikachess.R;

import java.util.List;

public class GameBackground {

    private static final int CONTROL_PANEL_HEIGHT = 480;
    private Bitmap image;
    private int x,y;
    private int canvasWidth;
    private Rect backgroundPos;
    private Rect framePos;

    public GameBackground(Context context, int canvasWidth) {
        image = BitmapFactory.decodeResource(context.getResources(), R.drawable.littleroot);
        x = 0;
        y = 0;
        framePos = new Rect(0, 0, image.getWidth(), image.getHeight() - CONTROL_PANEL_HEIGHT);
        backgroundPos = new Rect(framePos);
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, backgroundPos, framePos, null);
    }

//    public void changeBackgroundPos(int up, int right) {
//        int bLeft = backgroundPos.left + right;
//        int bTop = backgroundPos.top + up;
//        int bRight = backgroundPos.right - right;
//        int bBottom = backgroundPos.bottom - up;
//        backgroundPos.set(bLeft, bTop, bRight, bBottom);
//    }

    public void update(GameCharacter mainCharacter) {
//        int xVel = mainCharacter.getXVel();
//        int yVel = mainCharacter.getYVel();
//        int bLeft = backgroundPos.left + xVel;
//        int bTop = backgroundPos.top + yVel;
//        int bRight = backgroundPos.right + xVel;
//        int bBottom = backgroundPos.bottom + yVel;

        int xPos = mainCharacter.getX();
        int yPos = mainCharacter.getY();
        int bLeft = framePos.left + xPos;
        int bTop = framePos.top + yPos;
        int bRight = framePos.right + xPos;
        int bBottom = framePos.bottom + yPos;

        backgroundPos.setEmpty();
        backgroundPos.set(bLeft, bTop, bRight, bBottom);
    }

}
