package com.example.pikachess.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;

import com.example.pikachess.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CharacterSpritesheet {

    private Bitmap image;
    private static final int NUMBER_OF_ANIMATIONS = 14;
    private int stationaryNum = 0;
    private List<Integer> walkingUp = new ArrayList<>(Arrays.asList(4, 9));
    private List<Integer> walkingLeft = new ArrayList<>(Arrays.asList(5, 6));
    private List<Integer> walkingDown = new ArrayList<>(Arrays.asList(3, 10));
    private List<Integer> walkingRight = new ArrayList<>(Arrays.asList(7, 8));
    private int x,y;
    private int characterWidth;
    private int characterHeight;
    private int canvasWidth;
    Rect characterPos;
    private List<Rect> animationSections;
    private int drawCount = 0;
    private int animationNumber = 1;

    public CharacterSpritesheet(Bitmap bmp,  int x, int y) {
        image = bmp;
        this.x = x;
        this.y = y;
    }

    public CharacterSpritesheet(Context context, int canvasWidth) {
        this.canvasWidth = canvasWidth;
        image = BitmapFactory.decodeResource(context.getResources(), R.drawable.pokemon_fatty);

        characterWidth = image.getWidth() / NUMBER_OF_ANIMATIONS;
        characterHeight = image.getHeight();
        this.x = canvasWidth / 2;
        this.y = canvasWidth / 2;
        animationSections = new ArrayList<>();
        characterPos = new Rect(x - characterWidth / 2, y - characterHeight / 2, x + characterWidth / 2, y + characterHeight / 2);

        for (int i = 0; i < NUMBER_OF_ANIMATIONS; i++) {
            int topLeftX = i*characterWidth;
            int topLeftY = 0;
            int bottomRightX = (i + 1)*characterWidth;
            int bottomRightY = characterHeight;
            Rect animationSection = new Rect();
            animationSection.set(topLeftX, topLeftY, bottomRightX, bottomRightY);
            animationSections.add(animationSection);
        }
    }

//    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
//        int width = bm.getWidth();
//        int height = bm.getHeight();
//        float scaleWidth = ((float) newWidth) / width;
//        float scaleHeight = ((float) newHeight) / height;
//        // CREATE A MATRIX FOR THE MANIPULATION
//        Matrix matrix = new Matrix();
//        // RESIZE THE BIT MAP
//        matrix.postScale(scaleWidth, scaleHeight);
//
//        // "RECREATE" THE NEW BITMAP
//        Bitmap resizedBitmap = Bitmap.createBitmap(
//                bm, 0, 0, width, height, matrix, false);
//        bm.recycle();
//        return resizedBitmap;
//    }

    public void draw(Canvas canvas, CharacterState characterState) {
        if (characterState == CharacterState.MovingDown) {
            canvas.drawBitmap(image, animationSections.get(animationNumber), characterPos, null);
            nextFrame(walkingDown);
            //nextAnimationNumber();
        }
        else if (characterState == CharacterState.MovingUp) {
            canvas.drawBitmap(image, animationSections.get(animationNumber), characterPos, null);
            nextFrame(walkingUp);
        }
        else if (characterState == CharacterState.MovingLeft) {
            canvas.drawBitmap(image, animationSections.get(animationNumber), characterPos, null);
            nextFrame(walkingLeft);
        }
        else if (characterState == CharacterState.MovingRight) {
            canvas.drawBitmap(image, animationSections.get(animationNumber), characterPos, null);
            nextFrame(walkingRight);
        }
        else if (characterState == CharacterState.StationaryUp) {
            canvas.drawBitmap(image, animationSections.get(1), characterPos, null);
        }
        else if (characterState == CharacterState.StationaryLeft) {
            canvas.drawBitmap(image, animationSections.get(2), characterPos, null);
        }
        else if (characterState == CharacterState.StationaryRight) {
            canvas.drawBitmap(image, animationSections.get(11), characterPos, null);
        }
        else if (characterState == CharacterState.StationaryDown) {
            canvas.drawBitmap(image, animationSections.get(0), characterPos, null);
        }
        else {
            canvas.drawBitmap(image, animationSections.get(0), characterPos, null);
        }


        //canvas.drawBitmap(image, x, y, null);
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

    private void nextAnimationNumber() {
//        if (drawCount > 3) {
//            if (animationNumber < NUMBER_OF_ANIMATIONS - 1) {
//                animationNumber++;
//            }
//            else {
//                animationNumber = 1;
//            }
//            drawCount = 0;
//        }
//        drawCount++;
    }

    public void update() {
        //y++;
    }

}
