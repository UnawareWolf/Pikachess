package com.example.pikachess.game;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
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

    private Rect spritePosition;

    private int drawCount = 0;
    private int animationNumber = 1;
    private boolean moveWithBackground;

    public CharacterSpriteSheet(Context context, GameCharacter gameCharacter, int centreX, int centreY) {
        super();
        canvasWidth = gameCharacter.getCanvasWidth();
        numberOfAnimations = NUMBER_OF_ANIMATIONS;

        image = BitmapFactory.decodeResource(context.getResources(), R.drawable.pokemon_fatty_not_resized, options);
        resizeBitmap((float) gameCharacter.getBitmapResizeFactor(), (float) gameCharacter.getBitmapResizeFactor());

        sectionWidth = image.getWidth() / numberOfAnimations;
        sectionHeight = image.getHeight();
        moveWithBackground = gameCharacter.getClass() != PlayerCharacter.class;

        spritePosition = new Rect();
        setRect(centreX, centreY);
        createAnimationSections();

        numberOfCyclesPerGridSquare = PikaGame.GRID_SQUARE_SIZE / 8;
    }

    public void setRect(int centreX, int centreY) {
        spritePosition.set(centreX - sectionWidth / 2, centreY  - sectionHeight + sectionWidth / 2, centreX + sectionWidth / 2, centreY + sectionWidth / 2);

    }

    public void draw(Canvas canvas, CharacterState characterState) {
        if (characterState == CharacterState.MovingDown) {
            canvas.drawBitmap(image, animationSections.get(animationNumber), spritePosition, filterPaint);
            nextFrame(walkingDown);
            //nextAnimationNumber();
        }
        else if (characterState == CharacterState.MovingUp) {
            canvas.drawBitmap(image, animationSections.get(animationNumber), spritePosition, filterPaint);
            nextFrame(walkingUp);
        }
        else if (characterState == CharacterState.MovingLeft) {
            canvas.drawBitmap(image, animationSections.get(animationNumber), spritePosition, filterPaint);
            nextFrame(walkingLeft);
        }
        else if (characterState == CharacterState.MovingRight) {
            canvas.drawBitmap(image, animationSections.get(animationNumber), spritePosition, filterPaint);
            nextFrame(walkingRight);
        }
        else if (characterState == CharacterState.StationaryUp) {
            canvas.drawBitmap(image, animationSections.get(1), spritePosition, filterPaint);
        }
        else if (characterState == CharacterState.StationaryLeft) {
            canvas.drawBitmap(image, animationSections.get(2), spritePosition, filterPaint);
        }
        else if (characterState == CharacterState.StationaryRight) {
            canvas.drawBitmap(image, animationSections.get(11), spritePosition, filterPaint);
        }
        else if (characterState == CharacterState.StationaryDown) {
            canvas.drawBitmap(image, animationSections.get(0), spritePosition, filterPaint);
        }
        else {
            canvas.drawBitmap(image, animationSections.get(0), spritePosition, filterPaint);
        }
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

}
