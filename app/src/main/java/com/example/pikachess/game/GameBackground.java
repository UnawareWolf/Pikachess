package com.example.pikachess.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.pikachess.R;

import java.util.List;

public class GameBackground extends SpriteSheet{

    private static final int CONTROL_PANEL_HEIGHT = 480;
    private Bitmap unscaledImage;
    private Paint filterPaint;
    //private Bitmap image;
    private int x,y;
    private int canvasWidth;
    private Rect backgroundPos;
    private Rect framePos;

    public GameBackground(Context context, int canvasWidth) {
        this.canvasWidth = canvasWidth;
        Options options = new BitmapFactory.Options();
        options.inScaled = false;
        filterPaint = new Paint(Paint.FILTER_BITMAP_FLAG);
        image = BitmapFactory.decodeResource(context.getResources(), R.drawable.littleroot, options);
        resizeBitmap((float) getScaleFactor());
        //image = Bitmap.createScaledBitmap(unscaledImage, unscaledImage.getWidth()*2, unscaledImage.getHeight()*2, false);
        x = 0;
        y = 0;
        framePos = new Rect(0, 0, image.getWidth(), image.getHeight() - CONTROL_PANEL_HEIGHT);
        backgroundPos = new Rect(framePos);
    }

    private double getScaleFactor() {
//        int intendedWidth = PikaGame.GRID_SQUARE_SIZE * PikaGame.SQUARES_ACROSS_SCREEN;
//        return (double) intendedWidth / canvasWidth;

        double numberOfSquaresCurrentlyAcrossScreen = (double) canvasWidth / PikaGame.GRID_SQUARE_SIZE;
        return numberOfSquaresCurrentlyAcrossScreen / PikaGame.SQUARES_ACROSS_SCREEN;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, backgroundPos, framePos, filterPaint);
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

        int xPos = (int) mainCharacter.getX();
        int yPos = (int) mainCharacter.getY();
        int bLeft = framePos.left + xPos;
        int bTop = framePos.top + yPos;
        int bRight = framePos.right + xPos;
        int bBottom = framePos.bottom + yPos;

        backgroundPos.setEmpty();
        backgroundPos.set(bLeft, bTop, bRight, bBottom);
    }

}
