package com.example.pikachess.game;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.pikachess.R;

public class GameBackground extends SpriteSheet{

    private static final int CONTROL_PANEL_HEIGHT = 480;
//    private Paint filterPaint;
    //private Bitmap image;
    //private int x, y;
    private int canvasWidth;
//    private Rect backgroundPos;
//    private Rect framePos;
    private Rect initialPos;
    private double bitmapResizeFactor;
    private double[] startingShift;

//    public GameBackground(Context context, int canvasWidth) {
    public GameBackground(Context context, PikaGame pikaGame) {
        super();
        canvasWidth = pikaGame.getCanvasWidth();

        Options options = new BitmapFactory.Options();
        options.inScaled = false;
        filterPaint = new Paint(Paint.FILTER_BITMAP_FLAG);
        image = BitmapFactory.decodeResource(context.getResources(), R.drawable.littleroot_route_101, options);
        setBitmapResizeFactor();
        resizeBitmap((float) bitmapResizeFactor, (float) bitmapResizeFactor);

//        x = 0;
//        y = 0;
        startingShift = pikaGame.getPixelMap().getStartingPixelPosition(bitmapResizeFactor);
        framePos = new Rect(0, 0, image.getWidth(), image.getHeight());
        initialPos = new Rect((int) -startingShift[0], (int) -startingShift[1], image.getWidth() - (int) startingShift[0], image.getHeight() - (int) startingShift[1]);
        //initialPos = new Rect(framePos);
        backgroundPos = new Rect(initialPos);
    }

    private void setBitmapResizeFactor() {
//        int intendedWidth = PikaGame.GRID_SQUARE_SIZE * PikaGame.SQUARES_ACROSS_SCREEN;
//        return (double) intendedWidth / canvasWidth;

        double numberOfSquaresCurrentlyAcrossScreen = (double) canvasWidth / PikaGame.GRID_SQUARE_SIZE;
        bitmapResizeFactor = numberOfSquaresCurrentlyAcrossScreen / PikaGame.SQUARES_ACROSS_SCREEN;
        //return numberOfSquaresCurrentlyAcrossScreen / PikaGame.SQUARES_ACROSS_SCREEN;
    }

    public double getBitmapResizeFactor() {
        return bitmapResizeFactor;
    }

//    public void draw(Canvas canvas) {
//        canvas.drawBitmap(image, backgroundPos, framePos, filterPaint);
//    }

    public void update(PlayerCharacter mainCharacter) {
        int xPos = (int) mainCharacter.getXMoved();
        int yPos = (int) mainCharacter.getYMoved();
        int bLeft = initialPos.left + xPos;
        int bTop = initialPos.top + yPos;
        int bRight = initialPos.right + xPos;
        int bBottom = initialPos.bottom + yPos;

        backgroundPos.setEmpty();
        backgroundPos.set(bLeft, bTop, bRight, bBottom);
    }

    public double[] getStartingShift() {
        return startingShift;
    }
}
