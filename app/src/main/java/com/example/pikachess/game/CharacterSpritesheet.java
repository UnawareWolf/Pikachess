package com.example.pikachess.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.pikachess.R;

import java.util.ArrayList;
import java.util.List;

public class CharacterSpritesheet {

    private Bitmap image;
    private static final int CHARACTER_SIZE = 120;
    private static final int NUMBER_OF_ANIMATIONS = 5;
    private int x,y;
    private int characterWidth;
    private int characterHeight;
    private int canvasWidth;
    Rect characterPos;
    private List<Rect> animationSections;
    private int animationNumber = 0;

    public CharacterSpritesheet(Bitmap bmp,  int x, int y) {
        image = bmp;
        this.x = x;
        this.y = y;
    }

    public CharacterSpritesheet(Context context, int canvasWidth) {
        this.canvasWidth = canvasWidth;
        image = BitmapFactory.decodeResource(context.getResources(), R.drawable.peppapigsprites);
        //image.getWidth();
        characterWidth = image.getWidth() / NUMBER_OF_ANIMATIONS;
        this.x = canvasWidth / 2;
        this.y = canvasWidth / 2;
        animationSections = new ArrayList<>();
        characterPos = new Rect(x - CHARACTER_SIZE / 2, y - CHARACTER_SIZE / 2, x + CHARACTER_SIZE / 2, y + CHARACTER_SIZE / 2);

        for (int i = 0; i < NUMBER_OF_ANIMATIONS; i++) {
            int topLeftX = i*characterWidth;
            int topLeftY = 0;
            int bottomRightX = (i + 1)*characterWidth;
            int bottomRightY = characterWidth;
            Rect animationSection = new Rect();
            animationSection.set(topLeftX, topLeftY, bottomRightX, bottomRightY);
            animationSections.add(animationSection);
        }
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, animationSections.get(animationNumber), characterPos, null);
        nextAnimationNumber();
        canvas.drawBitmap(image, x, y, null);
    }

    private void nextAnimationNumber() {
        if (animationNumber < NUMBER_OF_ANIMATIONS - 1) {
            animationNumber++;
        }
        else {
            animationNumber = 0;
        }
    }

    public void update() {
        //y++;
    }

}
