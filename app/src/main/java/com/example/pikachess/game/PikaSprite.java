package com.example.pikachess.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.pikachess.R;
import com.example.pikachess.game.battle.Pikamon;

import java.util.ArrayList;
import java.util.List;

public class PikaSprite extends SpriteSheet {

    private static final int NUMBER_OF_ANIMATIONS = 14;
    int centreX, centreY;
    private boolean playerPikamon;


    public PikaSprite(Context context, Pikamon pikamon) {
        super();
        numberOfAnimations = NUMBER_OF_ANIMATIONS;
        playerPikamon = pikamon.isPlayerPikamon();
        canvasWidth = pikamon.getCanvasDims()[0];
        canvasHeight = pikamon.getCanvasDims()[1];

        image = BitmapFactory.decodeResource(context.getResources(), pikamon.getImageID(), options);
        double targetWidth = canvasWidth / (float) 6;
        double currentWidth = image.getWidth() / (float) numberOfAnimations;
        double resizeFactor = targetWidth / currentWidth;
        resizeBitmap((int) resizeFactor, (int) resizeFactor);




        sectionWidth = image.getWidth() / numberOfAnimations;
        sectionHeight = image.getHeight();

        createAnimationSections();
        setPositionAndOrientation();

        framePos = new Rect(centreX - sectionWidth / 2, centreY  - sectionHeight + sectionWidth / 2, centreX + sectionWidth / 2, centreY + sectionWidth / 2);

    }

    private void setPositionAndOrientation() {
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
    }

}
