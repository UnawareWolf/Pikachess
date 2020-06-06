package com.example.pikachess.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.pikachess.R;

import java.util.ArrayList;
import java.util.List;

public class PikaSprite extends SpriteSheet {

    private static final int NUMBER_OF_ANIMATIONS = 14;
    private int characterWidth, characterHeight;
    private List<Rect> animationSections;
    int centreX, centreY;

    public PikaSprite(Context context, int resourceID, boolean playerPikamon, int canvasWidth, int canvasHeight) {
        super();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        image = BitmapFactory.decodeResource(context.getResources(), resourceID, options);
        double targetWidth = canvasWidth / (float) 6;
        double currentWidth = image.getWidth() / (float) NUMBER_OF_ANIMATIONS;
        double resizeFactor = targetWidth / currentWidth;
        resizeBitmap((int) resizeFactor, (int) resizeFactor);


        filterPaint = new Paint(Paint.FILTER_BITMAP_FLAG);

        characterWidth = image.getWidth() / NUMBER_OF_ANIMATIONS;
        characterHeight = image.getHeight();


        animationSections = new ArrayList<>();

        for (int i = 0; i < NUMBER_OF_ANIMATIONS; i++) {
            int topLeftX = i*characterWidth;
            int topLeftY = 0;
            int bottomRightX = (i + 1)*characterWidth;
            int bottomRightY = characterHeight;
            Rect animationSection = new Rect();
            animationSection.set(topLeftX, topLeftY, bottomRightX, bottomRightY);
            animationSections.add(animationSection);
        }

        if (playerPikamon) {
            centreX = (int) (canvasWidth / (float) 4);
            centreY = (int) (canvasHeight / (float) 4);
            backgroundPos = animationSections.get(1);
        }
        else {
            centreX = (int) (3 * canvasWidth / (float) 4);
            centreY = (int) (canvasHeight / (float) 8);
            backgroundPos = animationSections.get(0);
        }


        framePos = new Rect(centreX - characterWidth / 2, centreY  - characterHeight + characterWidth / 2, centreX + characterWidth / 2, centreY + characterWidth / 2);




    }

}
