package com.example.pikachess.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.pikachess.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CharacterSpriteSheet extends SpriteSheet {

    //private Bitmap image;
    private static final int NUMBER_OF_ANIMATIONS = 14;
    private int numberOfCyclesPerGridSquare;
    private int stationaryNum = 0;
    private List<Integer> walkingUp = new ArrayList<>(Arrays.asList(4, 9));
    private List<Integer> walkingLeft = new ArrayList<>(Arrays.asList(5, 6));
    private List<Integer> walkingDown = new ArrayList<>(Arrays.asList(3, 10));
    private List<Integer> walkingRight = new ArrayList<>(Arrays.asList(7, 8));
    private int x,y;
    private Paint filterPaint;
    private int characterWidth;
    private int characterHeight;
    private int canvasWidth;
    Rect characterPos;
    private List<Rect> animationSections;
    private int drawCount = 0;
    private int animationNumber = 1;

    public CharacterSpriteSheet(Context context, GameCharacter mainCharacter) {
        this.canvasWidth = mainCharacter.getCanvasWidth();


        Options options = new Options();
        options.inScaled = false;


        image = BitmapFactory.decodeResource(context.getResources(), R.drawable.pokemon_fatty_not_resized, options);
        resizeBitmap((float) mainCharacter.getBitmapResizeFactor());
        filterPaint = new Paint(Paint.FILTER_BITMAP_FLAG);
        characterWidth = image.getWidth() / NUMBER_OF_ANIMATIONS;
        characterHeight = image.getHeight();
        x = canvasWidth / 2;
        y = canvasWidth / 2;
        animationSections = new ArrayList<>();
        characterPos = new Rect(x - characterWidth / 2, y  - characterHeight + characterWidth / 2, x + characterWidth / 2, y + characterWidth / 2);

        for (int i = 0; i < NUMBER_OF_ANIMATIONS; i++) {
            int topLeftX = i*characterWidth;
            int topLeftY = 0;
            int bottomRightX = (i + 1)*characterWidth;
            int bottomRightY = characterHeight;
            Rect animationSection = new Rect();
            animationSection.set(topLeftX, topLeftY, bottomRightX, bottomRightY);
            animationSections.add(animationSection);
        }
        numberOfCyclesPerGridSquare = PikaGame.GRID_SQUARE_SIZE / 8;
    }

    public void draw(Canvas canvas, CharacterState characterState) {
        if (characterState == CharacterState.MovingDown) {
            canvas.drawBitmap(image, animationSections.get(animationNumber), characterPos, filterPaint);
            nextFrame(walkingDown);
            //nextAnimationNumber();
        }
        else if (characterState == CharacterState.MovingUp) {
            canvas.drawBitmap(image, animationSections.get(animationNumber), characterPos, filterPaint);
            nextFrame(walkingUp);
        }
        else if (characterState == CharacterState.MovingLeft) {
            canvas.drawBitmap(image, animationSections.get(animationNumber), characterPos, filterPaint);
            nextFrame(walkingLeft);
        }
        else if (characterState == CharacterState.MovingRight) {
            canvas.drawBitmap(image, animationSections.get(animationNumber), characterPos, filterPaint);
            nextFrame(walkingRight);
        }
        else if (characterState == CharacterState.StationaryUp) {
            canvas.drawBitmap(image, animationSections.get(1), characterPos, filterPaint);
        }
        else if (characterState == CharacterState.StationaryLeft) {
            canvas.drawBitmap(image, animationSections.get(2), characterPos, filterPaint);
        }
        else if (characterState == CharacterState.StationaryRight) {
            canvas.drawBitmap(image, animationSections.get(11), characterPos, filterPaint);
        }
        else if (characterState == CharacterState.StationaryDown) {
            canvas.drawBitmap(image, animationSections.get(0), characterPos, filterPaint);
        }
        else {
            canvas.drawBitmap(image, animationSections.get(0), characterPos, filterPaint);
        }


        //canvas.drawBitmap(image, x, y, null);
    }

    public int getNumberOfCyclesPerGridSquare() {
        return numberOfCyclesPerGridSquare;
    }

    private void nextFrame(List<Integer> walkingDirection) {
        int animationIndex = walkingDirection.indexOf(animationNumber);
        if (animationIndex >= 0) {
            if (drawCount > 8) {
                if (animationIndex < walkingDirection.size() - 1) {
                    animationNumber = walkingDirection.get(animationIndex + 1);
                }
                else {
                    animationNumber = walkingDirection.get(0);
                }
                drawCount = 0;
            }
            drawCount++;
        }
        else {
            animationNumber = walkingDirection.get(0);
        }
    }

    public void update() {
        //y++;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
