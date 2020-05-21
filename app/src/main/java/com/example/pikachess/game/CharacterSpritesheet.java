package com.example.pikachess.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;

import com.example.pikachess.R;

import java.util.ArrayList;
import java.util.List;

public class CharacterSpritesheet {

    private Bitmap image;
    private static final int CHARACTER_SIZE = 480;
    private static final int NUMBER_OF_ANIMATIONS = 11;
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
        image = BitmapFactory.decodeResource(context.getResources(), R.drawable.pika_character_basic_try_2);



        //image.getWidth();
        //int initialCharacterHeight = initialImage.getHeight();

        //int scaleFactor = CHARACTER_SIZE / initialCharacterHeight;

        // CREATE A MATRIX FOR THE MANIPULATION
        // RESIZE THE BIT MAP
        //Bitmap resizedBitmap = Bitmap.createBitmap(initialImage, 0, 0, scaleFactor, scaleFactor, matrix, false);
        //image = Bitmap.createBitmap(initialImage, 0, 0, initialImage.getWidth()*scaleFactor, CHARACTER_SIZE, matrix, false);
        //image.recycle();
        //image = getResizedBitmap(initialImage, scaleFactor*initialImage.getWidth(), CHARACTER_SIZE);

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

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }

    public void draw(Canvas canvas, CharacterState characterState) {
        if (characterState == CharacterState.MovingDown) {
            canvas.drawBitmap(image, animationSections.get(animationNumber), characterPos, null);
            nextAnimationNumber();
        }
        else if (characterState == CharacterState.Stationary) {
            canvas.drawBitmap(image, animationSections.get(0), characterPos, null);
        }
        else {
            canvas.drawBitmap(image, animationSections.get(0), characterPos, null);
        }


        //canvas.drawBitmap(image, x, y, null);
    }

    private void nextAnimationNumber() {
        if (drawCount > 3) {
            if (animationNumber < NUMBER_OF_ANIMATIONS - 1) {
                animationNumber++;
            }
            else {
                animationNumber = 1;
            }
            drawCount = 0;
        }
        drawCount++;
//        if (animationNumber < NUMBER_OF_ANIMATIONS - 1) {
//            animationNumber++;
//        }
//        else {
//            animationNumber = 0;
//        }
    }

    public void update() {
        //y++;
    }

}
