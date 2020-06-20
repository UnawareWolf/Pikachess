package com.example.pikachess.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import androidx.core.view.MotionEventCompat;

import com.example.pikachess.R;

public class ControllerButton {

    private Paint paint;
    private Paint borderPaint;
    private Circle circle;

    public ControllerButton(Context context, float centreX, float centreY, int radius, int colourID) {
        circle = new Circle(centreX, centreY, radius);
        paint = new Paint();
        paint.setColor(context.getResources().getColor(colourID));
        paint.setStyle(Paint.Style.FILL_AND_STROKE);

        borderPaint = new Paint();
        borderPaint.setColor(context.getResources().getColor(R.color.colorPrimaryDark));
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(6);
    }

    public void draw(Canvas canvas) {
        circle.draw(canvas, paint);
        circle.draw(canvas, borderPaint);
    }

    public void onTouchEventA(MotionEvent event, PikaGame pikaGame, PlayerCharacter mainCharacter, PixelMap pixelMap) {
        float xTouch = event.getX();
        float yTouch = event.getY();
        if (event.getAction() == MotionEvent.ACTION_DOWN && isTouchWithinButton(xTouch, yTouch)) {
            touchAction(pikaGame, mainCharacter, pixelMap);
        }
    }

    public void onTouchEventB(MotionEvent event, PlayerCharacter mainCharacter) {
        float xTouch;
        float yTouch;
        int pointerCount = event.getPointerCount();
        if (pointerCount > 1) {
            xTouch = event.getX(1);
            yTouch = event.getY(1);
        }
        else {
            xTouch = event.getX();
            yTouch = event.getY();
        }
//        int pointerCount = event.getPointerCount();
//        int normalAction = event.getActionIndex();
//        int action = event.getActionMasked();
//        int expectedAction = MotionEvent.ACTION_POINTER_DOWN;
//        if (pointerCount > 1) {
//            System.out.println();
//        }
//        if (isTouchWithinButton(xTouch, yTouch)) {
//            System.out.println();
//
//        }

        if (event.getActionMasked() == MotionEvent.ACTION_POINTER_DOWN && isTouchWithinButton(xTouch, yTouch)) {
            mainCharacter.setRunning(true);
        }
        else if (event.getAction() == MotionEvent.ACTION_UP) {
            mainCharacter.setRunning(false);
        }
    }

    public void onTouchEventY(MotionEvent event, PikaGame pikaGame) {
        float xTouch = event.getX();
        float yTouch = event.getY();
        if (event.getAction() == MotionEvent.ACTION_DOWN && isTouchWithinButton(xTouch, yTouch)) {
            pikaGame.setGameState(PikaGameState.Menu);
            for (NPC npc : pikaGame.getNPCs()) {
                npc.getSpriteSheet().setPause(true);
            }
        }
    }

    private boolean isTouchWithinButton(float xTouch, float yTouch) {
        boolean touchWithinButton = false;
        if (circle.contains(Math.round(xTouch), Math.round(yTouch))) {
            touchWithinButton = true;
        }
        return touchWithinButton;
    }

    private void touchAction(PikaGame pikaGame, PlayerCharacter mainCharacter, PixelMap pixelMap) {
        PixelSquare facingSquare = pixelMap.getFacingSquare(mainCharacter);
        GameCharacter npc = facingSquare.getGameCharacter();
        if (npc != null && npc.getClass() == NPC.class && npc.getDistTravelled() == 0) {
            pikaGame.setGameState(PikaGameState.Talk);
        }
    }
}
