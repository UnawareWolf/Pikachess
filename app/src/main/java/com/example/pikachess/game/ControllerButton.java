package com.example.pikachess.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.example.pikachess.R;

public class ControllerButton {

    private Paint paint;
    private Circle circle;

    public ControllerButton(Context context, float centreX, float centreY, int radius, int colourID) {
        circle = new Circle(centreX, centreY, radius);
        paint = new Paint();
        paint.setColor(context.getResources().getColor(colourID));
        paint.setStrokeWidth(12);
        paint.setStyle(Paint.Style.FILL);

    }

    public void draw(Canvas canvas) {
        circle.draw(canvas, paint);
    }

    public void onTouchEvent(MotionEvent event, PikaGame pikaGame, PlayerCharacter mainCharacter, PixelMap pixelMap) {
        float xTouch = event.getX();
        float yTouch = event.getY();
        if (event.getAction() == MotionEvent.ACTION_DOWN && isTouchWithinButton(xTouch, yTouch)) {
            touchAction(pikaGame, mainCharacter, pixelMap);
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
