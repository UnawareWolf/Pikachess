package com.example.pikachess.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.example.pikachess.R;

import static java.lang.Math.abs;

public class JoystickButton {

    private static final int CONTROL_PANEL_HEIGHT = 480;
    private static final int PANEL_BORDER = 120;
    private Rect mRect;
    private Paint mPaint;
    private int canvasWidth;
    private int centreX;
    private int centreY;
    private boolean initialTouchWithinButton;

    public JoystickButton(Context context, int canvasHeight) {
        //this.canvasWidth = canvasWidth;
        mRect = new Rect();
        mPaint = new Paint();
        mPaint.setColor(context.getResources().getColor(R.color.chessBrown));
        mPaint.setStrokeWidth(12);
        mPaint.setStyle(Paint.Style.STROKE);
        int rectLeft = PANEL_BORDER;
        int rectTop = canvasHeight - CONTROL_PANEL_HEIGHT + PANEL_BORDER;
        int rectRight = rectLeft + CONTROL_PANEL_HEIGHT - 2*PANEL_BORDER;
        int rectBottom = canvasHeight - PANEL_BORDER;
        centreX = (rectRight + rectLeft) / 2;
        centreY = (rectBottom + rectTop) / 2;
        mRect.set(rectLeft, rectTop, rectRight, rectBottom);
    }

    public void draw(Canvas canvas) {
        canvas.drawRect(mRect, mPaint);
    }

    public Rect getRect() {
        return mRect;
    }

    public void onTouchEvent(MotionEvent event, GameCharacter mainCharacter) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            fingerMovingJoystick(event, mainCharacter);
        }
        if (event.getAction() == MotionEvent.ACTION_MOVE && initialTouchWithinButton) {
            fingerMovingJoystick(event, mainCharacter);
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            mainCharacter.setCharacterState(CharacterState.Stationary);
            initialTouchWithinButton = false;
        }
    }

    private void fingerMovingJoystick(MotionEvent event, GameCharacter mainCharacter) {
        float xTouch = event.getX();
        float yTouch = event.getY();
        if(mRect.contains(Math.round(event.getX()), Math.round(event.getY()))) {
            initialTouchWithinButton = true;
            int xDif = Math.round(xTouch) - centreX;
            int yDif = Math.round(yTouch) - centreY;
            if (abs(xDif) >= abs(yDif)) {
                if (xDif >= 0) {
                    mainCharacter.setCharacterState(CharacterState.MovingRight);
                }
                else {
                    mainCharacter.setCharacterState(CharacterState.MovingLeft);
                }
            }
            else {
                if (yDif >= 0 ) {
                    mainCharacter.setCharacterState(CharacterState.MovingUp);
                }
                else {
                    mainCharacter.setCharacterState(CharacterState.MovingDown);
                }
            }
        }
        else {
            mainCharacter.setCharacterState(CharacterState.Stationary);
        }
    }

}
